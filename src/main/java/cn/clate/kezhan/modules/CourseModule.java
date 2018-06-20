package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.domains.course.NoticeDomain;
import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.filters.RoleFilter;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.*;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.trans.Trans;

import java.util.ArrayList;
import java.util.List;

@At("/course")
public class CourseModule {
    @At("/getCourse")
    @Ok("json")
    public NutMap getCourse(@Param("cid") String cid, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(cid, "课程ID").require().num().lenMax(8);
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret = new NutMap();
        NutMap courseInfo = CourseDomain.getCourseByCourseId(Integer.parseInt(cid));
        if (!(boolean) courseInfo.get("ok?")) {
            return Ret.e(0, "不合法课程ID");
        }
        NutMap termCourseId = CourseDomain.getCourseTermByCourseId(Integer.parseInt(cid), Integer.parseInt(yid),
                Integer.parseInt(sid));
        List<List<CourseTimeSlot>> courseTimeSlots = new ArrayList<>();
        if (!(boolean) termCourseId.get("ok?")) {
            ret.addv("timeslot", courseTimeSlots);
            ret.addv("courseInfo", courseInfo);
            return Ret.s(ret);
        }
        CourseTerm courseTerm = (CourseTerm) termCourseId.get("courseTerm");
        NutMap subCourse = CourseDomain.getCourseSubByCourseTermId(courseTerm.getId(), Integer.parseInt(yid),
                Integer.parseInt(sid));
        if (!(boolean) subCourse.get("ok?")) {
            return Ret.e(0, "数据库爆炸了");
        }
        List<CourseSub> courseSubs = (List<CourseSub>) subCourse.get("courseSubs");
        for (CourseSub it : courseSubs) {
            courseTimeSlots.add((List<CourseTimeSlot>) CourseDomain.getTimeSlotsByCourseSubid(it.getId(), Integer.parseInt(yid), Integer.parseInt(sid)).get("time_slots"));
        }
        ret.addv("subCourse", courseSubs);
        ret.addv("courseInfo", courseInfo);
        ret.addv("timeSlots", courseTimeSlots);
        return Ret.s(ret);
    }

    @At("/getCourseComments")
    @Ok("json")
    public NutMap getCourseComments(@Param("cid") String cid, @Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(cid, "课程ID").require().num();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret = CourseDomain.getCourseCommentByCid(Integer.parseInt(cid), Integer.parseInt(pageNumber),
                Integer.parseInt(pageSize));
        return Ret.s(ret);
    }

    @At("/submitCourseComment")
    @Ok("json")
    @Filters(@By(type = RoleFilter.class, args = {"scene:" + RoleFilter.SCENE_MORE_THEN_OR_EQUAL_STUDENT_IN_SUB_COURSE}))
    public NutMap submitCourseComment(@Param("sbid") String sbid, @Param("uid") String uid, @Param("is_ano") String isAno, @Param("rating") String rating, @Param("content") String content, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(rating, "评分").require();
        validator.floatNum(rating, "评分不合法").require();
        if (Double.parseDouble(rating) < 0 || Double.parseDouble(rating) > 5) {
            return Ret.e(0, "评分不合法");
        }
        validator.now(isAno, "匿名属性").require();
        validator.bool(isAno, "匿名不合法").require();
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret = new NutMap();
        Trans.exec(() -> {
            NutMap courseSub = CourseDomain.getCourseSubBySubId(Integer.parseInt(sbid), Integer.parseInt(yid),
                    Integer.parseInt(sid));
            NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"),
                    Integer.parseInt(yid), Integer.parseInt(sid));
            if (CourseDomain.checkAlreadyComment(((Integer) courseTerm.get("course_id")), Integer.parseInt(uid))) {
                ret.addv("ok?", false);
                return;
            }
            CourseDomain.addCourseComment((Integer) courseTerm.get("course_id"), Integer.parseInt(uid), content, Double.parseDouble(rating), Boolean.parseBoolean(isAno));
            CourseDomain.updateCourseRating((Integer) courseTerm.get("course_id"), Double.parseDouble(rating));
        });
        if (!(boolean) ret.get("ok?")) {
            return Ret.e(0, "已经进行评分");
        }
        return Ret.s("ok");
    }

    @At("/getAllCourseByUserId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getAllCourseByUserId(@Param("uid") String id, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "用户id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap courseUserTakeList = CourseDomain.getSubCourseTermIdListByUserId(Integer.parseInt(id), Integer.parseInt(yid),
                Integer.parseInt(sid));
        if (courseUserTakeList == null)
            return Ret.e("用户没有课程安排");
        NutMap timeSlots = CourseDomain.getTimeSlotsByCourseSubIdList(courseUserTakeList, Integer.parseInt(yid),
                Integer.parseInt(sid));
        if (timeSlots == null)
            return Ret.e("课程开课时间未安排");
        List<CourseTimeSlot> courseTimeSlots = (List<CourseTimeSlot>) timeSlots.get("time_slots");
        ArrayList<NutMap> courseList = new ArrayList<>();
        for (CourseTimeSlot timeSlot : courseTimeSlots) {
            NutMap courseItem = new NutMap();
            NutMap courseSub = CourseDomain.getCourseSubBySubId(timeSlot.getSubCourseTermId(), Integer.parseInt(yid),
                    Integer.parseInt(sid));
            NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"),
                    Integer.parseInt(yid), Integer.parseInt(sid));
            NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
            courseItem.addv("course_name", course.get("name"));
            courseItem.addv("classroom", courseSub.get("classroom"));
            courseItem.addv("subCourseTermId", timeSlot.getSubCourseTermId());
            courseItem.addv("startDate", timeSlot.getStartDate());
            courseItem.addv("endDate", timeSlot.getEndDate());
            courseItem.addv("day", timeSlot.getDay());
            courseItem.addv("startTime", timeSlot.getStartTime());
            courseItem.addv("endTime", timeSlot.getEndTime());
            courseItem.addv("isOdd", timeSlot.getIsOdd());
            courseList.add(courseItem);
        }
        return Ret.s("courseList", courseList);
    }

    @At("/getCourseBySubId")
    @Ok("json")
    public NutMap getCourseBySubId(@Param("sbid") String id, @Param(df = "-1", value = "year") String yid,
                                   @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "班级课程id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap courseSub = CourseDomain.getCourseSubBySubId(Integer.parseInt(id), Integer.parseInt(yid),
                Integer.parseInt(sid));
        NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"),
                Integer.parseInt(yid), Integer.parseInt(sid));
        NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
        NutMap teacher = TeacherDomain.getTeacherById((int) course.get("teacher_id"));
        NutMap timeSlots = CourseDomain.getTimeSlotsByCourseSubid(Integer.parseInt(id), Integer.parseInt(yid),
                Integer.parseInt(sid));
        NutMap ret = new NutMap();
        ret.addv("course_name", course.get("name"));
        ret.addv("course_name_en", course.get("nameEn"));
        ret.addv("teacher", teacher.get("name"));
        ret.addv("type", course.get("type"));
        ret.addv("credit", course.get("credits"));
        ret.addv("classroom", courseSub.get("classroom"));
        ret.addv("count_thumbs_up", courseTerm.get("countThumbsUp"));
        ret.addv("count_learning", course.get("countLearning"));
        ret.attach(timeSlots);
        return Ret.s(ret);
    }

    @At("/getMembersBySubId")
    @Ok("json")
    public NutMap getMembersBySubId(@Param("sbid") String id, @Param(df = "-1", value = "year") String yid,
                                    @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "班级课程id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap studentList = CourseDomain.getStudentListByCourseSubId(Integer.parseInt(id), Integer.parseInt(yid),
                Integer.parseInt(sid));
        NutMap assistantList = CourseDomain.getAssistantByCourseSubId(Integer.parseInt(id), Integer.parseInt(yid),
                Integer.parseInt(sid));
        NutMap teacher = TeacherDomain.getTeacheInforByCourseSubId(Integer.parseInt(id), Integer.parseInt(yid),
                Integer.parseInt(sid));
        if (teacher == null)
            return Ret.e(0, "老师信息获取错误");
        List<CourseUserTake> courseUserTakes = (List<CourseUserTake>) studentList.get("student_list");
        ArrayList<Assistant> assistants = (ArrayList<Assistant>) assistantList.get("assistant_list");
        NutMap ret = new NutMap();
        NutMap teacherInfo = new NutMap();
        teacherInfo.addv("id", teacher.get("id"));
        teacherInfo.addv("name", teacher.get("username"));
        teacherInfo.addv("real_name", teacher.get("name"));
        teacherInfo.addv("avatar", teacher.get("avatar"));
        teacherInfo.addv("identity", 2);
        ret.addv("teachers", teacherInfo);
        ArrayList<NutMap> stuRet = new ArrayList<>();
        for (CourseUserTake courseUserTake : courseUserTakes) {
            NutMap item = new NutMap();
            NutMap user = UserInfoDomain.getUserById(courseUserTake.getUserId());
            item.addv("id", user.get("id"));
            item.addv("name", user.get("username"));
            item.addv("real_name", user.get("real_name"));
            item.addv("avatar", user.get("avatar"));
            item.addv("is_repre", courseUserTake.getIsRepre());
            item.addv("identity", 0);
            //identity 为0表示普通学生(通过is_repre判断是否为课代表) 1表示助教 2表示该课老师
            stuRet.add(item);
        }
//        Collections.sort(stuRet, (a,b)->{
//            return ((String) a.get("real_name")).compareTo((String) b.get("real_name"));
//        });
        ret.addv("students", stuRet);
        List<User> assistantUser = new ArrayList<>();
        for (Assistant it : assistants) {
            assistantUser.add(it.getAssistant());
        }
        ret.addv("assistants", assistantUser);
        return Ret.s("success", ret);
    }

    @At("/attendCourse")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap attendCourse(@Param("uid") String uid, @Param("sbid") String sbid, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(sbid, "课程ID").require();
        validator.num(sbid, "课程ID").require();
        if (!validator.check()) {
            return Ret.e(validator.getError());
        }
        NutMap ret = new NutMap();
        NutMap ret1 = CourseDomain.attendCourseByUidSbid(Integer.parseInt(uid), Integer.parseInt(sbid), Integer.parseInt(yid), Integer.parseInt(sid));
        if (!(boolean) ret1.get("ok?")) {
            return Ret.e((String) ret1.get("error"));
        }
        NoticeDomain.registerUserNoticeByUidSubCourseId(Integer.parseInt(uid), Integer.parseInt(sbid), Integer.parseInt(yid), Integer.parseInt(sid));
        NutMap courseSub = CourseDomain.getCourseSubBySubId(Integer.parseInt(sbid), Integer.parseInt(yid),
                Integer.parseInt(sid));
        NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"),
                Integer.parseInt(yid), Integer.parseInt(sid));
        cn.clate.kezhan.neo4j.domains.CourseDomain.addAttendCourseRs(Integer.parseInt(uid), (int) courseTerm.get("course_id"));
        return Ret.s("ok");
    }

    @At("/getRecommendCourseByCid")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getRecommendCourseByCourseId(@Param("cid") String cid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(cid, "课程ID").require();
        validator.num(cid, "课程ID不合法").require();
        NutMap ret = new NutMap();
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret1 = CourseDomain.getRecommendCourseByCourseId(Integer.parseInt(cid));
        if (!(boolean) ret1.get("ok?")) {
            return Ret.e(0, "崩了崩了");
        }
        ret.addv("recommends", ret1.get("recourse"));
        return Ret.s("success", ret);
    }


}

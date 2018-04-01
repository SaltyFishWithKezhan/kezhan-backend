package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.CourseTimeSlot;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

@At("/course")
public class CourseModule {
    @At("/getAllCourseByUserId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getAllCourseByUserId(@Param("uid") String id) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "用户id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap courseUserTakeList = CourseDomain.getSubCourseTermIdListByUserId(Integer.parseInt(id));
        if (courseUserTakeList == null)
            return Ret.e("用户没有课程安排");
        NutMap timeSlots = CourseDomain.getTimeSlotsByCourseSubidList(courseUserTakeList);
        if (timeSlots == null)
            return Ret.e("课程开课时间未安排");
        List<CourseTimeSlot> courseTimeSlots = (List<CourseTimeSlot>) timeSlots.get("time_slots");
        ArrayList<NutMap> courseList = new ArrayList<>();
        for (CourseTimeSlot timeSlot : courseTimeSlots) {
            NutMap courseItem = new NutMap();
            NutMap courseSub = CourseDomain.getCourseSubBySubId(timeSlot.getSubCourseTermId());
            NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"));
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
    public NutMap getCourseBySubId(@Param("sub_id") String id) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "班级课程id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap courseSub = CourseDomain.getCourseSubBySubId(Integer.parseInt(id));
        NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"));
        NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
        NutMap teacher = TeacherDomain.getTeacherById((int) course.get("teacher_id"));
        NutMap timeSlots = CourseDomain.getTimeSlotsByCourseSubid(Integer.parseInt(id));
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

}

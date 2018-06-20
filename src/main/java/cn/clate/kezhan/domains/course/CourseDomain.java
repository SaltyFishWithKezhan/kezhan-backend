package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.pojos.*;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.util.NutMap;
import org.nutz.trans.Trans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseDomain {

    public static NutMap attendCourseByUidSbid(int uid, int sbid, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            NutMap ret = new NutMap();
            Dao dao = DaoFactory.get();
            CourseSub courseSub = dao.fetch(CourseSub.class, sbid);
            if (courseSub == null) {
                ret.addv("ok?", false);
                ret.addv("error", "该课程暂未开课");
                return ret;
            }
            CourseUserTake courseUserTake = new CourseUserTake();
            courseUserTake.setUserId(uid).setSubCourseTermId(sbid);
            CourseUserTake already = dao.fetch(CourseUserTake.class, Cnd.where("user_id", "=", uid).and("sub_course_term_id", "=", sbid).and("status", "=", 0));
            if (already != null) {
                ret.addv("ok?", false);
                ret.addv("error", "已经加入该课程");
                return ret;
            }
            Trans.exec(() -> {
                synchronized (CourseDomain.class) {
                    courseSub.setNowSize(courseSub.getNowSize() + 1);
                    dao.update(courseSub);
                }

                dao.insert(courseUserTake);
                ret.addv("ok?", true);
            });
            return ret;

        } finally {
            TableName.clear();
        }
    }

    public static NutMap getCourseCommentByCid(int cid, int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<CourseComment> courseComments = dao.query(CourseComment.class, Cnd.where("course_id", "=", cid)
                .and("status", "=", 0).desc("time"), pager);
        for (CourseComment it : courseComments) {
            if (!it.isAnno()) {
                dao.fetchLinks(it, "poster");
                it.getPoster().removeCriticalInfo();
            } else {
                it.setPosterId(-1);
            }
        }
        pager.setRecordCount(dao.count(CourseComment.class, Cnd.where("course_id", "=", cid).and("status", "=", 0)));
        NutMap ret = new NutMap();
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        ret.addv("courseComments", courseComments);
        return ret;
    }

    public static NutMap getCourseSubBySubId(int subId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            CourseSub courseSub = dao.fetch(CourseSub.class, Cnd.where("id", "=", subId).and("status", "!=", -1));
            PojoSerializer pjsr = new PojoSerializer(courseSub);
            NutMap ret = pjsr.get();
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static NutMap getCourseTermByCourseTermId(int courseTermId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            CourseTerm couseTerm = dao.fetch(CourseTerm.class, Cnd.where("id", "=", courseTermId).and("status", "!=", -1));
            //该课程开设count个班级
            int count = dao.count(CourseSub.class, Cnd.where("course_term_id", "=", courseTermId).and("status", "!=", -1));
            PojoSerializer pjsr = new PojoSerializer(couseTerm);
            NutMap ret = pjsr.get();
            ret.addv("class_num", count);
            return ret;
        } finally {
            TableName.clear();
        }

    }

    public static NutMap getCourseByCourseId(int courseId) {
        Dao dao = DaoFactory.get();
        Course course = dao.fetch(Course.class, Cnd.where("id", "=", courseId).and("status", "!=", -1));
        if (course == null) {
            return new NutMap().addv("ok?", false);
        }
        dao.fetchLinks(course, "teacher");
        PojoSerializer pjsr = new PojoSerializer(course);
        NutMap ret = pjsr.get();
        ret.addv("ok?", true);
        return ret;
    }

    public static NutMap getCourseTermByCourseId(int cid, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            CourseTerm courseTerm = dao.fetch(CourseTerm.class, Cnd.where("course_id", "=", cid));
            NutMap ret = new NutMap();
            if (courseTerm != null) {
                ret.addv("courseTerm", courseTerm);
                ret.addv("ok?", true);
            } else {
                ret.addv("ok?", false);
            }
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static NutMap getCourseSubByCourseTermId(int ctid, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            List<CourseSub> courseSubs = dao.query(CourseSub.class, Cnd.where("course_term_id", "=", ctid));
            NutMap ret = new NutMap();
            if (courseSubs.size() != 0) {
                ret.addv("courseSubs", courseSubs);
                ret.addv("ok?", true);
            } else {
                ret.addv("ok?", false);
            }
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static NutMap getTimeSlotsByCourseSubid(int id, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, Cnd.where("sub_course_term_id", "=", id));
            ArrayList<CourseTimeSlot> timeSlots = new ArrayList<>(courseTimeSlots);
            NutMap ret = new NutMap();
            ret.addv("time_slots", timeSlots);
            return ret;
        } finally {
            TableName.clear();
        }

    }

    public static NutMap getCourseNumByTeacherId(int teacherId) {
        Dao dao = DaoFactory.get();
        int count = dao.count(Course.class, Cnd.where("teacher_id", "=", teacherId).and("status", "!=", -1));
        NutMap ret = new NutMap();
        ret.addv("teacher_course_num", count);
        return ret;
    }

    public static NutMap getCourseListByTeacherId(int teacherId) {
        Dao dao = DaoFactory.get();
        List<Course> courses = dao.query(Course.class, Cnd.where("teacher_id", "=", teacherId).and("status", "!=", -1));
        if (courses == null)
            return null;
        ArrayList<Course> courseArrayList = new ArrayList<>(courses);
        NutMap ret = new NutMap();
        ret.addv("teacher_courses", courseArrayList);
        return ret;
    }

    public static NutMap getCoursesByCourseNameFuzzy(String courseName, int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<Course> courses = dao.query(Course.class, Cnd.where("name", "LIKE", "%" + courseName + "%").and("status", "!=", -1), pager);
        if (courses == null)
            return null;
        pager.setRecordCount(dao.count(Course.class, Cnd.where("name", "LIKE", "%" + courseName + "%").and("status", "!=", -1)));
        NutMap ret = new NutMap();
        ret.addv("course_num", courses.size());
        List<NutMap> courseArrayList = new ArrayList<>();
        for (Course course : courses) {
            NutMap teacher = TeacherDomain.getTeacherById(course.getTeacherId());
            PojoSerializer pjsr = new PojoSerializer(course);
            NutMap courseNutmap = pjsr.get();
            courseNutmap.remove("desc");
            courseNutmap.addv("teacher_name", teacher.get("name"));
            courseArrayList.add(courseNutmap);
        }
        ret.addv("courses", courseArrayList);
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        return ret;
    }

    public static NutMap getCoursesByCourseName(String courseName) {
        Dao dao = DaoFactory.get();
        List<Course> courses = dao.query(Course.class, Cnd.where("name", "=", courseName).and("status", "!=", -1));
        if (courses == null || courses.size() == 0)
            return null;
        ArrayList<Course> courseArrayList = new ArrayList<>(courses);
        NutMap ret = new NutMap();
        ret.addv("courses", courseArrayList);
        return ret;
    }

    public static NutMap getSubCourseTermIdListByUserId(int id, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            List<CourseUserTake> courseUserTakeList = dao.query(CourseUserTake.class, Cnd.where("user_id", "=", id).and("status", "!=", -1));
            if (courseUserTakeList.size() == 0)
                return null;
            // System.out.println(courseUserTakeList.size());
            ArrayList<CourseUserTake> subCourseTermIds = new ArrayList<CourseUserTake>(courseUserTakeList);
            NutMap ret = new NutMap();
            ret.addv("courseUserTakeList", subCourseTermIds);
            return ret;
        } finally {
            TableName.clear();
        }

    }

    public static NutMap getTimeSlotsByCourseSubIdList(NutMap subIdList, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            List<CourseUserTake> courseUserTakes = (List<CourseUserTake>) subIdList.get("courseUserTakeList");
            Criteria cri = Cnd.cri();
            for (CourseUserTake cut : courseUserTakes) {
                cri.where().or("sub_course_term_id", "=", cut.getSubCourseTermId());
            }
            cri.where().and("status", "!=", -1);
            cri.getOrderBy().asc("start_time").asc("day");
            List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, cri, null);
            if (courseTimeSlots == null)
                return null;
            ArrayList<CourseTimeSlot> timeSlots = new ArrayList<>(courseTimeSlots);
            NutMap ret = new NutMap();
            ret.addv("time_slots", timeSlots);
            return ret;
        } finally {
            TableName.clear();
        }

    }

    public static NutMap getStudentListByCourseSubId(int courseSubId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            List<CourseUserTake> userTakes = dao.query(CourseUserTake.class, Cnd.where("sub_course_term_id", "=", courseSubId).and("status", "=", 0));
            ArrayList<CourseUserTake> courseUserTakeArrayList = new ArrayList<>(userTakes);

            NutMap ret = new NutMap();
            ret.addv("student_list", courseUserTakeArrayList);
            return ret;
        } finally {
            TableName.clear();
        }

    }

    public static NutMap getAssistantByCourseSubId(int courseSubId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            List<Assistant> assistants = dao.query(Assistant.class, Cnd.where("sub_course_term_id", "=", courseSubId).and("status", "=", 0));
            for (Assistant it : assistants) {
                dao.fetchLinks(it, "assistant");
                it.getAssistant().removeCriticalInfo();
            }
            ArrayList<Assistant> assistantsArrayList = new ArrayList<>(assistants);
            NutMap ret = new NutMap();
            ret.addv("assistant_list", assistantsArrayList);
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static NutMap getRecommendCourseByCourseId(int cid) {
        Dao dao = DaoFactory.get();
        NutMap ret = new NutMap();
        List<CourseRecommendMapping> recommendMappings = dao.query(CourseRecommendMapping.class, Cnd.where("course", "=", cid + 1)); // 原因为！！！！！！！！！！！！数据预处理失误！！！！！！！
        if (recommendMappings.size() == 0) {
            ret.addv("ok?", false);
            return ret;
        }
        List<DummyCourse> recommends = new ArrayList<>();
        for (CourseRecommendMapping it : recommendMappings) {
            CourseRecommendIndex index = dao.fetch(CourseRecommendIndex.class, it.getRecommendId());
            DummyCourse cr;
            switch (index.getType()) {
                case 1:
                    cr = dao.fetch(CourseMooc.class, index.getTypeId());
                    break;
                case 2:
                    cr = dao.fetch(CourseNetease.class, index.getTypeId());
                    break;
                case 3:
                    cr = dao.fetch(CourseImoocFree.class, index.getTypeId());
                    break;
                case 4:
                    cr = dao.fetch(Course.class, index.getTypeId());
                    break;
                default:
                    cr = null;
            }
            if (cr != null) {
                recommends.add(cr);
            }
        }
        ret.addv("ok?", true);
        ret.addv("recourse", recommends);
        return ret;
    }

    public static boolean checkAlreadyComment(int cid, int uid){
        Dao dao = DaoFactory.get();
        CourseComment courseComment = dao.fetch(CourseComment.class, Cnd.where("course_id", "=", cid).and("user_id", "=", uid));
        return courseComment != null;
    }

    public static void addCourseComment(int courseId, int uid, String content, Double rating, boolean isAno) {
        Dao dao = DaoFactory.get();
        CourseComment courseComment = new CourseComment().setCourseId(courseId).setAnno(isAno).setPosterId(uid).setContent(content).setRating(rating);
        dao.fastInsert(courseComment);
    }

    public static void updateCourseRating(int course_id, double v) {
        Dao dao = DaoFactory.get();
        synchronized (CourseDomain.class) {
            Course course = dao.fetch(Course.class, course_id);
            course.setCountEvaluate(course.getCountEvaluate() + 1);
            course.setTotal_rating(course.getTotal_rating() + v);
            dao.update(course);
        }
    }
}

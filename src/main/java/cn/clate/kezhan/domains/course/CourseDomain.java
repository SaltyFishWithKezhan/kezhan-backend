package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.pojos.*;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class CourseDomain {
    public static NutMap getCourseSubBySubId(int subId) {
        Dao dao = DaoFactory.get();
        CourseSub courseSub = dao.fetch(CourseSub.class, Cnd.where("id", "=", subId).and("status", "!=", -1));
        PojoSerializer pjsr = new PojoSerializer(courseSub);
        NutMap ret = pjsr.get();
        return ret;
    }

    public static NutMap getCourseTermByCourseTermId(int courseTermId) {
        Dao dao = DaoFactory.get();
        Couse2018 couseTerm = dao.fetch(Couse2018.class, Cnd.where("id", "=", courseTermId).and("status", "!=", -1));
        //该课程开设count个班级
        int count = dao.count(CourseSub.class, Cnd.where("course_term_id", "=", courseTermId).and("status", "!=", -1));
        PojoSerializer pjsr = new PojoSerializer(couseTerm);
        NutMap ret = pjsr.get();
        ret.addv("class_num", count);
        return ret;
    }

    public static NutMap getCourseByCourseId(int courseId) {
        Dao dao = DaoFactory.get();
        Course course = dao.fetch(Course.class, Cnd.where("id", "=", courseId).and("status", "!=", -1));
        PojoSerializer pjsr = new PojoSerializer(course);
        NutMap ret = pjsr.get();
        return ret;
    }

    public static NutMap getTimeSlotsByCourseSubid(int id) {
        Dao dao = DaoFactory.get();
        List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, Cnd.where("sub_course_term_id", "=", id));
        ArrayList<CourseTimeSlot> timeSlots = new ArrayList<>(courseTimeSlots);
        NutMap ret = new NutMap();
        ret.addv("time_slots", timeSlots);
        return ret;
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

    public static NutMap getCoursesByCourseNameFuzzy(String courseName ,int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<Course> courses = dao.query(Course.class, Cnd.where("name", "LIKE", "%" + courseName + "%").and("status", "!=", -1),pager);
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
            courseNutmap.addv("teacher_name",teacher.get("name"));
            courseArrayList.add(courseNutmap);
        }
        ret.addv("courses", courseArrayList);
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        System.out.println();ret.toString();
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

    public static NutMap getSubCourseTermIdListByUserId(int id) {
        Dao dao = DaoFactory.get();
        List<CourseUserTake> courseUserTakeList = dao.query(CourseUserTake.class, Cnd.where("user_id", "=", id).and("status", "!=", -1));
        if (courseUserTakeList == null)
            return null;
        // System.out.println(courseUserTakeList.size());
        ArrayList<CourseUserTake> subCourseTermIds = new ArrayList<CourseUserTake>(courseUserTakeList);
        NutMap ret = new NutMap();
        ret.addv("courseUserTakeList", subCourseTermIds);
        return ret;
    }

    public static NutMap getTimeSlotsByCourseSubIdList(NutMap subIdList) {
        Dao dao = DaoFactory.get();
        List<CourseUserTake> courseUserTakes = (List<CourseUserTake>) subIdList.get("courseUserTakeList");
        Criteria cri = Cnd.cri();
        for (CourseUserTake cut : courseUserTakes) {
            cri.where().or("sub_course_term_id", "=", cut.getSubCourseTermId());
        }
        cri.where().and("status", "!=", -1);
        cri.getOrderBy().asc("start_time").asc("day");
        List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, cri, null);
        if(courseTimeSlots == null)
            return null;
        ArrayList<CourseTimeSlot> timeSlots = new ArrayList<>(courseTimeSlots);
        NutMap ret = new NutMap();
        ret.addv("time_slots", timeSlots);
        return ret;
    }

    public static NutMap getStudentListByCourseSubId(int courseSubId) {
        Dao dao = DaoFactory.get();
        List<CourseUserTake> userTakes = dao.query(CourseUserTake.class, Cnd.where("sub_course_term_id", "=", courseSubId).and("status", "!=", -1));
        if (userTakes == null)
            return null;
        ArrayList<CourseUserTake> courseUserTakeArrayList = new ArrayList<>(userTakes);
        NutMap ret = new NutMap();
        ret.addv("student_list", courseUserTakeArrayList);
        return ret;
    }

    public static NutMap getRepresentiveByCourseSubId(int courseSubId) {
        Dao dao = DaoFactory.get();
        List<Representative> representatives = dao.query(Representative.class, Cnd.where("sub_course_term_id", "=", courseSubId).and("status", "!=", -1));
        if (representatives == null)
            return null;
        ArrayList<Representative> representativeArrayList = new ArrayList<>(representatives);
        NutMap ret = new NutMap();
        ret.addv("representative_list", representativeArrayList);
        return ret;
    }
}

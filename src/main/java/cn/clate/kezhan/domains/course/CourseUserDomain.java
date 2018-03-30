package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.*;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2018/3/25.
 */
public class CourseUserDomain {
    public static ArrayList<Integer> getSubCourseTermIdByUser(int id) {
        Dao dao = DaoFactory.get();
        List<CourseUserTake> courseUserTakeList = dao.query(CourseUserTake.class, Cnd.where("user_id", "=", id).and("status","!=",-1));
        // System.out.println(courseUserTakeList.size());
        ArrayList<Integer> subCourseTermIds = new ArrayList<Integer>();
        for (CourseUserTake courseUserTake : courseUserTakeList) {
            subCourseTermIds.add(courseUserTake.getSubCourseTermId());
        }
        return subCourseTermIds;
    }

//    public static ArrayList<NutMap> getTimeMessageByCourseid(int id) {
//        Dao dao = DaoFactory.get();
//        List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, Cnd.where("sub_course_term_id", "=", id));
//        ArrayList<NutMap> contentList = new ArrayList<NutMap>();
//        for (CourseTimeSlot courseTimeSlot : courseTimeSlots) {
//            NutMap testItem = new NutMap();
//            testItem.addv("day", courseTimeSlot.getDay());
//            testItem.addv("startDate", courseTimeSlot.getStartDate());
//            testItem.addv("endDate", courseTimeSlot.getEndDate());
//            testItem.addv("startTime", courseTimeSlot.getStartTime());
//            testItem.addv("endTime", courseTimeSlot.getEndTime());
//            contentList.add(testItem);
//        }
//        return contentList;
//    }

    public static NutMap getTimeSlotsByCourseSubid(int id) {
        Dao dao = DaoFactory.get();
        List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, Cnd.where("sub_course_term_id", "=", id));
        ArrayList<CourseTimeSlot> timeSlots = new ArrayList<>(courseTimeSlots);
        NutMap ret = new NutMap();
        ret.addv("time_slots",timeSlots);
        return ret;
    }

    public static String getCourseCodeByCourseTermId(int id) {
        Dao dao = DaoFactory.get();
        List<Couse2018> couse2018s = dao.query(Couse2018.class, Cnd.where("course_id", "=", id));
        return couse2018s.get(0).getCourseCode();
    }

    public static String getCouseNameByCourseCode(String code) {
        Dao dao = DaoFactory.get();
        List<Course> courses = dao.query(Course.class, Cnd.where("course_code", "=", code));
        return courses.get(0).getName();
    }

    public static NutMap getCouseMessageByCourseCode(String code) {
        Dao dao = DaoFactory.get();
        NutMap res = new NutMap();
        List<Course> courses = dao.query(Course.class, Cnd.where("course_code", "=", code));
        //System.out.println(courses.get(0));
        res.addv("name", courses.get(0).getName());
        res.addv("name_en", courses.get(0).getNameEn());
        res.addv("desc", courses.get(0).getDesc());
        res.addv("maxSize", courses.get(0).getMaxSize());
        res.addv("image", courses.get(0).getCoverImg());
        String teacherName = dao.query(Teacher.class, Cnd.where("id", "=", courses.get(0).getTeacherId())).get(0).getName();
        res.addv("teacher", teacherName);

        return res;
    }

    public static List<CourseTimeSlot> getTimeSlotsByCourseIdList(ArrayList<Integer> courseIdList) {
        Dao dao = DaoFactory.get();
        if(null == courseIdList ||  courseIdList.size()==0)
        {
            return null;
        }
        Criteria cri = Cnd.cri();
        for (Integer i:courseIdList) {
            cri.where().or("sub_course_term_id", "=",i);
        }
        cri.where().and("status","!=",-1);
        cri.getOrderBy().asc("start_time").asc("day");
        List<CourseTimeSlot> courseTimeSlots = dao.query(CourseTimeSlot.class, cri, null);
        return courseTimeSlots;
    }

    public static void main(String[] args) {
        ArrayList<Integer> tests = getSubCourseTermIdByUser(8);
        System.out.println(tests.size());
        List<CourseTimeSlot> courseTimeSlots = getTimeSlotsByCourseIdList(tests);
        for (CourseTimeSlot x : courseTimeSlots) {
            System.out.println("aaa");
            System.out.println(x.getStartTime());
        }
        System.out.println(getCouseMessageByCourseCode("SOFT0031131130").get("name"));
    }

    public static NutMap getCourseBySubId(int id){
        Dao dao = DaoFactory.get();
        CourseSub courseSub = dao.fetch(CourseSub.class,Cnd.where("id","=",id).and("status","!=",-1));
        Couse2018 couseTerm = dao.fetch(Couse2018.class,Cnd.where("id","=",courseSub.getCourseTermId()).and("status","!=",-1));
        Course course = dao.fetch(Course.class,Cnd.where("id","=",couseTerm.getCourseId()).and("status","!=",-1));
        Teacher teacher = dao.fetch(Teacher.class,Cnd.where("id","=",course.getTeacherId()).and("is_active","!=",-1));
        NutMap ret =new NutMap();
        ret.addv("course_name",course.getName());
        ret.addv("course_name_en",course.getNameEn());
        ret.addv("teacher",teacher.getName());
        ret.addv("type",course.getType());
        ret.addv("credit",course.getCredits());
        ret.addv("classroom",courseSub.getClassroom());
        ret.addv("count_thumbs_up",couseTerm.getCountThumbsUp());
        ret.addv("count_learning",couseTerm.getCountLearning());
        ret.attach(getTimeSlotsByCourseSubid(id));
        return ret;
    }
}

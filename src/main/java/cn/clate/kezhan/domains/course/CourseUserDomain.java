package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.*;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

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

    }
}

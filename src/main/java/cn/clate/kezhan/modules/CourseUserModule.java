package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseUserDomain;
import cn.clate.kezhan.domains.test.TestDomain;
import cn.clate.kezhan.pojos.CourseTimeSlot;
import cn.clate.kezhan.utils.Ret;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2018/3/27.
 */
@At("/course")
public class CourseUserModule {
    @At("/select")
    @Ok("json")
    public NutMap test(@Param("userid") String id) {
        NutMap res = new NutMap();
        ArrayList<Integer> courseids = CourseUserDomain.getSubCourseTermIdByUser(Integer.parseInt(id));
        ArrayList<NutMap> courseList = new ArrayList<NutMap>();
        for (Integer courseid : courseids) {
            NutMap courseItem = new NutMap();
            courseItem.addv("timeMessage", CourseUserDomain.getTimeMessageByCourseid(courseid));
            try {
                String coursecode = CourseUserDomain.getCourseCodeByCourseid(courseid);
                NutMap courseBasicMessage = CourseUserDomain.getCouseMessageByCourseCode(coursecode);
                courseItem.addv("basicMessage", courseBasicMessage);
            } catch (Exception e) {
                return Ret.e("学生有些课程id不匹配");
            }
            courseList.add(courseItem);
        }
        res.addv("res", courseList);
        return Ret.s(res);
    }

    @At("/getAllCourseByUserId")
    @Ok("json")
    public NutMap getAllCourseByUserId(@Param("id") String id) {
        NutMap res = new NutMap();
        ArrayList<Integer> courseids = CourseUserDomain.getSubCourseTermIdByUser(Integer.parseInt(id));
        ArrayList<NutMap> courseList = new ArrayList<NutMap>();
        List<CourseTimeSlot> courseTimeSlots = CourseUserDomain.getTimeSlotsByCourseIdList(courseids);
        for (CourseTimeSlot timeSlot : courseTimeSlots) {
            NutMap courseItem = new NutMap();
            courseItem.addv("subCourseTermId", timeSlot.getSubCourseTermId());
            String coursecode = CourseUserDomain.getCourseCodeByCourseid(timeSlot.getSubCourseTermId());
            courseItem.addv("course_code", coursecode);
            String courseName = CourseUserDomain.getCouseNameByCourseCode(coursecode);
            courseItem.addv("course_name", courseName);
            courseItem.addv("startDate", timeSlot.getStartDate());
            courseItem.addv("endDate", timeSlot.getEndDate());
            courseItem.addv("day", timeSlot.getDay());
            courseItem.addv("startTime", timeSlot.getStartTime());
            courseItem.addv("endTime", timeSlot.getEndTime());
            courseItem.addv("isOdd", timeSlot.getIsOdd());
            courseList.add(courseItem);
        }
        res.addv("list", courseList);
        return Ret.s(res);
    }

}

package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseUserDomain;
import cn.clate.kezhan.domains.test.TestDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.CourseTimeSlot;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2018/3/27.
 */
@At("/course")
public class CourseUserModule {
    @At("/getAllCourseByUserId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getAllCourseByUserId(@Param("uid") String id) {
        NutMap res = new NutMap();
        ArrayList<Integer> courseids = CourseUserDomain.getSubCourseTermIdByUser(Integer.parseInt(id));
        ArrayList<NutMap> courseList = new ArrayList<NutMap>();
        List<CourseTimeSlot> courseTimeSlots = CourseUserDomain.getTimeSlotsByCourseIdList(courseids);
        for (CourseTimeSlot timeSlot : courseTimeSlots) {
            NutMap courseItem = new NutMap();
            courseItem.addv("subCourseTermId", timeSlot.getSubCourseTermId());
            String coursecode = CourseUserDomain.getCourseCodeByCourseTermId(timeSlot.getSubCourseTermId());
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

    @At("/getCourseBySubId")
    @Ok("json")
    public NutMap getCourseBySubId(@Param("sub_id")String id){
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "班级课程id").require();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CourseUserDomain.getCourseBySubId(Integer.parseInt(id));
        return Ret.s(ret);
    }

}

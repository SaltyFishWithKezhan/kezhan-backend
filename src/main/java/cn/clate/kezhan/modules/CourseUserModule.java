package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseUserDomain;
import cn.clate.kezhan.domains.test.TestDomain;
import cn.clate.kezhan.utils.Ret;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.ArrayList;

/**
 * Created by 蛟川小盆友 on 2018/3/27.
 */
@At("/course")
public class CourseUserModule {
    @At("/select")
    @Ok("json")
    public NutMap test(@Param("userid") String id){
        NutMap res=new NutMap();
        ArrayList<Integer>courseids= CourseUserDomain.getSubCourseTermIdByUser(Integer.parseInt(id));
        ArrayList<NutMap> courseList = new ArrayList<NutMap>();
        for(Integer courseid:courseids){
            NutMap courseItem=new NutMap();
            courseItem.addv("timeMessage",CourseUserDomain.getTimeMessageByCourseid(courseid));
            try{
                String coursecode=CourseUserDomain.getCourseCodeByCourseid(courseid);
                NutMap courseBasicMessage=CourseUserDomain.getCouseMessageByCourseCode(coursecode);
                courseItem.addv("basicMessage",courseBasicMessage);
            }catch (Exception e){
                return Ret.e("学生有些课程id不匹配");
            }
            courseList.add(courseItem);
        }
        res.addv("res",courseList);
        return Ret.s(res);
    }
}

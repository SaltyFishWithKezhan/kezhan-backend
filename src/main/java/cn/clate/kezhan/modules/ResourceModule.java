package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.ResourceDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.Resource;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

@At("/resource")
public class ResourceModule {
    @At("/getCourseResource")
    @Ok("json")
    public NutMap getCourseResourceByCourseId(@Param("course_id") String courseId){
        SimpleValidator validator = new SimpleValidator();
        validator.now(courseId, "课程ID").require();
        validator.num(courseId, "课程ID参数不合法");
        if(!validator.check()){
            return Ret.e(0, validator.getError());
        }
        List<Resource> resourceList = ResourceDomain.getAllCourseResource(Integer.parseInt(courseId));
        return Ret.s("success", new ArrayList<>(resourceList));
    }

    @At("/getCourseTermResource")
    @Ok("json")
    public NutMap getCourseTermResourceByCourseTermId(@Param("course_term_id") String courseTermId){
        SimpleValidator validator = new SimpleValidator();
        validator.now(courseTermId, "学期课程ID").require();
        validator.num(courseTermId, "参数不合法");
        if(!validator.check()){
            return Ret.e(0, validator.getError());
        }
        List<Resource> resourceList = ResourceDomain.getAllCourseResource(Integer.parseInt(courseTermId));
        return Ret.s("success", new ArrayList<>(resourceList));
    }

    @At("/uploadCourseResource")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap uploadCourseResource(@Param("uid") String posterId){
        return null;
    }

    @At("/uploadCourseTermResource")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap uploadCourseTermResource(@Param("uid") String posterId){
        return null;
    }
}

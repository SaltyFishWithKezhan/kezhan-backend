package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@At("/teacher")
public class TeacherModule {

    @At("/getById")
    @Ok("json")
    public NutMap getById(@Param("teacher_id") String teacherId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(teacherId, "教师id").require().num();
        if (!validator.check()) {
            return Ret.e(60, validator.getError());
        }
        NutMap ret =TeacherDomain.getTeacherById(Integer.parseInt(teacherId));
        if (ret == null) {
            return Ret.e(61, "教师id不存在");
        }
        NutMap retCourse = CourseDomain.getCourseListByTeacherId(Integer.parseInt(teacherId));
        if(retCourse!=null)
            ret.attach(retCourse);
        return Ret.s(ret);
    }
}

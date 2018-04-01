package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.List;

@At("/search")
public class SearchModule {

    @At("/getByString")
    @Ok("json")
    public NutMap getById(@Param("string") String str) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(str, "搜索内容").require();
        if (!validator.check()) {
            return Ret.e(70, validator.getError());
        }
        NutMap retTeachers = TeacherDomain.getTeachersByName(str);
        NutMap retCourses = CourseDomain.getCoursesByCourseNameFuzzy(str);
        if (retTeachers == null && retCourses == null)
            return Ret.e(71, "查询无结果");
        NutMap ret = new NutMap();
        if (retTeachers != null) {
            ret.attach(retTeachers);
        } else {
            ret.addv("teachers", -1);
        }
        if (retCourses != null) {
            ret.attach(retCourses);
        } else {
            ret.addv("courses", -1);
        }
        return Ret.s(ret);
    }
}

package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.domains.search.SearchDomain;
import cn.clate.kezhan.domains.teacher.TeacherDomain;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.ArrayList;
import java.util.List;

@At("/search")
public class SearchModule {

    @At("/getByString")
    @Ok("json")
    public NutMap getByString(@Param("string") String str, @Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(str, "搜索内容").require();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(70, validator.getError());
        }
        NutMap retTeachers = TeacherDomain.getTeachersByName(str);
        if (retTeachers == null) {
            retTeachers.addv("teachers", -1);
        }
        NutMap retCourses = CourseDomain.getCoursesByCourseNameFuzzy(str, Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        if (retCourses != null) {
            retTeachers.attach(retCourses);
        } else {
            retTeachers.addv("courses", -1);
        }
        if (retTeachers == null && retCourses == null)
            return Ret.e(71, "查询无结果");
        return Ret.s(retTeachers);
    }

    @At("/getStringsByName")
    @Ok("json")
    public NutMap getStringsByName(@Param("name") String name) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(name, "搜索字段").require();
        if (!validator.check()) {
            return Ret.e(70, validator.getError());
        }
        NutMap ret = SearchDomain.getSearchByName(name);
        ArrayList<String> strings = (ArrayList<String>) ret.get("search_result");
        return Ret.s("search_result", strings);
    }
}

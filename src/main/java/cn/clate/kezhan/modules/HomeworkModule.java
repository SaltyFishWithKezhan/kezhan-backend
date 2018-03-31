package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.HomeworkDomain;
import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.ArrayList;
import java.util.List;

@At("/homework")
public class HomeworkModule {

    @At("/getBySubCourse")
    @Ok("json")
    public NutMap getAllHomeworkBySubCourse(@Param("sub_course_id") String subCourseId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require();
        validator.num(subCourseId, "班级ID格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        List<Homework> homeworkList = HomeworkDomain.getHomeworkListBySubCourseId(Integer.parseInt(subCourseId));
        if (homeworkList == null) {
            return Ret.e(4, "作业数据错误");
        }
        return Ret.s("success", new ArrayList<>(homeworkList));
    }

    @At("/getByHomeworkId")
    @Ok("json")
    public NutMap getHomeworkById(@Param("homework_id") String homeworkId){
        SimpleValidator validator = new SimpleValidator();
        validator.now(homeworkId, "作业ID").require();
        validator.num(homeworkId, "作业ID格式不合法");
        if(!validator.check()){
            return Ret.e(0, validator.getError());
        }
        Homework homework = HomeworkDomain.getHomeworkByHomeworkId(Integer.parseInt(homeworkId));
        if (homework == null){
            return Ret.e(4, "作业ID不存在");
        }
        homework.getPoster().removeCriticalInfo();
        PojoSerializer pojoSerializer = new PojoSerializer(homework);
        NutMap ret = pojoSerializer.allowField("id, title, description, deadline, updateTime, viewerCount, status, subCourse, poster").get();
        return Ret.s("success", ret);
    }
}

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

@At("/homework")
public class HomeworkModule {

    @At("/getBySubCourse")
    @Ok("json")
    public NutMap getHomeworkBySubCourse(@Param("sub_course_id") String subCourseId, @Param("page_number") String pageNumber, @Param("page_size") String pageSize,
                                         @Param("year") String yid, @Param("semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require().num();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap homeworkListRet = HomeworkDomain.getHomeworkListBySubCourseId(Integer.parseInt(subCourseId), Integer.parseInt(pageNumber), Integer.parseInt(pageSize),
                yid == null ? -1 : Integer.parseInt(yid), sid == null ? -1 : Integer.parseInt(sid));
        if (homeworkListRet == null) {
            return Ret.e(4, "作业数据错误");
        }
        return Ret.s("success", homeworkListRet);
    }

    @At("/getByHomeworkId")
    @Ok("json")
    public NutMap getHomeworkById(@Param("homework_id") String homeworkId, @Param("year") String yid, @Param("semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(homeworkId, "作业ID").require();
        validator.num(homeworkId, "作业ID格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        Homework homework = HomeworkDomain.getHomeworkByHomeworkId(Integer.parseInt(homeworkId), yid == null ? -1 : Integer.parseInt(yid), sid == null ? -1 : Integer.parseInt(sid));
        if (homework == null) {
            return Ret.e(4, "作业ID不存在");
        }
        homework.getPoster().removeCriticalInfo();
        PojoSerializer pojoSerializer = new PojoSerializer(homework);
        NutMap ret = pojoSerializer.allowField("id, title, description, deadline, updateTime, viewerCount, status, subCourse, poster").get();
        return Ret.s("success", ret);
    }
}

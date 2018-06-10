package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.HomeworkDomain;
import cn.clate.kezhan.domains.course.MomentDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.trans.Trans;

@At("/homework")
public class HomeworkModule {

    @At("/getBySubCourse")
    @Ok("json")
    public NutMap getHomeworkBySubCourse(@Param("sub_course_id") String subCourseId, @Param("page_number") String pageNumber, @Param("page_size") String pageSize,
                                         @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require().num();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap homeworkListRet = HomeworkDomain.getHomeworkListBySubCourseId(Integer.parseInt(subCourseId), Integer.parseInt(pageNumber), Integer.parseInt(pageSize),
                Integer.parseInt(yid), Integer.parseInt(sid));
        if (homeworkListRet == null) {
            return Ret.e(4, "作业数据错误");
        }
        return Ret.s("success", homeworkListRet);
    }

    @At("/getByHomeworkId")
    @Ok("json")
    public NutMap getHomeworkById(@Param("homework_id") String homeworkId, @Param(df = "-1", value = "year") String yid,
                                  @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(homeworkId, "作业ID").require();
        validator.num(homeworkId, "作业ID格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        Homework homework = HomeworkDomain.getHomeworkByHomeworkId(Integer.parseInt(homeworkId), Integer.parseInt(yid), Integer.parseInt(sid));
        if (homework == null) {
            return Ret.e(4, "作业ID不存在");
        }
        homework.getPoster().removeCriticalInfo();
        PojoSerializer pojoSerializer = new PojoSerializer(homework);
        NutMap ret = pojoSerializer.allowField("id, title, description, deadline, updateTime, viewerCount, status, subCourse, poster").get();
        return Ret.s("success", ret);
    }

    @At("/addHomework")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap addHomework(@Param("uid") String uid, @Param("title") String title, @Param("desc") String desc, @Param("ddl") String ddl, @Param("sub_course_id") String scid, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(scid, "课程ID").require();
        validator.num(scid, "课程ID格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret = new NutMap();
        Trans.exec(() -> {
            NutMap ret1 = HomeworkDomain.addHomework(Integer.parseInt(uid), title, desc, ddl, Integer.parseInt(scid), Integer.parseInt(yid), Integer.parseInt(sid));
            if (!(boolean) ret1.get("ok?")) {
                ret.addv("ok?", false);
                return;
            }
            Homework rethm = (Homework) ret1.get("hm");
            MomentDomain.addOrUpdateMoment(1, rethm.getId(), rethm.getUpdateTime(), Integer.parseInt(scid), Integer.parseInt(yid), Integer.parseInt(sid));
            ret.addv("ok?", true);
        });
        if(!(boolean) ret.get("ok?")){
            return Ret.e(0, "吃屎吧你");
        }
        else{
            return Ret.s("ok");
        }
    }
}

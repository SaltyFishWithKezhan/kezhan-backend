package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.NoticeDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.Notice;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

@At("/notice")
public class NoticeModule {
    @At("/getBySubCourse")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getBySubCourse(@Param("uid") String userId, @Param("sub_course_id") String subCourseId) {
        SimpleValidator validator = new SimpleValidator();
        validator.num(subCourseId, "请求格式不合法");
        if(!validator.check()){
            return Ret.e(0, validator.getError());
        }
        List<Notice> noticeList = NoticeDomain.getNoticeByUidSubCourseId(Integer.parseInt(userId), Integer.parseInt(subCourseId));
        return Ret.s("success", new ArrayList<>(noticeList));
    }

    @At("/getByNoticeId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getByNoticeId(@Param("uid")String userId, @Param("notice_id") String noticeId){
        SimpleValidator validator = new SimpleValidator();
        validator.num(noticeId, "请求格式不合法");
        if(!validator.check()){
            return Ret.e(0, validator.getError());
        }
        Notice notice = NoticeDomain.getNoticeDetailByUidNoticeId(Integer.parseInt(userId), Integer.parseInt(noticeId));
        if(notice == null){
            return Ret.e(4, "公告ID不存在");
        }
        notice.getPoster().removeCriticalInfo();
        PojoSerializer pojoSerializer = new PojoSerializer(notice);
        NutMap ret = pojoSerializer.allowField("id, title, description, poster, updateTime, viewerCount").get();
        return Ret.s("success", ret);
    }
}

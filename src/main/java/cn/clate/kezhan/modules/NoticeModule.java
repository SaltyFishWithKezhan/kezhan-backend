package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.MomentDomain;
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
    public NutMap getBySubCourse(@Param("uid") String userId, @Param("sub_course_id") String subCourseId,@Param("page_number") String pageNumber,@Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require();
        validator.num(subCourseId, "请求格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap noticeListRet = NoticeDomain.getNoticeByUidSubCourseId(Integer.parseInt(userId), Integer.parseInt(subCourseId),Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
        return Ret.s("success", noticeListRet);
    }

    @At("/getByNoticeId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getByNoticeId(@Param("uid") String userId, @Param("notice_id") String noticeId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(noticeId, "公告ID").require();
        validator.num(noticeId, "请求格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        Notice notice = NoticeDomain.getNoticeDetailByUidNoticeId(Integer.parseInt(userId), Integer.parseInt(noticeId));
        if (notice == null) {
            return Ret.e(4, "公告ID不存在");
        }
        notice.getPoster().removeCriticalInfo();
        PojoSerializer pojoSerializer = new PojoSerializer(notice);
        NutMap ret = pojoSerializer.allowField("id, title, description, poster, updateTime, viewerCount").get();
        return Ret.s("success", ret);
    }

    @At("/submitNotice")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap submitNotice(@Param("uid") String posterId, @Param("title") String title,
                               @Param("desc") String description, @Param("sub_course_id") String subCourseId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(title, "公告主题").require().lenMin(1);
        validator.now(description, "公告内容").require().lenMin(1);
        validator.now(subCourseId, "班级ID").require();
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret = NoticeDomain.addNotice(Integer.parseInt(posterId), title, description, Integer.parseInt(subCourseId));
        if (!ret.containsKey("success")) {
            return ret;
        }
        Notice notice = (Notice) ret.get("success");
        NoticeDomain.setNoticeUnreadAfterNewNotice(notice.getId(), notice.getSubCourseId());
        MomentDomain.addOrUpdateMoment(2, notice.getId(), notice.getUpdateTime(), notice.getSubCourseId());
        return Ret.s("success");
    }
}

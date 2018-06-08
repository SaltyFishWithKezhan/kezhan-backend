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
import org.nutz.trans.Trans;

import java.util.List;

@At("/notice")
public class NoticeModule {
    @At("/setAllRead")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap setAllNoticeRead(@Param("uid") String userId, @Param("sub_course_id") String subCourseId,
                                   @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid){
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require();
        validator.num(subCourseId, "请求格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap noticeListRet = NoticeDomain.getNoticeByUidSubCourseId(Integer.parseInt(userId), Integer.parseInt(subCourseId),
                1, Integer.MAX_VALUE, Integer.parseInt(yid), Integer.parseInt(sid));
        List<Notice> noticeList = (List<Notice>) noticeListRet.get("content");
        for(Notice it : noticeList){
            NoticeDomain.setRead(Integer.parseInt(userId), it.getId(),
                    Integer.parseInt(yid), Integer.parseInt(sid));
        }
        return Ret.s("ok");
    }

    @At("/getUnreadBySubCourse")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getUnreadCount(@Param("uid") String userId, @Param("sub_course_id") String subCourseId,
                                 @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require();
        validator.num(subCourseId, "请求格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap noticeListRet = NoticeDomain.getNoticeByUidSubCourseId(Integer.parseInt(userId), Integer.parseInt(subCourseId),
                1, Integer.MAX_VALUE, Integer.parseInt(yid), Integer.parseInt(sid));
        List<Notice> noticeList = (List<Notice>) noticeListRet.get("content");
        NutMap ret = new NutMap();
        int count = 0;
        for (Notice it : noticeList) {
            if (!it.isRead()) {
                count++;
            }
        }
        ret.addv("counts", count);
        return Ret.s(ret);
    }

    @At("/getBySubCourse")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getBySubCourse(@Param("uid") String userId, @Param("sub_course_id") String subCourseId,
                                 @Param("page_number") String pageNumber, @Param("page_size") String pageSize,
                                 @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require();
        validator.num(subCourseId, "请求格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap noticeListRet = NoticeDomain.getNoticeByUidSubCourseId(Integer.parseInt(userId), Integer.parseInt(subCourseId), Integer.parseInt(pageNumber), Integer.parseInt(pageSize), Integer.parseInt(yid), Integer.parseInt(sid));
        return Ret.s("success", noticeListRet);
    }

    @At("/getByNoticeId")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getByNoticeId(@Param("uid") String userId, @Param("notice_id") String noticeId,
                                @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(noticeId, "公告ID").require();
        validator.num(noticeId, "请求格式不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        Notice notice = NoticeDomain.getNoticeDetailByUidNoticeId(Integer.parseInt(userId), Integer.parseInt(noticeId),
                Integer.parseInt(yid), Integer.parseInt(sid));
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
                               @Param("desc") String description, @Param("sub_course_id") String subCourseId,
                               @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(title, "公告主题").require().lenMin(1);
        validator.now(description, "公告内容").require().lenMin(1);
        validator.now(subCourseId, "班级ID").require();
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        Trans.exec(() -> {
                    NutMap ret = NoticeDomain.addNotice(Integer.parseInt(posterId), title, description, Integer.parseInt(subCourseId),
                            Integer.parseInt(yid), Integer.parseInt(sid));
                    Notice notice = (Notice) ret.get("success");
                    MomentDomain.addOrUpdateMoment(2, notice.getId(), notice.getUpdateTime(), notice.getSubCourseId(),
                            Integer.parseInt(yid), Integer.parseInt(sid));
                }
        );
        return Ret.s("success");
    }

    @At("/updateNotice")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap updateNotice(@Param("uid") String posterId, @Param("notice_id") String noticeId, @Param("title") String title,
                               @Param("desc") String description, @Param(df = "-1", value = "year") String yid,
                               @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(title, "公告主题").require().lenMin(1);
        validator.now(description, "公告内容").require().lenMin(1);
        validator.now(noticeId, "公告ID").require();
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        //具体判断trans执行成功没有只能在lamda中抛runtimeexception
        Trans.exec(() -> {
            NutMap ret = NoticeDomain.updateNotice(Integer.parseInt(noticeId), title, description, Integer.parseInt(yid), Integer.parseInt(sid));
            MomentDomain.addOrUpdateMoment(2, Integer.parseInt(noticeId), ((Notice) ret.get("success")).getUpdateTime(),
                    -1, Integer.parseInt(yid), Integer.parseInt(sid));
        });
        return Ret.s("success");
    }

    @At("/deleteNotice")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap deleteNotice(@Param("notice_id") String nid, @Param(df = "-1", value = "year") String yid,
                               @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(nid, "公告ID").require();
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap rettmp1 = NoticeDomain.deleteNotice(Integer.parseInt(nid), Integer.parseInt(yid), Integer.parseInt(sid));
        if (!(boolean) rettmp1.get("ok?")) {
            return Ret.e(0, "公告不存在");
        }
        MomentDomain.deleteMomentAfterDeleteNotice(Integer.parseInt(nid), Integer.parseInt(yid), Integer.parseInt(sid));
        return Ret.s("ok");
    }
}

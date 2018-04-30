package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.CourseUserTake;
import cn.clate.kezhan.pojos.Notice;
import cn.clate.kezhan.pojos.NoticeReadStatus;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.NutMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeDomain {
    public static boolean getReadStatus(int uId, int noticeId) {
        Dao dao = DaoFactory.get();
        NoticeReadStatus noticeReadStatus = dao.fetch(NoticeReadStatus.class, Cnd.where("userId", "=", uId)
                .and("noticeId", "=", noticeId));
        if (noticeReadStatus == null) {
            return false;
        }
        return (noticeReadStatus.getStatus() == 1);
    }

    public static void setRead(int uId, int noticeId) {
        Dao dao = DaoFactory.get();
        NoticeReadStatus noticeReadStatus = new NoticeReadStatus()
                .setNoticeId(noticeId).setStatus(1).setUserId(uId);
        dao.update(noticeReadStatus);
    }

    public static void setNoticeUnreadAfterNoticeUpdate(int noticeId) {
        Dao dao = DaoFactory.get();
        dao.update(NoticeReadStatus.class, Chain.makeSpecial("status", "0"),
                Cnd.where("noticeId", "=", noticeId));
    }

    public static void setNoticeUnreadAfterNewNotice(int noticeId, int subCourseId) {
        Dao dao = DaoFactory.get();
        List<CourseUserTake> mappings = dao.query(CourseUserTake.class,
                Cnd.where("subCourseTermId", "=", subCourseId));
        for (CourseUserTake it : mappings) {
            NoticeReadStatus noticeReadStatus = new NoticeReadStatus();
            noticeReadStatus.setUserId(it.getUserId())
                    .setStatus(0).setNoticeId(noticeId);
            dao.fastInsert(noticeReadStatus);
        }
    }

    public static NutMap getNoticeByUidSubCourseId(int uId, int subCourseId, int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<Notice> noticeList = dao.query(Notice.class, Cnd.where("subCourseId", "=", subCourseId)
                .desc("updateTime"), pager);
        if (noticeList == null) {
            return null;
        }
        pager.setRecordCount(dao.count(Notice.class, Cnd.where("subCourseId", "=", subCourseId)));
        for (Notice it : noticeList) {
            it.setRead(getReadStatus(uId, it.getId()));
            it.setUpdateTime(Tools.dateTimeTodate(it.getUpdateTime()));
        }
        NutMap ret = new NutMap();
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        ret.addv("content", new ArrayList<>(noticeList));
        return ret;
    }

    public static Notice getNoticeDetailByUidNoticeId(int uId, int noticeId) {
        Dao dao = DaoFactory.get();
        Notice notice = dao.fetch(Notice.class, noticeId);
        if (notice == null) {
            return null;
        }
        notice.setUpdateTime(Tools.dateTimeTodate(notice.getUpdateTime()));
        dao.fetchLinks(notice, "poster");
        //TODO 原子性不保证
        setRead(uId, noticeId);
        dao.update(Notice.class, Chain.makeSpecial("viewerCount", "+1"), Cnd.where("id", "=", noticeId));
        return notice;
    }

    public static NutMap addNotice(int posterId, String title, String description, int subCourseId) {
        Dao dao = DaoFactory.get();
        User poster = dao.fetch(User.class, posterId);
        if (poster == null) {
            return Ret.e(0, "用户参数不存在");
        }
        Notice notice = new Notice();
        notice.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .setTitle(title).setPosterId(posterId).setDescription(description).setSubCourseId(subCourseId);
        dao.insert(notice);
        if (notice.getId() == 0) {
            return Ret.e(0, "公告发布失败：公告插入失败");
        }
        NutMap ret = new NutMap();
        ret.addv("success", notice);
        return ret;
    }

    public static NutMap updateNotice(int noticeId, String title, String description) {
        Dao dao = DaoFactory.get();
        Notice notice = dao.fetch(Notice.class, noticeId);
        if (notice == null) {
            return Ret.e(0, "公告ID不合法");
        }
        notice.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .setTitle(title).setDescription(description);
        dao.update(notice);
        NutMap ret = new NutMap();
        ret.addv("success", notice);
        return ret;
    }
}

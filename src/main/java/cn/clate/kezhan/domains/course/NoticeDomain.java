package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Notice;
import cn.clate.kezhan.pojos.NoticeReadStatus;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

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

    public static List<Notice> getNoticeByUidSubCourseId(int uId, int subCourseId) {
        Dao dao = DaoFactory.get();
        List<Notice> noticeList = dao.query(Notice.class, Cnd.where("subCourseId", "=", subCourseId)
                .desc("updateTime"));
        if (noticeList == null) {
            return null;
        }
        for (Notice it : noticeList) {
            it.setRead(getReadStatus(uId, it.getId()));
            it.setUpdateTime(Tools.getDateStr(Tools.convertDatetimeToDate(it.getUpdateTime())));
        }
        return noticeList;
    }

    public static Notice getNoticeDetailByUidNoticeId(int uId, int noticeId) {
        Dao dao = DaoFactory.get();
        Notice notice = dao.fetch(Notice.class, noticeId);
        if(notice == null){
            return null;
        }
        notice.setUpdateTime(Tools.getDateStr(Tools.convertDatetimeToDate(notice.getUpdateTime())));
        dao.fetchLinks(notice, "poster");
        //TODO 原子性不保证
        setRead(uId, noticeId);
        dao.update(Notice.class, Chain.makeSpecial("viewerCount", "+1"), Cnd.where("id","=", noticeId));
        return notice;
    }

    public static void main(String[] args) {
        System.out.println(getNoticeByUidSubCourseId(8, 1).get(1).isRead());
    }
}

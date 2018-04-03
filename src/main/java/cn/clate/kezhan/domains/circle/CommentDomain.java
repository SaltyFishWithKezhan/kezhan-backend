package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Comment;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDomain {

    public static NutMap getCommentByCircleId(int circleId, int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<Comment> comments = dao.query(Comment.class,
                Cnd.where("topic_id", "=", circleId).and("status", "!=", -1).desc("time"), pager);
        pager.setRecordCount(dao.count(Comment.class, Cnd.where("topic_id", "=", circleId).and("status", "!=", -1)));
        if (comments == null)//TODO:  circles.size()==0
            return null;
        ArrayList<Comment> commentArrayList = new ArrayList<>(comments);
        NutMap ret = new NutMap();
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        ret.addv("comments", commentArrayList);
        return ret;
    }

    public static NutMap submitComment(int circleId, int fromId, int toId, String content) {
        Dao dao = DaoFactory.get();
        if (toId != -1) {
            User toUser = dao.fetch(User.class, Cnd.where("id", "=", toId));
            if (toUser == null) {
                return Ret.e(20, "回复用户不存在");
            }
        }
        Comment comment = new Comment();
        comment.setTopicId(circleId).setContent(content).setStatus(0).
                setFromUid(fromId).setToUid(toId).setTime(new Date());
        dao.insert(comment);
        return Ret.s("success");
    }
}

package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Comment;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDomain {

    public static NutMap getCommentByTopic(int topicType, int topicId) {
        Dao dao = DaoFactory.get();
        List<Comment> comments = dao.query(Comment.class,
                Cnd.where("topic_type", "=", topicType).
                        and("topic_id", "=", topicId).getOrderBy().asc("time"));
        if (comments == null)//TODO:  circles.size()==0
            return null;
        ArrayList<Comment> commentArrayList = new ArrayList<>(comments);
        return Ret.s("success", commentArrayList);
    }

    public static NutMap submitComment(int topicType, int topicId, int fromId, String fromName, int toId, String toName, String content) {
        Dao dao = DaoFactory.get();
        Comment comment = new Comment();
        comment.setTopicId(topicId).setTopicType(topicType).setContent(content).setFromUid(fromId).setFromUname(fromName).setToUid(toId).setToUname(toName).setTime(new Date());
        dao.insert(comment);
        return Ret.s("success");
    }
}

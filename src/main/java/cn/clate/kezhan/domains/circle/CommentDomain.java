package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Comment;
import cn.clate.kezhan.pojos.User;
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

    public static NutMap submitComment(int topicType, int topicId, int fromId, int toId, String content) {
        Dao dao = DaoFactory.get();
        if(toId!=-1){
            User toUser = dao.fetch(User.class, Cnd.where("id", "=", toId));
            if (toUser == null) {
                return Ret.e(20,"回复用户不存在");
            }
        }
        Comment comment = new Comment();
        comment.setTopicId(topicId).setTopicType(topicType).setContent(content).
                setFromUid(fromId).setToUid(toId).setTime(new Date());
        dao.insert(comment);
        return Ret.s("success");
    }
}

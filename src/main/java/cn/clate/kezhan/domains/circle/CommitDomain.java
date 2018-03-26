package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Circle;
import cn.clate.kezhan.pojos.Commit;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommitDomain {

    public static NutMap getCommitByTopic(int topicType, int topicId) {
        Dao dao = DaoFactory.get();
        List<Commit> commits = dao.query(Commit.class,
                Cnd.where("topic_type", "=", topicType).
                        and("topic_id", "=", topicId).getOrderBy().asc("time"));
        if (commits == null)//TODO:  circles.size()==0
            return null;
        ArrayList<Commit> commitArrayList = new ArrayList<>(commits);
        return Ret.s("success", commitArrayList);
    }

    public static NutMap submitCommit(int topicType, int topicId, int fromId, String fromName, int toId, String toName, String content) {
        Dao dao = DaoFactory.get();
        Commit commit = new Commit();
        commit.setTopicId(topicId).setTopicType(topicType).setContent(content).setFromUid(fromId).setFromUname(fromName).setToUid(toId).setToUname(toName).setTime(new Date());
        dao.insert(commit);
        return Ret.s("success");
    }
}

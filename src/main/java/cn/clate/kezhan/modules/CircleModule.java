package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.circle.CircleDomain;
import cn.clate.kezhan.domains.circle.CommitDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

@At("/circle")
public class CircleModule {

    @At("/getByPage")
    @Ok("json")
    public NutMap getCirclesByPage(@Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CircleDomain.getCirclesByPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        if (ret == null) {
            return Ret.e(2, "分页错误");
        }
        return Ret.s(ret);
    }

    @At("/getAll")
    @Ok("json")
    public NutMap getAllCircles() {
        NutMap ret = CircleDomain.getAllCircles();
        if (ret == null) {
            return Ret.e(2, "圈子数据错误");
        }
        return Ret.s(ret);
    }

    @At("/commits")
    @Ok("json")
    public NutMap getCommitByTopic(@Param("topic_type") String topicType, @Param("topic_id") String topicId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(topicType, "主题类型").require().max(10);
        validator.now(topicId, "主题id").require().min(1);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CommitDomain.getCommitByTopic(Integer.parseInt(topicType), Integer.parseInt(topicId));
        if (ret == null) {
            return Ret.e(2, "评论数据错误");
        }
        return Ret.s(ret);
    }

    @At("/submitCommit")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap submitCommit(@Param("topic_type") String topicType, @Param("topic_id") String topicId,
                               @Param("from_id") String fromId, @Param("from_name") String fromName,
                               @Param("to_id") String toId, @Param("to_name") String toName,
                               @Param("content") String content) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(topicType, "主题类型").require();
        validator.now(topicId, "主题id").require();
        validator.now(content, "评论内容").require();
        validator.now(fromId, "评论者id").require();
        validator.now(fromName, "评论者").require();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CommitDomain.submitCommit(Integer.parseInt(topicType), Integer.parseInt(topicId), Integer.parseInt(fromId), fromName, Integer.parseInt(toId), toName, content);
        return ret;
    }


}
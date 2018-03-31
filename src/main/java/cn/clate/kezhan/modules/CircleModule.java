package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.circle.CircleDomain;
import cn.clate.kezhan.domains.circle.CircleTypeDomain;
import cn.clate.kezhan.domains.circle.CommentDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

@At("/circle")
public class CircleModule {

    @At("/getByTypePage")
    @Ok("json")
    public NutMap getCirclesByPage(@Param("type_id") String type, @Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(type, "圈子类型").require();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CircleDomain.getCirclesByTypePage(Integer.parseInt(type), Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        if (ret == null) {
            return Ret.e(2, "分页错误");
        }
        return Ret.s(ret);
    }

    @At("/comments")
    @Ok("json")
    public NutMap getCommentByTopic(@Param("topic_type") String topicType, @Param("topic_id") String topicId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(topicType, "主题类型").require().max(10);
        validator.now(topicId, "主题id").require().min(1);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CommentDomain.getCommentByTopic(Integer.parseInt(topicType), Integer.parseInt(topicId));
        if (ret == null) {
            return Ret.e(2, "评论数据错误");
        }
        return Ret.s(ret);
    }

    @At("/submitComment")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap submitComment(@Param("topic_type") String topicType, @Param("topic_id") String topicId,
                                @Param("to_id") String toId, @Param("uid") String fromId, @Param("content") String content) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(topicType, "主题类型").require().lenMin(1);
        validator.now(topicId, "主题id").require();
        validator.now(content, "评论内容").require();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CommentDomain.submitComment(Integer.parseInt(topicType), Integer.parseInt(topicId), Integer.parseInt(fromId), Integer.parseInt(toId), content);
        return ret;
    }

    @At("/allCircleTypes")
    @Ok("json")
    public NutMap getAllCircleTypes() {
        NutMap ret = CircleTypeDomain.getAllTypes();
        return ret;
    }

    @At("/getById")
    @Ok("json")
    public NutMap getCirclebyId(@Param("id")String id) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id,"圈子id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap circleRet = CircleDomain.getCircleDetails(Integer.parseInt(id));
        NutMap typeRet = CircleTypeDomain.getTypeById((int)circleRet.get("type"));
        circleRet.addv("type_name",typeRet.get("type_name"));
        return Ret.s(circleRet);
    }


}

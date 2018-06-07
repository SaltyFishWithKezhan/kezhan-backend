package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.circle.CircleDomain;
import cn.clate.kezhan.domains.circle.CircleTypeDomain;
import cn.clate.kezhan.domains.circle.CommentDomain;
import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.Circle;
import cn.clate.kezhan.pojos.CircleComment;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

@At("/circle")
public class CircleModule {

    @At("/getByTypePage")
    @Ok("json")
    public NutMap getCirclesByTypePage(@Param("type_id") String type, @Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
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
        List<NutMap> cirlesNutMap = new ArrayList<>();
        List<Circle> content = (List<Circle>)ret.get("content");
        for (Circle circle:content){
            PojoSerializer pjsr = new PojoSerializer(circle);
            NutMap circleNutmap = pjsr.get();
            NutMap typeRet = CircleTypeDomain.getTypeById(circle.getType());
            NutMap authorRet = UserInfoDomain.getUserById(circle.getAuthor());
            circleNutmap.remove("content");
            circleNutmap.addv("type_name",typeRet.get("type_name"));
            circleNutmap.addv("author_name",authorRet.get("username"));
            circleNutmap.addv("avatar",authorRet.get("avatar"));
            cirlesNutMap.add(circleNutmap);
        }
        NutMap retComplete= new NutMap();
        retComplete.addv("now_page", ret.get("now_page"));
        retComplete.addv("per_page_size", ret.get("per_page_size"));
        retComplete.addv("page_count", ret.get("page_count"));
        retComplete.addv("content", cirlesNutMap);
        return Ret.s(retComplete);
    }

    @At("/comments")
    @Ok("json")
    public NutMap getCommentByTopic(@Param("topic_id") String topicId,@Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(topicId, "评论所属的圈子id").require().min(1);
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CommentDomain.getCommentByCircleId(Integer.parseInt(topicId),Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
        if (ret == null) {
            return Ret.e("评论数据错误");
        }
        List<CircleComment> circleComments = (List<CircleComment>)ret.get("circleComments");
        ArrayList<NutMap> commentsNutMap = new ArrayList<>();
        ret.remove("circleComments");
        for (CircleComment circleComment : circleComments){
            PojoSerializer pjsr = new PojoSerializer(circleComment);
            NutMap commentNutmap = pjsr.get();
            NutMap fromUser = UserInfoDomain.getUserById(circleComment.getFromUid());
            commentNutmap.addv("from_name",fromUser.get("username"));
            commentNutmap.addv("from_avatar",fromUser.get("avatar"));
            NutMap toUser = UserInfoDomain.getUserById(circleComment.getToUid());
            if(toUser!=null){
                commentNutmap.addv("to_name",toUser.get("username"));
            }
            commentsNutMap.add(commentNutmap);
        }
        ret.addv("circleComments",commentsNutMap);
        return Ret.s(ret);
    }

    @At("/submitComment")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap submitComment(@Param("topic_id") String topicId,
                                @Param("to_id") String toId, @Param("uid") String fromId, @Param("content") String content) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(topicId, "主题id").require().num();
        validator.now(content, "评论内容").require();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CommentDomain.submitComment( Integer.parseInt(topicId), Integer.parseInt(fromId), Integer.parseInt(toId), content);
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
        NutMap authorRet = UserInfoDomain.getUserById((int)circleRet.get("author"));
        circleRet.addv("type_name",typeRet.get("type_name"));
        circleRet.addv("author_name",authorRet.get("username"));
        circleRet.addv("avatar",authorRet.get("avatar"));
        return Ret.s(circleRet);
    }


}

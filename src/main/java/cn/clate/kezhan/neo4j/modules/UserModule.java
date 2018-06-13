package cn.clate.kezhan.neo4j.modules;

import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.neo4j.domains.UserDomain;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@At("/neo4j")
public class UserModule {

    @At("/getRecommendByUserId")
    @Ok("json")
    public NutMap getRecommendByUserId(@Param("uid") String uid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(uid, "用户id").require().min(0);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        ArrayList<NutMap> userList = new ArrayList<>();
        List<Integer> uidList = UserDomain.findThreeDegreeRsByUserId(Integer.parseInt(uid));
        Collections.shuffle(uidList);
        int limit = uidList.size() < 10 ? 10 : uidList.size();
        for (int i = 0; i < limit; i++) {
            User user = UserDomain.getUserById(uidList.get(i));
            PojoSerializer pjsr = new PojoSerializer(user);
            NutMap userNutMap = pjsr.allowField("id, username, phone,realName").get();
            userList.add(userNutMap);
        }
        return Ret.s("success", userList);
    }

}

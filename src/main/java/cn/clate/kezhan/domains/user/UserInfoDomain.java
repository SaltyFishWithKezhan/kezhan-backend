package cn.clate.kezhan.domains.user;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

public class UserInfoDomain {
    private static NutMap mdfUserInfo(User user){
        Dao dao = DaoFactory.get();
        User userFound = dao.fetch(User.class, Cnd.where("id","=",user.getId()));
        if(user == null){
            return Ret.e(2, "用户名已存在");
        }
        //TODO: 修改啥呀  --zn
        userFound.setPhone(user.getPhone());
        userFound.setRealName(user.getRealName());
        userFound.setGender(user.getGender());
        dao.update(userFound);
        return Ret.s("success");
    }
}

package cn.clate.kezhan.domains.user;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.io.File;

public class UserInfoDomain {
    public static NutMap mdfUserInfo(User user){
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

    public static NutMap getUserById(int id){
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, Cnd.where("id","=",id));
        if (null == user) {
            return Ret.e(2, "用户id不存在");
        }
        String phone = user.getPhone().substring(0,3)+"****"+user.getPhone().substring(7,11);
        user.setPhone(phone);
        PojoSerializer pjsr = new PojoSerializer(user);
        NutMap ret = pjsr.allowField("id, username, avatar,type,gender,phone,birthday,college,stuId,realName,signature").get();
        if(user.getAvatar()!=null){
            ret.setv("avatar", Conf.get("user.avatarUrl")+user.getAvatar());
        }
        return ret;
    }

    public static User getUserByName(String name){
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, Cnd.where("username","=",name));
        if(user == null){
            return null;
        }
        return user;
    }

    public static NutMap upLoadAvatar(int id,String path){
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, Cnd.where("id","=",id));
        if(user == null){
            return Ret.e(2, "用户id不存在");
        }
        if(null!=user.getAvatar()){
            File f = new File("/www/server/jetty/webapps"+user.getAvatar());
            f.delete();
        }
        user.setAvatar(path);
        dao.update(user);
        return Ret.s("success");
    }


}

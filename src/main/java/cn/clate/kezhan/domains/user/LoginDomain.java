package cn.clate.kezhan.domains.user;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.lang.util.NutMap;

public class LoginDomain {
    private static String refreshAccessToken(User user){
        String newToken = Tools.getRandStr((int)Conf.get("user.tokenLength",Integer.class));
        user.updateLoginStatus();
        user.setAccessToken(newToken);
        DaoFactory.get().update(user);
        return user.getAccessToken();
    }


    public static NutMap fire(String username, String password){
        Dao dao = DaoFactory.get();
        String pwEncrypted = Tools.passwordEncrypt(password);
        SqlExpressionGroup e1 = Cnd.exps("username", "=", username).and("password","=",pwEncrypted);
        SqlExpressionGroup e2 = Cnd.exps("phone", "=", username).and("password", "=", pwEncrypted);
        SqlExpressionGroup e3 = Cnd.exps("stu_id", "=", username).and("password", "=", pwEncrypted);
        User user = dao.fetch(User.class, Cnd.where(e1).or(e2).or(e3));
        if(user == null){
            return null;
        }
        String token = refreshAccessToken(user);
        NutMap ret = new NutMap();
        ret.addv("id", user.getId());
        ret.addv("type", user.getType());
        ret.addv("username", user.getUsername());
        ret.addv("gender", user.getGender());
        ret.addv("birthday", user.getBirthday());
        ret.addv("avatar", user.getAvatar());
        ret.addv("college", user.getCollege());
        ret.addv("class_name", user.getClassName());
        ret.addv("signature", user.getSignature());
        ret.addv("last_login", user.getLastLogin());
        ret.addv("last_login_ip", user.getLastLoginIp());
        ret.addv("now_login", user.getNowLogin());
        ret.addv("now_login_ip", user.getNowLoginIp());
        ret.addv("token",token);
        return ret;
    }
}

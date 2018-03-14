package cn.clate.kezhan.domains.user;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.lang.util.NutMap;

public class RegisterDomain {

    public static NutMap fire(String username, String password, String phone) {
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, username);
        if (user != null) {
            return Ret.e(450,"用户名已存在");
        }
        user = new User();
        String pwEncrypted = Tools.passwordEncrypt(password);
        user.setUsername(username);
        user.setPassword(pwEncrypted);
        user.setPhone(phone);
        dao.insert(user);
        return Ret.s("success");

    }
}

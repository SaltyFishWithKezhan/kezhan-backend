package cn.clate.kezhan.domains.user;

import cn.clate.kezhan.pojos.Phone;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.lang.util.NutMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RegisterDomain {

    private static String initAccessToken(User user) {
        String newToken = Tools.getRandStr((int) Conf.get("user.tokenLength", Integer.class));
        user.initLoginStatus();
        user.setAccessToken(newToken);
        DaoFactory.get().update(user);
        return user.getAccessToken();
    }

    public static NutMap fire(String username, String password, String phone, String code) {
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, username);
        if (user != null) {
            return Ret.e(10, "用户名已存在");
        }
        NutMap phoneRet = PhoneDomain.phoneVerifition(phone, code);
        System.out.println();
        if (phoneRet.get("code").equals(200)) {
            user = new User();
            String pwEncrypted = Tools.passwordEncrypt(password);
            user.setUsername(username);
            user.setPassword(pwEncrypted);
            user.setPhone(phone);
            String token = initAccessToken(user);
            user.setAccessToken(token);
            dao.insert(user);
            return Ret.s("success");
        }
        return phoneRet;
    }

}

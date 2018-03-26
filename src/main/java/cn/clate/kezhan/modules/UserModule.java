package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.user.LoginDomain;
import cn.clate.kezhan.domains.user.RegisterDomain;
import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.Random;

@At("/user")
public class UserModule {
    @At("/login")
    @Ok("json")
    public NutMap login(@Param("username") String username, @Param("password") String password) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(username, "用户名").require().chsDash().lenMin(5).lenMax(16);
        validator.now(password, "密码").require().lenMin(8).lenMax(40);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = LoginDomain.fire(username, password);
        if (ret == null) {
            return Ret.e(2, "用户名或密码错误");
        }
        return Ret.s(ret);
    }


    @At("/register")
    @Ok("json")
    public NutMap register(@Param("username") String username, @Param("password") String password, @Param("phone") String phone ,@Param("verification_code") String code) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(username, "用户名").require().chsDash().lenMin(5).lenMax(16);
        validator.now(password, "密码").require().lenMin(8).lenMax(40);
        validator.now(phone, "手机号码").require().mobilePhone();
        validator.now(code, "验证码").require().length(6);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = RegisterDomain.fire(username, password, phone,code);
        if (ret == null) {
            return Ret.e(2, "注册失败");
        }
        return ret;
    }

    @At("/phone")
    @Ok("json")
    public NutMap phoneVerifition(@Param("phone") String phoneNumber) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(phoneNumber, "手机号码").require().mobilePhone();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        RegisterDomain.sendMsg(phoneNumber);
        return Ret.s("phone verifition success");
    }

    @At("/resetPhone")
    @Ok("json")
    public NutMap resetPhone(@Param("uid") String uid,@Param("phone") String phoneNumber){
        SimpleValidator validator = new SimpleValidator();
        int id = Integer.parseInt(uid);
        validator.now(uid,"用户id").require().min(0);
        validator.now(phoneNumber, "手机号码").require().mobilePhone();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = RegisterDomain.resetValidationSendMsg(id,phoneNumber);
        return ret;
    }

    @At("/resetPwd")
    @Ok("json")
    public NutMap resetPwd(@Param("uid") String uid, @Param("phone") String phone, @Param("code") String code) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(phone, "手机号码").require().mobilePhone();
        validator.now(code, "验证码").require().length(6);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        return Ret.s("reset pwd success");
    }

    @At("/getById")
    @Ok("json")
    @Filters(@By(type=UserAuthenication.class))
    public NutMap getUserById(@Param("uid") String uid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(uid, "用户id").require().min(0);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = UserInfoDomain.getUserById(Integer.parseInt(uid));
        return ret;
    }

}

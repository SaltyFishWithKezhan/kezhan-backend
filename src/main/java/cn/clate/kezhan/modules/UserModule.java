package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.user.LoginDomain;
import cn.clate.kezhan.domains.user.RegisterDomain;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@At("/user")
public class UserModule {
    @At("/login")
    @Ok("json")
    public NutMap login(@Param("username") String username, @Param("password") String password){
        SimpleValidator validator = new SimpleValidator();
        validator.now(username, "用户名").require().chsDash().lenMin(5).lenMax(16);
        validator.now(password, "密码").require().lenMin(8).lenMax(40);
        if(!validator.check()){
            return Ret.e(1, validator.getError());
        }
        NutMap ret = LoginDomain.fire(username, password);
        if(ret == null){
            return Ret.e(2, "用户名或密码错误");
        }
        return Ret.s(ret);
    }


    @At("/register")
    @Ok("json")
    public NutMap register(@Param("username") String username, @Param("password") String password,@Param("phone")String phone){
        SimpleValidator validator = new SimpleValidator();
        validator.now(username, "用户名").require().chsDash().lenMin(5).lenMax(16);
        validator.now(password, "密码").require().lenMin(8).lenMax(40);
        validator.now(phone, "手机号码").require().length(11);//我手机号用mobilephone验证通不过
        if(!validator.check()){
            return Ret.e(1, validator.getError());
        }
        NutMap ret = RegisterDomain.fire(username,password,phone);
        if(ret != null){
            return Ret.e(2, "注册失败");
        }
        return Ret.s(ret.addv("用户名",username));
    }
}

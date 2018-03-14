package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.user.LoginDomain;
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


}

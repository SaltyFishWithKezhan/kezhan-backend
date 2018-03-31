package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.user.LoginDomain;
import cn.clate.kezhan.domains.user.PhoneDomain;
import cn.clate.kezhan.domains.user.RegisterDomain;
import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.mvc.upload.UploadAdaptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;


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
    public NutMap register(@Param("username") String username, @Param("password") String password, @Param("phone") String phone, @Param("verification_code") String code) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(username, "用户名").require().chsDash().lenMin(5).lenMax(16);
        validator.now(password, "密码").require().lenMin(8).lenMax(40);
        validator.now(phone, "手机号码").require().mobilePhone();
        validator.now(code, "验证码").require().length(6);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = RegisterDomain.fire(username, password, phone, code);
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
        return PhoneDomain.sendMsg(phoneNumber);
    }

    @At("/resetSendMsg")
    @Ok("json")
    public NutMap resetPhone(@Param("uid") String uid, @Param("phone") String phoneNumber) {
        SimpleValidator validator = new SimpleValidator();
        int id = Integer.parseInt(uid);
        validator.now(uid, "用户id").require().min(0);
        validator.now(phoneNumber, "手机号码").require().mobilePhone();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = PhoneDomain.resetValidationSendMsg(id, phoneNumber);
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
        PhoneDomain.resetValidation(uid, phone, code);
        return Ret.s("reset pwd success");
    }

    @At("/getById")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getUserById(@Param("uid") String uid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(uid, "用户id").require().min(0);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = UserInfoDomain.getUserById(Integer.parseInt(uid));
        if (ret == null) {
            return Ret.e(2, "用户id不存在");
        }
        return Ret.s(ret);
    }

    @At("/getByName")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getUserByName(@Param("uname") String name) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(name, "用户姓名").require();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        User user = UserInfoDomain.getUserByName(name);
        if (null == user) {
            return Ret.e(2, "用户id不存在");
        }
        PojoSerializer pjsr = new PojoSerializer(user);
        NutMap ret = pjsr.allowField("id, username, avatar,type,gender,birthday,college,stuId,realName,signature").get();
        return Ret.s(ret);
    }

    @At("/uploadAvatar")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    public NutMap uploadAvatar(@Param("uid") String id, @Param("avatar") File f) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(id, "用户id").require().min(0);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        if (f != null && !f.exists()) {
            return Ret.e(44, "文件不存在");
        }
        String imageName = f.getName();
        //获得文件后缀名称
        String surfix = imageName.substring(imageName.lastIndexOf("."), imageName.length());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //localhost本地根目录是kezhan_backend
        //remote根目录是www/server/jetty
        String path = "/www/server/jetty/webapps";
        String avatar = "/static/avatar/" + uuid + surfix;
        //www.clate.cn:8080/static/
        //http://95.163.194.157:8080/kezhan/userImg/*.jpg
        File target = new File(path + avatar);
        Files.copy(f, target);
        NutMap ret = UserInfoDomain.upLoadAvatar(Integer.parseInt(id), avatar);
        return Ret.s(ret);
    }

}

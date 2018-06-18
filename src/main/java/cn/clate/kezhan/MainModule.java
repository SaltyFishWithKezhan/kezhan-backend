package cn.clate.kezhan;

import cn.clate.kezhan.filters.RoleFilter;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;


@IocBy(type = ComboIocProvider.class,
        args = {"*js",
                "ioc/avatarUpload.js",
                "ioc/resourceUpload.js"
        })
public class MainModule {
    @At("/hello")
    @Ok("jsp:jsp.hello")
    public String doHello(){
        DaoFactory.get();
        return "Hello Nutz";
    }

    @At("/test")
    @Filters(@By(type = RoleFilter.class, args = {"action:SuperAction", "scene:sdsd", "and"}))
    @Ok("json")
    public String test(){
        return "test here";
    }
}

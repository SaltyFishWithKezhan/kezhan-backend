package cn.clate.kezhan;

import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.mvc.annotation.*;

public class MainModule {
    @At("/hello")
    @Ok("jsp:jsp.hello")
    public String doHello(){
        DaoFactory.get();
        return "Hello Nutz";
    }
}

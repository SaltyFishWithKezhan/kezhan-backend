package cn.clate.kezhan.neo4j.modules;

import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@At("/neo4j")
public class UserModule {

    @At("/getUserById")
    @Ok("json")
    public NutMap getAllCircleTypes() {
        NutMap ret = null;
        return ret;
    }

}

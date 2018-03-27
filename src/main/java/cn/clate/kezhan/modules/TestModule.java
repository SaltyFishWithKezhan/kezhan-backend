package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.test.TestDomain;
import cn.clate.kezhan.utils.Ret;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

/**
 * Created by 蛟川小盆友 on 2018/3/23.
 */
@At("/test")
public class TestModule {
    @At("/test")
    @Ok("json")
    public NutMap test(@Param("username") String id){
        NutMap data = TestDomain.getMessage(Integer.parseInt(id));

        return Ret.s(data);
    }
}

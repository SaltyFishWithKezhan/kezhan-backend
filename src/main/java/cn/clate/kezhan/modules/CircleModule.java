package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.circle.CircleDomain;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@At("/circle")
public class CircleModule {

    @At("/getByPage")
    @Ok("json")
    public NutMap getCirclesByPage(@Param("page_number") String pageNumber, @Param("page_size") String pageSize) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        NutMap ret = CircleDomain.getCirclesByPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        if (ret == null) {
            return Ret.e(2, "分页错误");
        }
        return Ret.s(ret);
    }

    @At("/getAll")
    @Ok("json")
    public NutMap getAllCircles() {
        NutMap ret = CircleDomain.getAllCircles();
        if (ret == null) {
            return Ret.e(2, "数据错误");
        }
        return Ret.s(ret);
    }

}

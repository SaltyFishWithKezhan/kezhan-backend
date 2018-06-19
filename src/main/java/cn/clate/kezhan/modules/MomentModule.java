package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.MomentDomain;
import cn.clate.kezhan.filters.RoleFilter;
import cn.clate.kezhan.pojos.Moment;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ┌─┐       ┌─┐ + +
 * ┌──┘ ┴───────┘ ┴──┐++
 * │                 │
 * │       ───       │++ + + +
 * ███████───███████ │+
 * │                 │+
 * │       ─┴─       │
 * │                 │
 * └───┐         ┌───┘
 * │         │
 * │         │   + +
 * │         │
 * │         └──────────────┐
 * │                        │
 * │                        ├─┐
 * │                        ┌─┘
 * │                        │
 * └─┐  ┐  ┌───────┬──┐  ┌──┘  + + + +
 * │ ─┤ ─┤       │ ─┤ ─┤
 * └──┴──┘       └──┴──┘  + + + +
 * 神兽保佑
 * 代码无BUG!
 * 不存在的
 */
@At("/moment")
public class MomentModule {
    @At("/getBySubCourseId")
    @Ok("json")
    @Filters(@By(type = RoleFilter.class, args = {"scene:" + RoleFilter.SCENE_MORE_THEN_OR_EQUAL_STUDENT_IN_SUB_COURSE}))
    public NutMap getBySubCourseId(@Param("sbid") String subCourseId, @Param("page_number") String pageNumber, @Param("page_size") String pageSize, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "班级ID").require();
        validator.now(pageNumber, "当前页数").require().min(0);
        validator.now(pageSize, "页大小").require().min(1);
        validator.num(subCourseId, "参数不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        NutMap ret = MomentDomain.getMomentByCourseId(Integer.parseInt(subCourseId),
                Integer.parseInt(pageNumber), Integer.parseInt(pageSize),
                Integer.parseInt(yid), Integer.parseInt(sid));
        if (ret == null) {
            return Ret.e(4, "分页错误");
        }
        List<NutMap> momentsNutMap = new ArrayList<>();
        List<Moment> content = (List<Moment>) ret.get("content");
        for (Moment moment : content) {
            NutMap momentNutmap = MomentDomain.getMomentDetailByTypeId(moment.getType(), moment.getTypeId(),
                    Integer.parseInt(yid), Integer.parseInt(sid));
            momentsNutMap.add(momentNutmap);
        }
        NutMap retComplete = new NutMap();
        retComplete.addv("now_page", ret.get("now_page"));
        retComplete.addv("per_page_size", ret.get("per_page_size"));
        retComplete.addv("page_count", ret.get("page_count"));
        retComplete.addv("content", momentsNutMap);
        return Ret.s(retComplete);
    }
}

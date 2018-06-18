package cn.clate.kezhan.filters.rolescenes;

import cn.clate.kezhan.pojos.CourseUserTake;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;
import org.omg.PortableInterceptor.INACTIVE;

public class StudentInSubCourseRoleSceneRule implements RoleSceneRule {
    @Override
    public boolean check(Dao dao, User user, ActionContext actionContext) {

        return true;
    }
}

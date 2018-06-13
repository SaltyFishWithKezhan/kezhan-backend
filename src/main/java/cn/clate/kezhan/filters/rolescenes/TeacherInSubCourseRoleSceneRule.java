package cn.clate.kezhan.filters.rolescenes;

import cn.clate.kezhan.pojos.User;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;

public class TeacherInSubCourseRoleSceneRule implements RoleSceneRule {
    @Override
    public boolean check(Dao dao, User user, ActionContext actionContext) {
        return false;
    }
}

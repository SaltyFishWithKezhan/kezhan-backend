package cn.clate.kezhan.filters.rolescenes.SubCourseScene;

import cn.clate.kezhan.filters.rolescenes.RoleSceneRule;
import cn.clate.kezhan.filters.rolescenes.RoleSceneToolBox;
import cn.clate.kezhan.pojos.User;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;

// >=教师级别
public class TeacherLevelInSubCourseRoleSceneRule implements RoleSceneRule {
    @Override
    public boolean check(Dao dao, User user, ActionContext actionContext) {
        String subCourseId = actionContext.getRequest().getParameter("sbid");
        String year = actionContext.getRequest().getParameter("year");
        String semester = actionContext.getRequest().getParameter("semester");
        if (user.getRole() < 2) {
            return false;
        }
        // 教师级别
        if (user.getRole() == 2) {
            if (RoleSceneToolBox.checkTeacherTeachSubCourse(dao, user.getId(), subCourseId, year, semester)) {
                return false;
            }
        }
        return true;
    }
}

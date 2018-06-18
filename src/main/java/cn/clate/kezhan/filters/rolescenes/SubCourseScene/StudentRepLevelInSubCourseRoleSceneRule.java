package cn.clate.kezhan.filters.rolescenes.SubCourseScene;

import cn.clate.kezhan.filters.rolescenes.RoleSceneRule;
import cn.clate.kezhan.filters.rolescenes.RoleSceneToolBox;
import cn.clate.kezhan.pojos.CourseUserTake;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;

// >=课代表级
public class StudentRepLevelInSubCourseRoleSceneRule implements RoleSceneRule {
    @Override
    public boolean check(Dao dao, User user, ActionContext actionContext) {
        String subCourseId = actionContext.getRequest().getParameter("sbid");
        String year = actionContext.getRequest().getParameter("year");
        String semester = actionContext.getRequest().getParameter("semester");
        if (user.getRole() < 1){
            return false;
        }
        if (user.getRole() == 1){
            if(!RoleSceneToolBox.checkIsAssistantSubCourse(dao, user.getId(), subCourseId, year, semester) &&
                    !RoleSceneToolBox.checkIsRepAssistantSubCourse(dao, user.getId(), subCourseId, year, semester)){
                return false;
            }
        }
        if (user.getRole() == 2){
            if (!RoleSceneToolBox.checkTeacherTeachSubCourse(dao, user.getId(), subCourseId, year, semester)){
                return false;
            }
        }
        return true;
    }
}

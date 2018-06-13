package cn.clate.kezhan.filters.rolescenes;

import cn.clate.kezhan.pojos.CourseUserTake;
import cn.clate.kezhan.pojos.User;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;

public class AssistantInSubCourseRoleSceneRule implements RoleSceneRule {
    @Override
    public boolean check(Dao dao, User user, ActionContext actionContext) {
        String subCourseId = actionContext.getRequest().getParameter("sub_course_id");
        CourseUserTake courseUserTake = dao.fetch(CourseUserTake.class, Cnd.where("user_id", "=", user.getId())
                .and("sub_course_term_id", "=", subCourseId));
        if(courseUserTake == null || courseUserTake.getIsAssistant() == 0){
            return false;
        }
        return true;
    }
}

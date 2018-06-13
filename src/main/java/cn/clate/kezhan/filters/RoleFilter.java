package cn.clate.kezhan.filters;

import cn.clate.kezhan.filters.rolescenes.*;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;
import org.nutz.mvc.view.ViewWrapper;

public class RoleFilter implements ActionFilter {
    public static final String SCENE_STUDENT_IN_SUB_COURSE = "1";
    public static final String SCENE_ASSISTANT_IN_SUB_COURSE = "2";
    public static final String SCENE_TEACHER_IN_SUB_COURSE = "3";
    public static final String SCENE_TEACHER_OR_ASSISTANT_IN_SUB_COURSE = "4";

    private String mField;
    private String mSceneRule;
    private Dao dao;

    public RoleFilter(){
        this((String) null);
    }

    public RoleFilter(String sceneRule){
        this(null, sceneRule);
    }

    public RoleFilter(String action, String sceneRule){
        mField = action;
        mSceneRule = sceneRule;
        dao = DaoFactory.get();
    }

    @Override
    public View match(ActionContext actionContext) {
        getFieldName(actionContext);
        String userId = actionContext.getRequest().getParameter("uid");
        String userToken = actionContext.getRequest().getParameter("utoken");
        try {
            User user = UserAuthenication.auth(userId, userToken);
            if(isFieldDefined() && !checkRole(user.getRole())){
                throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(54,"role unauthorized")));
            }
            if(mSceneRule != null && !checkRoleScene(user, actionContext)){
                throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(56,"role unauthorized")));
            }
            return null;
        } catch (AuthException e) {
            return e.getView();
        }
    }

    private void getFieldName(ActionContext actionContext){
        if(mField == null)
            mField = actionContext.getModule().getClass().getSimpleName()
                    + "@"
                    + actionContext.getMethod().getName();
    }

    private boolean isFieldDefined(){
        Sql sql = Sqls.create("SELECT count(*) FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'kz_users_roles' AND column_name = @columnName");
        sql.params().set("columnName", mField);
        sql.setCallback(new Sqls.CallbackFactory().integer());
        dao.execute(sql);
        int result = sql.getInt();
        return result != 0;
    }

    private boolean checkRole(int roleId){
        Sql sql = Sqls.create("SELECT `$field` FROM kz_users_roles WHERE id = @id");
        sql.vars().set("field", mField);
        sql.params().set("id", roleId);
        sql.setCallback(new Sqls.CallbackFactory().integer());
        dao.execute(sql);
        int isAllowed = sql.getInt();
        return isAllowed != 0;
    }

    private boolean checkRoleScene(User user, ActionContext actionContext) throws AuthException {
        boolean accepted;
        try {
            RoleSceneRule rule;
            switch (mSceneRule){
                case SCENE_STUDENT_IN_SUB_COURSE:
                    rule = new StudentInSubCourseRoleSceneRule();
                    break;
                case SCENE_ASSISTANT_IN_SUB_COURSE:
                    rule = new AssistantInSubCourseRoleSceneRule();
                    break;
                case SCENE_TEACHER_IN_SUB_COURSE:
                    rule = new TeacherInSubCourseRoleSceneRule();
                    break;
                case SCENE_TEACHER_OR_ASSISTANT_IN_SUB_COURSE:
                    rule = new TeacherOrAssistantInSubCourseRoleSceneRule();
                    break;
                default:
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(55,"unknown role scene")));
            }
            accepted = rule.check(dao, user, actionContext);
        } catch (Exception e) {
            throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(55,"role check exception")));
        }
        return accepted;
    }
}

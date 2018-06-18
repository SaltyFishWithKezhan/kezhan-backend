package cn.clate.kezhan.filters;

import cn.clate.kezhan.filters.rolescenes.*;
import cn.clate.kezhan.filters.rolescenes.SubCourseScene.AssistantLevelInSubCourseRoleSceneRule;
import cn.clate.kezhan.filters.rolescenes.SubCourseScene.StudentRepLevelInSubCourseRoleSceneRule;
import cn.clate.kezhan.filters.rolescenes.SubCourseScene.StudentLevelInSubCourseRoleSceneRule;
import cn.clate.kezhan.filters.rolescenes.SubCourseScene.TeacherLevelInSubCourseRoleSceneRule;
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
    public static final String SCENE_MORE_THEN_OR_EQUAL_STUDENT_IN_SUB_COURSE = "1";
    public static final String SCENE_MORE_THEN_OR_EQUAL_STUDENT_REP_IN_SUB_COURSE = "2";
    public static final String SCENE_MORE_THEN_OR_EQUAL_ASSISTANCE_IN_SUB_COURSE = "3";
    public static final String SCENE_MORE_THEN_OR_EQUAL_TEACHER_IN_SUB_COURSE = "4";

    private String mField;
    private String mSceneRule;
    private boolean mAsc;
    private String mLogic;
    private Dao dao;

    public RoleFilter(){
        this((String) null);
    }

    public RoleFilter(String par1){
        this(par1, null);
    }

    public RoleFilter(String par1, String par2){
        this(par1, par2, "and");
    }

    public RoleFilter(String par1, String par2, String logic){
        String action = null;
        String sceneRule = null;
        boolean asc = true;
        if(par1 != null && par1.indexOf("action:") == 0)
            action = par1.substring(7);
        if(par1 != null && par1.indexOf("scene:") == 0)
            sceneRule = par1.substring(6);
        if(par2 != null && par2.indexOf("action:") == 0)
            action = par2.substring(7);
        if(par2 != null && par2.indexOf("scene:") == 0)
            sceneRule = par2.substring(6);
        if(par1 != null && par1.trim().length() != 0 && par1.indexOf("action:") != 0 && par1.indexOf("scene:") != 0){
            action = par1;
        }
        if(par2 != null && par2.trim().length() != 0 && par2.indexOf("action:") != 0 && par2.indexOf("scene:") != 0){
            sceneRule = par2;
        }
        if(par1 != null && par2 != null && par1.indexOf("scene:") == 0 && par2.indexOf("action:") == 0){
            asc = false;
        }
        mField = action;
        mSceneRule = sceneRule;
        mLogic = logic == null ? "and" : logic;
        mAsc = asc;
        dao = DaoFactory.get();
    }

    @Override
    public View match(ActionContext actionContext) {
        getFieldName(actionContext);
        String userId = actionContext.getRequest().getParameter("uid");
        String userToken = actionContext.getRequest().getParameter("utoken");
        try {
            User user = UserAuthenication.auth(userId, userToken);
            if(mAsc && mLogic.equals("and")){
                if (isFieldDefined() && !checkRole(user.getRole())){
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(54,"role unauthorized")));
                }
                if (mSceneRule != null && !checkRoleScene(user, actionContext)){
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(56,"role scene unauthorized")));
                }
            } else if (!mAsc && mLogic.equals("and")){
                if(mSceneRule != null && !checkRoleScene(user, actionContext)){
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(56,"role scene unauthorized")));
                }
                if(isFieldDefined() && !checkRole(user.getRole())){
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(54,"role unauthorized")));
                }
            } else if (mLogic.equals("or")) {
                boolean isFieldDefined = isFieldDefined();
                boolean roleChecked = false;
                boolean isSceneRuleDefined = mSceneRule != null;
                boolean sceneChecked = false;
                if(isFieldDefined){
                    if(checkRole(user.getRole())){
                        return null;
                    } else {
                        roleChecked = true;
                    }
                }
                if(isSceneRuleDefined){
                    if(checkRoleScene(user, actionContext)){
                        return null;
                    } else {
                        sceneChecked = true;
                    }
                }
                if(!isFieldDefined && !isSceneRuleDefined){
                    return null;
                }
                if(roleChecked){
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(54,"role unauthorized")));
                } else {
                    throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(56,"role scene unauthorized")));
                }
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
                case SCENE_MORE_THEN_OR_EQUAL_STUDENT_IN_SUB_COURSE:
                    rule = new StudentLevelInSubCourseRoleSceneRule();
                    break;
                case SCENE_MORE_THEN_OR_EQUAL_STUDENT_REP_IN_SUB_COURSE:
                    rule = new StudentRepLevelInSubCourseRoleSceneRule();
                    break;
                case SCENE_MORE_THEN_OR_EQUAL_ASSISTANCE_IN_SUB_COURSE:
                    rule = new AssistantLevelInSubCourseRoleSceneRule();
                    break;
                case SCENE_MORE_THEN_OR_EQUAL_TEACHER_IN_SUB_COURSE:
                    rule = new TeacherLevelInSubCourseRoleSceneRule();
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

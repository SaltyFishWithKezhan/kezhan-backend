package cn.clate.kezhan.filters.rolescenes;

import cn.clate.kezhan.pojos.User;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;

public interface RoleSceneRule {
    public boolean check(Dao dao, User user, ActionContext actionContext);
}

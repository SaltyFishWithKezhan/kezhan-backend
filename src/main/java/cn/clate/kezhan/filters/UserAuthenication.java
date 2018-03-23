package cn.clate.kezhan.filters;

import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;
import org.nutz.mvc.view.ViewWrapper;

public class UserAuthenication implements ActionFilter {
    @Override
    public View match(ActionContext actionContext) {
        String user = actionContext.getRequest().getParameter("uid");
        String token = actionContext.getRequest().getParameter("utoken");
        Dao dao = DaoFactory.get();

        if(user==null)
            return new ViewWrapper(new UTF8JsonView(), "failed");
        return null;
    }
}

package cn.clate.kezhan.filters;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;
import org.nutz.mvc.view.ViewWrapper;

public class UserAuthenication implements ActionFilter {
    @Override
    public View match(ActionContext actionContext) {
        String userId = actionContext.getRequest().getParameter("uid");
        String userToken = actionContext.getRequest().getParameter("utoken");
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, Integer.parseInt(userId));
        System.out.println(user.getAccessToken());
        if(!userToken.equals(user.getAccessToken()))
            return new ViewWrapper(new UTF8JsonView(), "failed authenication ");
        return null;
    }
}

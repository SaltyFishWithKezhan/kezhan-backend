package cn.clate.kezhan.filters;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Tools;
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
        String userId = actionContext.getRequest().getParameter("uid");
        String userToken = actionContext.getRequest().getParameter("utoken");
        if (userId == null) {
            return new ViewWrapper(new UTF8JsonView(), "uid cannot be null");
        }
        if (userToken == null) {
            return new ViewWrapper(new UTF8JsonView(), "utoken cannot be null");
        }
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, Integer.parseInt(userId));
        long nowTime = Tools.getTimeStamp();
        if (nowTime - user.getLastActiveTime() > (Integer) Conf.get("tokenValidTime")) {
            return new ViewWrapper(new UTF8JsonView(), "token failed");
        }
        user.setLastActiveTime((int) Tools.getTimeStamp());
        dao.update(user);
        if (!userToken.equals(user.getAccessToken()))
            return new ViewWrapper(new UTF8JsonView(), "failed authenication ");
        return null;
    }
}

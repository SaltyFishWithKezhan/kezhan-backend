package cn.clate.kezhan.filters;

import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
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
        try {
            auth(userId, userToken);
        } catch (AuthException e) {
            return e.getView();
        }
        return null;
    }

    public static User auth(String userId, String userToken) throws AuthException {
        if (userId == null) {
            throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(50,"uid cannot be null")));
        }
        if (userToken == null) {
            throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(51,"utoken cannot be null")));
        }

        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, Cnd.where("id", "=", userId).and("access_token", "=", userToken));
        if(user == null)
            throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(52,"unauthorized")));
        long nowTime = Tools.getTimeStamp();
        if (nowTime - user.getLastActiveTime() > (Integer) Conf.get("tokenValidTime"))
            throw new AuthException(new ViewWrapper(new UTF8JsonView(), Ret.e(53,"unauthorized")));
        user.setLastActiveTime((int) Tools.getTimeStamp());
        dao.update(user);
        return user;
    }
}

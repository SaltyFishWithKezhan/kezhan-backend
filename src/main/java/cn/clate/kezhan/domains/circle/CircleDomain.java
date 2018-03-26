package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Circle;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class CircleDomain {

    public static NutMap getCirclesByPage(int pageNumber,int pageSize) {
        Dao dao = DaoFactory.get();
        List<Circle> circles = dao.query(Circle.class, null, dao.createPager(pageNumber, pageSize));  //LinkedList
        if(circles==null)//TODO:  circles.size()==0
            return null;
        ArrayList<Circle> circleArrayList = new ArrayList<>(circles);
        return Ret.s("success",circleArrayList);
    }

    public static NutMap getAllCircles() {
        Dao dao = DaoFactory.get();
        List<Circle> circles = dao.query(Circle.class, null);  //LinkedList
        if(circles==null)
            return null;
        ArrayList<Circle> circleArrayList = new ArrayList<>(circles);
        return Ret.s("success",circleArrayList);
    }
}

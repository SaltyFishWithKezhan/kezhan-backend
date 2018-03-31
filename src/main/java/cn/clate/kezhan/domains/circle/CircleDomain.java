package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Circle;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CircleDomain {

    public static NutMap getCirclesByTypePage(int type, int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<Circle> circles = new LinkedList<>();
        if(type==0){
            circles = dao.query(Circle.class, Cnd.where("status", "!=", -1).
                    desc("id"), pager);
        }else {
            circles = dao.query(Circle.class, Cnd.where("status", "!=", -1).
                    and("type_id","=",type).desc("id"), pager);
        }
        pager.setRecordCount(dao.count(Circle.class, Cnd.where("status", "!=", -1)));
        if (circles == null)//TODO:  circles.size()==0
            return null;
        ArrayList<Circle> circleArrayList = new ArrayList<>(circles);
        NutMap ret = new NutMap();
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        ret.addv("content", circleArrayList);
        return ret;
    }

}

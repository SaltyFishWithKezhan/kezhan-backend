package cn.clate.kezhan.domains.test;

import cn.clate.kezhan.pojos.Teacher;
import cn.clate.kezhan.pojos.Test;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2018/3/23.
 */
public class TestDomain {
    public static NutMap getMessage(int id){
        Dao dao = DaoFactory.get();


        List<Test>tests= dao.query(Test.class, Cnd.where("id", "=", id));
        ArrayList<NutMap> contentList = new ArrayList<NutMap>();

        for (Test test: tests){
            NutMap testItem=new NutMap();
            testItem.addv("id",test.getId());
            testItem.addv("name",test.getName());
            contentList.add(testItem);
        }
        NutMap ret = new NutMap();
        ret.addv("code","200" );
        ret.addv("testItems", contentList);
        return ret;
    }
}

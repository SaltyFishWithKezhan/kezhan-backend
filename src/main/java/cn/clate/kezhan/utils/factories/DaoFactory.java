package cn.clate.kezhan.utils.factories;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import javax.sql.DataSource;

public class DaoFactory {
    private static Dao theDao = null;
    private static DataSource ds;

    private static void generate(){
        Ioc ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
        ds = ioc.get(DataSource.class);
        theDao = new NutDao(ds);
    }

    public synchronized static Dao get(){
        if(theDao == null){
            generate();
        }
        return theDao;
    }

}
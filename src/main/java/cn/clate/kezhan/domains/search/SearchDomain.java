package cn.clate.kezhan.domains.search;

import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.lang.util.NutMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SearchDomain {

    private static List<String> searchSql(Dao dao,String name) {
        Sql sql = Sqls.create("SELECT kz_teachers.name as result \n" +
                "FROM kezhan_app.kz_teachers\n" +
                "where name like @name\n" +
                "union \n" +
                "SELECT kz_courses.name as result \n" +
                "FROM kezhan_app.kz_courses\n" +
                "where name like @name\n" +
                "limit 0,10");
        sql.params().set("name", "%"+name+"%");
        sql.setCallback(new SqlCallback() {
            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                List<String> list = new LinkedList<String>();
                while (rs.next())
                    list.add(rs.getString("result"));
                return list;
            }
        });
        dao.execute(sql);
        return sql.getList(String.class);
        // Nutz内置了大量回调, 请查看Sqls.callback的属性
    }

    public static NutMap getSearchByName(String name){
        Dao dao = DaoFactory.get();
        List<String> result = searchSql(dao,name);
        ArrayList<String> res = new ArrayList<>(result);
        res.sort((a,b) -> a.length() - b.length());
        return Ret.s("search_result",res);
    }
}

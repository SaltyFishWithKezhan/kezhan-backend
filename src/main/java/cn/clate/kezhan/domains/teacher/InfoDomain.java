package cn.clate.kezhan.domains.teacher;

import cn.clate.kezhan.pojos.Teacher;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

public class InfoDomain {
    public static NutMap getTeacherById(int id){
        Dao dao = DaoFactory.get();
        Teacher teacher = dao.fetch(Teacher.class, id);
        if (teacher == null) {
            return null;
        }

        return Ret.s("success");
    }
}

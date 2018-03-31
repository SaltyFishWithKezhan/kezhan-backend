package cn.clate.kezhan.domains.teacher;

import cn.clate.kezhan.pojos.Teacher;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

public class TeacherDomain {
    public static NutMap getTeacherById(int id) {
        Dao dao = DaoFactory.get();
        Teacher teacher = dao.fetch(Teacher.class, Cnd.where("id", "=", id).and("is_active", "!=", -1));
        if (teacher == null) {
            return null;
        }
        PojoSerializer pjsr = new PojoSerializer(teacher);
        NutMap ret = pjsr.get();
        return ret;
    }
}

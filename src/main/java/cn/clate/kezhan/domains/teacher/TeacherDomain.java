package cn.clate.kezhan.domains.teacher;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.pojos.Teacher;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.ListSet;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class TeacherDomain {
    public static NutMap getTeacherById(int id) {
        Dao dao = DaoFactory.get();
        Teacher teacher = dao.fetch(Teacher.class, Cnd.where("id", "=", id).and("is_active", "!=", -1));
        if (teacher == null) {
            return null;
        }
        NutMap countNut = CourseDomain.getCourseNumByTeacherId(id);
        PojoSerializer pjsr = new PojoSerializer(teacher);
        NutMap ret = pjsr.get();
        ret.attach(countNut);
        return ret;
    }

    //fuzzy check
    public static NutMap getTeachersByName(String name) {
        Dao dao = DaoFactory.get();
        Teacher teacher = dao.fetch(Teacher.class, Cnd.where("name", "=", name).and("is_active", "!=", -1));
        List<Teacher> teachers = dao.query(Teacher.class, Cnd.where("name", "LIKE", "%" + name + "%").and("is_active", "!=", -1));
        if (teachers == null||teachers.size()==0) {//teachers.size()==0
            return null;
        }
        System.out.print(teachers.size());
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        if (teacher != null) {
            teacherArrayList.add(teacher);
            teacherArrayList.add(teachers.get(0));
        } else {
            teacherArrayList.add(teachers.get(0));
            if (teachers.size() > 1) {
                teacherArrayList.add(teachers.get(1));
            }
        }
        NutMap ret = new NutMap();
        ret.addv("teacher_num",teachers.size());
        ret.addv("teachers",teacherArrayList);
        return ret;
    }
}

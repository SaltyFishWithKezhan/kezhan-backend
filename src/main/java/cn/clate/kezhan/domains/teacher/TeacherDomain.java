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
        List<Teacher> teachers = dao.query(Teacher.class, Cnd.where("name", "LIKE", "%" + name + "%").and("is_active", "!=", -1));
        if (teachers == null) {//teachers.size()==0
            return null;
        }
        System.out.print("mmm"+teachers.size());
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        for(int i = 0; i < Math.min(2, teachers.size()); i++){
            teacherArrayList.add(teachers.get(i));
        }
        NutMap ret = new NutMap();
        ret.addv("teacher_num",teacherArrayList.size());
        ret.addv("teachers",teacherArrayList);
        System.out.println(ret.toString());
        return ret;
    }

    public static NutMap getTeacheInforByCourseSubId(int id, int yid, int sid) {
        Dao dao = DaoFactory.get();
        NutMap courseSub = CourseDomain.getCourseSubBySubId(id, yid, sid);
        NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"), yid, sid );
        NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
        Teacher teacher = dao.fetch(Teacher.class, Cnd.where("id", "=", (int)course.get("teacher_id")).and("is_active", "!=", -1));
        if (teacher == null) {
            return null;
        }
        PojoSerializer pjsr = new PojoSerializer(teacher);
        NutMap ret = pjsr.get();
        return ret;
    }
}

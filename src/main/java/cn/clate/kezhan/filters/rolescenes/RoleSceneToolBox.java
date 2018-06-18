package cn.clate.kezhan.filters.rolescenes;

import cn.clate.kezhan.domains.course.CourseDomain;
import cn.clate.kezhan.pojos.Assistant;
import cn.clate.kezhan.pojos.CourseSub;
import cn.clate.kezhan.pojos.Teacher;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.lang.util.NutMap;

public class RoleSceneToolBox {
    private static boolean checkIsNumber(String num) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(num).require();
        validator.num(num).require();
        if (!validator.check()) {
            return false;
        }
        return true;
    }


    private static boolean checkSubCourseExists(Dao dao, int sbid, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            CourseSub t = dao.fetch(CourseSub.class, sbid);
            if (t == null) {
                return false;
            }
            return true;
        } finally {
            TableName.clear();
        }

    }

    private static boolean checkSubCourseExists(Dao dao, String sbid, String yid, String sid) {
        if (!(checkIsNumber(sbid) && checkIsNumber(yid) && checkIsNumber(sid))) {
            return false;
        }
        if (!checkSubCourseExists(dao, Integer.parseInt(sbid), Integer.parseInt(yid), Integer.parseInt(sid))) {
            return false;
        }
        return true;
    }

    public static boolean checkTeacherTeachSubCourse(Dao dao, int uid, String sbid, String yid, String sid) {
        if (!checkSubCourseExists(dao, sbid, yid, sid)) {
            return false;
        }
        NutMap courseSub = CourseDomain.getCourseSubBySubId(Integer.parseInt(sbid), Integer.parseInt(yid), Integer.parseInt(sid));
        NutMap courseTerm = CourseDomain.getCourseTermByCourseTermId((int) courseSub.get("course_term_id"), Integer.parseInt(yid), Integer.parseInt(sid));
        NutMap course = CourseDomain.getCourseByCourseId((int) courseTerm.get("course_id"));
        Teacher teacher = dao.fetch(Teacher.class, Cnd.where("id", "=", (int) course.get("teacher_id")).and("is_active", "=", 1));
        if (teacher == null) {
            return false;
        }
        if (teacher.getUserId() != uid) {
            return false;
        }
        return true;
    }


    public static boolean checkIsAssistantSubCourse(Dao dao, int id, String subCourseId, String year, String semester) {
        if (!checkSubCourseExists(dao, subCourseId, year, semester)) {
            return false;
        }
        Assistant t = dao.fetch(Assistant.class, Cnd.where("user_id", "=", id).and("sub_course_term_id", "=", Integer.parseInt(subCourseId)).and("status", "=", 0));
        if (t == null) {
            return false;
        }
        return true;
    }
}

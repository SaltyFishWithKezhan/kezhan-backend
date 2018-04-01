package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import java.util.List;

public class HomeworkDomain {

    public static List<Homework> getHomeworkListBySubCourseId(int subCourseId) {
        Dao dao = DaoFactory.get();
        List<Homework> homeworkList = dao.query(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)
                .desc("update_time"));
        if (homeworkList == null) {
            return null;
        }
        for (Homework it : homeworkList) {
            it.setUpdateTime(Tools.dateTimeTodate(it.getUpdateTime()));
            it.setDeadline(Tools.dateTimeTodate(it.getDeadline()));
        }
        return homeworkList;
    }

    public static Homework getHomeworkByHomeworkId(int homeworkId) {
        Dao dao = DaoFactory.get();

        Homework homework = dao.fetch(Homework.class, homeworkId);
        if (homework == null) {
            return null;
        }
        //懒加载
        dao.fetchLinks(homework, "poster");
        dao.fetchLinks(homework, "subCourse");
        homework.setUpdateTime(Tools.dateTimeTodate(homework.getUpdateTime()));
        homework.setDeadline(Tools.dateTimeTodate(homework.getDeadline()));
        //TODO 未考虑原子性问题
        dao.update(Homework.class, Chain.makeSpecial("viewerCount", "+1"), Cnd.where("id", "=", homeworkId));
        return homework;
    }

    public static void main(String[] args) {
        List<Homework> homework = getHomeworkListBySubCourseId(13);
        for (Homework it : homework) {
            System.out.println(it.getDescription());
        }
        Homework homework1 = getHomeworkByHomeworkId(2);
        System.out.println(homework1.getPoster().getRealName());
    }
}

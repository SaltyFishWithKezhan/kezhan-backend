package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class HomeworkDomain {

    public static NutMap getHomeworkListBySubCourseId(int subCourseId,int pageNumber, int pageSize) {
        Dao dao = DaoFactory.get();
        Pager pager = dao.createPager(pageNumber, pageSize);
        List<Homework> homeworkList = dao.query(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)
                .desc("update_time"),pager);
        if (homeworkList == null) {
            return null;
        }
        pager.setRecordCount(dao.count(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)));
        for (Homework it : homeworkList) {
            it.setUpdateTime(Tools.dateTimeTodate(it.getUpdateTime()));
            it.setDeadline(Tools.dateTimeTodate(it.getDeadline()));
        }
        NutMap ret = new NutMap();
        ret.addv("now_page", pager.getPageNumber());
        ret.addv("per_page_size", pager.getPageSize());
        ret.addv("page_count", pager.getPageCount());
        ret.addv("homeworkList",new ArrayList<Homework>(homeworkList));
        return ret;
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
//        List<Homework> homework = getHomeworkListBySubCourseId(13);
//        for (Homework it : homework) {
//            System.out.println(it.getDescription());
//        }
//        Homework homework1 = getHomeworkByHomeworkId(2);
//        System.out.println(homework1.getPoster().getRealName());
    }
}

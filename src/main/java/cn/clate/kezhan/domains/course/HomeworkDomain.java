package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.Homework;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.dao.pager.Pager;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class HomeworkDomain {

    public static NutMap getHomeworkListBySubCourseId(int subCourseId, int pageNumber, int pageSize, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            Pager pager = dao.createPager(pageNumber, pageSize);
            List<Homework> homeworkList = dao.query(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)
                    .desc("update_time"), pager);
            if (homeworkList == null) {
                return null;
            }
            pager.setRecordCount(dao.count(Homework.class, Cnd.where("course_sub_id", "=", subCourseId)));
            for (Homework it : homeworkList) {
                dao.fetchLinks(it, "poster");
                it.getPoster().removeCriticalInfo();
                it.setUpdateTime(Tools.dateTimeTodate(it.getUpdateTime()));
                it.setDeadline(Tools.dateTimeTodate(it.getDeadline()));
            }
            NutMap ret = new NutMap();
            ret.addv("now_page", pager.getPageNumber());
            ret.addv("per_page_size", pager.getPageSize());
            ret.addv("page_count", pager.getPageCount());
            ret.addv("homeworkList", new ArrayList<Homework>(homeworkList));
            return ret;
        } finally {
            TableName.clear();
        }
    }

    public static Homework getHomeworkByHomeworkId(int homeworkId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            Homework homework = dao.fetch(Homework.class, homeworkId);
            if (homework == null) {
                return null;
            }
            dao.fetchLinks(homework, "poster");
            dao.fetchLinks(homework, "subCourse");
            homework.setUpdateTime(Tools.dateTimeTodate(homework.getUpdateTime()));
            homework.setDeadline(Tools.dateTimeTodate(homework.getDeadline()));
            synchronized (HomeworkDomain.class) {
                dao.update(Homework.class, Chain.makeSpecial("viewerCount", "+1"), Cnd.where("id", "=", homeworkId));
            }
            return homework;
        } finally {

        }

    }
}

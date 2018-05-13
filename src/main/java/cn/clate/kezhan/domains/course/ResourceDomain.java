package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.CourseSub;
import cn.clate.kezhan.pojos.Resource;
import cn.clate.kezhan.pojos.ResourceTerm;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;

import java.util.List;

public class ResourceDomain {
    public static List<Resource> getAllCourseResource(int courseId) {
        Dao dao = DaoFactory.get();
        List<Resource> resourceList = dao.query(Resource.class, Cnd.where("courseId", "=", courseId).desc("uploadTime"));
        if (resourceList == null) {
            return null;
        }
        for (Resource it : resourceList) {
            dao.fetchLinks(it, "poster");
            it.getPoster().removeCriticalInfo();
            it.setUpLoadTime(Tools.dateTimeTodate(it.getUpLoadTime()));
        }
        return resourceList;
    }

    public static List<ResourceTerm> getAllTermCourseResource(int subCourseId, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            CourseSub courseSub = dao.fetch(CourseSub.class, subCourseId);
            List<ResourceTerm> resourceTermList = dao.query(ResourceTerm.class,
                    Cnd.where("courseTermId", "=", courseSub.getCourseTermId())
                            .desc("uploadTime"));
            if (resourceTermList == null) {
                return null;
            }
            for (ResourceTerm it : resourceTermList) {
                dao.fetchLinks(it, "poster");
                it.getPoster().removeCriticalInfo();
                it.setUploadTime(Tools.dateTimeTodate(it.getUploadTime()));
            }
            return resourceTermList;
        } finally {
            TableName.clear();
        }

    }

    public static void insertCourseResource(Resource resource) {
        Dao dao = DaoFactory.get();
        dao.insert(resource);
    }

    public static void insertCourseTermResource(ResourceTerm resourceTerm, int yid, int sid) {
        try {
            TableName.set(Tools.getYestAndSemester(yid, sid));
            Dao dao = DaoFactory.get();
            dao.insert(resourceTerm);
        } finally {
            TableName.clear();
        }

    }
}

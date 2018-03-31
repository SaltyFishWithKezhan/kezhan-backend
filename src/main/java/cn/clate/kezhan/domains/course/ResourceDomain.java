package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.CourseSub;
import cn.clate.kezhan.pojos.Resource;
import cn.clate.kezhan.pojos.ResourceTerm;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import java.util.List;

public class ResourceDomain {
    public static List<Resource> getAllCourseResource(int courseId) {
        Dao dao = DaoFactory.get();
        List<Resource> resourceList = dao.query(Resource.class, Cnd.where("courseId", "=", courseId).desc("updateTime"));
        if (resourceList == null) {
            return null;
        }
        for(Resource it : resourceList){
            dao.fetchLinks(it, "poster");
            it.getPoster().removeCriticalInfo();
        }
        return resourceList;
    }

    public static List<ResourceTerm> getAllSubCourseResource(int subCourseId) {
        Dao dao = DaoFactory.get();
        CourseSub courseSub = dao.fetch(CourseSub.class, subCourseId);
        List<ResourceTerm> resourceTermList = dao.query(ResourceTerm.class,
                Cnd.where("courseTermId", "=", courseSub.getCourseTermId())
                        .desc("updateTime"));
        if(resourceTermList == null){
            return null;
        }
        for(ResourceTerm it : resourceTermList){
            dao.fetchLinks(it, "poster");
            it.getPoster().removeCriticalInfo();
        }
        return resourceTermList;
    }

    public static void insertCourseResource(Resource resource){
        Dao dao = DaoFactory.get();
        dao.insert(resource);
    }

    public static void insertCourseTermResource(ResourceTerm resourceTerm){
        Dao dao = DaoFactory.get();
        dao.insert(resourceTerm);
    }
}

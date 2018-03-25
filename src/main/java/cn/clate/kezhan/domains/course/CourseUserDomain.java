package cn.clate.kezhan.domains.course;

import cn.clate.kezhan.pojos.CourseUserTake;
import cn.clate.kezhan.pojos.Test;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蛟川小盆友 on 2018/3/25.
 */
public class CourseUserDomain {
    public static ArrayList<Integer>getSubCourseTermIdByUser(int id){
        Dao dao = DaoFactory.get();


        List<CourseUserTake> courseUserTakeList= dao.query(CourseUserTake.class, Cnd.where("user_id", "=", id));
       // System.out.println(courseUserTakeList.size());
        ArrayList<Integer> subCourseTermIds = new ArrayList<Integer>();
        for (CourseUserTake courseUserTake: courseUserTakeList){
            subCourseTermIds.add(courseUserTake.getSubCourseTermId());
        }

        return subCourseTermIds;
    }

    public static void main(String[] args){
        ArrayList<Integer>tests=getSubCourseTermIdByUser(8);
        System.out.println(tests.size());
        for(Integer test:tests){
            System.out.println(test);
        }

    }
}

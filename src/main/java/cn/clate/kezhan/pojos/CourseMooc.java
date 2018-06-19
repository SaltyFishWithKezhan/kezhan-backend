package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Table;

@Table("mooc_course")
public class CourseMooc extends CourseRecommend{

    public CourseMooc(){
        super();
        this.category = 1;
    }
}

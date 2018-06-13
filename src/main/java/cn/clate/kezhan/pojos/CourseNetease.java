package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Table;

@Table("netease_course")
public class CourseNetease extends CourseRecommend{
    public CourseNetease(){
        super();
        this.type = 1;
    }
}

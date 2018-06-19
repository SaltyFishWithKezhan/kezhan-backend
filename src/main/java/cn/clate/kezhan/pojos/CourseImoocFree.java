package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("imooc_free")
public class CourseImoocFree extends CourseRecommend{

    public CourseImoocFree(){
        super();
        this.category = 3;
    }
}

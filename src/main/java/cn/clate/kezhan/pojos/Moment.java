package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_moments_2018_1")
public class Moment {
    @Id
    private int id;

    @Column("type")
    private int type;

    @Column("type_id")
    private int typeId;

    @Column("update_time")
    private String updateTime;

    @Column("course_sub_id")
    private int subCourseId;

    @One(field = "subCourseId")
    private CourseSub courseSub;

    public Moment() {
    }

    public int getId() {
        return id;
    }

    public Moment setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public Moment setType(int type) {
        this.type = type;
        return this;
    }

    public int getTypeId() {
        return typeId;
    }

    public Moment setTypeId(int typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Moment setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getSubCourseId() {
        return subCourseId;
    }

    public Moment setSubCourseId(int subCourseId) {
        this.subCourseId = subCourseId;
        return this;
    }

    public CourseSub getCourseSub() {
        return courseSub;
    }

    public Moment setCourseSub(CourseSub courseSub) {
        this.courseSub = courseSub;
        return this;
    }
}

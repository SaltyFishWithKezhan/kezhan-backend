package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_courses")
public class Course {
    @Id
    private int id;
    @Column("name")
    private String name;
    @Column("name_en")
    private String nameEn;
    @Column("teacher_id")
    private int teacherId;
    @Column("type")
    private int type;
    @Column("desc")
    private String desc;
    @Column("max_size")
    private int maxSize;
    @Column("cover_img")
    private String coverImg;
    @Column("count_evaluate")
    private int countEvaluate;
    @Column("course_code")
    private String courseCode;
    @Column("status")
    private int status;
    @Column("credits")
    private int credits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getType() {
        return type;
    }

    public Course setType(int type) {
        this.type = type;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getCountEvaluate() {
        return countEvaluate;
    }

    public void setCountEvaluate(int countEvaluate) {
        this.countEvaluate = countEvaluate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCredits() {
        return credits;
    }

    public Course setCredits(int credits) {
        this.credits = credits;
        return this;
    }
}

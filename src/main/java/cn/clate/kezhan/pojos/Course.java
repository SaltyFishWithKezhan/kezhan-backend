package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_courses")
public class Course extends DummyCourse{
    @Id
    private int id;
    @Column("name")
    private String name;
    @Column("name_en")
    private String nameEn;
    @Column("teacher_id")
    private int teacherId;
    @One(field = "teacherId")
    private Teacher teacher;
    @Column("type")
    private int type;
    @Column("max_size")
    private int maxSize;
    @Column("cover_img")
    private String coverImg;
    @Column("description")
    private String description;
    @Column("count_evaluate")
    private int countEvaluate;
    @Column("course_code")
    private String courseCode;
    @Column("status")
    private int status;
    @Column("credits")
    private int credits;
    @Column("total_rating")
    private double totalRating;

    public String getDescription() {
        return description;
    }

    public Course setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public Course setTotalRating(double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public double getTotal_rating() {
        return totalRating;
    }

    public Course setTotal_rating(double total_rating) {
        this.totalRating = total_rating;
        return this;
    }

    public Course() {
        this.category = 4;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public int getId() {
        return id;
    }

    public Course setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Course setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public Course setTeacherId(int teacherId) {
        this.teacherId = teacherId;
        return this;
    }

    public int getType() {
        return type;
    }

    public Course setType(int type) {
        this.type = type;
        return this;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public Course setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public Course setCoverImg(String coverImg) {
        this.coverImg = coverImg;
        return this;
    }

    public int getCountEvaluate() {
        return countEvaluate;
    }

    public Course setCountEvaluate(int countEvaluate) {
        this.countEvaluate = countEvaluate;
        return this;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Course setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Course setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public Course setCredits(int credits) {
        this.credits = credits;
        return this;
    }
}

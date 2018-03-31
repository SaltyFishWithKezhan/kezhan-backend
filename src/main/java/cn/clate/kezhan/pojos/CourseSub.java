package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by 蛟川小盆友 on 2018/3/25.
 */
@Table("kz_courses_term_2018_1_sub")
public class CourseSub {
    @Id
    private int id;
    @Column("course_term_id")
    private int courseTermId;
    @Column("course_code")
    private String courseCode;
    @Column("max_size")
    private int maxSize;
    @Column("now_size")
    private int nowSize;
    @Column("classroom")
    private String classroom;
    @Column("status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getNowSize() {
        return nowSize;
    }

    public void setNowSize(int nowSize) {
        this.nowSize = nowSize;
    }

    public String getClassroom() {
        return classroom;
    }

    public CourseSub setClassroom(String classroom) {
        this.classroom = classroom;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

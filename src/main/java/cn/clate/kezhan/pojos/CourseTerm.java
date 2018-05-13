package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_courses_term_${yid}_${sid}")
public class CourseTerm {
    @Id
    private int id;
    @Column("course_id")
    private int courseId;
    @Column("desc")
    private String desc;
    @Column("max_size")
    private int maxSize;
    @Column("course_code")
    private String courseCode;
    @Column("cover_img")
    private String coverImg;
    @Column("status")
    private int status;
    @Column("count_thumbs_up")
    private int countThumbsUp;
    @Column("count_learning")
    private int countLearning;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCountThumbsUp() {
        return countThumbsUp;
    }

    public void setCountThumbsUp(int countThumbsUp) {
        this.countThumbsUp = countThumbsUp;
    }

    public int getCountLearning() {
        return countLearning;
    }

    public CourseTerm setCountLearning(int countLearning) {
        this.countLearning = countLearning;
        return this;
    }
}

package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
import java.util.Date;


@Table("kz_course_comments")
public class CourseComment {

    @Id
    private int id;

    @Column("course_id")
    private int courseId;

    @One(field = "courseId")
    private Course course;

    @Column("user_id")
    private int posterId;

    @One(field = "posterId")
    private User poster;

    @Column("content")
    private String content;

    @Column("time")
    private Date time;

    @Column("is_anno")
    private boolean isAnno;

    @Column("status")
    private int status;

    @Column("rating")
    private double rating;

    public CourseComment() {
    }

    public int getId() {
        return id;
    }

    public CourseComment setId(int id) {
        this.id = id;
        return this;
    }

    public int getCourseId() {
        return courseId;
    }

    public CourseComment setCourseId(int courseId) {
        this.courseId = courseId;
        return this;
    }

    public Course getCourse() {
        return course;
    }

    public CourseComment setCourse(Course course) {
        this.course = course;
        return this;
    }

    public int getPosterId() {
        return posterId;
    }

    public CourseComment setPosterId(int posterId) {
        this.posterId = posterId;
        return this;
    }

    public User getPoster() {
        return poster;
    }

    public CourseComment setPoster(User poster) {
        this.poster = poster;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CourseComment setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public CourseComment setTime(Date time) {
        this.time = time;
        return this;
    }

    public boolean isAnno() {
        return isAnno;
    }

    public CourseComment setAnno(boolean anno) {
        isAnno = anno;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public CourseComment setStatus(int status) {
        this.status = status;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public CourseComment setRating(double rating) {
        this.rating = rating;
        return this;
    }
}

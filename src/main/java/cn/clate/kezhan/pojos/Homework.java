package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_homework_${yid}_${sid}")
public class Homework {
    @Id
    private int id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("deadline")
    private String deadline;

    @Column("update_time")
    private String updateTime;

    @Column("viewer_count")
    private int viewerCount;

    @Column("status")
    private int status;

    @Column("course_sub_id")
    private int subCourseId;

    @One(field = "subCourseId")
    private CourseSub subCourse;

    @Column("poster_id")
    private int posterId;

    @One(field = "posterId")
    private User poster;

    public Homework() {
    }

    public int getSubCourseId() {
        return subCourseId;
    }

    public Homework setSubCourseId(int subCourseId) {
        this.subCourseId = subCourseId;
        return this;
    }

    public int getPosterId() {
        return posterId;
    }

    public Homework setPosterId(int posterId) {
        this.posterId = posterId;
        return this;
    }

    public int getId() {
        return id;
    }

    public Homework setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Homework setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Homework setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDeadline() {
        return deadline;
    }

    public Homework setDeadline(String deadline) {
        this.deadline = deadline;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Homework setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getViewerCount() {
        return viewerCount;
    }

    public Homework setViewerCount(int viewerCount) {
        this.viewerCount = viewerCount;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Homework setStatus(int status) {
        this.status = status;
        return this;
    }

    public CourseSub getSubCourse() {
        return subCourse;
    }

    public Homework setSubCourse(CourseSub subCourse) {
        this.subCourse = subCourse;
        return this;
    }

    public User getPoster() {
        return poster;
    }

    public Homework setPoster(User poster) {
        this.poster = poster;
        return this;
    }
}

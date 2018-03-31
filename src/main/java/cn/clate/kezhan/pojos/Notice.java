package cn.clate.kezhan.pojos;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.mvc.annotation.Ok;

import java.sql.Date;

@Table("kz_notices_2018_1")
public class Notice {
    @Id
    private int id;

    @Column("title")
    private String title;

    @Column("desc")
    private String description;

    @Column("poster_id")
    private int posterId;

    @One(field = "posterId")
    private User poster;

    @Column("update_time")
    private Date updateTime;

    @Column("viewer_count")
    private int viewerCount;

    @Column("course_sub_id")
    private int subCourseId;

    @One(field = "subCourseId")
    private CourseSub courseSub;

    private boolean read;

    public Notice() {
    }

    public int getId() {
        return id;
    }

    public Notice setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Notice setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Notice setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPosterId() {
        return posterId;
    }

    public Notice setPosterId(int posterId) {
        this.posterId = posterId;
        return this;
    }

    public User getPoster() {
        return poster;
    }

    public Notice setPoster(User poster) {
        this.poster = poster;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Notice setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getViewerCount() {
        return viewerCount;
    }

    public Notice setViewerCount(int viewerCount) {
        this.viewerCount = viewerCount;
        return this;
    }

    public int getSubCourseId() {
        return subCourseId;
    }

    public Notice setSubCourseId(int subCourseId) {
        this.subCourseId = subCourseId;
        return this;
    }

    public CourseSub getCourseSub() {
        return courseSub;
    }

    public Notice setCourseSub(CourseSub courseSub) {
        this.courseSub = courseSub;
        return this;
    }

    public boolean isRead() {
        return read;
    }

    public Notice setRead(boolean read) {
        this.read = read;
        return this;
    }
}

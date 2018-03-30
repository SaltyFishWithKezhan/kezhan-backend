package cn.clate.kezhan.pojos;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import java.sql.Date;

@Table("kz_notices_2018_1")
public class Notice {
    @Id
    private int id;

    @Column("title")
    private String title;

    @Column("desc")
    private String description;

    @One(field = "poster_id")
    private User poster;

    @Column("update_time")
    private Date updateTime;

    @Column("viewer_count")
    private int viewerCount;

    @Column("course_sub_id")
    private int belongToCourseId;

    public Notice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getViewerCount() {
        return viewerCount;
    }

    public void setViewerCount(int viewerCount) {
        this.viewerCount = viewerCount;
    }

    public int getBelongToCourseId() {
        return belongToCourseId;
    }

    public void setBelongToCourseId(int belongToCourseId) {
        this.belongToCourseId = belongToCourseId;
    }
}

package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("kz_circle_comments")
public class Comment {

    @Id
    private int id;

    @Column("topic_id")
    private int topicId;

    @Column("from_uid")
    private int fromUid;

    @Column("to_uid")
    private int toUid;

    @Column("content")
    private String content;

    @Column("time")
    private Date time;

    @Column("status")
    private Date status;

    public int getId() {
        return id;
    }

    public Comment setId(int id) {
        this.id = id;
        return this;
    }

    public int getTopicId() {
        return topicId;
    }

    public Comment setTopicId(int topicId) {
        this.topicId = topicId;
        return this;
    }

    public int getFromUid() {
        return fromUid;
    }

    public Comment setFromUid(int fromUid) {
        this.fromUid = fromUid;
        return this;
    }

    public int getToUid() {
        return toUid;
    }

    public Comment setToUid(int toUid) {
        this.toUid = toUid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Comment setTime(Date time) {
        this.time = time;
        return this;
    }

    public Date getStatus() {
        return status;
    }

    public Comment setStatus(Date status) {
        this.status = status;
        return this;
    }
}

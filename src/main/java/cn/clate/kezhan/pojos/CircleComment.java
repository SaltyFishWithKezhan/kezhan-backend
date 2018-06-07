package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("kz_circle_comments")
public class CircleComment {

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
    private int status;

    public int getId() {
        return id;
    }

    public CircleComment setId(int id) {
        this.id = id;
        return this;
    }

    public int getTopicId() {
        return topicId;
    }

    public CircleComment setTopicId(int topicId) {
        this.topicId = topicId;
        return this;
    }

    public int getFromUid() {
        return fromUid;
    }

    public CircleComment setFromUid(int fromUid) {
        this.fromUid = fromUid;
        return this;
    }

    public int getToUid() {
        return toUid;
    }

    public CircleComment setToUid(int toUid) {
        this.toUid = toUid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CircleComment setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public CircleComment setTime(Date time) {
        this.time = time;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public CircleComment setStatus(int status) {
        this.status = status;
        return this;
    }
}

package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("kz_comments")
public class Comment {

    @Id
    private int id;

    @Column("topic_id")
    private int topicId;

    @Column("topic_type")
    private int topicType;

    @Column("from_uid")
    private int fromUid;

    @Column("to_uid")
    private int toUid;

    @Column("content")
    private String content;

    @Column("from_uname")
    private String fromUname;

    @Column("to_uname")
    private String toUname;

    @Column("time")
    private Date time;

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

    public int getTopicType() {
        return topicType;
    }

    public Comment setTopicType(int topicType) {
        this.topicType = topicType;
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

    public String getFromUname() {
        return fromUname;
    }

    public Comment setFromUname(String fromUname) {
        this.fromUname = fromUname;
        return this;
    }

    public String getToUname() {
        return toUname;
    }

    public Comment setToUname(String toUname) {
        this.toUname = toUname;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Comment setTime(Date time) {
        this.time = time;
        return this;
    }
}

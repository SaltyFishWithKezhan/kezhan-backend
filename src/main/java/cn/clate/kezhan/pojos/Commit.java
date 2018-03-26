package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("kz_commits")
public class Commit {

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

    public Commit setId(int id) {
        this.id = id;
        return this;
    }

    public int getTopicId() {
        return topicId;
    }

    public Commit setTopicId(int topicId) {
        this.topicId = topicId;
        return this;
    }

    public int getTopicType() {
        return topicType;
    }

    public Commit setTopicType(int topicType) {
        this.topicType = topicType;
        return this;
    }

    public int getFromUid() {
        return fromUid;
    }

    public Commit setFromUid(int fromUid) {
        this.fromUid = fromUid;
        return this;
    }

    public int getToUid() {
        return toUid;
    }

    public Commit setToUid(int toUid) {
        this.toUid = toUid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Commit setContent(String content) {
        this.content = content;
        return this;
    }

    public String getFromUname() {
        return fromUname;
    }

    public Commit setFromUname(String fromUname) {
        this.fromUname = fromUname;
        return this;
    }

    public String getToUname() {
        return toUname;
    }

    public Commit setToUname(String toUname) {
        this.toUname = toUname;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Commit setTime(Date time) {
        this.time = time;
        return this;
    }
}

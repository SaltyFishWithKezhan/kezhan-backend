package cn.clate.kezhan.pojos;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("kz_circles")
public class Circle {
    @Id
    private int id;

    @Column("type_id")
    private int type;

    @Column
    private int author;

    @Column
    private String title;

    @Column
    private String desc;

    @Column
    private Date date;

    @Column("count_comment")
    private int countComment;

    @Column("count_thumbs_up")
    private int countThumbsUp;

    @Column
    private int status;

    public int getId() {
        return id;
    }

    public Circle setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public Circle setType(int type) {
        this.type = type;
        return this;
    }

    public int getAuthor() {
        return author;
    }

    public Circle setAuthor(int author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Circle setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Circle setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Circle setDate(Date date) {
        this.date = date;
        return this;
    }

    public int getCountComment() {
        return countComment;
    }

    public Circle setCountComment(int countComment) {
        this.countComment = countComment;
        return this;
    }

    public int getCountThumbsUp() {
        return countThumbsUp;
    }

    public Circle setCountThumbsUp(int countThumbsUp) {
        this.countThumbsUp = countThumbsUp;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Circle setStatus(int status) {
        this.status = status;
        return this;
    }
}

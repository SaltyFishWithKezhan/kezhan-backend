package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_assistant_${yid}_${sid}")
public class Assistant {
    @Id
    private int id;
    @Column("user_id")
    private int userId;
    @One(field = "userId")
    private User assistant;
    @Column("sub_course_term_id")
    private int subCourseTermId;
    @Column("status")
    private int status;

    public Assistant() {
    }

    public User getAssistant() {
        return assistant;
    }

    public Assistant setAssistant(User assistant) {
        this.assistant = assistant;
        return this;
    }

    public int getId() {
        return id;
    }

    public Assistant setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Assistant setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getSubCourseTermId() {
        return subCourseTermId;
    }

    public Assistant setSubCourseTermId(int subCourseTermId) {
        this.subCourseTermId = subCourseTermId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Assistant setStatus(int status) {
        this.status = status;
        return this;
    }
}

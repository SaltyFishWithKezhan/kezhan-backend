package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_representative_${yid}_${sid}")
public class Representative {
    @Id
    private int id;
    @Column("user_id")
    private int userId;
    @Column("sub_course_term_id")
    private int subCourseTermId;
    @Column("status")
    private int status;

    public int getId() {
        return id;
    }

    public Representative setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Representative setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getSubCourseTermId() {
        return subCourseTermId;
    }

    public Representative setSubCourseTermId(int subCourseTermId) {
        this.subCourseTermId = subCourseTermId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Representative setStatus(int status) {
        this.status = status;
        return this;
    }
}

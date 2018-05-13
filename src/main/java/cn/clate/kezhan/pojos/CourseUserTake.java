package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by 蛟川小盆友 on 2018/3/25.
 */
@Table("kz_courses_term_${yid}_${sid}_users_take")
public class CourseUserTake {
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

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubCourseTermId() {
        return subCourseTermId;
    }

    public void setSubCourseTermId(int subCourseTermId) {
        this.subCourseTermId = subCourseTermId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

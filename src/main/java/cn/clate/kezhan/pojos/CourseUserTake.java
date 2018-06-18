package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_courses_term_${yid}_${sid}_users_take")
public class CourseUserTake {
    @Id
    private int id;
    @Column("user_id")
    private int userId;
    @Column("sub_course_term_id")
    private int subCourseTermId;
    @Column("is_repre")
    private boolean isRepre;
    @Column("status")
    private int status;

    public CourseUserTake() {
    }

    public int getId() {
        return id;
    }

    public CourseUserTake setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public CourseUserTake setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getSubCourseTermId() {
        return subCourseTermId;
    }

    public CourseUserTake setSubCourseTermId(int subCourseTermId) {
        this.subCourseTermId = subCourseTermId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public CourseUserTake setStatus(int status) {
        this.status = status;
        return this;
    }

    public boolean getIsRepre() {
        return isRepre;
    }

    public CourseUserTake setIsRepre(boolean isRepre) {
        this.isRepre = isRepre;
        return this;
    }
}

package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

/**
 * Created by 蛟川小盆友 on 2018/3/25.
 */
@Table("kz_courses_term_${yid}_${sid}_time_slots")
public class CourseTimeSlot {
    @Id
    private int id;
    @Column("sub_course_term_id")
    private int subCourseTermId;
    @Column("start_date")
    private Date startDate;
    @Column("end_date")
    private Date endDate;
    @Column("start_time")
    private int startTime;
    @Column("end_time")
    private int endTime;
    @Column("status")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubCourseTermId() {
        return subCourseTermId;
    }

    public void setSubCourseTermId(int subCourseTermId) {
        this.subCourseTermId = subCourseTermId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getIsOdd() {
        return isOdd;
    }

    public void setIsOdd(int isOdd) {
        this.isOdd = isOdd;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStatus() {
        return status;
    }

    public CourseTimeSlot setStatus(int status) {
        this.status = status;
        return this;
    }

    @Column("is_odd")
    private int isOdd;
    @Column("day")
    private int day;

}

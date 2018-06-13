package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_recommend_mapping")
public class CourseRecommendMapping {
    @Column("course")
    private int courseId;
    @Column("recommend")
    private int recommendId;
    @Column("score")
    private double score;

    public CourseRecommendMapping() {
    }

    public int getCourseId() {
        return courseId;
    }

    public CourseRecommendMapping setCourseId(int courseId) {
        this.courseId = courseId;
        return this;
    }

    public int getRecommendId() {
        return recommendId;
    }

    public CourseRecommendMapping setRecommendId(int recommendId) {
        this.recommendId = recommendId;
        return this;
    }

    public double getScore() {
        return score;
    }

    public CourseRecommendMapping setScore(double score) {
        this.score = score;
        return this;
    }
}

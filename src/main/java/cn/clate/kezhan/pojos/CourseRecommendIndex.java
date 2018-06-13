package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_course_recommend_index")
public class CourseRecommendIndex {
    @Id
    private int id;

    @Column("type")
    private int type;

    @Column("type_id")
    private int typeId;

    @Column("title")
    private String title;

    @Column("labels")
    private String labels;

    public CourseRecommendIndex() {
    }

    public int getId() {
        return id;
    }

    public CourseRecommendIndex setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public CourseRecommendIndex setType(int type) {
        this.type = type;
        return this;
    }

    public int getTypeId() {
        return typeId;
    }

    public CourseRecommendIndex setTypeId(int typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CourseRecommendIndex setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLabels() {
        return labels;
    }

    public CourseRecommendIndex setLabels(String labels) {
        this.labels = labels;
        return this;
    }
}

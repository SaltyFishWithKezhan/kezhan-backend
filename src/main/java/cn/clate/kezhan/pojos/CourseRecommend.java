package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;

public class CourseRecommend extends DummyCourse{
    @Id
    private int id;

    @Column("title")
    private String title;

    @Column("url")
    private String url;

    @Column("url_object_id")
    private String urlObjectId;

    @Column("front_image_url")
    private String frontImageUrl;

    @Column("front_image_path")
    private String frontImagePath;

    @Column("labels")
    private String labels;

    @Column("attend_count")
    private int attendCount;

    @Column("rating")
    private double rating;

    @Column("description")
    private String description;

    public CourseRecommend() {
    }

    public String getLabels() {
        return labels;
    }

    public CourseRecommend setLabels(String labels) {
        this.labels = labels;
        return this;
    }

    public int getAttendCount() {
        return attendCount;
    }

    public CourseRecommend setAttendCount(int attendCount) {
        this.attendCount = attendCount;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public CourseRecommend setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseRecommend setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getCategory() {
        return category;
    }

    public CourseRecommend setCategory(int category) {
        this.category = category;
        return this;
    }

    public int getId() {
        return id;
    }

    public CourseRecommend setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CourseRecommend setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public CourseRecommend setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrlObjectId() {
        return urlObjectId;
    }

    public CourseRecommend setUrlObjectId(String urlObjectId) {
        this.urlObjectId = urlObjectId;
        return this;
    }

    public String getFrontImageUrl() {
        return frontImageUrl;
    }

    public CourseRecommend setFrontImageUrl(String frontImageUrl) {
        this.frontImageUrl = frontImageUrl;
        return this;
    }

    public String getFrontImagePath() {
        return frontImagePath;
    }

    public CourseRecommend setFrontImagePath(String frontImagePath) {
        this.frontImagePath = frontImagePath;
        return this;
    }
}

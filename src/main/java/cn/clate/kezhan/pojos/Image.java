package cn.clate.kezhan.pojos;


import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_image_2018_1")
public class Image {
    @Id
    private int id;

    @Column
    private String url;

    @Column
    private String path;

    @Column
    private int type;

    @Column("related_id")
    private int related_id;

    @Column("upload_time")
    private String upload_time;

    @Column
    private int status;

    public int getId() {
        return id;
    }

    public Image setId(int id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Image setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Image setPath(String path) {
        this.path = path;
        return this;
    }

    public int getType() {
        return type;
    }

    public Image setType(int type) {
        this.type = type;
        return this;
    }

    public int getRelated_id() {
        return related_id;
    }

    public Image setRelated_id(int related_id) {
        this.related_id = related_id;
        return this;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public Image setUpload_time(String upload_time) {
        this.upload_time = upload_time;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Image setStatus(int status) {
        this.status = status;
        return this;
    }
}

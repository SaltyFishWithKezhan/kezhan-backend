package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import java.sql.Date;

@Table("kz_resources")
public class Resource {
    @Id
    private int id;

    @Column("update_time")
    private Date updateTime;

    @Column("poster_id")
    private int posterId;

    @One(field = "posterId")
    private User poster;

    @Column("file_loc")
    private String fileLoc;

    @Column("file__name")
    private String fileName;

    @Column("file_type")
    private String fileType;

    @Column("course_id")
    private int courseId;

    @Column("download_count")
    private int downloadCount;

    public Resource() {
    }

    public int getId() {
        return id;
    }

    public Resource setId(int id) {
        this.id = id;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Resource setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getPosterId() {
        return posterId;
    }

    public Resource setPosterId(int posterId) {
        this.posterId = posterId;
        return this;
    }

    public User getPoster() {
        return poster;
    }

    public Resource setPoster(User poster) {
        this.poster = poster;
        return this;
    }

    public String getFileLoc() {
        return fileLoc;
    }

    public Resource setFileLoc(String fileLoc) {
        this.fileLoc = fileLoc;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Resource setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public Resource setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public int getCourseId() {
        return courseId;
    }

    public Resource setCourseId(int courseId) {
        this.courseId = courseId;
        return this;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public Resource setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }
}

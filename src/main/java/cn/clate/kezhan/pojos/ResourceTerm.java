package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_resources_${yid}_${sid}")
public class ResourceTerm {
    @Id
    private int id;

    @Column("upload_time")
    private String uploadTime;

    @Column("poster_id")
    private int posterId;

    @One(field = "posterId")
    private User poster;

    @Column("file_loc")
    private String fileLoc;

    @Column("file_name")
    private String fileName;

    @Column("file_type")
    private String fileType;

    @Column("course_term_id")
    private int courseTermId;

    @Column("download_count")
    private int downloadCount;

    public ResourceTerm() {
    }

    public int getId() {
        return id;
    }

    public ResourceTerm setId(int id) {
        this.id = id;
        return this;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public ResourceTerm setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }

    public int getPosterId() {
        return posterId;
    }

    public ResourceTerm setPosterId(int posterId) {
        this.posterId = posterId;
        return this;
    }

    public User getPoster() {
        return poster;
    }

    public ResourceTerm setPoster(User poster) {
        this.poster = poster;
        return this;
    }

    public String getFileLoc() {
        return fileLoc;
    }

    public ResourceTerm setFileLoc(String fileLoc) {
        this.fileLoc = fileLoc;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ResourceTerm setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public ResourceTerm setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public int getCourseTermId() {
        return courseTermId;
    }

    public ResourceTerm setCourseTermId(int courseTermId) {
        this.courseTermId = courseTermId;
        return this;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public ResourceTerm setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }
}

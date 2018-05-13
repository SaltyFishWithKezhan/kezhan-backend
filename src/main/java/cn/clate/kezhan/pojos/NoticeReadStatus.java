package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_notices_read_status_${yid}_${sid}")
@PK({"userId", "noticeId"})
public class NoticeReadStatus {
    @Column("user_id")
    private int userId;

    @Column("notice_id")
    private int noticeId;

    @Column("status")
    private int status;

    public NoticeReadStatus() {
    }

    public int getUserId() {
        return userId;
    }

    public NoticeReadStatus setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public NoticeReadStatus setNoticeId(int noticeId) {
        this.noticeId = noticeId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public NoticeReadStatus setStatus(int status) {
        this.status = status;
        return this;
    }
}

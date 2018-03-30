package cn.clate.kezhan.pojos;

import cn.clate.kezhan.utils.Tools;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_phone_verification")
public class Phone {

    @Id
    private int id;

    @Column("phone_number")
    private String phone;

    @Column("verification_code")
    private String code;

    @Column("request_time")
    private long requestTime;

    @Column("request_ip")
    private String requestIp;

    @Column("type")
    private int type;

    @Column("related_id")
    private long relatedId;

    @Column("status")
    private int status;

    public int getId() {
        return id;
    }

    public Phone setId(int id) {
        this.id = id;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Phone setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Phone setCode(String code) {
        this.code = code;
        return this;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public Phone setRequestIp(String requestIp) {
        this.requestIp = requestIp;
        return this;
    }

    public Phone setRequestTime(long requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public int getType() {
        return type;
    }

    public Phone setType(int type) {
        this.type = type;
        return this;
    }

    public long getRelatedId() {
        return relatedId;
    }

    public Phone setRelatedId(long relatedId) {
        this.relatedId = relatedId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Phone setStatus(int status) {
        this.status = status;
        return this;
    }

    public void updateStatus(){
        setStatus(1);
        setRequestTime(Tools.getTimeStamp());
        setRequestIp(Tools.getRemoteAddr());
    }

    public void initRequestInfo(){
        setRequestTime(Tools.getTimeStamp());
        setRequestIp(Tools.getRemoteAddr());
    }



}

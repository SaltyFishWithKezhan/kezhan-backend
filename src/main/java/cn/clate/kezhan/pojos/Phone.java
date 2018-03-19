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

    @Name
    private String phone;

    @Column("verification_code")
    private String code;

    @Column("last_active_time")
    private long lastActive;

    @Column("is_confirmed")
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

    public long getLastActive() {
        return lastActive;
    }

    public Phone setLastActive(long lastActive) {
        this.lastActive = lastActive;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Phone setStatus(int status) {
        this.status = status;
        return this;
    }

    public void updateLastActiveTime(){
        this.lastActive = Tools.getTimeStamp();
    }


}

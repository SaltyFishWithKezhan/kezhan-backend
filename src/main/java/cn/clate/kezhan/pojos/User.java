package cn.clate.kezhan.pojos;

import cn.clate.kezhan.utils.Tools;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.Date;

@Table("kz_users")
public class User {
    @Id
    private int id;

    @Name
    private String username;

    @Column
    private String password;

    @Column
    private int type;

    @Column
    private int gender;

    @Column
    private Date birthday;

    @Column
    private String avatar;

    @Column
    private String college;

    @Column("class_name")
    private String className;

    @Column("stu_id")
    private String stuId;

    @Column("real_name")
    private String realName;

    @Column
    private String signature;

    @Column("now_login")
    private long nowLogin;

    @Column("now_login_ip")
    private String nowLoginIp;

    @Column("last_login")
    private long lastLogin;

    @Column("last_login_ip")
    private String lastLoginIp;

    @Column("login_times")
    private long loginTimes;

    @Column
    private String phone;

    @Column("last_active_time")
    private int lastActiveTime;

    @Column("access_token")
    private String accessToken;

    public void removeCriticalInfo(){
        this.birthday = null;
        this.password = null;
        this.signature = null;
        this.nowLogin = -1;
        this.nowLoginIp = null;
        this.lastLogin = -1;
        this.lastLoginIp = null;
        this.loginTimes = -1;
        this.phone = null;
        this.lastActiveTime = -1;
        this.accessToken = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getNowLogin() {
        return nowLogin;
    }

    public void setNowLogin(long nowLogin) {
        this.nowLogin = nowLogin;
    }

    public String getNowLoginIp() {
        return nowLoginIp;
    }

    public void setNowLoginIp(String nowLoginIp) {
        this.nowLoginIp = nowLoginIp;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public long getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(long loginTimes) {
        this.loginTimes = loginTimes;
    }

    public void updateLoginStatus(){
        this.loginTimes += 1;
        this.lastLogin = this.nowLogin;
        this.lastLoginIp = this.nowLoginIp;
        this.nowLogin = Tools.getTimeStamp();
        this.nowLoginIp = Tools.getRemoteAddr();
        this.lastActiveTime = (int)Tools.getTimeStamp();
    }

    public void initLoginStatus(){
        this.loginTimes =0;
        this.nowLogin = Tools.getTimeStamp();
        this.nowLoginIp = Tools.getRemoteAddr();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(int lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

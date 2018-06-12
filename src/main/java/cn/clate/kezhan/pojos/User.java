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

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getType() {
        return type;
    }

    public User setType(int type) {
        this.type = type;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public User setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getCollege() {
        return college;
    }

    public User setCollege(String college) {
        this.college = college;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public User setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getStuId() {
        return stuId;
    }

    public User setStuId(String stuId) {
        this.stuId = stuId;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public User setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public long getNowLogin() {
        return nowLogin;
    }

    public User setNowLogin(long nowLogin) {
        this.nowLogin = nowLogin;
        return this;
    }

    public String getNowLoginIp() {
        return nowLoginIp;
    }

    public User setNowLoginIp(String nowLoginIp) {
        this.nowLoginIp = nowLoginIp;
        return this;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public User setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public User setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public long getLoginTimes() {
        return loginTimes;
    }

    public User setLoginTimes(long loginTimes) {
        this.loginTimes = loginTimes;
        return this;
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

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getLastActiveTime() {
        return lastActiveTime;
    }

    public User setLastActiveTime(int lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}

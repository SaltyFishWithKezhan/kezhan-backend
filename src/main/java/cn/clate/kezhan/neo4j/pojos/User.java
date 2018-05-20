package cn.clate.kezhan.neo4j.pojos;


import java.util.Date;
import java.util.Set;

public class User {

    private int id;

    private String username;

    private String password;

    private int type;

    private int gender;

    private Date birthday;

    private String avatar;

    private String college;

    private String className;

    private String stuId;

    private String realName;

    private String signature;

    private long nowLogin;

    private String nowLoginIp;

    private long lastLogin;

    private String lastLoginIp;

    private long loginTimes;

    private String phone;

    private int lastActiveTime;

    private String accessToken;

    Set<User> friends;

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

    public Set<User> getFriends() {
        return friends;
    }

    public User setFriends(Set<User> friends) {
        this.friends = friends;
        return this;
    }
}
package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_teachers")
public class Teacher {
    @Id
    private int id;

    @Name
    private String name;

    @Column("name_en")
    private String nameEn;

    @Column("teacher_id")
    private String teacherId;

    @Column
    private String phone;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String desc;

    @Column
    private String avatar;

    @Column("is_active")
    private int isActive;

    @Column("user_id")
    private int userId;

    public Teacher() {
    }

    public int getId() {
        return id;
    }

    public Teacher setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Teacher setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Teacher setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public Teacher setTeacherId(String teacherId) {
        this.teacherId = teacherId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Teacher setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Teacher setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Teacher setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Teacher setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Teacher setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public int getIsActive() {
        return isActive;
    }

    public Teacher setIsActive(int isActive) {
        this.isActive = isActive;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Teacher setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}

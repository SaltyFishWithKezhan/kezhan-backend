package cn.clate.kezhan.neo4j.pojos;


import java.util.Set;

public class User {

    private Long id;

    private String username;

    private String realName;

    private String phone;

    private int status;


    Set<User> friends;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
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

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public User setStatus(int status) {
        this.status = status;
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
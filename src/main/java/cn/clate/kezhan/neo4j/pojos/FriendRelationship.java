package cn.clate.kezhan.neo4j.pojos;


import java.util.Date;

public class FriendRelationship {

    User startUser;

    User endUser;

    private int type;

    private Date startDate;

    public User getStartUser() {
        return startUser;
    }

    public FriendRelationship setStartUser(User startUser) {
        this.startUser = startUser;
        return this;
    }

    public User getEndUser() {
        return endUser;
    }

    public FriendRelationship setEndUser(User endUser) {
        this.endUser = endUser;
        return this;
    }

    public int getType() {
        return type;
    }

    public FriendRelationship setType(int type) {
        this.type = type;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public FriendRelationship setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }
}

package cn.clate.kezhan.neo4j.pojos;


public class FriendRelationship {

    User startUser;

    User endUser;

    private int type;

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
}

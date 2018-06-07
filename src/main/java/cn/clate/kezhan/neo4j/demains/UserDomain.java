package cn.clate.kezhan.neo4j.demains;

import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import cn.clate.kezhan.neo4j.pojos.User;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class UserDomain {


    private final static String ADD_USER = "CREATE (u:User) " +
            "SET u.username = $username , u.phone = $phone , u.realName = $realName " +
            "RETURN u.username + ', from node ' + id(u)";

    private final static String ADD_FRIEND_RELATIONSHIP = "MATCH (u1:User) WHERE u1.username = $username1 " +
            "MATCH (u2:User) WHERE u2.username = $username2 " +
            "WITH u1, u2 " +
            "CREATE (u1)-[f:FRIEND]->(u2) " +
            "SET f.startUser = $startUser, f.endUser = $endUser " +
            "RETURN id(f) + ', from node ' + id(f)";

    public static void addUser(User user) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String ret = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_USER,
                            parameters("username",user.getUsername(), "phone",user.getPhone(),
                                    "realName",user.getRealName()));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(ret);
        }
    }

    public static void addFriendRs(User user1,User user2) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_FRIEND_RELATIONSHIP,
                            parameters("username1", user1.getUsername(), "username2", user2.getUsername(),
                                    "startUser",user1.getUsername(), "endUser",user2.getUsername()));
                    return result.next().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static void main(String... args) throws Exception {
        User user = new User();
        user.setRealName("胡狗子").setUsername("hhw").setPhone("137****4233");
        addUser(user);
        User user1 = new User();
        User user2 = new User();
        user1.setUsername("cyj");
        user2.setUsername("hhw");
        addFriendRs(user1,user2);
    }

}

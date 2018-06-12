package cn.clate.kezhan.neo4j.domains;

import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import cn.clate.kezhan.pojos.User;
import org.neo4j.driver.v1.*;

import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

public class UserDomain {

    private final static String ADD_USER = "CREATE (u:User) " +
            "SET u.userId = $userId ,u.username = $username , u.phone = $phone , u.realName = $realName " +
            "RETURN u.username + ', from node ' + id(u)";

    private final static String ADD_FRIEND_RELATIONSHIP = "MATCH (u1:User) WHERE u1.userId = $userId1 " +
            "MATCH (u2:User) WHERE u2.userId = $userId2 " +
            "WITH u1, u2 " +
            "CREATE (u1)-[f:FRIEND]->(u2) " +
            "SET f.startUserId = $startUserId, f.endUserId = $endUserId " +
            "RETURN id(f) + ', from node ' + id(f)";


    public static void addUser(User user) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String ret = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_USER,
                            parameters("userId",user.getId(), "username",user.getUsername(),
                                    "phone",user.getPhone(), "realName",user.getRealName()));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(ret);
        }
    }

    public static void addFriendRs(int uid1,int uid2) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_FRIEND_RELATIONSHIP,
                            parameters("userId1",uid1, "userId2", uid2,
                                    "startUserId",uid1, "endUserId",uid2));
                    return result.next().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static void main(String... args) throws Exception {
        addFriendRs(8,11);
    }

}

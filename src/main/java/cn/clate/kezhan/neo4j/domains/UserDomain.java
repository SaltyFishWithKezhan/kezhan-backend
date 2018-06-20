package cn.clate.kezhan.neo4j.domains;

import cn.clate.kezhan.domains.user.UserInfoDomain;
import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.neo4j.driver.v1.Values.parameters;

public class UserDomain {

    private final static String ADD_USER = "CREATE (u:User) " +
            "SET u.userId = $userId ,u.username = $username , u.phone = $phone , u.realName = $realName " +
            " RETURN u.username + ', from node ' + id(u)";

    private final static String ADD_FRIEND_RELATIONSHIP = "MATCH (u1:User) WHERE u1.userId = $userId1 " +
            "MATCH (u2:User) WHERE u2.userId = $userId2 " +
            "WITH u1, u2 " +
            "CREATE (u1)-[f:FRIEND]->(u2) " +
            "SET f.startUserId = $startUserId, f.endUserId = $endUserId " +
            " RETURN id(f) + ', from node ' + id(f)";

    private final static String GET_USER_BY_ID = "MATCH (u:User) " +
            "WHERE u.userId = $userId " +
            " RETURN u.userId, u.username, u.phone, u.realName";

    private final static String FIND_THREE_DEGREE_RELATIONSHIP = "MATCH (:User{userId:$userId})-[*..3]-(u:User) " +
            " RETURN u.userId, u.username, u.phone, u.realName";

    public static void addUser(User user) {
        if(getUserById(user.getId())==null){
            try (Session session = Neo4jDriver.getInstance().session()) {
                String ret = session.writeTransaction(new TransactionWork<String>() {
                    @Override
                    public String execute(Transaction tx) {
                        StatementResult result = tx.run(ADD_USER,
                                parameters("userId", user.getId(), "username", user.getUsername(),
                                        "phone", user.getPhone(), "realName", user.getRealName()));
                        return result.single().get(0).asString();
                    }
                });
                System.out.println(ret);
            }
        }else {
            System.out.println("neo4j user has existed! ");
        }
    }

    public static void addFriendRs(int uid1, int uid2) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String ret = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    StatementResult result = tx.run(ADD_FRIEND_RELATIONSHIP,
                            parameters("userId1", uid1, "userId2", uid2,
                                    "startUserId", uid1, "endUserId", uid2));
                    return result.next().get(0).asString();
                }
            });
            System.out.println(ret);
        }
    }

    public static User getUserById(int uid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            User ret = session.writeTransaction(new TransactionWork<User>() {
                @Override
                public User execute(Transaction tx) {
                    StatementResult result = tx.run(GET_USER_BY_ID,
                            parameters("userId", uid));
                    if (result.hasNext()) {
                        Record record = result.next();
                        User user = new User();
                        user.setId(record.get("u.userId").asInt()).setUsername(record.get("u.username").asString()).
                                setPhone(record.get("u.phone").asString()).setRealName(record.get("u.realName").asString());
                        return user;
                    }
                    return null;
                }
            });
            return ret;
        }
    }


    public static List<Integer> findThreeDegreeRsByUserId(int uid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            List<Integer> ret = session.writeTransaction(new TransactionWork<List<Integer>>() {
                @Override
                public List<Integer> execute(Transaction tx) {
                    List<Integer> uidList = new ArrayList<>();
                    StatementResult result = tx.run(FIND_THREE_DEGREE_RELATIONSHIP,
                            parameters("userId", uid));
                    int t = result.next().get("u.userId").asInt();
                    uidList.add(t);
                    try {
                        while (result.hasNext()) {
                            int t1 = result.next().get("u.userId").asInt();
                            uidList.add(t1);
                        }
                        return uidList;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return uidList;
                    }
                }
            });
            System.out.println(ret.toString());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String... args) throws Exception {
        Dao dao = DaoFactory.get();
        List<User> user = dao.query(User.class, null);
        Random random = new Random();
        int totalCount = user.size();
        for (int i = 0; i < 100; i++){
            int count = 7;
            while(count > 0){
                int j = (int) (Math.random()*7000) % 3661;
                if (j != i && j >= 0 && j < 400){
                    System.out.println("From: " + user.get(i).getId() + " To: " + user.get(j).getId());
                    addFriendRs(user.get(i).getId(), user.get(j).getId());
                    count--;
                }
            }

        }
    }

}

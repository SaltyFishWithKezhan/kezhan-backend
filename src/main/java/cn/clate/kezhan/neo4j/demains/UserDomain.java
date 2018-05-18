package cn.clate.kezhan.neo4j.demains;

import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class UserDomain {

    public static void  printGreeting(final String message) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    StatementResult result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static void main(String... args) throws Exception {
        printGreeting("hello, world");
    }
}

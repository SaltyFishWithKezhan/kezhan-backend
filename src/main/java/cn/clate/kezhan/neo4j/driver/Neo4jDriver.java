package cn.clate.kezhan.neo4j.driver;

import cn.clate.kezhan.neo4j.demains.UserDomain;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class Neo4jDriver implements AutoCloseable{
    private static Driver driver;

    private Neo4jDriver() {}

    public static synchronized Driver getInstance() {
        if (driver == null) {
            driver = GraphDatabase.driver("bolt://localhost:7687",AuthTokens.basic("neo4j", "123456"));
        }
        return driver;
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}

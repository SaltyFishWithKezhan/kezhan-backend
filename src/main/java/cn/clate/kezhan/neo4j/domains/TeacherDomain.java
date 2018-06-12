package cn.clate.kezhan.neo4j.domains;

import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import cn.clate.kezhan.pojos.Course;
import cn.clate.kezhan.pojos.Teacher;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import static org.neo4j.driver.v1.Values.parameters;

public class TeacherDomain {

    private final static String ADD_TEACHER = "CREATE (t:Teacher) " +
            "SET t.teacherId = $teacherId ,t.teacherName = $teacherName , t.phone = $phone " +
            "RETURN t.teacherName + ', from node ' + id(t)";

    private final static String ADD_TEACH_COURSE = "MATCH (t:Teacher) WHERE t.teacherId = $teacherId " +
            "MATCH (c:Course) WHERE c.courseId = $courseId " +
            "WITH t, c " +
            "CREATE (t)-[f:TEACH]->(c) " +
            "SET f.teacherId = $teacherId, f.courseId = $courseId " +
            "RETURN id(f) + ', from node ' + id(f)";

    public static void addTeacher(Teacher teacher) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String ret = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_TEACHER,
                            parameters("teacherId",teacher.getId(), "teacherName",teacher.getName(),
                                    "phone",teacher.getPhone()));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(ret);
        }
    }

    public static void addTeachCourseRs(int tid, int cid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_TEACH_COURSE,
                            parameters("teacherId", tid, "courseId", cid,
                                    "teacherId",tid, "courseId",cid));
                    return result.next().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }


    public static void main(String... args) throws Exception {

    }

}

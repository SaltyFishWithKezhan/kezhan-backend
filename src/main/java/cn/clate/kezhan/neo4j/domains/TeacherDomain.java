package cn.clate.kezhan.neo4j.domains;

import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import cn.clate.kezhan.pojos.Course;
import cn.clate.kezhan.pojos.Teacher;
import org.neo4j.driver.v1.*;

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

    private final static String GET_TEACHER_BY_ID = "MATCH (t:Teacher) " +
            "WHERE t.teacherId = $teacherId " +
            " RETURN t.teacherId, t.teacherName, t.phone ";


    public static void addTeacher(Teacher teacher) {
        if (getTeacherByTeacherId(teacher.getId()) == null) {
            try (Session session = Neo4jDriver.getInstance().session()) {
                String ret = session.writeTransaction(new TransactionWork<String>() {
                    @Override
                    public String execute(Transaction tx) {

                        StatementResult result = tx.run(ADD_TEACHER,
                                parameters("teacherId", teacher.getId(), "teacherName", teacher.getName(),
                                        "phone", teacher.getPhone()));
                        return result.single().get(0).asString();
                    }
                });
                System.out.println(ret);
            }
        } else {
            System.out.println("neo4j teacher has existed! ");
        }
    }

    public static void addTeachCourseRs(int tid, int cid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_TEACH_COURSE,
                            parameters("teacherId", tid, "courseId", cid,
                                    "teacherId", tid, "courseId", cid));
                    return result.next().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static Teacher getTeacherByTeacherId(int tid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            Teacher ret = session.writeTransaction(new TransactionWork<Teacher>() {
                @Override
                public Teacher execute(Transaction transaction) {
                    StatementResult result = transaction.run(GET_TEACHER_BY_ID,
                            parameters("teacherId", tid));
                    if (result.hasNext()) {
                        Record record = result.next();
                        Teacher teacher = new Teacher();
                        teacher.setId(record.get("t.teacherId").asInt()).setUsername(record.get("t.teacherName").asString()).
                                setPhone(record.get("u.phone").asString());
                        return teacher;
                    }
                    return null;
                }
            });
            return ret;
        }
    }

    public static void main(String... args) throws Exception {

    }

}

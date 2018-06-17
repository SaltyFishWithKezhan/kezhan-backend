package cn.clate.kezhan.neo4j.domains;

import cn.clate.kezhan.neo4j.driver.Neo4jDriver;
import cn.clate.kezhan.pojos.Course;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class CourseDomain {

    private final static String ADD_COURSE = "CREATE (c:Course) " +
            "SET c.courseId = $courseId , c.name = $name ,c.description = $description " +
            "RETURN c.name + ', from node ' + id(c)";

    private final static String ADD_ATTEND_COURSE = "MATCH (u1:User) WHERE u1.userId = $userId " +
            "MATCH (c1:Course) WHERE c1.courseId = $courseId " +
            "WITH u1, c1 " +
            "CREATE (u1)-[f:ATTEND]->(c1) " +
            "SET f.studentId = $student, f.courseId = $course " +
            "RETURN id(f) + ', from node ' + id(f)";

    private final static String GET_COURSE_BY_COURSE_ID = "MATCH (c:Course) " +
            "WHERE c.courseId = $courseId " +
            " RETURN c.courseId, c.name, c.description ";

    public static void addCourse(Course course) {
        if (getCourseByCourseId(course.getId()) == null) {
            try (Session session = Neo4jDriver.getInstance().session()) {
                String ret = session.writeTransaction(new TransactionWork<String>() {
                    @Override
                    public String execute(Transaction tx) {

                        StatementResult result = tx.run(ADD_COURSE,
                                parameters("courseId", course.getId(), "name", course.getName(),
                                        "description", course.getDesc()));
                        return result.single().get(0).asString();
                    }
                });
                System.out.println(ret);
            }
        } else {
            System.out.println("neo4j course has existed! ");
        }
    }

    public static void addAttendCourseRs(int uid, int cid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {

                    StatementResult result = tx.run(ADD_ATTEND_COURSE,
                            parameters("userId", uid, "courseId", cid,
                                    "student", uid, "course", cid));
                    return result.next().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

    public static Course getCourseByCourseId(int cid) {
        try (Session session = Neo4jDriver.getInstance().session()) {
            Course ret = session.writeTransaction(new TransactionWork<Course>() {
                @Override
                public Course execute(Transaction tx) {
                    StatementResult result = tx.run(GET_COURSE_BY_COURSE_ID,
                            parameters("courseId", cid));
                    if (result.hasNext()) {
                        Record record = result.next();
                        Course course = new Course();
                        course.setId(record.get("c.courseId").asInt()).setName(record.get("c.name").asString()).
                                setDesc(record.get("c.description").asString());
                        return course;
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

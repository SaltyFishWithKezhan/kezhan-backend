<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<h4>用户登录：<br>
    http://95.163.194.157:8080/kezhan/user/login<br>
    params: <br>
    uid<br>
    utoken<br>
    username<br>
    password<br>
    <br>
    用户根据id获得个人信息<br>
    http://localhost:8080/user/getById<br>
    params:<br>
    uid<br>
    utoken<br>
    <br>
    用户发送手机验证码：<br>
    http://95.163.194.157:8080/kezhan/user/phone<br>
    params:<br>
    phone<br>
    <br>
    用户注册：<br>
    http://95.163.194.157:8080/kezhan/user/register<br>
    params:<br>
    username<br>
    password<br>
    phone<br>
    verification_code<br>
    <br>
    用户忘记密码发送手机验证码：<br>
    http://localhost:8080/user/resetPhone<br>
    params:<br>
    uid<br>
    phone<br>
    <br>
    获得所有圈子<br>
    http://localhost:8080/circle/getAll<br>
    无参数<br>
    <br>
    按页获得圈子<br>
    http://localhost:8080/circle/getByPage<br>
    params:<br>
    page_number<br>
    page_size<br>
    <br>
    根据帖子id获取评论（type=1表示是圈子类型的评论）<br>
    http://localhost:8080/circle/comments<br>
    params:<br>
    topic_type<br>
    topic_id<br>
    <br>
    发布评论<br>
    http://localhost:8080/circle/submitComment<br>
    params:<br>
    topic_type=1<br>
    topic_id=1<br>
    from_id=8<br>
    from_name=zhaoning<br>
    to_id=-1<br>
    to_name<br>
    content=喵喵喵喵喵喵<br>
    uid=8<br>
    utoken=ziTk48xKFrdkRUoG<br>
    <br>
    根据用户id查询用户的课表<br>
    http://localhost:8080/course/getAllCourseByUserId<br>
    params:<br>
    uid=8<br>
    utoken=ziTk48xKFrdkRUoG<br>
    <br>
</h4>
</body>
</html>

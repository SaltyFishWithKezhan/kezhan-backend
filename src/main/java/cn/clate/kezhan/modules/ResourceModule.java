package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.course.ResourceDomain;
import cn.clate.kezhan.filters.RoleFilter;
import cn.clate.kezhan.filters.UserAuthenication;
import cn.clate.kezhan.pojos.Resource;
import cn.clate.kezhan.pojos.ResourceTerm;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.Files;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.UploadAdaptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      佛祖保佑       永无BUG

@At("/resource")
public class ResourceModule {
    @At("/getCourseResource")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap getCourseResourceByCourseId(@Param("course_id") String courseId) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(courseId, "课程ID").require();
        validator.num(courseId, "课程ID参数不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        List<Resource> resourceList = ResourceDomain.getAllCourseResource(Integer.parseInt(courseId));
        return Ret.s("success", new ArrayList<>(resourceList));
    }

    @At("/getCourseTermResource")
    @Ok("json")
    @Filters(@By(type = RoleFilter.class, args = {"scene:" + RoleFilter.SCENE_MORE_THEN_OR_EQUAL_STUDENT_IN_SUB_COURSE}))
    public NutMap getCourseTermResourceByCourseTermId(@Param("sbid") String subCourseId, @Param(df = "-1", value = "year") String yid, @Param(df = "-1", value = "semester") String sid) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(subCourseId, "学期课程ID").require();
        validator.num(subCourseId, "参数不合法");
        if (!validator.check()) {
            return Ret.e(0, validator.getError());
        }
        List<ResourceTerm> resourceList = ResourceDomain.getAllTermCourseResource(Integer.parseInt(subCourseId), Integer.parseInt(yid), Integer.parseInt(sid));
        return Ret.s("success", new ArrayList<>(resourceList));
    }

    @At("/uploadCourseResource")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:fileUpload"})
    public NutMap uploadCourseResource(@Param("uid") String posterId, @Param("course_id") String courseId, @Param("resource") File file) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(posterId, "用户id").require().min(5).max(16);
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        if (file != null && !file.exists()) {
            return Ret.e(44, "文件不存在");
        }
        String imageName = file.getName();
        String surfix = imageName.substring(imageName.lastIndexOf("."), imageName.length());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String path = "/www/server/jetty/webapps";
//        String avatar = "/static/resource/" + uuid + surfix;
        String avatar = "/src/main/webapp/userImg/" + uuid + surfix;
        File target = new File(avatar);
        Files.copy(file, target);
        Resource resource = new Resource().
                //setUpLoadTime(new Date(System.currentTimeMillis())).
                        setPosterId(Integer.parseInt(posterId)).
                        setCourseId(Integer.parseInt(courseId)).
                        setFileLoc(Conf.get("user.avatarUrl") + avatar).
                        setFileName(imageName).
                        setFileType(surfix.substring(1, surfix.length()));
        ResourceDomain.insertCourseResource(resource);
        return Ret.s(Conf.get("user.avatarUrl") + avatar);
    }

    @At("/uploadCourseTermResource")
    @Ok("json")
    @Filters(@By(type = UserAuthenication.class))
    public NutMap uploadCourseTermResource(@Param("uid") String posterId) {
        return null;
    }
}

package cn.clate.kezhan.modules;

import cn.clate.kezhan.domains.Image.ImageDomain;
import cn.clate.kezhan.pojos.Image;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.validators.SimpleValidator;
import org.nutz.lang.Files;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@At("/image")
public class ImageModule {
    @At("/uploadImg")
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    public NutMap uploadImage(@Param("type") String type, @Param("related_id") String relatedId, @Param("image") File f) {
        SimpleValidator validator = new SimpleValidator();
        validator.now(type, "类型id").require().num();
        validator.now(type, "关联对象id").require().num();
        if (!validator.check()) {
            return Ret.e(1, validator.getError());
        }
        if (f != null && !f.exists()) {
            return Ret.e(44, "文件不存在");
        }
        String imageName = f.getName();
        String surfix = imageName.substring(imageName.lastIndexOf("."), imageName.length());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //localhost本地根目录是kezhan_backend
        //remote根目录是www/server/jetty
        String path = "/www/server/jetty/webapps";
//        String path = "src/main/webapp";
        String imagePath = "/static/image/" + uuid + surfix;
        //www.clate.cn:8080/static/
        //http://95.163.194.157:8080/kezhan/userImg/*.jpg
        File target = new File(path + imagePath);
        Files.copy(f, target);
        Image image = new Image().setRelated_id(Integer.parseInt(relatedId))
                .setPath(path+imagePath)
                .setType(Integer.parseInt(type))
                .setUrl(Conf.get("user.avatarUrl")+imagePath)
                .setUpload_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .setStatus(0);
        NutMap ret = ImageDomain.updateImage(image);
        return Ret.s(ret);
    }
}

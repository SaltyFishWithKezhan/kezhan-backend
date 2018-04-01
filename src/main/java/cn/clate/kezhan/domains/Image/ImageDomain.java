package cn.clate.kezhan.domains.Image;

import cn.clate.kezhan.pojos.Image;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.io.File;

public class ImageDomain {

    public static NutMap updateImage(Image image) {
        Dao dao = DaoFactory.get();
        dao.insert(image);
        NutMap ret = new NutMap();
        ret.addv("image",image.getUrl());
        return ret;
    }
}

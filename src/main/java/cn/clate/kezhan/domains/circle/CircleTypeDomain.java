package cn.clate.kezhan.domains.circle;

import cn.clate.kezhan.pojos.Circle;
import cn.clate.kezhan.pojos.CircleType;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.factories.DaoFactory;
import cn.clate.kezhan.utils.serializer.PojoSerializer;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.List;

public class CircleTypeDomain {
    public static NutMap getAllTypes() {
        Dao dao = DaoFactory.get();
        List<CircleType> types = dao.query(CircleType.class, null);
        if (types == null)
            return null;
        ArrayList<CircleType> typeArrayList = new ArrayList<>(types);
        return Ret.s("all_types", typeArrayList);
    }

    public static NutMap getTypeById(int id) {
        Dao dao = DaoFactory.get();
        CircleType type = dao.fetch(CircleType.class, Cnd.where("id","=",id).and("status","!=",-1));
        if (type == null)
            return null;
        PojoSerializer pjsr = new PojoSerializer(type);
        NutMap ret = pjsr.allowField("id,type_name,type_name_en").get();
        return ret;
    }
}

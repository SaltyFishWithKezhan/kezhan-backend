package cn.clate.kezhan.utils.serializer;

import cn.clate.kezhan.utils.Tools;
import org.nutz.lang.util.NutMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PojoSerializer {
    private Object mPojo = null;
    private NutMap mNutMap = null;
    public PojoSerializer(Class<Object> c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this(c.getDeclaredConstructor().newInstance());
    }

    public PojoSerializer(Object mPojo) {
        this.mPojo = mPojo;
        mNutMap = new NutMap();
        process();
    }

    public Object getPojo(){
        return  mPojo;
    }

    public PojoSerializer allowField(String fields){
        NutMap newMap = (NutMap) mNutMap.clone();
        String fieldsp[] = fields.split(",");
        String fieldspCamel[] = new String[fieldsp.length];
        for (int i=0; i<fieldsp.length; i++) {
            fieldsp[i] = fieldsp[i].trim();
            fieldspCamel[i] = Tools.camel2Underline(fieldsp[i]);
        }
        Iterator iter = mNutMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            if(!contain(fieldsp, (String) entry.getKey()) && !contain(fieldspCamel, (String) entry.getKey())){
                newMap.remove(entry.getKey());
            }
        }
        mNutMap = newMap;
        return this;
    }

    public PojoSerializer removeField(String fields){
        NutMap newMap = (NutMap) mNutMap.clone();
        String fieldsp[] = fields.split(",");
        String fieldspCamel[] = new String[fieldsp.length];
        for (int i=0; i<fieldsp.length; i++) {
            fieldsp[i] = fieldsp[i].trim();
            fieldspCamel[i] = Tools.camel2Underline(fieldsp[i]);
            if(mNutMap.has(fieldsp[i])){
                newMap.remove(fieldsp[i]);
            }else if(mNutMap.has(fieldspCamel[i])){
                newMap.remove(fieldspCamel[i]);
            }
        }
        mNutMap = newMap;
        return this;
    }

    public NutMap get(){
        return mNutMap;
    }



    private static boolean contain(String[] array,String str){
        for(int i=0;i<array.length;i++){
            if(array[i].equals(str)){
                return true;
            }
        }
        return false;
    }

    private void process(){
        Class c;
        c = mPojo.getClass();
        Field[] fields = c.getDeclaredFields();

        NutMap ret = new NutMap();
        for (Field field:fields) {
            try {
                field.setAccessible(true);
                ret.addv(Tools.camel2Underline(field.getName()), field.get(mPojo));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mNutMap = ret;
    }
}

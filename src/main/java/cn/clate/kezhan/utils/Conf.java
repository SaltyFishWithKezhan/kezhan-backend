package cn.clate.kezhan.utils;

import org.nutz.conf.NutConf;

import java.lang.reflect.Type;

public class Conf {
    private static boolean isLoaded = false;

    private static void load(){
        NutConf.load("/conf/");
        isLoaded = true;
    }

    public static Object get(String key){
        return get(key, null);
    }

    public static Object get(String key, Type type){
        if(!isLoaded){
            load();
        }
        return NutConf.get(key, type);
    }
}

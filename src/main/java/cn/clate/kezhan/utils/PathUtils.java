package cn.clate.kezhan.utils;

import javax.servlet.ServletContext;

public class PathUtils {
    private ServletContext sc;

    public String getPath(String path) {
        return sc.getRealPath(path);
    }
}

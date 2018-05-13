package cn.clate.kezhan.utils;

import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    private final static int year = 2018;

    private final static int semester = 1;

    public static HashMap<String, Integer> getYestAndSemester(int yid, int sid) {
        HashMap<String, Integer> retMp = new HashMap<>();
        retMp.put("yid", yid != -1 ? yid : year);
        retMp.put("sid", sid != -1 ? sid: semester);
        return retMp;
    }

    public static String dateTimeTodate(String datetime) {
        return getDateStr(convertDatetimeToDate(datetime));
    }

    public static String getDateStr(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date convertDatetimeToDate(String datetime) {
        Date ret = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            ret = f.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String MD5(String src) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(src.getBytes());
            StringBuilder buffer = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String passwordEncrypt(String password) {
        return MD5(MD5(password) + Conf.get("user.passwordSalt"));
    }

    public static String getRandStr(int length) {
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr() {
        HttpServletRequest request = Mvcs.getReq();
        String remoteAddr = request.getHeader("X-Real-IP");
        if (Strings.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (Strings.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (Strings.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        String ip = remoteAddr != null ? remoteAddr : Strings.sNull(request.getRemoteAddr());
        return ip;
    }

    public static String underline2Camel(String line) {
        return underline2Camel(line, true);
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String camel2Underline(String line) {
        return camel2Underline(line, true);
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line, boolean lowercase) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        if (lowercase) {
            return sb.toString().toLowerCase();
        }
        return sb.toString();
    }
}

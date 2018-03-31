package cn.clate.kezhan.utils;

import org.nutz.lang.util.NutMap;

import java.util.ArrayList;

public class Ret {
    public static NutMap json(int code, String msg, NutMap data) {
        NutMap ret = new NutMap();
        ret.addv("code", code);
        ret.addv("msg", msg);
        ret.addv("data", data);
        return ret;
    }

    public static NutMap json(int code, String msg, ArrayList data) {
        NutMap ret = new NutMap();
        ret.addv("code", code);
        ret.addv("msg", msg);
        ret.addv("data", data);
        return ret;
    }

    public static NutMap s(String msg, NutMap data) {
        return json(200, msg, data);
    }

    public static NutMap s(String msg, ArrayList data) {
        return json(200, msg, data);
    }

    public static NutMap s(String msg) {
        return json(200, msg, (NutMap) null);
    }

    public static NutMap s(NutMap data) {
        return json(200, "success", data);
    }
    /*
    400	Bad Request	客户端请求的语法错误，服务器无法理解
    401	Unauthorized	请求要求用户的身份认证
    402	Payment Required	保留，将来使用
    403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
    404	Not Found	服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
    405	Method Not Allowed	客户端请求中的方法被禁止
    406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
    407	Proxy Authentication Required	请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
    408	Request Time-out	服务器等待客户端发送的请求时间过长，超时
    409	Conflict	服务器完成客户端的PUT请求是可能返回此代码，服务器处理请求时发生了冲突
    410	Gone	客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
    411	Length Required	服务器无法处理客户端发送的不带Content-Length的请求信息
    412	Precondition Failed	客户端请求信息的先决条件错误
    413	Request Entity Too Large	由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
    414	Request-URI Too Large	请求的URI过长（URI通常为网址），服务器无法处理
    415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
    416	Requested range not satisfiable	客户端请求的范围无效
    417	Expectation Failed	服务器无法满足Expect的请求头信息
    */
    public static NutMap e(int code, String msg, NutMap data) {
        return json(400 + code, msg, data);
    }

    public static NutMap e(int code, String msg, ArrayList data) {
        return json(400 + code, msg, data);
    }

    public static NutMap e(int code, String msg) {
        return e(code, msg, (NutMap) null);
    }

    public static NutMap e(String msg, NutMap data) {
        return e(0, msg, data);
    }

    public static NutMap e(String msg, ArrayList data) {
        return e(0, msg, data);
    }

    public static NutMap e(String msg) {
        return e(msg, (NutMap) null);
    }
}
/**
 * 仅支持一级Key-value
 */
{
    "user.passwordSalt": "m4kS1oQ",
    "user.tokenLength": 16,


    /**
     * sms设置
     */
    //产品名称:云通信短信API产品,开发者无需替换
    "aliyun_product": "Dysmsapi",
    //产品域名,开发者无需替换
    "aliyun_domain": "dysmsapi.aliyuncs.com",

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    "aliyun_accessKeyId": "LTAIGCRVPHG0R0wT",
    "aliyun_accessKeySecret": "eQecMYk2GU8dJIFYOjLyEDbOrm77HI",

    "aliyun_msg_signName": "赵宁",
    //必填:短信模板-可在短信控制台中找到
    "aliyun_msg_templateCode": "SMS_127840183",

    "MsgValidTime" : 300,
    "sendInterval": 60,

    /**
     * user设置
     */
    "tokenValidTime":  864000,//十天
    "user.avatarUrl": "http://119.29.87.183:8080"
}
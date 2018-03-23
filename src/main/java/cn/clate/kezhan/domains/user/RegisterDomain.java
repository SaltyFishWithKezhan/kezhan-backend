package cn.clate.kezhan.domains.user;

import cn.clate.kezhan.pojos.Phone;
import cn.clate.kezhan.pojos.User;
import cn.clate.kezhan.utils.Conf;
import cn.clate.kezhan.utils.Ret;
import cn.clate.kezhan.utils.Tools;
import cn.clate.kezhan.utils.factories.DaoFactory;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.lang.util.NutMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RegisterDomain {

    private static String initAccessToken(User user) {
        String newToken = Tools.getRandStr((int) Conf.get("user.tokenLength", Integer.class));
        user.initLoginStatus();
        user.setAccessToken(newToken);
        DaoFactory.get().update(user);
        return user.getAccessToken();
    }

    public static NutMap fire(String username, String password, String phone, String code) {
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, username);
        if (user != null) {
            return Ret.e(10, "用户名已存在");
        }
        NutMap phoneRet = phoneVerifition(phone, code);
        System.out.println();
        if (phoneRet.get("code").equals(200)) {
            user = new User();
            String pwEncrypted = Tools.passwordEncrypt(password);
            user.setUsername(username);
            user.setPassword(pwEncrypted);
            user.setPhone(phone);
            String token = initAccessToken(user);
            user.setAccessToken(token);
            dao.insert(user);
            return Ret.s("success");
        }
        return phoneRet;
    }

    public static NutMap sendMsg(String phoneNumber) { //调用阿里短信服务API发送手机验证吗
        try {
            String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
            String sourceWithoutZreo = "123456789"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            StringBuffer flag = new StringBuffer();
            flag.append(sourceWithoutZreo.charAt(rand.nextInt(9)) + "");
            for (int j = 0; j < 5; j++) {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
            String code = flag.toString();
            System.out.print(code);

            Dao dao = DaoFactory.get();
            User user = dao.fetch(User.class, Cnd.where("phone","=",phoneNumber));
            if (user == null)//该手机号未验证过或验证成功
            {
                SendSmsResponse response = sendSms(phoneNumber, code);
                Phone phone = new Phone();
                phone.setPhone(phoneNumber);
                phone.setCode(code);
                phone.setRequestTime(Tools.getTimeStamp());
                phone.setType(1);
                phone.setStatus(0);
                dao.insert(phone);
                System.out.println("短信接口返回的数据----------------");
                System.out.println("Code=" + response.getCode());
                System.out.println("Message=" + response.getMessage());
                System.out.println("RequestId=" + response.getRequestId());
                System.out.println("BizId=" + response.getBizId());
                return Ret.s("success");
            } else {
                return Ret.e(13, "该手机号已被注册");
            }
        } catch (ClientException ce) {
            ce.printStackTrace();
            return Ret.e(11, "短信验证错误1");
        } catch (Exception e) {
            e.printStackTrace();
            return Ret.e(12, "短信验证错误2");
        }
    }

    private static SendSmsResponse sendSms(String phoneNumber, String code) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", (String) Conf.get("aliyun_accessKeyId", String.class), (String) Conf.get("aliyun_accessKeySecret", String.class));
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", (String) Conf.get("aliyun_product", String.class), (String) Conf.get("aliyun_domain", String.class));
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName((String) Conf.get("aliyun_msg_signName", String.class));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode((String) Conf.get("aliyun_msg_templateCode", String.class));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":" + code + "}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public static NutMap phoneVerifition(String phoneNumber, String code) {
        Dao dao = DaoFactory.get();
        Criteria cri = Cnd.cri();
        cri.where().andEquals("phone_number",phoneNumber);
        cri.getOrderBy().asc("request_time");
        Phone phone = dao.fetch(Phone.class,cri);
        if (phone == null) {
            return Ret.e(14, "请先获取验证码");
        } else if (phone.getCode().equals(code)) {
            long time = (Tools.getTimeStamp() - phone.getRequestTime());//间隔秒数
            System.out.println(time);
            if (phone.getStatus() == 1) {
                return Ret.e(15, "该手机号已被注册");
            }
            if (time <= (int)Conf.get("tokenValidTime")) {
                phone.setStatus(1);
                phone.updateRequestTime();
                dao.update(phone);
                return Ret.s("success");
            } else {
                return Ret.e(15, "验证码已失效");
            }
        } else {
            return Ret.e(16, "验证码错误");
        }
    }

}

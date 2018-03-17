package cn.clate.kezhan.domains.user;

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
import org.nutz.dao.Dao;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.lang.util.NutMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RegisterDomain {

    public static NutMap fire(String username, String password, String phone) {
        Dao dao = DaoFactory.get();
        User user = dao.fetch(User.class, username);
        if (user != null) {
            return Ret.e(50, "用户名已存在");
        }
        user = new User();
        String pwEncrypted = Tools.passwordEncrypt(password);
        user.setUsername(username);
        user.setPassword(pwEncrypted);
        user.setPhone(phone);
        dao.insert(user);
        return Ret.s("success");
    }

    public static void phoneVerifition(String phoneNumber) {
        try {
            String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            StringBuffer flag = new StringBuffer();
            for (int j = 0; j < 6; j++) {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
            String code = flag.toString();
            System.out.print(code);
            SendSmsResponse response = sendSms(phoneNumber, code);
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
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

    private QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", (String) Conf.get("aliyun_accessKeyId", String.class), (String) Conf.get("aliyun_accessKeySecret", String.class));
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", (String) Conf.get("aliyun_product", String.class), (String) Conf.get("aliyun_domain", String.class));
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

}

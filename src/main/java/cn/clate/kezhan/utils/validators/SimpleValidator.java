/**
 * SimpleValidator 简单验证器
 *
 * A simple tool to validate submitted fields
 * Author: Dizy
 */

package cn.clate.kezhan.utils.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleValidator {
    private String mError = null;
    private String mLastStanding = null;
    private String mHeader = "";

    public SimpleValidator(String subject, String header) {
        this.mLastStanding = subject;
        this.mHeader = header;
    }

    public SimpleValidator(String subject){
        this(subject, "");
    }

    public SimpleValidator(){
        this(null);
    }

    /**
     * 指定之后验证链的验证主体
     * @param subject 验证主体
     * @param header 验证主体名称
     */
    public SimpleValidator now(String subject, String header){
        mLastStanding = subject;
        mHeader = header==null?"":header;
        return this;
    }

    public SimpleValidator now(String subject){
        return now(subject, "");
    }

    public SimpleValidator now(){
        return now(null);
    }

    /**
     * 获得验证结果
     * @return 验证是否通过
     */
    public boolean check(){
        return mError == null;
    }

    /**
     * 获得错误提示
     * @return 提示文本
     */
    public String getError(){
        return mError;
    }

    private boolean matchPreg(String subject, String patternStr){
        if(subject == null){
            return false;
        }
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(subject);
        if( !matcher.matches()){
            return false;
        }
        return true;
    }

    /**
     * 验证不能为空
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator require(String subject, String errorText){
        if(mError == null){
            if(subject == null || subject.length() == 0){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator require(String errorText){
        return require(mLastStanding, errorText);
    }

    public SimpleValidator require(){
        return require(mHeader+"不能为空");
    }

    /**
     * 验证不超过指定长度
     * @param subject 验证主体
     * @param max 最大长度
     * @param errorText 错误提示
     */
    public SimpleValidator lenMax(String subject, int max, String errorText){
        if(mError == null && subject!=null){
            if(subject.length()>max){
                mError = errorText;
            }
        }
        return this;
    }
    public SimpleValidator lenMax(String subject, int max){
        return lenMax(subject, max, mHeader+"最多不能超过"+String.valueOf(max)+"位");
    }

    public SimpleValidator lenMax(int max, String errorText){
        return lenMax(mLastStanding, max, errorText);
    }

    public SimpleValidator lenMax(int max){
        return lenMax(mLastStanding, max);
    }

    /**
     * 验证至少为指定长度
     * @param subject 验证主体
     * @param min 最小长度
     * @param errorText 错误提示
     */
    public SimpleValidator lenMin(String subject, int min, String errorText){
        if(mError == null && subject!=null){
            if(subject.length()<min){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator lenMin(String subject, int min){
        return lenMin(subject, min, mHeader+"至少为"+String.valueOf(min)+"位");
    }

    public SimpleValidator lenMin(int min, String errorText){
        return lenMin(mLastStanding, min, errorText);
    }

    public SimpleValidator lenMin(int min){
        return lenMin(mLastStanding, min);
    }

    /**
     * 验证不超过最大值
     * @param subject 验证主体
     * @param max 最大值
     * @param errorText 错误提示
     */
    public SimpleValidator max(String subject, int max, String errorText){
        if(mError == null && subject!=null){
            if(Float.valueOf(subject)>max){
                mError = errorText;
            }
        }
        return this;
    }
    public SimpleValidator max(String subject, int max){
        return max(subject, max, mHeader+"最大为"+String.valueOf(max));
    }

    public SimpleValidator max(int max, String errorText){
        return max(mLastStanding, max, errorText);
    }

    public SimpleValidator max(int max){
        return max(mLastStanding, max);
    }

    /**
     * 验证不小于最小值
     * @param subject 验证主体
     * @param min 最小值
     * @param errorText 错误提示
     */
    public SimpleValidator min(String subject, int min, String errorText){
        if(mError == null && subject!=null){
            if(Float.valueOf(subject)<min){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator min(String subject, int min){
        return min(subject, min, mHeader+"至少为"+String.valueOf(min));
    }

    public SimpleValidator min(int min, String errorText){
        return min(mLastStanding, min, errorText);
    }

    public SimpleValidator min(int min){
        return min(mLastStanding, min);
    }

    /**
     * 验证是否为指定长度
     * @param subject 验证主体
     * @param length 长度
     * @param errorText 错误提示
     */
    public SimpleValidator length(String subject, int length, String errorText){
        if(mError == null && subject!=null){
            if(subject.length()!=length){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator length(String subject, int length){
        return length(subject, length, mHeader+"只能为"+String.valueOf(length)+"位");
    }

    public SimpleValidator length(int length, String errorText){
        return length(mLastStanding, length, errorText);
    }

    public SimpleValidator length(int length){
        return length(mLastStanding, length);
    }

    /**
     * 验证是否为纯数字
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator num(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[0-9]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator num(String errorText){
        return num(mLastStanding, errorText);
    }

    public SimpleValidator num(){
        return num( mHeader+"必须为数字");
    }

    /**
     * 验证是否为浮点数
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator floatNum(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "^[+-]?[0-9]+(.[0-9]+)?$")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator floatNum(String errorText){
        return floatNum(mLastStanding, errorText);
    }

    public SimpleValidator floatNum(){
        return floatNum(mHeader+"必须为浮点数");
    }

    /**
     * 验证是否为布尔值(true/false)
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator bool(String subject, String errorText){
        if(mError == null && subject!=null){
            subject = subject.toLowerCase();
            if(!subject.equals("true") && !subject.equals("false")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator bool(String errorText){
        return bool(mLastStanding, errorText);
    }

    public SimpleValidator bool(){
        return bool(mHeader+"必须为布尔值");
    }

    /**
     * 验证是否为邮箱格式
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator email(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\\\.){1,3}[a-zA-z\\\\-]{1,}")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator email(String errorText){
        return email(mLastStanding, errorText);
    }

    public SimpleValidator email(){
        return email(mHeader+"必须为邮箱格式");
    }

    /**
     * 验证是否为同意与否格式(yes/1/no)
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator accepted(String subject, String errorText){
        if(mError == null && subject!=null){
            subject = subject.toLowerCase();
            if(!subject.equals("yes") && !subject.equals("no") && !subject.equals("1")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator accepted(String errorText){
        return accepted(mLastStanding, errorText);
    }

    public SimpleValidator accepted(){
        return accepted(mHeader+"必须为同意与否格式");
    }

    /**
     * 验证是否为纯字母
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator alpha(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[a-zA-Z]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator alpha(String errorText){
        return alpha(mLastStanding, errorText);
    }

    public SimpleValidator alpha(){
        return alpha(mHeader+"必须为字母");
    }

    /**
     * 验证是否为数字或字母
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator alphaNum(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[a-zA-Z0-9]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator alphaNum(String errorText){
        return alphaNum(mLastStanding, errorText);
    }

    public SimpleValidator alphaNum(){
        return alphaNum(mHeader+"必须为字母或数字");
    }

    /**
     * 验证是否为数字/字母/下划线/破折号
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator alphaDash(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[a-zA-Z0-9_-]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator alphaDash(String errorText){
        return alphaDash(mLastStanding, errorText);
    }

    public SimpleValidator alphaDash(){
        return alphaDash(mHeader+"必须为字母、数字、下划线或破折号");
    }

    /**
     * 验证是否为汉字
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator chs(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[u4e00-u9fa5]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator chs(String errorText){
        return chs(mLastStanding, errorText);
    }

    public SimpleValidator chs(){
        return chs(mHeader+"必须为汉字");
    }

    /**
     * 验证是否为汉字或字母
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator chsAlpha(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[u4e00-u9fa5a-zA-Z]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator chsAlpha(String errorText){
        return chsAlpha(mLastStanding, errorText);
    }

    public SimpleValidator chsAlpha(){
        return chsAlpha(mHeader+"必须为汉字或字母");
    }

    /**
     * 验证是否为汉字/字母/数字
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator chsAlphaNum(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[u4e00-u9fa5a-zA-Z0-9]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator chsAlphaNum(String errorText){
        return chsAlphaNum(mLastStanding, errorText);
    }

    public SimpleValidator chsAlphaNum(){
        return chsAlphaNum(mHeader+"必须为汉字、字母或数字");
    }

    /**
     * 验证是否为汉字/数字/字母/下划线/破折号
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator chsDash(String subject, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, "[u4e00-u9fa5a-zA-Z0-9_-]*")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator chsDash(String errorText){
        return chsDash(mLastStanding, errorText);
    }

    public SimpleValidator chsDash(){
        return chsDash(mHeader+"必须为汉字、字母、数字、下划线或破折号");
    }

    /**
     * 验证是否为手机号码
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator mobilePhone(String subject, String errorText){
        if(mError == null && subject != null){
            if(!matchPreg(subject, "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator mobilePhone(String errorText){
        return mobilePhone(mLastStanding, errorText);
    }

    public SimpleValidator mobilePhone(){
        return mobilePhone(mHeader+"必须为手机号");
    }

    /**
     * 验证是否为网址URL
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator url(String subject, String errorText){
        if(mError == null && subject != null){
            if(!matchPreg(subject, "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator url(String errorText){
        return url(mLastStanding, errorText);
    }

    public SimpleValidator url(){
        return url("必须为URL");
    }

    /**
     * 验证是否为IP地址
     * @param subject 验证主体
     * @param errorText 错误提示
     */
    public SimpleValidator ip(String subject, String errorText){
        if(mError == null && subject != null){
            if(!matchPreg(subject, "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)")){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator ip(String errorText){
        return url(mLastStanding, errorText);
    }

    public SimpleValidator ip(){
        return url(mHeader+"必须为IP地址");
    }

    /**
     * 自定义正则匹配
     * @param subject 验证主体
     * @param pattern 正则表达式
     * @param errorText 错误提示
     */
    public SimpleValidator preg(String subject, String pattern, String errorText){
        if(mError == null && subject!=null){
            if( !matchPreg(subject, pattern)){
                mError = errorText;
            }
        }
        return this;
    }

    public SimpleValidator preg(String pattern, String errorText){
        return preg(mLastStanding, pattern, errorText);
    }

}
package com.czj.utils;

/**
 * Created by 11273 on 2018-3-12.
 */
public class ConstantClass {
    //表示有效的标志。（用于判断数据库中对象是否有效）
    public static final String VALID = "1";

    //数据库中app表中的userId字段的切割
    public static final String SPLIT_USERID = ",";

    //log日志行和appid之间的间隔符，要和flume中的一致
    public static final String SPLIT_LOG_LINE = ":::";

    /***************************邮件发送相关**********************************************/
    //发件人账号
    public static final String EMAIL_SENDER = "zengjin_chen@163.com";

    //邮件主题
    public static final String EMAIL_SUBJECT = "日志监控系统报警！";

    //连接账号
    public static final String EMAIL_CONN_NAME = "zengjin_chen";

    //连接授权码
    //TODO
    public static final String EMAIL_CONN_CODE = "***";



}

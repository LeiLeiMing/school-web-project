package com.secondhand.pojo.utils;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by LeiMing on 2020/3/9 10:11
 */
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101700708877";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCTs2c/IGt6KpFB2TMpV9m0OWH9vE34A4M0YRsyl4gs9gCDIJmcU3/uQ7Sg/mVXQOSV+/Fec2oEabH1l3d3qH1R/ghzgem5v/DnjdD3M4Qg62T5L2W3vJRhFF9LjFN+YNl0kGIgRRcY7ohbPdEw5mSP7MD+11iWyItGtDYEAUz8QsMpS7xPc52qNbsDkA4xcU4LbEz2pxOQwzsV0kJ2sZQkYoRfmsGiL5hbH2ueJDtWTF/KF9ImasWkg5Mx626QhMtKi0vsiPk9J8cfUixTlZnb0KZUdl9Eb5+p/zv3UiqVWPhCD9UaoMJ9INkiT+Wacf4UVY6gZS1v97MDrlH9EVppAgMBAAECggEAYqRpau6C+SceteSSV6U1XzkHqccuJ1ppMP09hc9QAf8Vnn2SOaZGhWEwbGkaU2iigUyvCc542pOgZ44OGpsrfyvyhvoXl5eKE4tSKRZ9wuKTIBy4iTpnumI5tqH0TS3vbPrUngqmf18nMBCb+lWUic6rokc+Ag3Tfpfyj0AzIzGLlQdWY4si/xEtCsMw1B10z2dAGNlB0jYIXWJ2T0SSPNr+ebckJ5QRpAZkZjF8QLXk2/Wdmoxc0COFC0wQGJO7ij8urowmQo78TGUC1bu/olWbwtVky8jsTCN6hGRsYhZ2gwYYp0NjD4qsvQtKEpNR8/mHnrR/qiLIvQ/jkO2/tQKBgQDFRNqa1vVt1FtiMfy+G8Niowuo67ymGyt8S65SoAxZLj1oF3QQr4OQ086lpu74CYrbjulMj4djyctZKE8SX087fVvvoIyZWSJZjxVbMjauV1CC7LMPfX7mefal5ssyl9pOb1+h7RY7aloURY13c2RulJ76SSNa/7cCIrCXwiEaTwKBgQC/rKNdSbdeR7rSH/14+PQtRdtk8znFCbF9HCkM8zBO3l4tF97tFP3xGneu+GJlak28BMvFkcbHcNdS/W7KBNcu8awHrrpOgPCJXef0PKQHPyYbFeslDMiWfF5is0vZdj15WDWA+kr9QCRX/iaQBLY7ybFmU4ZbbWzQ0JBTgFzpxwKBgD1GfvTb0o6UCPK+fjh7HTzVUnjdCNM7200XoQVvS4akE1Ty/A12x1JESIsyCEapiCVdBeDPMInxk6WVHMKjMlRTQyxy3VmA02eoR0T1mwCahJq2dYRZQgLLXa74uqNzgc8dT0JYB7Da/zIOzLuO72Jd7efmsgHBLpKOPfZVybyHAoGAR1+t5YsRsRXcx2oKJQXvRvyAWfMITAr0ALZ/wsa78mJzhy8gYAA1H5bPgmQTn9gZ4i2XA5bpAlNOmySsIKX0yxJLEQFLdvgkmcJSIWyDV/933RYoXb+I4iCltPM3B180PP9PzRtm2S8NWYbWLULT6SeN26C2o0z3/DntxGpELvkCgYAOK7nFi5jpyUQLUFUCrHpfsaqYLe6+3b+7E+G1qKcO/L6uJihuE2+4nGx7aVYaUYNupDniYSbxKW51B0bkfIgibfmy35EYmCFR8SbgasPpoHdWH2Ks4CruFoQYCCCsTOO02J1eol8kY1jpyELwzg2B6M1RcpQt+gBu4MB/CKYhEA==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqXi16A4J3n7ha8UGsbdbBvnRSLUCD5OSedjYLBOfO2kosqV5RFgGYQao4OAOkU39YtIWUpOYvpvdh39boGro3icVXxBX5cV6GwbJAOdbfU77t0vZwn6rFzXgBDso1jjRa56RESoZutVdx+54W571Rn6G8M5GPCgj7N58+HyroH4ppvLylLJKgcOfE1ZweSm2hW22XXVGfI7EjeXE6aYe3S6pTAXWzAHRzsK8FJrNmtmjqDg75lrQOuw89XnvYwaMLx1d//6pbdGmqGvH2o8ZwKQzB9u2PJVND5L+S4upKVx0Kbdp/Ay2LDDZQdXRB+fs5VmlSeRnTGjPcEUXZrzA6wIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:1000/transaction-service/cart/getalipay";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:1000/transaction-service/cart/getalipay";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



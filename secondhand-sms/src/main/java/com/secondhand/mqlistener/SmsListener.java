package com.secondhand.mqlistener;

import com.aliyuncs.exceptions.ClientException;
import com.secondhand.utils.SmsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Created by LeiMing on 2020/2/13 11:45
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "secondhand.sms.queue",durable = "true"),
            exchange =@Exchange(value = "secondhand.sms.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"phoneCode"}
    ))
    public void sendSms(Map<String,String> msg) throws ClientException {

        if (CollectionUtils.isEmpty(msg)){
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");
        if (StringUtils.isNotBlank(phone)&&StringUtils.isNotBlank(code)){
            smsUtils.sendSms(phone,code);
            System.out.println("验证码发送成功!!");
        }
    }
}

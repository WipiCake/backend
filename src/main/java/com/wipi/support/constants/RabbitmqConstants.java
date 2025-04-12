package com.wipi.support.constants;

public final class RabbitmqConstants {
    //이메일
    public static final String EXCHANGE_MAIL = "exchange.mail";
    public static final String QUEUE_MAIL_SEND = "queue.mail.send";
    public static final String ROUTING_MAIL_SEND = "route.mail.send";

    //SMS
    public static final String EXCHANGE_SMS_COOL = "exchange.sms.cool";
    public static final String QUEUE_SMS_COOL = "queue.sms.cool.send";
    public static final String ROUTING_SMS_SEND = "route.sms.cool.send";



    private RabbitmqConstants() {
    }
}

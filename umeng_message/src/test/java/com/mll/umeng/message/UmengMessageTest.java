package com.mll.umeng.message;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * author: walter
 * date:2016/4/7
 * comment:测试
 */
public class UmengMessageTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testConstructAndroidUnicastMessage() throws Exception{
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody.appKey("应用key")
                .unicast("设备tokens")//单播,参数传设备token
                .policy(sdf.parse(startTime), sdf.parse(expireTime), maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .message("custom").build();//消息类型，参数赋值给custom字段
        logger.info(jsonStr);
    }
    @Test
    public void testConstructAndroidListCastMessage() throws Exception{
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        String[] tokenArr = new String[]{"token1","token2","token3"};
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody.appKey("应用key")
                .listcast(tokenArr)//列播
                .policy(sdf.parse(startTime), sdf.parse(expireTime), maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .message("custom").build();//消息类型，参数赋值给custom字段
        logger.info(jsonStr);
    }
    @Test
    public void testConstructAndroidBroadCastMessage() throws Exception{
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        String[] tokenArr = new String[]{"token1","token2","token3"};
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody.appKey("应用key")
                .broadcast()//广播
                .policy(sdf.parse(startTime), sdf.parse(expireTime), maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .message("custom").build();//消息类型，参数赋值给custom字段
        logger.info(jsonStr);
    }
    @Test
    public void testConstructAndroidUnicastNotification() throws Exception {
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        Map<String,String> extra = new HashMap<String,String>();
        extra.put("key1","value1");extra.put("key2","value2");extra.put("key3","value3");
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody.appKey("应用key")
                .unicast("设备tokens")//单播,参数传设备token
                .policy(sdf.parse(startTime), sdf.parse(expireTime), maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .androidNotification("通知栏提示文字", "通知标题", "通知文字描述")//消息类型，参数赋值给custom字段
                .icon("状态栏图标ID")//自定义图标
                .largeIcon("通知栏拉开后左侧图标ID")
                .img("通知栏大图标的URL链接")
                .sound("通知声音")
                .play(true, true, true)//通知到达设备后的提醒方式
                .goApp()
                .goUrl("url")
                .goActivity("activity")
                .goCustom("custom")
                .extra(extra)
                .build();
        logger.info(jsonStr);
    }
    @Test
    public void testConstructIosUnicastNotification() throws Exception {
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        Map<String,String> kv = new HashMap<String,String>();
        kv.put("key1","value1");kv.put("key2","value2");kv.put("key3","value3");
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody.appKey("应用key")
                .unicast("设备tokens")//单播,参数传设备token
                .policy(sdf.parse(startTime), sdf.parse(expireTime), maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .iosNotification("alert")//ios通知
                .apsOptions("badge", "sound", "content-available", "category")
                .customProps(kv)
                .build();
        logger.info(jsonStr);
    }
}

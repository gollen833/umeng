package com.mll.umeng.message;

import com.mll.umeng.message.exception.UmengException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * author: walter
 * date:2016/4/8
 * comment:测试
 */
public class UmengMessagePushTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String appKey = "56e1758967e58eff1000016f";
    private final String appMasterSecret = "0xydswk5cyw2rkxky2utwgiicwexyboh";

    @Test
    public void testPushSuccess() throws Exception{
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody
                .unicast("AsukU0lZCXcixl0poBf6-zRcjbj2uuMcfMmvXTBPlALP")//单播,参数传设备token
                .policy(null, null, maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .message("custom").build();//消息类型，参数赋值给custom字段
        logger.info(jsonStr);
        Client.newClient(appKey,appMasterSecret).push(jsonStr);
    }

    @Test
    public void testPushFailed() throws Exception{
        String startTime = "2016-04-07 16:36:04";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String expireTime = "2016-04-07 16:36:04";
        int maxSendNum = 10;
        ReqBodyBuilder reqBody = new ReqBodyBuilder();
        String jsonStr = reqBody
                .unicast("AsukU0lZCXcixl0poBf6-zRcjbj2uuMcfMmvXTBPlALP")//单播,参数传设备token
                .policy(null, null, maxSendNum)//发送策略
                .productionMode(false)
                .description("发送消息描述")
                .thirdPartyId("自定义消息标识ID")
                .message("custom").build();//消息类型，参数赋值给custom字段
        logger.info(jsonStr);
        try{
            Client.newClient("appkeyinvalid",appMasterSecret).push(jsonStr);
        }catch (UmengException e){
            logger.info(e.toString());
        }

    }

    @Test
    public void testUpload() throws Exception{
        List<String> contentList = new ArrayList<String>();
        contentList.add("111");
        contentList.add("222");
        contentList.add("333");
        String fileId = Client.newClient(appKey,appMasterSecret).upload(contentList);
        logger.info("fileId:"+fileId);
    }

    @Test
    public void testTaskStatus() throws Exception{
        String jsonStr = new ReqBodyBuilder().message("android消息").broadcast().productionMode(false).build();
        PushResult pushResult = Client.newClient(appKey, appMasterSecret).push(jsonStr);
        if (pushResult.isTaskMode()){
            TaskStatusQueryResult taskStatusQueryResult = Client.newClient(appKey,appMasterSecret).taskStatus(pushResult.getTaskid());
            logger.info("status:"+taskStatusQueryResult.getStatus());
        }
    }

    @Test
    public void testCancelTask() throws Exception{
        String jsonStr = new ReqBodyBuilder().message("android消息").broadcast().productionMode(false).build();
        PushResult pushResult = Client.newClient(appKey, appMasterSecret).push(jsonStr);
        if (pushResult.isTaskMode()){
            Client.newClient(appKey,appMasterSecret).cancelTask(pushResult.getTaskid());
        }
    }

}

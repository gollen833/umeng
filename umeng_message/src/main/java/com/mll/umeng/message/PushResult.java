package com.mll.umeng.message;

import org.apache.commons.lang3.StringUtils;

/**
 * @author walter
 * @Time 2016/3/2
 * 友盟推送接口成功返回值
 */
public class PushResult {

    private String msgid;
    private String taskid;

    public PushResult(){}
    public PushResult(String msgid,String taskid){
        this.msgid = msgid;
        this.taskid = taskid;
    }

    public boolean isTaskMode() {
        return StringUtils.isNotEmpty(taskid);
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getTaskid() {
        return taskid;
    }
    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

}

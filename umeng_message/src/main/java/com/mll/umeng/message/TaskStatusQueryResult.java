package com.mll.umeng.message;

/**
 * @author walter
 * @Time 2016/4/11
 * 任务状态查询返回结果
 */
public class TaskStatusQueryResult {

    private String taskid;
    private TaskStatusEnum status;
    private int totalCount;
    private int acceptCount;
    private int sentCount;
    private int openCount;
    private int dismissCount;

    public TaskStatusQueryResult(String taskid,TaskStatusEnum status,int totalCount,int acceptCount,
                                 int sentCount,int openCount,int dismissCount){
        this.taskid = taskid;
        this.status = status;
        this.totalCount = totalCount;
        this.acceptCount = acceptCount;
        this.sentCount = sentCount;
        this.openCount = openCount;
        this.dismissCount = dismissCount;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getDismissCount() {
        return dismissCount;
    }

    public void setDismissCount(int dismissCount) {
        this.dismissCount = dismissCount;
    }
}

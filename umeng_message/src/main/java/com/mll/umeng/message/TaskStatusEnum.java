package com.mll.umeng.message;

/**
 * author: walter
 * date:2016/4/11
 * comment:任务状态
 *  // 消息状态: 0-排队中, 1-发送中，2-发送完成，3-发送失败，4-消息被撤销，
 // 5-消息过期, 6-筛选结果为空，7-定时任务尚未开始处理
 */
public enum TaskStatusEnum {
    in_queue(0),
    sending(1),
    send_ok(2),
    send_failed(3),
    canceled(4),
    expired(5),
    resul_tempty(6),
    not_handled(7);

    private int statusValue;

    private TaskStatusEnum(int statusValue){
        this.statusValue = statusValue;
    }

    public static TaskStatusEnum getInstanceByStatusValue(int statusValue){
        for (TaskStatusEnum status : TaskStatusEnum.values()){
            if(status.getStatusValue() == statusValue){
                return status;
            }
        }
        return null;
    }

    public int getStatusValue() {
        return statusValue;
    }
}

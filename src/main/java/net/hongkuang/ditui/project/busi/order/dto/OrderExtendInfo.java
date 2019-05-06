package net.hongkuang.ditui.project.busi.order.dto;

import java.util.Date;

/**
 * Created by apple on 2019/1/8.
 */
public class OrderExtendInfo {
    private String orderId;
    /**
     * extend 扩展字段
     */
    private String saleId;
    private String saleName;
    private String saleArea;
    private Long saleCommission;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务完成时间
     */
    private Date taskCompletionTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    public Long getSaleCommission() {
        return saleCommission;
    }

    public void setSaleCommission(Long saleCommission) {
        this.saleCommission = saleCommission;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getTaskCompletionTime() {
        return taskCompletionTime;
    }

    public void setTaskCompletionTime(Date taskCompletionTime) {
        this.taskCompletionTime = taskCompletionTime;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    @Override
    public String toString() {
        return "OrderExtendInfo{" +
                "orderId='" + orderId + '\'' +
                ", saleId='" + saleId + '\'' +
                ", saleName='" + saleName + '\'' +
                ", saleArea='" + saleArea + '\'' +
                ", saleCommission=" + saleCommission +
                ", taskId='" + taskId + '\'' +
                ", taskCompletionTime=" + taskCompletionTime +
                '}';
    }
}

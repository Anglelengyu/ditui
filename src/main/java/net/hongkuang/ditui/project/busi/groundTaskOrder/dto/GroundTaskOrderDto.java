package net.hongkuang.ditui.project.busi.groundTaskOrder.dto;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;


/**
 * 任务订单
 *
 * @author:zy
 * @date: 2019/4/17
 */
public class GroundTaskOrderDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /*
    * 任务id
    * */
    private String taskId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 状态1正常1取消
     */
    private Integer status;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

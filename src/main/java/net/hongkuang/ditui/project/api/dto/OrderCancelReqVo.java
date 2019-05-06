package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/12.
 */
public class OrderCancelReqVo {
    private Long userId;
    private String orderId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderCancelReqVo{" +
                "userId=" + userId +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}

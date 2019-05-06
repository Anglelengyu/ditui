package net.hongkuang.ditui.project.busi.order.dto;

import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.task.domain.Task;

import java.util.List;

/**
 * Created by apple on 2019/1/3.
 */
public class OrderTaskDto extends Task {

    private List<Order> orderTasks;

    public List<Order> getOrderTasks() {
        return orderTasks;
    }

    public void setOrderTasks(List<Order> orderTasks) {
        this.orderTasks = orderTasks;
    }
}

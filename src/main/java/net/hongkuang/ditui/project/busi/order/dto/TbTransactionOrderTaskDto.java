package net.hongkuang.ditui.project.busi.order.dto;

import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/4/12
 */
public class TbTransactionOrderTaskDto extends GroundTask {

    private List<TbTransactionOrder> orderTasks;

    public List<TbTransactionOrder> getOrderTasks() {
        return orderTasks;
    }

    public void setOrderTasks(List<TbTransactionOrder> orderTasks) {
        this.orderTasks = orderTasks;
    }
}

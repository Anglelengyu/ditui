package net.hongkuang.ditui.project.busi.taskOrder.service;

import net.hongkuang.ditui.project.busi.taskOrder.domain.TaskOrder;
import net.hongkuang.ditui.project.busi.taskOrder.dto.TaskOrderDto;

import java.util.List;

/**
 * 任务订单 服务层
 *
 * @author yj
 * @date 2019-01-03
 */
public interface ITaskOrderService {
    /**
     * 查询任务订单信息
     *
     * @param id 任务订单ID
     * @return 任务订单信息
     */
    public TaskOrder selectTaskOrderById(Long id);

    /**
     * 查询任务订单列表
     *
     * @param taskOrder 任务订单信息
     * @return 任务订单集合
     */
    public List<TaskOrder> selectTaskOrderList(TaskOrder taskOrder);

    /**
     * 新增任务订单
     *
     * @param taskOrderDto 任务订单信息
     * @return 结果
     */
    public int insertTaskOrder(TaskOrderDto taskOrderDto);

    /**
     * 修改任务订单
     *
     * @param taskOrder 任务订单信息
     * @return 结果
     */
    public int updateTaskOrder(TaskOrder taskOrder);

    /**
     * 删除任务订单信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskOrderByIds(String taskId, String ids);

}

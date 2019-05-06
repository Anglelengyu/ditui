package net.hongkuang.ditui.project.busi.taskOrder.mapper;

import net.hongkuang.ditui.project.busi.taskOrder.domain.TaskOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务订单 数据层
 *
 * @author yj
 * @date 2019-01-03
 */
public interface TaskOrderMapper {
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
     * @param taskOrder 任务订单信息
     * @return 结果
     */
    public int insertTaskOrder(TaskOrder taskOrder);

    /**
     * 修改任务订单
     *
     * @param taskOrder 任务订单信息
     * @return 结果
     */
    public int updateTaskOrder(TaskOrder taskOrder);

    /**
     * 删除任务订单
     *
     * @param id 任务订单ID
     * @return 结果
     */
    public int deleteTaskOrderById(Long id);

    /**
     * 批量删除任务订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskOrderByIds(String[] ids);

    int batchInsert(@Param("list") List<TaskOrder> taskOrderList);

    int updateTaskOrderStatusByTaskIds(String[] taskId);

    List<String> selectOrderIdByTaskId(@Param("taskId") String taskId);

    List<TaskOrder> selectTaskOrderByOrderId(@Param("orderId") String orderId);

    List<TaskOrder> selectTaskOrderByOrderIdList(@Param("list") List<String> orderIdList);

    int deleteTaskOrderByOrderIdList(@Param("list") List<String> orderIdList);

    /**
     * 删除任务订单
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaskOrderByOrderId(@Param("taskId") String taskId, @Param("id") String id);

}
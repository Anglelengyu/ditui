package net.hongkuang.ditui.project.busi.task.service;

import net.hongkuang.ditui.project.api.dto.*;
import net.hongkuang.ditui.project.busi.task.domain.SearchTask;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.domain.TaskVo;

import java.util.List;
import java.util.Map;

/**
 * 任务 服务层
 *
 * @author yj
 * @date 2018-12-30
 */
public interface ITaskService {
    /**
     * 查询任务信息
     *
     * @param id 任务ID
     * @return 任务信息
     */
    Task selectTaskById(Long id);

    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    List<TaskVo> selectTaskList(SearchTask task);

    /**
     * 新增任务
     *
     * @param task 任务信息
     * @return 结果
     */
    int insertTask(Task task);

    /**
     * 修改任务
     *
     * @param task 任务信息
     * @return 结果
     */
    int updateTask(Task task);

    /**
     * 删除任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTaskByIds(String ids);

    /**
     * 批量更新任务状态为分配
     *
     * @param taskIds
     */
    int updateBatchTaskStatusAllot(List<String> taskIds);

    /**
     * 任务转移到历史记录中去
     *
     * @param type
     * @param taskIds
     * @return
     */
    int moveToHistory(Integer type, String taskIds);

    /**
     * 根据订单id查询任务信息
     *
     * @param orderId
     * @return
     */
    Task selectTaskByOrderId(String orderId);


    /**
     * @param taskId
     * @return
     */
    int updateTaskByTaskIds(String[] taskId);

    Task getNotCompleteTask(SimpleTaskReqVo taskReqVo);

    TaskDetailRespVo getTaskDetail(SimpleTaskReqVo taskReqVo);

    List<TaskListRespVo> selectTaskList(TaskSearchReqVo taskReqVo);

    int cancel(TaskCancelReqVo taskCancelReqVo);

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中
     */
    Map<String, Object> checkStatus(String userId);

    int submitTask(TaskSubmitReqVo taskSubmit);

    int toReal(String taskDate, String startTime, String stopTime, String taskIds);

    int toSale(String taskId, String saleId, String userId);
}

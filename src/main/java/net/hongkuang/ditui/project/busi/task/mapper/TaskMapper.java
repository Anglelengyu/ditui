package net.hongkuang.ditui.project.busi.task.mapper;

import net.hongkuang.ditui.project.api.dto.TaskSubmitReqVo;
import net.hongkuang.ditui.project.busi.order.dto.OrderExtendInfo;
import net.hongkuang.ditui.project.busi.task.domain.SearchTask;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.domain.TaskVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 任务 数据层
 *
 * @author yj
 * @date 2018-12-30
 */
@Repository
public interface TaskMapper {
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
     * 删除任务
     *
     * @param id 任务ID
     * @return 结果
     */
    int deleteTaskById(Long id);

    /**
     * 批量删除任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTaskByIds(String[] ids);

    int batchInsert(@Param("list") List<Task> taskList);

    int updateBatchTaskStatusAllot(List<String> taskIds);

    List<Task> selectAllocTaskList(@Param("list") List<String> goodsNames, @Param("status") Integer status);

    int updateBatchStatusByIds(@Param("list") List<String> taskIdList, @Param("status") Integer status
            , @Param("preStatus") Integer preStatus, @Param("allotType") Integer allotType
            , @Param("realTimeStart") String realTimeStart, @Param("realTimeEnd") String realTimeEnd);

    int updateBatchTaskStatusByIds(@Param("list") List<String> taskIdList
            , @Param("status") Integer status, @Param("preStatus") Integer preStatus, @Param("saleId") String saleId);

    int updateAllTaskStatus(@Param("status") Integer status, @Param("preStatus") Integer preStatus, @Param("list") List<String> taskIds);

    List<OrderExtendInfo> selectTaskByOrderIdList(@Param("list") List<String> orderIdList);

    /**
     * 根据订单id查询任务任务信息
     *
     * @param orderId
     * @return
     */
    Task selectTaskByOrderId(String orderId);

    int updateTaskByTaskIds(String[] taskId);

    Task getFirstTask(@Param("saleId") String saleId, @Param("taskStatus") Integer taskStatus);

    Task selectTaskByTaskId(@Param("taskId") String taskId);

    List<Task> selectTaskList2(SearchTask task);

    Task checkTaskStatusBySaleId(@Param("saleId") String saleId, @Param("status") Integer status);

    void updateTaskStatusByTaskId(TaskSubmitReqVo taskSubmit);

    List<Task> selectTaskByIds(String[] ids);

    int deleteTaskByTaskIds(String[] ids);
}
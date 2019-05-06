package net.hongkuang.ditui.project.busi.onlineTask.mapper;

import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTaskVo;
import net.hongkuang.ditui.project.busi.onlineTask.domain.SearchOnlineTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务 数据层
 *
 * @author:zy
 * @date: 2019/4/17
 */
@Repository
public interface OnlineTaskMapper {

    int batchInsert(@Param("list") List<OnlineTask> onlineTaskList);

    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    List<OnlineTaskVo> selectOnlineTaskList(SearchOnlineTask task);

    /**
     * 查询单个任务
     *
     * @param id 任务ID
     * @return 任务
     */
    OnlineTask selectOnlineTaskById(Long id);

    /**
     * 查询单个任务
     *
     * @param taskId 任务ID
     * @return 任务
     */
    OnlineTask selectOnlineTaskByTaskId(String taskId);

    /**
     * 更新任务团队备注
     *
     * @param onlineTask
     * @return 信息
     */
    int updateOnlineTaskTeamRemark(OnlineTask onlineTask);

    List<OnlineTask> selectOnlineTaskByIds(String[] ids);

    /**
     * 批量删除任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineTaskByIds(String[] ids);

    /**
     * 批量删除任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineTaskByTaskIds(String[] ids);

    /**
     * 修改任务
     *
     * @param onlineTask 任务信息
     * @return 结果
     */
    int updateOnlineTask(OnlineTask onlineTask);

    List<OnlineTask> selectAllocTaskList(@Param("list") List<String> goodsNames, @Param("status") Integer status, @Param("ids")Long[] ids);

    int updateBatchStatusByIds(@Param("list") List<String> taskIdList, @Param("status") Integer status
            , @Param("preStatus") Integer preStatus, @Param("allotType") Integer allotType
            , @Param("realTimeStart") String realTimeStart, @Param("realTimeEnd") String realTimeEnd);

    int updateBatchOnlineTaskStatusAllot(List<String> taskIds);

    int updateBatchTaskStatusByIds(@Param("ids") String[] taskIds
            , @Param("status") Integer status, @Param("preStatus") Integer preStatus, @Param("employeeId") String employeeId);

    int updateAllTaskStatus(@Param("status") Integer status, @Param("preStatus") Integer preStatus, @Param("ids") String[] taskIds);

    OnlineTask checkOnlineTaskStatusByEmployeeId(@Param("employeeId") Long employeeId, @Param("status") Integer status);

    /**
     * 查询单个任务
     *
     * @param orderId 订单ID
     * @return 任务
     */
    OnlineTask selectOnlineTaskByOrderId(String orderId);

}
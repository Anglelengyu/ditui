package net.hongkuang.ditui.project.busi.groundTask.mapper;

import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTaskVo;
import net.hongkuang.ditui.project.busi.groundTask.domain.SearchGroundTask;
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
public interface GroundTaskMapper {

    int batchInsert(@Param("list") List<GroundTask> groundTaskList);

    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    List<GroundTaskVo> selectGroundTaskList(SearchGroundTask task);

    /**
     * 查询单个任务
     *
     * @param id 任务ID
     * @return 任务
     */
    GroundTask selectGroundTaskById(Long id);

    /**
     * 查询单个任务
     *
     * @param taskId 任务ID
     * @return 任务
     */
    GroundTask selectGroundTaskByTaskId(String taskId);

    /**
     * 更新任务团队备注
     *
     * @param groundTask
     * @return 信息
     */
    int updateGroundTaskTeamRemark(GroundTask groundTask);

    List<GroundTask> selectGroundTaskByIds(String[] ids);

    /**
     * 批量删除任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundTaskByIds(String[] ids);

    /**
     * 批量删除任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundTaskByTaskIds(String[] ids);

    /**
     * 修改任务
     *
     * @param groundTask 任务信息
     * @return 结果
     */
    int updateGroundTask(GroundTask groundTask);

    List<GroundTask> selectAllocTaskList(@Param("list") List<String> goodsNames, @Param("status") Integer status,@Param("ids")Long[] ids);

    int updateBatchStatusByIds(@Param("list") List<String> taskIdList, @Param("status") Integer status
            , @Param("preStatus") Integer preStatus, @Param("allotType") Integer allotType
            , @Param("realTimeStart") String realTimeStart, @Param("realTimeEnd") String realTimeEnd);

    int updateBatchGroundTaskStatusAllot(List<String> taskIds);

    int updateBatchTaskStatusByIds(@Param("ids") String[] taskIds
            , @Param("status") Integer status, @Param("preStatus") Integer preStatus, @Param("employeeId") String employeeId);

    int updateAllTaskStatus(@Param("status") Integer status, @Param("preStatus") Integer preStatus, @Param("ids") String[] taskIds);

    GroundTask checkGroundTaskStatusByEmployeeId(@Param("employeeId") Long employeeId, @Param("status") Integer status);

    /**
     * 查询单个任务
     *
     * @param orderId 订单ID
     * @return 任务
     */
    GroundTask selectGroundTaskByOrderId(String orderId);

}
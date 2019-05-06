package net.hongkuang.ditui.project.busi.realTimeTask.mapper;

import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实时任务 数据层
 *
 * @author yj
 * @date 2019-01-05
 */
@Repository
public interface RealTimeTaskMapper {
    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    RealTimeTask selectRealTimeTaskById(Long id);

    /**
     * 查询实时任务列表
     *
     * @param realTimeTask 实时任务信息
     * @return 实时任务集合
     */
    List<RealTimeTask> selectRealTimeTaskList(RealTimeTask realTimeTask);

    /**
     * 新增实时任务
     *
     * @param realTimeTask 实时任务信息
     * @return 结果
     */
    int insertRealTimeTask(RealTimeTask realTimeTask);

    /**
     * 修改实时任务
     *
     * @param realTimeTask 实时任务信息
     * @return 结果
     */
    int updateRealTimeTask(RealTimeTask realTimeTask);

    /**
     * 删除实时任务
     *
     * @param id 实时任务ID
     * @return 结果
     */
    int deleteRealTimeTaskById(Long id);

    /**
     * 批量删除实时任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeTaskByIds(String[] ids);

    int batchInsert(@Param("list") List<RealTimeTask> realTimeTaskList);

    int selectRealTimeTaskCount(@Param("startTime") String startTimeNode
            , @Param("stopTime") String stopTimeNode, @Param("taskDate") String taskDate);

    /**
     * 更新任务状态为未抢单
     *
     * @param taskId
     * @return
     */
    int updateTimeTaskByTaskIds(String[] taskId);

    RealTimeTask selectCurrentTimeTaskCount(@Param("taskDate") String taskDate, @Param("currentTime") String currentTime);

    List<String> selectTodoRealTimeTaskList(@Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("taskDate") String taskDate);
}
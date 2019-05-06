package net.hongkuang.ditui.project.busi.realTimeOnlineTask.mapper;

import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTask;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTaskVo;
import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实时任务 数据层
 *
 * @author:zy
 * @date: 2019/4/18
 */
@Repository
public interface RealTimeOnlineTaskMapper {
    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    RealTimeOnlineTask selectRealTimeOnlineTaskById(Long id);

    /**
     * 查询实时任务列表
     *
     * @param realTimeOnlineTask 实时任务信息
     * @return 实时任务集合
     */
    List<RealTimeOnlineTask> selectRealTimeOnlineTaskList(RealTimeOnlineTask realTimeOnlineTask);

    /**
     * 新增实时任务
     *
     * @param realTimeOnlineTask 实时任务信息
     * @return 结果
     */
    int insertRealTimeOnlineTask(RealTimeOnlineTask realTimeOnlineTask);

    /**
     * 修改实时任务
     *
     * @param realTimeOnlineTask 实时任务信息
     * @return 结果
     */
    int updateRealTimeOnlineTask(RealTimeOnlineTask realTimeOnlineTask);

    /**
     * 删除实时任务
     *
     * @param id 实时任务ID
     * @return 结果
     */
    int deleteRealTimeOnlineTaskById(Long id);

    /**
     * 批量删除实时任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeOnlineTaskByIds(String[] ids);

    int batchInsert(@Param("list") List<RealTimeOnlineTask> realTimeTaskList);

    int selectRealTimeOnlineTaskCount(@Param("startTime") String startTimeNode
            , @Param("stopTime") String stopTimeNode, @Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    List<RealTimeOnlineTaskVo> selectRealTimeOnlineTaskTodayCount(@Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    List<RealTimeOnlineTaskVo> selectRealTimeOnlineTaskUnFinishNumCount(@Param("taskDate") String taskDate,@Param("teamId")Long teamId,@Param("taskStatus")Integer taskStatus);

    /**
     * 更新任务状态为未抢单
     *
     * @param taskId
     * @return
     */
    int updateTimeTaskByOnlineTaskIds(String[] taskId);

    RealTimeOnlineTask selectCurrentTimeOnlineTaskCount(@Param("taskDate") String taskDate, @Param("currentTime") String currentTime);

    List<String> selectTodoRealTimeOnlineTaskList(@Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("taskDate") String taskDate);


}
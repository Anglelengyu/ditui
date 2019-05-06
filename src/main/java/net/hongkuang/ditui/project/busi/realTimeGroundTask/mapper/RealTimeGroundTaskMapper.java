package net.hongkuang.ditui.project.busi.realTimeGroundTask.mapper;

import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTask;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTaskVo;
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
public interface RealTimeGroundTaskMapper {
    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    RealTimeGroundTask selectRealTimeGroundTaskById(Long id);

    /**
     * 查询实时任务列表
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 实时任务集合
     */
    List<RealTimeGroundTask> selectRealTimeGroundTaskList(RealTimeGroundTask realTimeGroundTask);

    /**
     * 新增实时任务
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 结果
     */
    int insertRealTimeGroundTask(RealTimeGroundTask realTimeGroundTask);

    /**
     * 修改实时任务
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 结果
     */
    int updateRealTimeGroundTask(RealTimeGroundTask realTimeGroundTask);

    /**
     * 删除实时任务
     *
     * @param id 实时任务ID
     * @return 结果
     */
    int deleteRealTimeGroundTaskById(Long id);

    /**
     * 批量删除实时任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeGroundTaskByIds(String[] ids);

    int batchInsert(@Param("list") List<RealTimeGroundTask> realTimeTaskList);

    int selectRealTimeGroundTaskCount(@Param("startTime") String startTimeNode
            , @Param("stopTime") String stopTimeNode, @Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    List<RealTimeGroundTaskVo> selectRealTimeGroundTaskTodayCount(@Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    List<RealTimeGroundTaskVo> selectRealTimeGroundTaskUnFinishNumCount(@Param("taskDate") String taskDate,@Param("teamId")Long teamId,@Param("taskStatus")Integer taskStatus);

    /**
     * 更新任务状态为未抢单
     *
     * @param taskId
     * @return
     */
    int updateTimeTaskByGroundTaskIds(String[] taskId);

    RealTimeGroundTask selectCurrentTimeGroundTaskCount(@Param("taskDate") String taskDate, @Param("currentTime") String currentTime);

    List<String> selectTodoRealTimeGroundTaskList(@Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("taskDate") String taskDate);


}
package net.hongkuang.ditui.project.busi.realTimeGroundTask.service;

import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTask;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTaskVo;
import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;

import java.util.List;

/**
 * 实时任务 服务层
 *
 * @author:zy
 * @date: 2019/4/18
 */
public interface IRealTimeGroundTaskService {
    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    public RealTimeGroundTask selectRealTimeGroundTaskById(Long id);

    /**
     * 查询实时任务列表
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 实时任务集合
     */
    public List<RealTimeGroundTask> selectRealTimeGroundTaskList(RealTimeGroundTask realTimeGroundTask);

    /**
     * 新增实时任务
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 结果
     */
    public int insertRealTimeGroundTask(RealTimeGroundTask realTimeGroundTask);

    /**
     * 修改实时任务
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 结果
     */
    public int updateRealTimeGroundTask(RealTimeGroundTask realTimeGroundTask);

    /**
     * 删除实时任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRealTimeGroundTaskByIds(String ids);

    RealTimeGroundTask selectCurrentTimeGroundTaskCount(String taskDate, String currentTime);

    List<RealTimeGroundTaskVo> selectRealTimeGroundTaskTodayCount(String taskDate,Long teamId);

    List<RealTimeGroundTaskVo> selectRealTimeGroundTaskUnFinishNumCount(String taskDate,Long teamId,Integer taskStatus);
}

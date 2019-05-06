package net.hongkuang.ditui.project.busi.realTimeOnlineTask.service;

import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTask;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTaskVo;
import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;

import java.util.List;

/**
 * 实时任务 服务层
 *
 * @author:zy
 * @date: 2019/4/18
 */
public interface IRealTimeOnlineTaskService {
    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    public RealTimeOnlineTask selectRealTimeOnlineTaskById(Long id);

    /**
     * 查询实时任务列表
     *
     * @param realTimeOnlineTask 实时任务信息
     * @return 实时任务集合
     */
    public List<RealTimeOnlineTask> selectRealTimeOnlineTaskList(RealTimeOnlineTask realTimeOnlineTask);

    /**
     * 新增实时任务
     *
     * @param realTimeOnlineTask 实时任务信息
     * @return 结果
     */
    public int insertRealTimeOnlineTask(RealTimeOnlineTask realTimeOnlineTask);

    /**
     * 修改实时任务
     *
     * @param realTimeOnlineTask 实时任务信息
     * @return 结果
     */
    public int updateRealTimeOnlineTask(RealTimeOnlineTask realTimeOnlineTask);

    /**
     * 删除实时任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRealTimeOnlineTaskByIds(String ids);

    RealTimeOnlineTask selectCurrentTimeOnlineTaskCount(String taskDate, String currentTime);

    List<RealTimeOnlineTaskVo> selectRealTimeOnlineTaskTodayCount(String taskDate,Long teamId);

    List<RealTimeOnlineTaskVo> selectRealTimeOnlineTaskUnFinishNumCount(String taskDate,Long teamId,Integer taskStatus);
}

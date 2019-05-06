package net.hongkuang.ditui.project.busi.realTimeTask.service;

import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;

import java.util.List;

/**
 * 实时任务 服务层
 *
 * @author yj
 * @date 2019-01-05
 */
public interface IRealTimeTaskService {
    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    public RealTimeTask selectRealTimeTaskById(Long id);

    /**
     * 查询实时任务列表
     *
     * @param realTimeTask 实时任务信息
     * @return 实时任务集合
     */
    public List<RealTimeTask> selectRealTimeTaskList(RealTimeTask realTimeTask);

    /**
     * 新增实时任务
     *
     * @param realTimeTask 实时任务信息
     * @return 结果
     */
    public int insertRealTimeTask(RealTimeTask realTimeTask);

    /**
     * 修改实时任务
     *
     * @param realTimeTask 实时任务信息
     * @return 结果
     */
    public int updateRealTimeTask(RealTimeTask realTimeTask);

    /**
     * 删除实时任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRealTimeTaskByIds(String ids);

    RealTimeTask selectCurrentTimeTaskCount(String taskDate, String currentTime);
}

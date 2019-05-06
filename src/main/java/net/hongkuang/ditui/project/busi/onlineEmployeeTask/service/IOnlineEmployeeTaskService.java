package net.hongkuang.ditui.project.busi.onlineEmployeeTask.service;

import net.hongkuang.ditui.project.busi.onlineEmployeeTask.domain.OnlineEmployeeTask;

import java.util.List;

/**
 * 线下员工任务 服务层
 *
 * @author:zy
 * @date: 2019/4/19
 */
public interface IOnlineEmployeeTaskService {
    /**
     * 查询线下员工任务信息
     *
     * @param id 线下员工任务ID
     * @return 线下员工任务信息
     */
    OnlineEmployeeTask selectOnlineEmployeeTaskById(Long id);

    /**
     * 查询线下员工任务列表
     *
     * @param onlineEmployeeTask 线下员工任务信息
     * @return 线下员工任务集合
     */
    List<OnlineEmployeeTask> selectOnlineEmployeeTaskList(OnlineEmployeeTask onlineEmployeeTask);

    /**
     * 新增线下员工任务
     *
     * @param onlineEmployeeTask 线下员工任务信息
     * @return 结果
     */
    int insertOnlineEmployeeTask(OnlineEmployeeTask onlineEmployeeTask);

    /**
     * 修改线下员工任务
     *
     * @param onlineEmployeeTask 线下员工任务信息
     * @return 结果
     */
    int updateOnlineEmployeeTask(OnlineEmployeeTask onlineEmployeeTask);

    /**
     * 删除线下员工任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineEmployeeTaskByIds(String ids);

}

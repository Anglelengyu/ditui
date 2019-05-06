package net.hongkuang.ditui.project.busi.groundEmployeeTask.service;

import net.hongkuang.ditui.project.busi.groundEmployeeTask.domain.GroundEmployeeTask;

import java.util.List;

/**
 * 线下员工任务 服务层
 *
 * @author:zy
 * @date: 2019/4/19
 */
public interface IGroundEmployeeTaskService {
    /**
     * 查询线下员工任务信息
     *
     * @param id 线下员工任务ID
     * @return 线下员工任务信息
     */
    GroundEmployeeTask selectGroundEmployeeTaskById(Long id);

    /**
     * 查询线下员工任务列表
     *
     * @param groundEmployeeTask 线下员工任务信息
     * @return 线下员工任务集合
     */
    List<GroundEmployeeTask> selectGroundEmployeeTaskList(GroundEmployeeTask groundEmployeeTask);

    /**
     * 新增线下员工任务
     *
     * @param groundEmployeeTask 线下员工任务信息
     * @return 结果
     */
    int insertGroundEmployeeTask(GroundEmployeeTask groundEmployeeTask);

    /**
     * 修改线下员工任务
     *
     * @param groundEmployeeTask 线下员工任务信息
     * @return 结果
     */
    int updateGroundEmployeeTask(GroundEmployeeTask groundEmployeeTask);

    /**
     * 删除线下员工任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundEmployeeTaskByIds(String ids);

}

package net.hongkuang.ditui.project.busi.reserveOnlineTask.service;


import net.hongkuang.ditui.project.busi.reserveOnlineTask.domain.ReserveOnlineTask;

import java.util.List;

/**
 * 储备任务 服务层
 *
 * @author:zy
 * @date: 2019/4/19
 */
public interface IReserveOnlineTaskService {
    /**
     * 查询储备任务信息
     *
     * @param id 储备任务ID
     * @return 储备任务信息
     */
    ReserveOnlineTask selectReserveTaskById(Long id);

    /**
     * 查询储备任务列表
     *
     * @param reserveTask 储备任务信息
     * @return 储备任务集合
     */
    List<ReserveOnlineTask> selectReserveTaskList(ReserveOnlineTask reserveTask);

    /**
     * 新增储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    int insertReserveTask(ReserveOnlineTask reserveTask);

    /**
     * 修改储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    int updateReserveTask(ReserveOnlineTask reserveTask);

    /**
     * 删除储备任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteReserveTaskByIds(String ids);

    int insertBatchReserveTask(List<String> taskIds,Long teamId);

    Integer selectCountByReserveDate(String taskDate);
}

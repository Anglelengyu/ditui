package net.hongkuang.ditui.project.busi.reserveTask.service;

import net.hongkuang.ditui.project.busi.reserveTask.domain.ReserveTask;

import java.util.List;

/**
 * 储备任务 服务层
 *
 * @author yj
 * @date 2019-01-03
 */
public interface IReserveTaskService {
    /**
     * 查询储备任务信息
     *
     * @param id 储备任务ID
     * @return 储备任务信息
     */
    ReserveTask selectReserveTaskById(Long id);

    /**
     * 查询储备任务列表
     *
     * @param reserveTask 储备任务信息
     * @return 储备任务集合
     */
    List<ReserveTask> selectReserveTaskList(ReserveTask reserveTask);

    /**
     * 新增储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    int insertReserveTask(ReserveTask reserveTask);

    /**
     * 修改储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    int updateReserveTask(ReserveTask reserveTask);

    /**
     * 删除储备任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteReserveTaskByIds(String ids);

    int insertBatchReserveTask(List<String> taskIds);

    Integer selectCountByReserveDate(String taskDate);
}

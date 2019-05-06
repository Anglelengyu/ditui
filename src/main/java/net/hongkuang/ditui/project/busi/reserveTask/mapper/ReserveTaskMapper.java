package net.hongkuang.ditui.project.busi.reserveTask.mapper;

import net.hongkuang.ditui.project.busi.reserveTask.domain.ReserveTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 储备任务 数据层
 *
 * @author yj
 * @date 2019-01-03
 */
@Repository
public interface ReserveTaskMapper {
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
     * 删除储备任务
     *
     * @param id 储备任务ID
     * @return 结果
     */
    int deleteReserveTaskById(Long id);

    /**
     * 批量删除储备任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteReserveTaskByIds(String[] ids);

    int insertBatchReserveTask(List<ReserveTask> reserveTasks);

    Integer selectCountByReserveDate(String reserveDate);

    List<String> selectTodoReserveTaskList(@Param("reserveDate") String reserveDate);
}
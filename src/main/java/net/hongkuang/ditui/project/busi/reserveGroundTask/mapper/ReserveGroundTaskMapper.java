package net.hongkuang.ditui.project.busi.reserveGroundTask.mapper;

import net.hongkuang.ditui.project.busi.reserveGroundTask.domain.ReserveGroundTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 储备任务 数据层
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Repository
public interface ReserveGroundTaskMapper {
    /**
     * 查询储备任务信息
     *
     * @param id 储备任务ID
     * @return 储备任务信息
     */
    ReserveGroundTask selectReserveTaskById(Long id);

    /**
     * 查询储备任务列表
     *
     * @param reserveTask 储备任务信息
     * @return 储备任务集合
     */
    List<ReserveGroundTask> selectReserveTaskList(ReserveGroundTask reserveTask);

    /**
     * 新增储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    int insertReserveTask(ReserveGroundTask reserveTask);

    /**
     * 修改储备任务
     *
     * @param reserveTask 储备任务信息
     * @return 结果
     */
    int updateReserveTask(ReserveGroundTask reserveTask);

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

    int insertBatchReserveTask(List<ReserveGroundTask> reserveTasks);

    Integer selectCountByReserveDate(String reserveDate);

    List<String> selectTodoReserveTaskList(@Param("reserveDate") String reserveDate);
}
package net.hongkuang.ditui.project.busi.reserveOnlineTask.mapper;

import net.hongkuang.ditui.project.busi.reserveOnlineTask.domain.ReserveOnlineTask;
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
public interface ReserveOnlineTaskMapper {
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

    int insertBatchReserveTask(List<ReserveOnlineTask> reserveTasks);

    Integer selectCountByReserveDate(String reserveDate);

    List<String> selectTodoReserveTaskList(@Param("reserveDate") String reserveDate);
}
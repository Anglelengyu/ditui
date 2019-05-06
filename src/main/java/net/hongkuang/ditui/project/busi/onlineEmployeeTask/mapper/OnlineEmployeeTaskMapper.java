package net.hongkuang.ditui.project.busi.onlineEmployeeTask.mapper;

import net.hongkuang.ditui.project.busi.onlineEmployeeTask.domain.OnlineEmployeeTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 业务员任务 数据层
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Repository
public interface OnlineEmployeeTaskMapper {
    /**
     * 查询业务员任务信息
     *
     * @param id 业务员任务ID
     * @return 业务员任务信息
     */
    OnlineEmployeeTask selectOnlineEmployeeTaskById(Long id);

    /**
     * 查询业务员任务列表
     *
     * @param onlineEmployeeTask 业务员任务信息
     * @return 业务员任务集合
     */
    List<OnlineEmployeeTask> selectOnlineEmployeeTaskList(OnlineEmployeeTask onlineEmployeeTask);

    /**
     * 新增业务员任务
     *
     * @param onlineEmployeeTask 业务员任务信息
     * @return 结果
     */
    int insertOnlineEmployeeTask(OnlineEmployeeTask onlineEmployeeTask);

    /**
     * 修改业务员任务
     *
     * @param onlineEmployeeTask 业务员任务信息
     * @return 结果
     */
    int updateOnlineEmployeeTask(OnlineEmployeeTask onlineEmployeeTask);

    /**
     * 删除业务员任务
     *
     * @param id 业务员任务ID
     * @return 结果
     */
    int deleteOnlineEmployeeTaskById(Long id);

    /**
     * 批量删除业务员任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineEmployeeTaskByIds(String[] ids);

    int deleteByTaskIds(String[] taskIds);

    int batchInsert(@Param("list") List<OnlineEmployeeTask> onlineEmployeeTaskList);

    /**
     * 获取用户的接单量
     *
     * @param saleId
     * @param taskDate
     * @return
     */
    Integer getOnlineEmployeeTaskNum(@Param("saleId") String saleId, @Param("taskDate") String taskDate);
}
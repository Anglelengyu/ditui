package net.hongkuang.ditui.project.busi.groundEmployeeTask.mapper;

import net.hongkuang.ditui.project.busi.groundEmployeeTask.domain.GroundEmployeeTask;
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
public interface GroundEmployeeTaskMapper {
    /**
     * 查询业务员任务信息
     *
     * @param id 业务员任务ID
     * @return 业务员任务信息
     */
    GroundEmployeeTask selectGroundEmployeeTaskById(Long id);

    /**
     * 查询业务员任务列表
     *
     * @param groundEmployeeTask 业务员任务信息
     * @return 业务员任务集合
     */
    List<GroundEmployeeTask> selectGroundEmployeeTaskList(GroundEmployeeTask groundEmployeeTask);

    /**
     * 新增业务员任务
     *
     * @param groundEmployeeTask 业务员任务信息
     * @return 结果
     */
    int insertGroundEmployeeTask(GroundEmployeeTask groundEmployeeTask);

    /**
     * 修改业务员任务
     *
     * @param groundEmployeeTask 业务员任务信息
     * @return 结果
     */
    int updateGroundEmployeeTask(GroundEmployeeTask groundEmployeeTask);

    /**
     * 删除业务员任务
     *
     * @param id 业务员任务ID
     * @return 结果
     */
    int deleteGroundEmployeeTaskById(Long id);

    /**
     * 批量删除业务员任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundEmployeeTaskByIds(String[] ids);

    int deleteByTaskIds(String[] taskIds);

    int batchInsert(@Param("list") List<GroundEmployeeTask> groundEmployeeTaskList);

    /**
     * 获取用户的接单量
     *
     * @param saleId
     * @param taskDate
     * @return
     */
    Integer getGroundEmployeeTaskNum(@Param("saleId") String saleId, @Param("taskDate") String taskDate);
}
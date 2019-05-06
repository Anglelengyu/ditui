package net.hongkuang.ditui.project.busi.groundTask.service;

import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTaskVo;
import net.hongkuang.ditui.project.busi.groundTask.domain.SearchGroundTask;

import java.util.List;
import java.util.Map;

/**
 * 任务 服务层
 *
 * @author:zy
 * @date: 2019/4/17
 */
public interface IGroundTaskService {

    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    List<GroundTaskVo> selectTaskList(SearchGroundTask task);

    /**
     * 查询单个任务
     *
     * @param id 订单id
     * @return 订单集合
     */
    public GroundTask selectGroundTaskById(Long id);

    /**
     * 查询单个任务
     *
     * @param orderId 订单id
     * @return 订单集合
     */
    public GroundTask selectGroundTaskByOrderId(String orderId);

    /**
     * 保存任务团队备注
     *
     * @param groundTask 信息
     * @return 结果
     */
    int updateGroundTaskTeamRemark(GroundTask groundTask);

    /**
     * 删除任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundTaskByIds(String ids);

    /**
     * 批量更新任务状态为分配
     *
     * @param taskIds
     */
    int updateBatchGroundTaskStatusAllot(List<String> taskIds);

    int toReal(String taskDate, String startTime, String stopTime, String taskIds,Long teamId);

    int toSale(String[] taskIds, String employeeId, String userId,Long teamId);

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中
     */
    Map<String, Object> checkStatus(String userId);

    boolean selectGroundTaskIsDistribution(String[] ids);

    /**
     * 任务分配合作团队
     *
     * @param taskIds 信息
     * @return 结果
     */
    int taskDistributionTeam(String[] taskIds, Long teamId, String teamName);

}

package net.hongkuang.ditui.project.busi.onlineTask.service;

import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTaskVo;
import net.hongkuang.ditui.project.busi.onlineTask.domain.SearchOnlineTask;

import java.util.List;
import java.util.Map;

/**
 * 任务 服务层
 *
 * @author:zy
 * @date: 2019/4/17
 */
public interface IOnlineTaskService {

    /**
     * 查询任务列表
     *
     * @param task 任务信息
     * @return 任务集合
     */
    List<OnlineTaskVo> selectTaskList(SearchOnlineTask task);

    /**
     * 查询单个任务
     *
     * @param id 订单id
     * @return 订单集合
     */
    public OnlineTask selectOnlineTaskById(Long id);

    /**
     * 查询单个任务
     *
     * @param orderId 订单id
     * @return 订单集合
     */
    public OnlineTask selectOnlineTaskByOrderId(String orderId);

    /**
     * 保存任务团队备注
     *
     * @param onlineTask 信息
     * @return 结果
     */
    int updateOnlineTaskTeamRemark(OnlineTask onlineTask);

    /**
     * 删除任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineTaskByIds(String ids);

    /**
     * 批量更新任务状态为分配
     *
     * @param taskIds
     */
    int updateBatchOnlineTaskStatusAllot(List<String> taskIds);

    int toReal(String taskDate, String startTime, String stopTime, String taskIds,Long teamId);

    int toSale(String[] taskIds, String employeeId, String userId,Long teamId);

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中
     */
    Map<String, Object> checkStatus(String userId);

    boolean selectOnlineTaskIsDistribution(String[] ids);

    /**
     * 任务分配合作团队
     *
     * @param taskIds 信息
     * @return 结果
     */
    int taskDistributionTeam(String[] taskIds, Long teamId, String teamName);

}

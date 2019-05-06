package net.hongkuang.ditui.project.busi.taskCancelApproval.service;

import net.hongkuang.ditui.project.busi.taskCancelApproval.domain.TaskCancelApproval;

import java.util.List;

/**
 * 任务取消审批 服务层
 *
 * @author yj
 * @date 2019-01-06
 */
public interface ITaskCancelApprovalService {
    /**
     * 查询任务取消审批信息
     *
     * @param id 任务取消审批ID
     * @return 任务取消审批信息
     */
    TaskCancelApproval selectTaskCancelApprovalById(Integer id);

    /**
     * 查询任务取消审批列表
     *
     * @param taskCancelApproval 任务取消审批信息
     * @return 任务取消审批集合
     */
    List<TaskCancelApproval> selectTaskCancelApprovalList(TaskCancelApproval taskCancelApproval);

    /**
     * 新增任务取消审批
     *
     * @param taskCancelApproval 任务取消审批信息
     * @return 结果
     */
    int insertTaskCancelApproval(TaskCancelApproval taskCancelApproval);

    /**
     * 修改任务取消审批
     *
     * @param taskCancelApproval 任务取消审批信息
     * @return 结果
     */
    int updateTaskCancelApproval(TaskCancelApproval taskCancelApproval);

    /**
     * 删除任务取消审批信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTaskCancelApprovalByIds(String ids);

    /**
     * 通过取消申请
     *
     * @param ids
     * @return
     */
    int pass(String ids);

    /**
     * 驳回取消申请
     *
     * @param ids
     * @return
     */
    int reject(String ids);
}

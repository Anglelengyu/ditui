package net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.mapper;

import net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.domain.OnlineTaskCancelApproval;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务取消审批 数据层
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Repository
public interface OnlineTaskCancelApprovalMapper {
    /**
     * 查询任务取消审批信息
     *
     * @param id 任务取消审批ID
     * @return 任务取消审批信息
     */
    OnlineTaskCancelApproval selectOnlineTaskCancelApprovalById(Integer id);

    /**
     * 查询任务取消审批列表
     *
     * @param onlineTaskCancelApproval 任务取消审批信息
     * @return 任务取消审批集合
     */
    List<OnlineTaskCancelApproval> selectOnlineTaskCancelApprovalList(OnlineTaskCancelApproval onlineTaskCancelApproval);

    /**
     * 新增任务取消审批
     *
     * @param onlineTaskCancelApproval 任务取消审批信息
     * @return 结果
     */
    int insertOnlineTaskCancelApproval(OnlineTaskCancelApproval onlineTaskCancelApproval);

    /**
     * 修改任务取消审批
     *
     * @param onlineTaskCancelApproval 任务取消审批信息
     * @return 结果
     */
    int updateOnlineTaskCancelApproval(OnlineTaskCancelApproval onlineTaskCancelApproval);

    /**
     * 删除任务取消审批
     *
     * @param id 任务取消审批ID
     * @return 结果
     */
    int deleteOnlineTaskCancelApprovalById(Integer id);

    /**
     * 批量删除任务取消审批
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteOnlineTaskCancelApprovalByIds(String[] ids);

    /**
     * 通过取消申请
     *
     * @param ids
     * @return
     */
    int pass(String[] ids);

    /**
     * 驳回取消申请
     *
     * @param ids
     * @return
     */
    int reject(String[] ids);

    /**
     * 根据id查询所有任务id
     *
     * @param ids
     * @return
     */
    List<String> selectOnlineTaskIdsByIds(String[] ids);

    int deleteOnlineTaskCancelApprovalByTaskIdList(List<String> taskIdList);

    List<String> getOnlineTaskIdsById(String[] idStr);
}
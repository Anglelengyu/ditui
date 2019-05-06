package net.hongkuang.ditui.project.busi.groundTaskCancelApproval.mapper;

import net.hongkuang.ditui.project.busi.groundTaskCancelApproval.domain.GroundTaskCancelApproval;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务取消审批 数据层
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Repository
public interface GroundTaskCancelApprovalMapper {
    /**
     * 查询任务取消审批信息
     *
     * @param id 任务取消审批ID
     * @return 任务取消审批信息
     */
    GroundTaskCancelApproval selectGroundTaskCancelApprovalById(Integer id);

    /**
     * 查询任务取消审批列表
     *
     * @param groundTaskCancelApproval 任务取消审批信息
     * @return 任务取消审批集合
     */
    List<GroundTaskCancelApproval> selectGroundTaskCancelApprovalList(GroundTaskCancelApproval groundTaskCancelApproval);

    /**
     * 新增任务取消审批
     *
     * @param groundTaskCancelApproval 任务取消审批信息
     * @return 结果
     */
    int insertGroundTaskCancelApproval(GroundTaskCancelApproval groundTaskCancelApproval);

    /**
     * 修改任务取消审批
     *
     * @param groundTaskCancelApproval 任务取消审批信息
     * @return 结果
     */
    int updateGroundTaskCancelApproval(GroundTaskCancelApproval groundTaskCancelApproval);

    /**
     * 删除任务取消审批
     *
     * @param id 任务取消审批ID
     * @return 结果
     */
    int deleteGroundTaskCancelApprovalById(Integer id);

    /**
     * 批量删除任务取消审批
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteGroundTaskCancelApprovalByIds(String[] ids);

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
    List<String> selectGroundTaskIdsByIds(String[] ids);

    int deleteGroundTaskCancelApprovalByTaskIdList(List<String> taskIdList);

    List<String> getGroundTaskIdsById(String[] idStr);
}
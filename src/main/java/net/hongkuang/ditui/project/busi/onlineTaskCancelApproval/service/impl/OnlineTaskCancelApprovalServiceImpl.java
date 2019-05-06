package net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.service.impl;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.domain.OnlineTaskCancelApproval;
import net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.mapper.OnlineTaskCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.service.IOnlineTaskCancelApprovalService;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.realTimeTask.mapper.RealTimeTaskMapper;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.task.service.ITaskService;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任务取消审批 服务层实现
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Service
public class OnlineTaskCancelApprovalServiceImpl implements IOnlineTaskCancelApprovalService {
    @Autowired
    private OnlineTaskCancelApprovalMapper onlineTaskCancelApprovalMapper;

    @Autowired
    private ITaskService taskService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SaleTaskMapper saleTaskMapper;
    @Autowired
    private RealTimeTaskMapper realTimeTaskMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private TaskOrderMapper taskOrderMapper;

    /**
     * 查询任务取消审批信息
     *
     * @param id 任务取消审批ID
     * @return 任务取消审批信息
     */
    @Override
    public OnlineTaskCancelApproval selectOnlineTaskCancelApprovalById(Integer id) {
        return onlineTaskCancelApprovalMapper.selectOnlineTaskCancelApprovalById(id);
    }

    /**
     * 查询任务取消审批列表
     *
     * @param onlineTaskCancelApproval 任务取消审批信息
     * @return 任务取消审批集合
     */
    @Override
    public List<OnlineTaskCancelApproval> selectOnlineTaskCancelApprovalList(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        return onlineTaskCancelApprovalMapper.selectOnlineTaskCancelApprovalList(onlineTaskCancelApproval);
    }

    /**
     * 新增任务取消审批
     *
     * @param onlineTaskCancelApproval 任务取消审批信息
     * @return 结果
     */
    @Override
    public int insertOnlineTaskCancelApproval(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        return onlineTaskCancelApprovalMapper.insertOnlineTaskCancelApproval(onlineTaskCancelApproval);
    }

    /**
     * 修改任务取消审批
     *
     * @param onlineTaskCancelApproval 任务取消审批信息
     * @return 结果
     */
    @Override
    public int updateOnlineTaskCancelApproval(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        return onlineTaskCancelApprovalMapper.updateOnlineTaskCancelApproval(onlineTaskCancelApproval);
    }

    /**
     * 删除任务取消审批对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOnlineTaskCancelApprovalByIds(String ids) {
        return onlineTaskCancelApprovalMapper.deleteOnlineTaskCancelApprovalByIds(Convert.toStrArray(ids));
    }

    /**
     * 通过取消申请
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int pass(String id) {
        String[] ids = id.split(",");
        int count = onlineTaskCancelApprovalMapper.pass(ids);
        List<String> taskIds = onlineTaskCancelApprovalMapper.selectOnlineTaskIdsByIds(ids);
        String[] taskId = new String[taskIds.size()];
        taskIds.toArray(taskId);
        // 删除对应的业务员任务订单
        int dcount = saleTaskMapper.deleteByTaskIds(taskId);
        // 更改对应的任务为未接单
        int ccount = taskService.updateTaskByTaskIds(taskId);
//        // 更新实时任务表状态
//        int crcount = realTimeTaskMapper.updateTimeTaskByTaskIds(taskId);
        //  更新订单状态
        int ocount = orderMapper.updateOrderStatusByTaskIds(taskId);
//        int tcount = taskOrderMapper.updateTaskOrderStatusByTaskIds(taskId);
//        int scount = saleOrderMapper.updateSaleOrderStatusByTaskId(taskId);
        // 数值不相同为通过失败
        if (count > 0 && dcount > 0 && ccount > 0 && ocount > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 驳回取消申请
     *
     * @param id
     * @return
     */
    @Override
    public int reject(String id) {
        String[] idStr = id.split(",");
        List<String> ids = onlineTaskCancelApprovalMapper.getOnlineTaskIdsById(idStr);
        int count = onlineTaskCancelApprovalMapper.reject(idStr);
        // 修改状态
        int result = 0;
        try {
            result = taskMapper.updateBatchTaskStatusByIds(ids, TaskTaskStatus.RECEIVED.getCode(), TaskTaskStatus.CANCEL.getCode(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result <= 0) {
            throw new BusinessException("订单驳回失败，请重试");
        }
        return count;
    }
}

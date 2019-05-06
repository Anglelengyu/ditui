package net.hongkuang.ditui.project.busi.realTimeGroundTask.service;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.exception.BusinessDataException;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.exception.ExceptionCode;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.LogUtils;
import net.hongkuang.ditui.project.api.dto.TodoTaskReqVo;
import net.hongkuang.ditui.project.api.dto.TodoTaskRespVo;
import net.hongkuang.ditui.project.api.service.ITodoTaskService;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTask;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.domain.RealTimeGroundTaskVo;
import net.hongkuang.ditui.project.busi.realTimeGroundTask.mapper.RealTimeGroundTaskMapper;
import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleTask.domain.SaleTask;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.mapper.SalesmanMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实时任务 服务层实现
 *
 * @author:zy
 * @date: 2019/4/18
 */
@Service(value = "realTimeGroundTaskService")
public class RealTimeGroundTaskServiceImpl implements IRealTimeGroundTaskService, ITodoTaskService {
    private ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    private RealTimeGroundTaskMapper realTimeGroundTaskMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SaleOrderMapper saleOrderMapper;
    @Autowired
    private SaleTaskMapper saleTaskMapper;
    @Autowired
    private TaskOrderMapper taskOrderMapper;
    @Autowired
    private SalesmanMapper salesmanMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询实时任务信息
     *
     * @param id 实时任务ID
     * @return 实时任务信息
     */
    @Override
    public RealTimeGroundTask selectRealTimeGroundTaskById(Long id) {
        return realTimeGroundTaskMapper.selectRealTimeGroundTaskById(id);
    }

    /**
     * 查询实时任务列表
     *
     * @param realTimeTask 实时任务信息
     * @return 实时任务集合
     */
    @Override
    public List<RealTimeGroundTask> selectRealTimeGroundTaskList(RealTimeGroundTask realTimeTask) {
        return realTimeGroundTaskMapper.selectRealTimeGroundTaskList(realTimeTask);
    }

    /**
     * 新增实时任务
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 结果
     */
    @Override
    public int insertRealTimeGroundTask(RealTimeGroundTask realTimeGroundTask) {
        return realTimeGroundTaskMapper.insertRealTimeGroundTask(realTimeGroundTask);
    }

    /**
     * 修改实时任务
     *
     * @param realTimeGroundTask 实时任务信息
     * @return 结果
     */
    @Override
    public int updateRealTimeGroundTask(RealTimeGroundTask realTimeGroundTask) {
        return realTimeGroundTaskMapper.updateRealTimeGroundTask(realTimeGroundTask);
    }

    /**
     * 删除实时任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRealTimeGroundTaskByIds(String ids) {
        return realTimeGroundTaskMapper.deleteRealTimeGroundTaskByIds(Convert.toStrArray(ids));
    }

    @Override
    public RealTimeGroundTask selectCurrentTimeGroundTaskCount(String taskDate, String currentTime) {
        return realTimeGroundTaskMapper.selectCurrentTimeGroundTaskCount(taskDate, currentTime);
    }

    @Override
    public List<TodoTaskRespVo> allocTodoTaskList(TodoTaskReqVo taskReqVo) {
        Salesman salesman = salesmanMapper.selectSalesmanById(taskReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }

        LogUtils.getAccessLog().info("开始分配任务, 争抢锁开始 userID:{}", taskReqVo.getUserId());
        String saleId = salesman.getSaleId();
        boolean lockResult = false;
        try {
            lockResult = reentrantLock.tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LogUtils.getAccessLog().info("开始分配任务, 抢锁失败 userID:{}", taskReqVo.getUserId());
            throw new BusinessException("当前有其他业务员在抢实时任务，请稍后重试");
        }
        if (!lockResult) {
            LogUtils.getAccessLog().info("开始分配任务, 抢锁失败 userID:{}", taskReqVo.getUserId());
            throw new BusinessException("当前有其他业务员在抢实时任务，请稍后重试");
        }
        LogUtils.getAccessLog().info("开始分配任务, 抢锁成功 userID:{}", taskReqVo.getUserId());

        long startTime = System.currentTimeMillis();
        List<TodoTaskRespVo> todoTaskRespVoList = new ArrayList<>();

        try {
            // 判断当前用户是否有为完成的任务
            Task task = taskMapper.getFirstTask(saleId, TaskTaskStatus.RECEIVED.getCode());
            if (task != null) {
                throw new BusinessDataException(ExceptionCode.CODE_TASK_WAIT, "当前有未完成任务，不可领取信息任务", task.getTaskId());
            }
            String taskDate = DateUtils.getDate();
            if (salesman.getOrderLimit() == null || salesman.getOrderLimit() == 0) {
                throw new BusinessException("账号未设置接单上限,无法接取任务!");
            }
            // 判断用户当日接单量
            Integer taskNum = saleTaskMapper.getSaleTaskNum(saleId, taskDate);
            if (taskNum >= salesman.getOrderLimit()) {
                throw new BusinessException("当日任务订单量已达到上限,无法接取任务!");
            }

            PageHelper.startPage(1, taskReqVo.getTaskCount());

            List<String> todoTaskList = realTimeGroundTaskMapper.selectTodoRealTimeGroundTaskList(taskReqVo.getStartTime()
                    , taskReqVo.getStopTime(), taskDate);
            for (String taskId : todoTaskList) {
                todoTaskRespVoList.add(new TodoTaskRespVo(taskId));
            }
            if (todoTaskList.isEmpty()) {
                throw new BusinessException("目前已无实时任务分配，请选择备份任务");
            }
            // 修改任务状态
            taskMapper.updateBatchTaskStatusByIds(todoTaskList, TaskTaskStatus.RECEIVED.getCode()
                    , TaskTaskStatus.WAIT.getCode(), saleId);

            List<SaleOrder> saleOrderList = new ArrayList<>();
            List<SaleTask> saleTaskList = new ArrayList<>();
            List<String> updateOrderIdList = new ArrayList<>();
            Date date = DateUtils.getNowDate();
            for (String taskId : todoTaskList) {
                // 关联任务
                SaleTask saleTask = SaleTask.build(taskId, saleId);
                saleTask.setCreateTime(date);
                saleTask.setUpdateTime(date);
                saleTaskList.add(saleTask);

                // 获取关联订单
                List<String> orderIdList = taskOrderMapper.selectOrderIdByTaskId(taskId);
                for (String orderId : orderIdList) {
                    SaleOrder saleOrder = SaleOrder.build(orderId, saleId);
                    saleOrder.setCreateTime(date);
                    saleOrder.setUpdateTime(date);
                    saleOrderList.add(saleOrder);
                }
                updateOrderIdList.addAll(orderIdList);
            }
            // 创建业务员任务表
            saleTaskMapper.batchInsert(saleTaskList);
            // 创建业务员订单表
            saleOrderMapper.batchInsert(saleOrderList);
            // 修改订单状态
            orderMapper.updateOrderStatusInOrderIds(updateOrderIdList, OrderStatus.COMPLETE.getCode(), OrderStatus.UNFINISHED.getCode());

        } finally {
            long endTime = System.currentTimeMillis();
            LogUtils.getAccessLog().info("开始分配任务, 锁释放 userID:{}, cost time:{}", taskReqVo.getUserId()
                    , endTime - startTime);
            reentrantLock.unlock();
        }
        return todoTaskRespVoList;
    }

    @Override
    public List<RealTimeGroundTaskVo> selectRealTimeGroundTaskTodayCount(String taskDate,Long teamId) {
        return realTimeGroundTaskMapper.selectRealTimeGroundTaskTodayCount(taskDate,teamId);
    }

    @Override
    public List<RealTimeGroundTaskVo> selectRealTimeGroundTaskUnFinishNumCount(String taskDate, Long teamId,Integer taskStatus) {
        return realTimeGroundTaskMapper.selectRealTimeGroundTaskUnFinishNumCount(taskDate,teamId,taskStatus);
    }
}

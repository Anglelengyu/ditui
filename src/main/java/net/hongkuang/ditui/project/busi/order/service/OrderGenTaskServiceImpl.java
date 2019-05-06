package net.hongkuang.ditui.project.busi.order.service;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.idSegment.service.IIdSegmentService;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.SearchOrder;
import net.hongkuang.ditui.project.busi.order.dto.GenTaskDto;
import net.hongkuang.ditui.project.busi.order.dto.OrderTaskDto;
import net.hongkuang.ditui.project.busi.order.dto.UnfinishGoodsDto;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskOrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskOrder.domain.TaskOrder;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by apple on 2019/1/3.
 */
@Service
public class OrderGenTaskServiceImpl implements IOrderGenTaskService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskOrderMapper taskOrderMapper;
    @Autowired
    private IIdSegmentService idSegmentService;

    @Override
    public List<UnfinishGoodsDto> getUnFinishGoodsCategoryList(List<String> shopIdList) {
        return orderMapper.getUnFinishGoodsCategoryList(OrderAllocatStatus.UNFINISHED.getCode(), shopIdList);
    }

    @Override
    public int batchInsert(List<List<Order>> finishOrderLists) {
        if (finishOrderLists == null || finishOrderLists.isEmpty()) {
            return 0;
        }
        String operator = ShiroUtils.getLoginName();
        List<OrderTaskDto> orderTaskDtoList = new ArrayList<>();
        Date now = DateUtils.getNowDate();
        int lsize = finishOrderLists.get(0).size();
        for (int i = 0; i < lsize; i++) {
            OrderTaskDto orderTaskDto = new OrderTaskDto();
            orderTaskDto.setTaskId(idSegmentService.genTaskId());
            orderTaskDto.setCreateBy(operator);
            orderTaskDto.setUpdateBy(operator);
            orderTaskDto.setCreateTime(DateUtils.getNowDate());
            orderTaskDto.setUpdateTime(now);
            orderTaskDto.setStatus(OrderAllocatStatus.UNFINISHED.getCode());
            orderTaskDto.setTaskStatus(TaskTaskStatus.WAIT.getCode());
            orderTaskDtoList.add(orderTaskDto);
        }

        for (int i = 0; i < lsize; i++) {
            int jsize = finishOrderLists.size();
            for (int j = 0; j < jsize; j++) {
                OrderTaskDto orderTaskDto = orderTaskDtoList.get(i);
                if (orderTaskDto.getOrderTasks() == null) {
                    orderTaskDto.setOrderTasks(new ArrayList<>(lsize));
                }
                orderTaskDto.getOrderTasks().add(finishOrderLists.get(j).get(i));
            }
        }
        // 进行DB 操作
        List<Task> taskList = new ArrayList<>(orderTaskDtoList.size());
        List<TaskOrder> taskOrderList = new ArrayList<>();
        for (OrderTaskDto orderTaskDto : orderTaskDtoList) {
            taskList.add(orderTaskDto);
            orderTaskDto.setOrderNum(orderTaskDto.getOrderTasks().size());
            long taskCommission = 0;
            long taskCorpus = 0;
            for (Order order : orderTaskDto.getOrderTasks()) {
                TaskOrder taskOrder = new TaskOrder();
                taskOrder.setTaskOrderId(order.getOrderId());
                taskOrder.setTaskId(orderTaskDto.getTaskId());
                taskOrder.setOrderId(order.getOrderId());
                taskOrder.setCreateTime(now);
                taskOrder.setStatus(TaskOrderStatus.NORMAL.getCode());
                taskOrder.setUpdateTime(now);
                taskOrder.setUpdateBy(operator);
                taskOrder.setCreateBy(operator);
                taskOrderList.add(taskOrder);

                taskCommission += order.getCommission();
                taskCorpus += order.getUnitPrice();
            }
            orderTaskDto.setTaskCommission(taskCommission);
            orderTaskDto.setTaskCorpus(taskCorpus);
        }
        taskOrderMapper.batchInsert(taskOrderList);
        return taskMapper.batchInsert(taskList);
    }

    @Override
    public List<Order> gen(String category, Integer categoryCount, List<String> shopIdList) {
        // 查询指定商品 (这里看匹配订单的数量 --> 超过3000条查询)
        List<UnfinishGoodsDto> unFinishGoodsList = orderMapper.getUnFinishGoodsList(OrderAllocatStatus.UNFINISHED.getCode()
                , category, shopIdList);
        List<String> unFlinsiGoodsNameList = new ArrayList<>(unFinishGoodsList.size());
        for (UnfinishGoodsDto unfinishGoodsDto : unFinishGoodsList) {
            unFlinsiGoodsNameList.add(unfinishGoodsDto.getGoodsName());
        }
        SearchOrder searchOrder = new SearchOrder();
        searchOrder.setCategory(category);
        searchOrder.setAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
        searchOrder.setShopIdList(shopIdList);
        searchOrder.setSortGoodsNameList(unFlinsiGoodsNameList);

        PageHelper.startPage(1, categoryCount);
        List<Order> unFinishedList = orderMapper.selectUnFinishedList(searchOrder);
        List<Long> orderIdList = new ArrayList<>();
        for (Order order : unFinishedList) {
            orderIdList.add(order.getId());
        }
        if (!unFinishedList.isEmpty()) {
            // 进行更新状态
            orderMapper.updateOrderAllocStatusInIds(orderIdList, OrderAllocatStatus.COMPLETE.getCode(), OrderAllocatStatus.UNFINISHED.getCode());
            orderMapper.updateOrderAllocStatusInIdsForReckonAllocatStatus(orderIdList, OrderAllocatStatus.COMPLETE.getCode(), OrderAllocatStatus.UNFINISHED.getCode());
        }
        return unFinishedList;
    }

    @Override
    public List<Order> getTaskNum(String category, Integer categoryCount, List<String> shopIdList) {
        // 查询指定商品 (这里看匹配订单的数量 --> 超过3000条查询)
        List<UnfinishGoodsDto> unFinishGoodsList = orderMapper.getUnFinishGoodsReckonAllocatstatusList(OrderAllocatStatus.UNFINISHED.getCode()
                , category, shopIdList);
        if (unFinishGoodsList == null || unFinishGoodsList.size() <= 0) {
            return null;
        }
        List<String> unFlinsiGoodsNameList = new ArrayList<>(unFinishGoodsList.size());
        for (UnfinishGoodsDto unfinishGoodsDto : unFinishGoodsList) {
            unFlinsiGoodsNameList.add(unfinishGoodsDto.getGoodsName());
        }
        SearchOrder searchOrder = new SearchOrder();
        searchOrder.setCategory(category);
        searchOrder.setReckonAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
        searchOrder.setShopIdList(shopIdList);
        searchOrder.setSortGoodsNameList(unFlinsiGoodsNameList);

        PageHelper.startPage(1, categoryCount);
        List<Order> unFinishedList = orderMapper.selectUnFinishedList(searchOrder);
        List<Long> orderIdList = new ArrayList<>();
        for (Order order : unFinishedList) {
            orderIdList.add(order.getId());
        }
        if (!unFinishedList.isEmpty()) {
            // 进行更新状态
            orderMapper.updateOrderAllocStatusInIdsForReckonAllocatStatus(orderIdList, OrderAllocatStatus.COMPLETE.getCode(), OrderAllocatStatus.UNFINISHED.getCode());
        }
        return unFinishedList;
    }

    @Override
    public List<UnfinishGoodsDto> getUnFinishGoodsCategoryListForReckonAllocatStatus(List<String> shopIdList) {
        return orderMapper.getUnFinishGoodsCategoryListForReckonAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode(), shopIdList);
    }
}

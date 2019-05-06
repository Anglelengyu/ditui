package net.hongkuang.ditui.project.busi.order.service;

import com.github.pagehelper.PageHelper;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.idSegment.service.IIdSegmentService;
import net.hongkuang.ditui.project.busi.order.domain.SearchTbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionOrderTaskDto;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionWaitBuildGoodsDto;
import net.hongkuang.ditui.project.busi.order.enums.*;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.mapper.GroundTaskMapper;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrder;
import net.hongkuang.ditui.project.busi.groundTaskOrder.mapper.GroundTaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:zy
 * @date: 2019/4/17
 */
@Service
public class TbTransactionOrderGenTaskServiceImpl implements ITbTransactionOrderGenTaskService {
    @Autowired
    private IIdSegmentService idSegmentService;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private GroundTaskOrderMapper tbTransactionTaskOrderMapper;
    @Autowired
    private GroundTaskMapper tbTransactionTaskMapper;

    @Override
    public List<TbTransactionWaitBuildGoodsDto> getWaitBuildGroupClassifysList(Long teamId) {
        return tbTransactionOrderMapper.getWaitBuildGroupClassifysList(TbTransactionOrderAllocatStatus.UNFINISHED.getCode(), teamId);
    }

    @Override
    public int batchInsert(List<List<TbTransactionOrder>> finishOrderLists,Long teamId) {
        if (finishOrderLists == null || finishOrderLists.isEmpty()) {
            return 0;
        }
        String operator = ShiroUtils.getLoginName();
        List<TbTransactionOrderTaskDto> orderTaskDtoList = new ArrayList<>();
        Date now = DateUtils.getNowDate();
        int lsize = finishOrderLists.get(0).size();
        for (int i = 0; i < lsize; i++) {
            TbTransactionOrderTaskDto orderTaskDto = new TbTransactionOrderTaskDto();
            orderTaskDto.setTaskId(idSegmentService.genTaskId());
            orderTaskDto.setCreateBy(operator);
            orderTaskDto.setUpdateBy(operator);
            orderTaskDto.setCreateTime(DateUtils.getNowDate());
            orderTaskDto.setUpdateTime(now);
            orderTaskDto.setStatus(TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
            orderTaskDto.setTaskStatus(TbTransactionTaskStatus.WAIT.getCode());
            orderTaskDto.setTeamId(teamId);
            orderTaskDtoList.add(orderTaskDto);
        }

        for (int i = 0; i < lsize; i++) {
            int jsize = finishOrderLists.size();
            for (int j = 0; j < jsize; j++) {
                TbTransactionOrderTaskDto orderTaskDto = orderTaskDtoList.get(i);
                if (orderTaskDto.getOrderTasks() == null) {
                    orderTaskDto.setOrderTasks(new ArrayList<>(lsize));
                }
                orderTaskDto.getOrderTasks().add(finishOrderLists.get(j).get(i));
            }
        }
        // 进行DB 操作
        List<GroundTask> taskList = new ArrayList<>(orderTaskDtoList.size());
        List<GroundTaskOrder> taskOrderList = new ArrayList<>();
        for (TbTransactionOrderTaskDto orderTaskDto : orderTaskDtoList) {
            taskList.add(orderTaskDto);
            orderTaskDto.setOrderNum(orderTaskDto.getOrderTasks().size());
            long taskCommissionPrice = 0;
            long taskCorpus = 0;
            for (TbTransactionOrder order : orderTaskDto.getOrderTasks()) {
                GroundTaskOrder taskOrder = new GroundTaskOrder();
                taskOrder.setTaskOrderId(order.getOrderId());
                taskOrder.setTaskId(orderTaskDto.getTaskId());
                taskOrder.setOrderId(order.getOrderId());
                taskOrder.setCreateTime(now);
                taskOrder.setStatus(TbTransactionTaskOrderStatus.NORMAL.getCode());
                taskOrder.setUpdateTime(now);
                taskOrder.setUpdateBy(operator);
                taskOrder.setCreateBy(operator);
                taskOrderList.add(taskOrder);

                taskCommissionPrice += order.getCommissionPrice();
                taskCorpus += order.getUnitPrice();
            }
            orderTaskDto.setTaskCommissionPrice(taskCommissionPrice);
            orderTaskDto.setTaskCorpus(taskCorpus);
        }
        tbTransactionTaskOrderMapper.batchInsert(taskOrderList);
        return tbTransactionTaskMapper.batchInsert(taskList);
    }

    @Override
    public List<TbTransactionOrder> gen(String groupClassifys, Integer groupClassifysCount, Long teamId, String[] ids) {
        // 查询指定商品 (这里看匹配订单的数量 --> 超过3000条查询)
        List<TbTransactionWaitBuildGoodsDto> waitBuildGoodsList = tbTransactionOrderMapper.getWaitBuildGoodsReckonAllocatstatusList(TbTransactionOrderAllocatStatus.WAITBUILD.getCode()
                , groupClassifys, teamId, ids);
        List<String> waitBuildGoodsNameList = new ArrayList<>(waitBuildGoodsList.size());
        for (TbTransactionWaitBuildGoodsDto tbTransactionWaitBuildGoodsDto : waitBuildGoodsList) {
            waitBuildGoodsNameList.add(tbTransactionWaitBuildGoodsDto.getGoodsName());
        }
        SearchTbTransactionOrder searchTbTransactionOrder = new SearchTbTransactionOrder();
        searchTbTransactionOrder.setGroupClassifys(groupClassifys);
        searchTbTransactionOrder.setReckonAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
        searchTbTransactionOrder.setTeamId(teamId);
        searchTbTransactionOrder.setSortGoodsNameList(waitBuildGoodsNameList);
        searchTbTransactionOrder.setIds(ids);

        PageHelper.startPage(1, groupClassifysCount);
        List<TbTransactionOrder> waitBuildList = tbTransactionOrderMapper.selectTbTrasactionWaitBuildList(searchTbTransactionOrder);
        List<Long> orderIdList = new ArrayList<>();
        for (TbTransactionOrder tbTransactionOrder : waitBuildList) {
            orderIdList.add(tbTransactionOrder.getId());
        }
        if (!waitBuildList.isEmpty()) {
            // 进行更新状态
            tbTransactionOrderMapper.updateTbTransactionOrderAllocStatusInIds(orderIdList, TbTransactionOrderAllocatStatus.UNFINISHED.getCode(), TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
            tbTransactionOrderMapper.updateTbTransactionOrderAllocStatusInIdsForReckonAllocatStatus(orderIdList, TbTransactionOrderAllocatStatus.UNFINISHED.getCode(), TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
        }
        return waitBuildList;
    }

    @Override
    public List<TbTransactionOrder> getTaskNum(String groupClassifys, Integer groupClassifysCount, Long teamId, String[] ids) {
        // 查询指定商品 (这里看匹配订单的数量 --> 超过3000条查询)
        List<TbTransactionWaitBuildGoodsDto> waitBuildGoodsList = tbTransactionOrderMapper.getWaitBuildGoodsReckonAllocatstatusList(TbTransactionOrderAllocatStatus.WAITBUILD.getCode()
                , groupClassifys, teamId,ids);
        if (waitBuildGoodsList == null || waitBuildGoodsList.size() <= 0) {
            return null;
        }
        List<String> waitBuildGoodsNameList = new ArrayList<>(waitBuildGoodsList.size());
        for (TbTransactionWaitBuildGoodsDto waitBuildGoodsDto : waitBuildGoodsList) {
            waitBuildGoodsNameList.add(waitBuildGoodsDto.getGoodsName());
        }
        SearchTbTransactionOrder searchTbTransactionOrder = new SearchTbTransactionOrder();
        searchTbTransactionOrder.setGroupClassifys(groupClassifys);
        searchTbTransactionOrder.setReckonAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
        searchTbTransactionOrder.setTeamId(teamId);
        searchTbTransactionOrder.setSortGoodsNameList(waitBuildGoodsNameList);
        searchTbTransactionOrder.setIds(ids);

        PageHelper.startPage(1, groupClassifysCount);
        List<TbTransactionOrder> waitBuildList = tbTransactionOrderMapper.selectTbTrasactionWaitBuildList(searchTbTransactionOrder);
        List<Long> orderIdList = new ArrayList<>();
        for (TbTransactionOrder tbTransactionOrder : waitBuildList) {
            orderIdList.add(tbTransactionOrder.getId());
        }
        if (!waitBuildList.isEmpty()) {
            // 进行更新状态
            tbTransactionOrderMapper.updateTbTransactionOrderAllocStatusInIdsForReckonAllocatStatus(orderIdList, TbTransactionOrderAllocatStatus.UNFINISHED.getCode(), TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
        }
        return waitBuildList;
    }

    @Override
    public List<TbTransactionWaitBuildGoodsDto> getWaitBuildGroupClassifysListForReckonAllocatStatus(Integer reckonAllocatStatus,Long teamId, String[] ids) {
        return tbTransactionOrderMapper.getWaitBuildGroupClassifysListForReckonAllocatStatus(reckonAllocatStatus, teamId, ids);
    }
}

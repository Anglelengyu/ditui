package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionWaitBuildGoodsDto;
import net.hongkuang.ditui.project.busi.order.dto.UnfinishGoodsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/4/12
 */
public interface ITbTransactionOrderGenTaskService {
    List<TbTransactionWaitBuildGoodsDto> getWaitBuildGroupClassifysList(Long teamId);

    int batchInsert(List<List<TbTransactionOrder>> finishOrderList,Long teamId);

    List<TbTransactionOrder> gen(String groupClassifys, Integer groupClassifysCount, Long teamId, String[] ids);

    List<TbTransactionOrder> getTaskNum(String groupClassifys, Integer groupClassifysCount, Long teamId, String[] ids);

    List<TbTransactionWaitBuildGoodsDto> getWaitBuildGroupClassifysListForReckonAllocatStatus(Integer reckonAllocatStatus, Long teamId, String[] ids);

}

package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.dto.UnfinishGoodsDto;

import java.util.List;

/**
 * Created by apple on 2019/1/3.
 */
public interface IOrderGenTaskService {
    List<UnfinishGoodsDto> getUnFinishGoodsCategoryList(List<String> shopIdList);

    int batchInsert(List<List<Order>> finishOrderList);

    List<Order> gen(String category, Integer categoryCount, List<String> shopIdList);

    List<Order> getTaskNum(String category, Integer categoryCount, List<String> shopIdList);

    List<UnfinishGoodsDto> getUnFinishGoodsCategoryListForReckonAllocatStatus(List<String> shopIdList);

}

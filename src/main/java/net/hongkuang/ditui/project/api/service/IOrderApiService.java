package net.hongkuang.ditui.project.api.service;

import net.hongkuang.ditui.project.api.dto.OrderCancelReqVo;
import net.hongkuang.ditui.project.api.dto.OrderCheckDto;
import net.hongkuang.ditui.project.busi.task.dto.CheckOrderResult;

import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2019/1/12.
 */
public interface IOrderApiService {
    int cancel(OrderCancelReqVo orderCancelReqVo);

    List<CheckOrderResult> checkOrderNo(List<OrderCheckDto> orderCheckDtoList);
}

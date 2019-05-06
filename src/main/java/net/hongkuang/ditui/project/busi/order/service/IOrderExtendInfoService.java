package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.project.busi.order.dto.OrderExtendInfo;

import java.util.List;

/**
 * Created by apple on 2019/1/8.
 */
public interface IOrderExtendInfoService {

    List<OrderExtendInfo> selectOrderExtendInfo(List<String> orderIdList);

}

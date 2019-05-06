package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.project.busi.order.dto.OrderExtendInfo;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.mapper.SalesmanMapper;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by apple on 2019/1/8.
 */
@Service
public class OrderExtendInfoServiceImpl implements IOrderExtendInfoService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SalesmanMapper salesmanMapper;

    @Override
    public List<OrderExtendInfo> selectOrderExtendInfo(List<String> orderIdList) {
        // 获取订单信息
        List<OrderExtendInfo> orderExtendInfoList = taskMapper.selectTaskByOrderIdList(orderIdList);
        List<String> saleIdList = new ArrayList<>();
        for (OrderExtendInfo extendInfo : orderExtendInfoList) {
            if (StringUtils.isNotEmpty(extendInfo.getSaleId())) {
                saleIdList.add(extendInfo.getSaleId());
            }
        }
        if (saleIdList.isEmpty()) {
            return orderExtendInfoList;
        }
        // 获取销售信息
        List<Salesman> salesmanList = salesmanMapper.selectSalesmanBySaleIdList(saleIdList);
        Map<String, Salesman> salesmanMap = salesmanList.stream()
                .collect(Collectors.toMap(Salesman::getSaleId, a -> a, (k1, k2) -> k1));
        for (OrderExtendInfo extendInfo : orderExtendInfoList) {
            if (extendInfo.getSaleId() == null) {
                continue;
            }
            Salesman salesman = salesmanMap.get(extendInfo.getSaleId());
            if (salesman == null) {
                continue;
            }
            extendInfo.setSaleName(salesman.getName());
            extendInfo.setSaleArea(salesman.getArea());
            extendInfo.setSaleCommission(salesman.getCommission());
        }
        return orderExtendInfoList;
    }
}

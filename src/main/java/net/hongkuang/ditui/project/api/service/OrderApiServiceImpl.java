package net.hongkuang.ditui.project.api.service;

import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.common.constant.Constants;
import net.hongkuang.ditui.common.constant.QinChengEnum;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.*;
import net.hongkuang.ditui.project.api.dto.OrderCancelReqVo;
import net.hongkuang.ditui.project.api.dto.OrderCheckDto;
import net.hongkuang.ditui.project.api.dto.OrderRespDto;
import net.hongkuang.ditui.project.api.dto.QingChengCheckOrder;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import net.hongkuang.ditui.project.busi.orderCancelApproval.mapper.OrderCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.mapper.SalesmanMapper;
import net.hongkuang.ditui.project.busi.shop.mapper.ShopMapper;
import net.hongkuang.ditui.project.busi.task.dto.CheckOrderResult;
import net.hongkuang.ditui.project.busi.taskCancelApproval.enums.TaskCancelApprovalStatus;
import net.hongkuang.ditui.project.common.IQinChengUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by apple on 2019/1/12.
 */
@Service
public class OrderApiServiceImpl implements IOrderApiService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SalesmanMapper salesmanMapper;
    @Autowired
    private OrderCancelApprovalMapper orderCancelApprovalMapper;
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public int cancel(OrderCancelReqVo orderCancelReqVo) {
        LogUtils.getAccessLog().info("订单取消,申请中，请求 {} ", orderCancelReqVo);
        Salesman salesman = salesmanMapper.selectSalesmanById(orderCancelReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }
        Order order = orderMapper.selectOrderByOrderId(orderCancelReqVo.getOrderId());
        if (order == null) {
            throw new BusinessException(orderCancelReqVo.getOrderId() + "该订单不存在");
        }
        if (!order.getStatus().equals(OrderStatus.COMPLETE.getCode())) {
            LogUtils.getAccessLog().info("订单取消,订单状态为未接单状态 {},{}", orderCancelReqVo.getOrderId(), order.getStatus());
            throw new BusinessException("取消正在审核中，请勿重复取消");
        }
        // 修改状态
        int result = orderMapper.updateOrderStatusInIds(Arrays.asList(order.getId()), OrderStatus.PLACEMENT.getCode(), OrderStatus.COMPLETE.getCode());
        if (result <= 0) {
            LogUtils.getAccessLog().info("订单取消,取消失败，订单状态更新失败 {} ", orderCancelReqVo);
            throw new BusinessException("订单取消失败，请重试");
        }
        // 插入状态
        OrderCancelApproval orderCancelApproval = new OrderCancelApproval();
        orderCancelApproval.setApprovalId(RandomUtil.genString());
        orderCancelApproval.setOrderId(order.getOrderId());
        orderCancelApproval.setRemark("");
        orderCancelApproval.setCreateTime(DateUtils.getNowDate());
        orderCancelApproval.setSaleId(salesman.getSaleId());
        orderCancelApproval.setSalePhone(salesman.getPhone());
        orderCancelApproval.setSaleName(salesman.getName());
        orderCancelApproval.setSaleArea(salesman.getArea());
        orderCancelApproval.setStatus(TaskCancelApprovalStatus.WAIT.getCode());
        orderCancelApprovalMapper.insertOrderCancelApproval(orderCancelApproval);
        LogUtils.getAccessLog().info("订单取消,取消成功 {} ", orderCancelReqVo);
        return result;
    }

    @Override
    public List<CheckOrderResult> checkOrderNo(List<OrderCheckDto> orderCheckDtoList) {
        List<String> results = new ArrayList<>();
        List<CheckOrderResult> checkOrderResults = new ArrayList<>();
        orderCheckDtoList.forEach(orderCheckDto -> {
            OrderRespDto shop = shopMapper.selectShopByOrderId(orderCheckDto.getOrderId());
            if (StringUtils.isEmpty(shop.getWangwang())) {
                results.add("订单" + orderCheckDto.getIndex() + "店铺信息不完整，请联系管理员完善信息!");
                return;
            } else {
                String result = IQinChengUtils.getOrder(orderCheckDto.getOrderNo(), shop.getWangwang());
                JSONObject json = JSONObject.parseObject(result);
                Integer resultCode = json.getInteger("code");
                CheckOrderResult orderResult = new CheckOrderResult();
                if (resultCode == QinChengEnum.FAILURE.getCode()) {
                    results.add("订单" + orderCheckDto.getIndex() + "获取订单信息失败,请检查订单编号");
                    return;
                } else if (resultCode.compareTo(QinChengEnum.UNAUTHORIZED_USER.getCode()) == 0) {
                    orderResult.setStatus(Constants.RESULT_STATUS_EXE);
                    orderResult.setTid(orderCheckDto.getOrderNo());
                    orderResult.setOrderId(orderCheckDto.getOrderId());
                    orderResult.setMsg(QinChengEnum.UNAUTHORIZED_USER.getMsg());
                    checkOrderResults.add(orderResult);
                } else if (resultCode.compareTo(QinChengEnum.SUCCESS.getCode()) == 0) {
                    QingChengCheckOrder qingChengCheckOrder = json.getObject("data", QingChengCheckOrder.class);
                    orderResult.setOrderId(orderCheckDto.getOrderId());
                    orderResult.setTid(qingChengCheckOrder.getTid());
                    orderResult.setBuyerNick(qingChengCheckOrder.getBuyer_nick());
                    orderResult.setPayment(qingChengCheckOrder.getPayment());
                    orderResult.setStatus(Constants.RESULT_STATUS_NORMAL);
                    if (shop.getUnitPrice().compareTo(UnitUtils.unitFen(orderResult.getPayment())) != 0) {
                        orderResult.setStatus(Constants.RESULT_STATUS_EXE);
                        orderResult.setMsg("实际付款金额与订单金额不符");
                    }
                    checkOrderResults.add(orderResult);
                }
            }
        });
        if (results.size() > 0) {
            System.out.println(JSONObject.toJSONString(results));
            throw new BusinessException(JSONObject.toJSONString(results));
        }
        return checkOrderResults;
    }
}

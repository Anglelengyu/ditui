package net.hongkuang.ditui.project.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.config.AccessCheck;
import net.hongkuang.ditui.project.api.dto.OrderCancelReqVo;
import net.hongkuang.ditui.project.api.dto.OrderCheckDto;
import net.hongkuang.ditui.project.api.service.IOrderApiService;
import net.hongkuang.ditui.project.busi.task.dto.CheckOrderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2019/1/11.
 */
@RestController
@RequestMapping("/api/order")
public class OrderApiController {

    @Autowired
    private IOrderApiService orderApiService;

    @AccessCheck
    @PostMapping("/cancel")
    public AjaxResult cancel(OrderCancelReqVo orderCancelReqVo) {
        orderApiService.cancel(orderCancelReqVo);
        return AjaxResult.success("取消成功");
    }

    @AccessCheck
    @PostMapping("/checkOrderNo")
    public AjaxResult checkOrderNo(String orderCheck) {
        try {
            List<OrderCheckDto> orderCheckDtos = JSONArray.parseArray(orderCheck, OrderCheckDto.class);
            List<CheckOrderResult> cons = orderApiService.checkOrderNo(orderCheckDtos);
            return AjaxResult.success().put("data", cons);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}

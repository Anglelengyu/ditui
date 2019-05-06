package net.hongkuang.ditui.project.busi.orderCancelApproval.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import net.hongkuang.ditui.project.busi.orderCancelApproval.service.IOrderCancelApprovalService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 订单取消审批 信息操作处理
 *
 * @author yj
 * @date 2019-01-11
 */
@Controller
@RequestMapping("/busi/orderCancelApproval")
public class OrderCancelApprovalController extends BaseController {
    private String prefix = "busi/order";

    @Autowired
    private IOrderCancelApprovalService orderCancelApprovalService;

    @RequiresPermissions("busi:orderCancelApproval:view")
    @GetMapping()
    public String orderCancelApproval() {
        return prefix + "/orderCancelApproval";
    }

    /**
     * 查询订单取消审批列表
     */
    @RequiresPermissions("busi:orderCancelApproval:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderCancelApproval orderCancelApproval) {
        startPage();
        List<OrderCancelApproval> list = orderCancelApprovalService.selectOrderCancelApprovalList(orderCancelApproval);
        return getDataTable(list);
    }


    /**
     * 导出订单取消审批列表
     */
    @RequiresPermissions("busi:orderCancelApproval:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderCancelApproval orderCancelApproval) {
        List<OrderCancelApproval> list = orderCancelApprovalService.selectOrderCancelApprovalList(orderCancelApproval);
        ExcelUtil<OrderCancelApproval> util = new ExcelUtil<OrderCancelApproval>(OrderCancelApproval.class);
        return util.exportExcel(list, "orderCancelApproval");
    }

    /**
     * 新增订单取消审批
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单取消审批
     */
    @RequiresPermissions("busi:orderCancelApproval:add")
    @Log(title = "订单取消审批", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderCancelApproval orderCancelApproval) {
        return toAjax(orderCancelApprovalService.insertOrderCancelApproval(orderCancelApproval));
    }

    /**
     * 修改订单取消审批
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        OrderCancelApproval orderCancelApproval = orderCancelApprovalService.selectOrderCancelApprovalById(id);
        mmap.put("orderCancelApproval", orderCancelApproval);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单取消审批
     */
    @RequiresPermissions("busi:orderCancelApproval:edit")
    @Log(title = "订单取消审批", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderCancelApproval orderCancelApproval) {
        return toAjax(orderCancelApprovalService.updateOrderCancelApproval(orderCancelApproval));
    }

    /**
     * 删除订单取消审批
     */
    @RequiresPermissions("busi:orderCancelApproval:remove")
    @Log(title = "订单取消审批", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(orderCancelApprovalService.deleteOrderCancelApprovalByIds(ids));
    }

    /**
     * 通过取消
     */
    @RequiresPermissions("busi:orderCancelApproval:pass")
    @Log(title = "订单取消审批", businessType = BusinessType.PASS)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult pass(String ids) {
        return toAjax(orderCancelApprovalService.pass(ids));
    }

    /**
     * 驳回取消
     */
    @RequiresPermissions("busi:orderCancelApproval:reject")
    @Log(title = "订单取消审批", businessType = BusinessType.REJECT)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult reject(String ids) {
        return toAjax(orderCancelApprovalService.reject(ids));
    }

}

package net.hongkuang.ditui.project.busi.order.controller;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.SearchOrder;
import net.hongkuang.ditui.project.busi.order.dto.GenTaskDto;
import net.hongkuang.ditui.project.busi.order.dto.ImportOrderDto;
import net.hongkuang.ditui.project.busi.order.service.IOrderService;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.service.ITaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单 信息操作处理
 *
 * @author yj
 * @date 2018-12-30
 */
@Controller
@RequestMapping("/busi/order")
public class OrderController extends BaseController {
    private String prefix = "busi/order";

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IImgService imgService;

    @RequiresPermissions("busi:order:view")
    @GetMapping()
    public String order() {
        return prefix + "/order";
    }


    @RequiresPermissions("busi:order:allocated")
    @RequestMapping("/allocated")
    public String orderOther(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, @RequestParam(required = false) Long salesmanId, ModelMap mmap) {
        mmap.put("shopManagerId", shopManagerId);
        mmap.put("shopId", shopId);
        mmap.put("salesmanId", salesmanId);
        return prefix + "/order" + "_allocated";
    }

    @RequiresPermissions("busi:order:history")
    @RequestMapping("/history")
    public String orderHisotry(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, @RequestParam(required = false) Long salesmanId, ModelMap mmap) {
        mmap.put("shopManagerId", shopManagerId);
        mmap.put("shopId", shopId);
        mmap.put("salesmanId", salesmanId);
        return prefix + "/order" + "_history";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchOrder order) {
        // 获取用户查询订单
        order.setShopIdList(orderService.getShopIdList(order.getShopManagerId(), order.getShopId()));
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    @RequiresPermissions("busi:order:show")
    @GetMapping("/showOrderDetails/{id}")
    public String showOrderDetails(@PathVariable Long id, ModelMap mmap) {
        Order order = orderService.selectOrderById(id);
        Task task = taskService.selectTaskByOrderId(order.getOrderId());
        List<Img> imgs = null;
        if (task != null) {
            imgs = imgService.selectByTaskId(task.getTaskId());
        } else {
            task = new Task();
        }
        mmap.put("order", new ArrayList<Order>() {{
            add(order);
        }});
        mmap.put("task", task);
        mmap.put("imgs", imgs);
        return prefix + "/showDetails";
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("busi:order:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SearchOrder order) {
        // TODO 需考虑导出超过条目数 OOM
        List<Order> list = orderService.selectOrderList(order);
        list.forEach(order1 -> {
            order1.setUnitPriceYuan(UnitUtils.unitYuanString(order1.getUnitPrice(), "--"));
            order1.setCommissionYuan(UnitUtils.unitYuanString(order1.getCommission(), "--"));
            order1.setSaleCommissionYuan(UnitUtils.unitYuanString(order1.getSaleCommission(), "--"));
            order1.setPaymentYuan(UnitUtils.unitYuanString(order1.getPayment(), "--"));
            if (order1.getMarkStatus() == null) {
                order1.setMarkStatusStr("");
            } else if (order1.getMarkStatus().equals(2)) {
                order1.setMarkStatusStr("✔");
            } else {
                order1.setMarkStatusStr("");
            }
        });
        ExcelUtil<Order> util = new ExcelUtil<>(Order.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("busi:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Order order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 新增导入订单
     */
    @RequiresPermissions("busi:order:import")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importOrder(ImportOrderDto importOrderDto) {
        return toAjax(orderService.importExcel(importOrderDto));
    }

    /**
     * 修改订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Order order = orderService.selectOrderById(id);
        order.setCommissionYuan(UnitUtils.unitYuanString(order.getCommission()));
        order.setUnitPriceYuan(UnitUtils.unitYuanString(order.getUnitPrice()));
        mmap.put("order", order);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("busi:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Order order) {
        if (!StringUtils.isEmpty(order.getUnitPriceYuan())) {
            order.setUnitPrice(UnitUtils.unitFen(new BigDecimal(order.getUnitPriceYuan())));
        }
        if (!StringUtils.isEmpty(order.getCommissionYuan())) {
            order.setCommission(UnitUtils.unitFen(new BigDecimal(order.getCommissionYuan())));
        }
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("busi:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(orderService.deleteOrderByIds(ids));
    }

    /**
     * 移到任务订单
     */
    @RequiresPermissions("busi:order:migration")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/migration")
    @ResponseBody
    public AjaxResult migration(String ids) {
        return toAjax(orderService.migrationOrderByIds(ids));
    }

    /**
     * 生成任务订单
     */
    @GetMapping("/gen")
    public String gen(ModelMap mmap) {
        mmap.put("genType", orderService.getGenType());
        return prefix + "/gen";
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("busi:order:gentask")
    @Log(title = "订单", businessType = BusinessType.GEN)
    @PostMapping("/genTask")
    @ResponseBody
    public AjaxResult genTask(GenTaskDto genTaskDto) {
        if (genTaskDto.getGenType() == null || genTaskDto.getGenType() <= 0) {
            throw new BusinessException("无效的生成模式");
        }
        int taskResult = orderService.genTask(genTaskDto);
        return toAjax(taskResult).put("taskResult", taskResult);
    }

    /**
     * 获取生成任务数量
     */
    @GetMapping("/getTaskNum/{genType}")
    @ResponseBody
    public AjaxResult getTaskNum(@PathVariable("genType") Integer genType) {
        if (genType == null || genType <= 0) {
            throw new BusinessException("无效的生成模式");
        }
        GenTaskDto genTaskDto = orderService.getTaskNum(genType);
        return AjaxResult.success().put("data", genTaskDto);
    }
}

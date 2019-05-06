package net.hongkuang.ditui.project.busi.onlineEmployeeOrder.controller;

import java.util.List;

import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.domain.OnlineEmployeeOrder;
import net.hongkuang.ditui.project.busi.onlineEmployeeOrder.service.IOnlineEmployeeOrderService;
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
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 业务员订单 信息操作处理
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Controller
@RequestMapping("/busi/onlineOrder")
public class OnlineEmployeeOrderController extends BaseController {
    private String prefix = "busi/onlineOrder";

    @Autowired
    private IOnlineEmployeeOrderService onlineEmployeeOrderService;

    @RequiresPermissions("busi:saleOrder:view")
    @GetMapping()
    public String saleOrder() {
        return prefix + "/saleOrder";
    }

    /**
     * 查询业务员订单列表
     */
    @RequiresPermissions("busi:saleOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OnlineEmployeeOrder onlineEmployeeOrder) {
        startPage();
        List<OnlineEmployeeOrder> list = onlineEmployeeOrderService.selectOnlineEmployeeOrderList(onlineEmployeeOrder);
        return getDataTable(list);
    }


    /**
     * 导出业务员订单列表
     */
    @RequiresPermissions("busi:saleOrder:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OnlineEmployeeOrder onlineEmployeeOrder) {
        List<OnlineEmployeeOrder> list = onlineEmployeeOrderService.selectOnlineEmployeeOrderList(onlineEmployeeOrder);
        ExcelUtil<OnlineEmployeeOrder> util = new ExcelUtil<OnlineEmployeeOrder>(OnlineEmployeeOrder.class);
        return util.exportExcel(list, "onlineEmployeeOrder");
    }

    /**
     * 新增业务员订单
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存业务员订单
     */
    @RequiresPermissions("busi:saleOrder:add")
    @Log(title = "业务员订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OnlineEmployeeOrder onlineEmployeeOrder) {
        return toAjax(onlineEmployeeOrderService.insertOnlineEmployeeOrder(onlineEmployeeOrder));
    }

    /**
     * 修改业务员订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        OnlineEmployeeOrder onlineEmployeeOrder = onlineEmployeeOrderService.selectOnlineEmployeeOrderById(id);
        mmap.put("onlineEmployeeOrder", onlineEmployeeOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务员订单
     */
    @RequiresPermissions("busi:saleOrder:edit")
    @Log(title = "业务员订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OnlineEmployeeOrder onlineEmployeeOrder) {
        return toAjax(onlineEmployeeOrderService.updateOnlineEmployeeOrder(onlineEmployeeOrder));
    }

    /**
     * 删除业务员订单
     */
    @RequiresPermissions("busi:saleOrder:remove")
    @Log(title = "业务员订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(onlineEmployeeOrderService.deleteOnlineEmployeeOrderByIds(ids));
    }

}

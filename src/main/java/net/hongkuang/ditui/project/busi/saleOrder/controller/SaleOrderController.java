package net.hongkuang.ditui.project.busi.saleOrder.controller;

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
import net.hongkuang.ditui.project.busi.saleOrder.domain.SaleOrder;
import net.hongkuang.ditui.project.busi.saleOrder.service.ISaleOrderService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 业务员订单 信息操作处理
 *
 * @author yj
 * @date 2019-01-11
 */
@Controller
@RequestMapping("/busi/saleOrder")
public class SaleOrderController extends BaseController {
    private String prefix = "busi/saleOrder";

    @Autowired
    private ISaleOrderService saleOrderService;

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
    public TableDataInfo list(SaleOrder saleOrder) {
        startPage();
        List<SaleOrder> list = saleOrderService.selectSaleOrderList(saleOrder);
        return getDataTable(list);
    }


    /**
     * 导出业务员订单列表
     */
    @RequiresPermissions("busi:saleOrder:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SaleOrder saleOrder) {
        List<SaleOrder> list = saleOrderService.selectSaleOrderList(saleOrder);
        ExcelUtil<SaleOrder> util = new ExcelUtil<SaleOrder>(SaleOrder.class);
        return util.exportExcel(list, "saleOrder");
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
    public AjaxResult addSave(SaleOrder saleOrder) {
        return toAjax(saleOrderService.insertSaleOrder(saleOrder));
    }

    /**
     * 修改业务员订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SaleOrder saleOrder = saleOrderService.selectSaleOrderById(id);
        mmap.put("saleOrder", saleOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务员订单
     */
    @RequiresPermissions("busi:saleOrder:edit")
    @Log(title = "业务员订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SaleOrder saleOrder) {
        return toAjax(saleOrderService.updateSaleOrder(saleOrder));
    }

    /**
     * 删除业务员订单
     */
    @RequiresPermissions("busi:saleOrder:remove")
    @Log(title = "业务员订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(saleOrderService.deleteSaleOrderByIds(ids));
    }

}

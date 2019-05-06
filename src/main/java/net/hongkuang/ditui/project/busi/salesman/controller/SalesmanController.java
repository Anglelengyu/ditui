package net.hongkuang.ditui.project.busi.salesman.controller;

import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.service.IOrderService;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.domain.SalesmanHistory;
import net.hongkuang.ditui.project.busi.salesman.domain.SearchSalesman;
import net.hongkuang.ditui.project.busi.salesman.service.ISalesmanService;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.service.ITaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务员 信息操作处理
 *
 * @author yj
 * @date 2018-12-30
 */
@Controller
@RequestMapping("/busi/salesman")
public class SalesmanController extends BaseController {
    private String prefix = "busi/salesman";

    @Autowired
    private ISalesmanService salesmanService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IImgService imgService;

    @RequiresPermissions("busi:salesman:view")
    @GetMapping()
    public String salesman() {
        return prefix + "/salesman";
    }

    @RequiresPermissions("busi:salesman:subordinate:view")
    @GetMapping("/subordinate")
    public String subordinate() {
        return prefix + "/salesman_subordinate";
    }

    @RequiresPermissions("busi:salesman:history")
    @GetMapping("/history")
    public String history() {
        return prefix + "/history";
    }


    /**
     * 查询业务员历史接单地点
     */
    @RequiresPermissions("busi:salesman:history")
    @PostMapping("/getHistoryInfo")
    @ResponseBody
    public AjaxResult getHistoryInfo(SearchSalesman salesman) {
        System.out.println(salesman.getName());
        List<SalesmanHistory> list = salesmanService.selectSalesmanHistoryList(salesman);
        AjaxResult r = AjaxResult.success();
        r.put("data", list);
        return r;
    }

    @RequiresPermissions("busi:task:show")
    @GetMapping("/showTaskDetails/{id}")
    public String showTaskDetails(@PathVariable Long id, ModelMap mmap) {
        Task task = taskService.selectTaskById(id);
        List<Order> orders = orderService.selectOrderListByOrderId(task.getTaskId());
        List<Img> imgs = imgService.selectByTaskId(task.getTaskId());
        task.setOrders(orders);
        mmap.put("task", task);
        mmap.put("imgs", imgs);
        return "busi/task/showDetails";
    }

    /**
     * 查询业务员列表
     */
    @RequiresPermissions("busi:salesman:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Salesman salesman) {
        startPage();
        List<Salesman> list = salesmanService.selectSalesmanList(salesman);
        return getDataTable(list);
    }


    /**
     * 导出业务员列表
     */
    @RequiresPermissions("busi:salesman:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Salesman salesman) {
        List<Salesman> list = salesmanService.selectSalesmanList(salesman);
        ExcelUtil<Salesman> util = new ExcelUtil<Salesman>(Salesman.class);
        return util.exportExcel(list, "salesman");
    }

    /**
     * 新增业务员
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存业务员
     */
    @RequiresPermissions("busi:salesman:add")
    @Log(title = "业务员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Salesman salesman) {
        return toAjax(salesmanService.insertSalesman(salesman));
    }

    /**
     * 修改业务员
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Salesman salesman = salesmanService.selectSalesmanById(id);
        mmap.put("salesman", salesman);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务员
     */
    @RequiresPermissions("busi:salesman:edit")
    @Log(title = "业务员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Salesman salesman) {
        return toAjax(salesmanService.updateSalesman(salesman));
    }

    /**
     * 删除业务员
     */
    @RequiresPermissions("busi:salesman:remove")
    @Log(title = "业务员", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(salesmanService.deleteSalesmanByIds(ids));
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(Salesman sales) {
        return salesmanService.checkPhoneUnique(sales);
    }

    @RequiresPermissions("busi:salesman:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{id}")
    public String resetPwd(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sales", salesmanService.selectSalesmanById(id));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("busi:salesman:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(Salesman salesman) {
        return toAjax(salesmanService.resetUserPwd(salesman));
    }
}
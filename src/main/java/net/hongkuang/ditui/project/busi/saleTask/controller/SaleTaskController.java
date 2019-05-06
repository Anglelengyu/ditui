package net.hongkuang.ditui.project.busi.saleTask.controller;

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
import net.hongkuang.ditui.project.busi.saleTask.domain.SaleTask;
import net.hongkuang.ditui.project.busi.saleTask.service.ISaleTaskService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 业务员任务 信息操作处理
 *
 * @author yj
 * @date 2019-01-11
 */
@Controller
@RequestMapping("/busi/saleTask")
public class SaleTaskController extends BaseController {
    private String prefix = "busi/saleTask";

    @Autowired
    private ISaleTaskService saleTaskService;

    @RequiresPermissions("busi:saleTask:view")
    @GetMapping()
    public String saleTask() {
        return prefix + "/saleTask";
    }

    /**
     * 查询业务员任务列表
     */
    @RequiresPermissions("busi:saleTask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SaleTask saleTask) {
        startPage();
        List<SaleTask> list = saleTaskService.selectSaleTaskList(saleTask);
        return getDataTable(list);
    }


    /**
     * 导出业务员任务列表
     */
    @RequiresPermissions("busi:saleTask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SaleTask saleTask) {
        List<SaleTask> list = saleTaskService.selectSaleTaskList(saleTask);
        ExcelUtil<SaleTask> util = new ExcelUtil<SaleTask>(SaleTask.class);
        return util.exportExcel(list, "saleTask");
    }

    /**
     * 新增业务员任务
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存业务员任务
     */
    @RequiresPermissions("busi:saleTask:add")
    @Log(title = "业务员任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SaleTask saleTask) {
        return toAjax(saleTaskService.insertSaleTask(saleTask));
    }

    /**
     * 修改业务员任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SaleTask saleTask = saleTaskService.selectSaleTaskById(id);
        mmap.put("saleTask", saleTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存业务员任务
     */
    @RequiresPermissions("busi:saleTask:edit")
    @Log(title = "业务员任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SaleTask saleTask) {
        return toAjax(saleTaskService.updateSaleTask(saleTask));
    }

    /**
     * 删除业务员任务
     */
    @RequiresPermissions("busi:saleTask:remove")
    @Log(title = "业务员任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(saleTaskService.deleteSaleTaskByIds(ids));
    }

}

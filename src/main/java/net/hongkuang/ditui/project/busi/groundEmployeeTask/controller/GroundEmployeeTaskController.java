package net.hongkuang.ditui.project.busi.groundEmployeeTask.controller;

import java.util.List;

import net.hongkuang.ditui.project.busi.groundEmployeeTask.domain.GroundEmployeeTask;
import net.hongkuang.ditui.project.busi.groundEmployeeTask.service.IGroundEmployeeTaskService;
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
 * 线下员工任务 信息操作处理
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Controller
@RequestMapping("/busi/groundEmployeeTask")
public class GroundEmployeeTaskController extends BaseController {
    private String prefix = "busi/groundEmployeeTask";

    @Autowired
    private IGroundEmployeeTaskService groundEmployeeTaskService;

    @RequiresPermissions("busi:groundEmployeeTask:view")
    @GetMapping()
    public String saleTask() {
        return prefix + "/groundEmployeeTask";
    }

    /**
     * 查询线下员工任务列表
     */
    @RequiresPermissions("busi:groundEmployeeTask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GroundEmployeeTask groundEmployeeTask) {
        startPage();
        List<GroundEmployeeTask> list = groundEmployeeTaskService.selectGroundEmployeeTaskList(groundEmployeeTask);
        return getDataTable(list);
    }

    /**
     * 导出线下员工任务列表
     */
    @RequiresPermissions("busi:groundEmployeeTask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroundEmployeeTask groundEmployeeTask) {
        List<GroundEmployeeTask> list = groundEmployeeTaskService.selectGroundEmployeeTaskList(groundEmployeeTask);
        ExcelUtil<GroundEmployeeTask> util = new ExcelUtil<GroundEmployeeTask>(GroundEmployeeTask.class);
        return util.exportExcel(list, "groundEmployeeTask");
    }

    /**
     * 新增线下员工任务
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存线下员工任务
     */
    @RequiresPermissions("busi:groundEmployeeTask:add")
    @Log(title = "线下员工任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GroundEmployeeTask groundEmployeeTask) {
        return toAjax(groundEmployeeTaskService.insertGroundEmployeeTask(groundEmployeeTask));
    }

    /**
     * 修改线下员工任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        GroundEmployeeTask groundEmployeeTask = groundEmployeeTaskService.selectGroundEmployeeTaskById(id);
        mmap.put("groundEmployeeTask", groundEmployeeTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存线下员工任务
     */
    @RequiresPermissions("busi:groundEmployeeTask:edit")
    @Log(title = "线下员工任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GroundEmployeeTask groundEmployeeTask) {
        return toAjax(groundEmployeeTaskService.updateGroundEmployeeTask(groundEmployeeTask));
    }

    /**
     * 删除线下员工任务
     */
    @RequiresPermissions("busi:groundEmployeeTask:remove")
    @Log(title = "线下员工任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(groundEmployeeTaskService.deleteGroundEmployeeTaskByIds(ids));
    }

}

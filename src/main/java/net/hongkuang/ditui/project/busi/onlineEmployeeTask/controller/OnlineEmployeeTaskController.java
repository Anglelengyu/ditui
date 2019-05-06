package net.hongkuang.ditui.project.busi.onlineEmployeeTask.controller;

import java.util.List;

import net.hongkuang.ditui.project.busi.onlineEmployeeTask.domain.OnlineEmployeeTask;
import net.hongkuang.ditui.project.busi.onlineEmployeeTask.service.IOnlineEmployeeTaskService;
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
@RequestMapping("/busi/onlineEmployeeTask")
public class OnlineEmployeeTaskController extends BaseController {
    private String prefix = "busi/onlineEmployeeTask";

    @Autowired
    private IOnlineEmployeeTaskService onlineEmployeeTaskService;

    @RequiresPermissions("busi:onlineEmployeeTask:view")
    @GetMapping()
    public String saleTask() {
        return prefix + "/onlineEmployeeTask";
    }

    /**
     * 查询线下员工任务列表
     */
    @RequiresPermissions("busi:onlineEmployeeTask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OnlineEmployeeTask onlineEmployeeTask) {
        startPage();
        List<OnlineEmployeeTask> list = onlineEmployeeTaskService.selectOnlineEmployeeTaskList(onlineEmployeeTask);
        return getDataTable(list);
    }

    /**
     * 导出线下员工任务列表
     */
    @RequiresPermissions("busi:onlineEmployeeTask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OnlineEmployeeTask onlineEmployeeTask) {
        List<OnlineEmployeeTask> list = onlineEmployeeTaskService.selectOnlineEmployeeTaskList(onlineEmployeeTask);
        ExcelUtil<OnlineEmployeeTask> util = new ExcelUtil<OnlineEmployeeTask>(OnlineEmployeeTask.class);
        return util.exportExcel(list, "onlineEmployeeTask");
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
    @RequiresPermissions("busi:onlineEmployeeTask:add")
    @Log(title = "线下员工任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OnlineEmployeeTask onlineEmployeeTask) {
        return toAjax(onlineEmployeeTaskService.insertOnlineEmployeeTask(onlineEmployeeTask));
    }

    /**
     * 修改线下员工任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        OnlineEmployeeTask onlineEmployeeTask = onlineEmployeeTaskService.selectOnlineEmployeeTaskById(id);
        mmap.put("onlineEmployeeTask", onlineEmployeeTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存线下员工任务
     */
    @RequiresPermissions("busi:onlineEmployeeTask:edit")
    @Log(title = "线下员工任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OnlineEmployeeTask onlineEmployeeTask) {
        return toAjax(onlineEmployeeTaskService.updateOnlineEmployeeTask(onlineEmployeeTask));
    }

    /**
     * 删除线下员工任务
     */
    @RequiresPermissions("busi:onlineEmployeeTask:remove")
    @Log(title = "线下员工任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(onlineEmployeeTaskService.deleteOnlineEmployeeTaskByIds(ids));
    }

}

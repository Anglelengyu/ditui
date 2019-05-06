package net.hongkuang.ditui.project.busi.taskCancelApproval.controller;

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
import net.hongkuang.ditui.project.busi.taskCancelApproval.domain.TaskCancelApproval;
import net.hongkuang.ditui.project.busi.taskCancelApproval.service.ITaskCancelApprovalService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 任务取消审批 信息操作处理
 *
 * @author yj
 * @date 2019-01-06
 */
@Controller
@RequestMapping("/busi/taskCancelApproval")
public class TaskCancelApprovalController extends BaseController {
    private String prefix = "busi/task";

    @Autowired
    private ITaskCancelApprovalService taskCancelApprovalService;

    @RequiresPermissions("busi:taskCancelApproval:view")
    @GetMapping()
    public String taskCancelApproval() {
        return prefix + "/taskCancelApproval";
    }

    /**
     * 查询任务取消审批列表
     */
    @RequiresPermissions("busi:taskCancelApproval:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TaskCancelApproval taskCancelApproval) {
        startPage();
        List<TaskCancelApproval> list = taskCancelApprovalService.selectTaskCancelApprovalList(taskCancelApproval);
        return getDataTable(list);
    }


    /**
     * 导出任务取消审批列表
     */
    @RequiresPermissions("busi:taskCancelApproval:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TaskCancelApproval taskCancelApproval) {
        List<TaskCancelApproval> list = taskCancelApprovalService.selectTaskCancelApprovalList(taskCancelApproval);
        ExcelUtil<TaskCancelApproval> util = new ExcelUtil<TaskCancelApproval>(TaskCancelApproval.class);
        return util.exportExcel(list, "taskCancelApproval");
    }

    /**
     * 新增任务取消审批
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存任务取消审批
     */
    @RequiresPermissions("busi:taskCancelApproval:add")
    @Log(title = "任务取消审批", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TaskCancelApproval taskCancelApproval) {
        return toAjax(taskCancelApprovalService.insertTaskCancelApproval(taskCancelApproval));
    }

    /**
     * 修改任务取消审批
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        TaskCancelApproval taskCancelApproval = taskCancelApprovalService.selectTaskCancelApprovalById(id);
        mmap.put("taskCancelApproval", taskCancelApproval);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务取消审批
     */
    @RequiresPermissions("busi:taskCancelApproval:edit")
    @Log(title = "任务取消审批", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TaskCancelApproval taskCancelApproval) {
        return toAjax(taskCancelApprovalService.updateTaskCancelApproval(taskCancelApproval));
    }

    /**
     * 删除任务取消审批
     */
    @RequiresPermissions("busi:taskCancelApproval:remove")
    @Log(title = "任务取消审批", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(taskCancelApprovalService.deleteTaskCancelApprovalByIds(ids));
    }

    /**
     * 通过取消
     */
    @RequiresPermissions("busi:taskCancelApproval:pass")
    @Log(title = "任务取消审批", businessType = BusinessType.PASS)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult pass(String ids) {
        return toAjax(taskCancelApprovalService.pass(ids));
    }

    /**
     * 驳回取消
     */
    @RequiresPermissions("busi:taskCancelApproval:reject")
    @Log(title = "任务取消审批", businessType = BusinessType.REJECT)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult reject(String ids) {
        return toAjax(taskCancelApprovalService.reject(ids));
    }
}

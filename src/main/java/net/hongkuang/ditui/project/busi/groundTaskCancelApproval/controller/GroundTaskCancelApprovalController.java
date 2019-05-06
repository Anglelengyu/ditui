package net.hongkuang.ditui.project.busi.groundTaskCancelApproval.controller;

import java.util.List;

import net.hongkuang.ditui.project.busi.groundTaskCancelApproval.domain.GroundTaskCancelApproval;
import net.hongkuang.ditui.project.busi.groundTaskCancelApproval.service.IGroundTaskCancelApprovalService;
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
 * 任务取消审批 信息操作处理
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Controller
@RequestMapping("/busi/groundTaskCancelApproval")
public class GroundTaskCancelApprovalController extends BaseController {
    private String prefix = "busi/groundTask";

    @Autowired
    private IGroundTaskCancelApprovalService groundTaskCancelApprovalService;

    @RequiresPermissions("busi:groundTaskCancelApproval:view")
    @GetMapping()
    public String taskCancelApproval() {
        return prefix + "/groundTaskCancelApproval";
    }

    /**
     * 查询任务取消审批列表
     */
    @RequiresPermissions("busi:groundTaskCancelApproval:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GroundTaskCancelApproval groundTaskCancelApproval) {
        startPage();
        List<GroundTaskCancelApproval> list = groundTaskCancelApprovalService.selectGroundTaskCancelApprovalList(groundTaskCancelApproval);
        return getDataTable(list);
    }


    /**
     * 导出任务取消审批列表
     */
    @RequiresPermissions("busi:groundTaskCancelApproval:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GroundTaskCancelApproval groundTaskCancelApproval) {
        List<GroundTaskCancelApproval> list = groundTaskCancelApprovalService.selectGroundTaskCancelApprovalList(groundTaskCancelApproval);
        ExcelUtil<GroundTaskCancelApproval> util = new ExcelUtil<GroundTaskCancelApproval>(GroundTaskCancelApproval.class);
        return util.exportExcel(list, "groundTaskCancelApproval");
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
    @RequiresPermissions("busi:groundTaskCancelApproval:add")
    @Log(title = "任务取消审批", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GroundTaskCancelApproval groundTaskCancelApproval) {
        return toAjax(groundTaskCancelApprovalService.insertGroundTaskCancelApproval(groundTaskCancelApproval));
    }

    /**
     * 修改任务取消审批
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        GroundTaskCancelApproval groundTaskCancelApproval = groundTaskCancelApprovalService.selectGroundTaskCancelApprovalById(id);
        mmap.put("groundTaskCancelApproval", groundTaskCancelApproval);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务取消审批
     */
    @RequiresPermissions("busi:groundTaskCancelApproval:edit")
    @Log(title = "任务取消审批", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GroundTaskCancelApproval groundTaskCancelApproval) {
        return toAjax(groundTaskCancelApprovalService.updateGroundTaskCancelApproval(groundTaskCancelApproval));
    }

    /**
     * 删除任务取消审批
     */
    @RequiresPermissions("busi:groundTaskCancelApproval:remove")
    @Log(title = "任务取消审批", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(groundTaskCancelApprovalService.deleteGroundTaskCancelApprovalByIds(ids));
    }

    /**
     * 通过取消
     */
    @RequiresPermissions("busi:groundTaskCancelApproval:pass")
    @Log(title = "任务取消审批", businessType = BusinessType.PASS)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult pass(String ids) {
        return toAjax(groundTaskCancelApprovalService.pass(ids));
    }

    /**
     * 驳回取消
     */
    @RequiresPermissions("busi:groundTaskCancelApproval:reject")
    @Log(title = "任务取消审批", businessType = BusinessType.REJECT)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult reject(String ids) {
        return toAjax(groundTaskCancelApprovalService.reject(ids));
    }
}

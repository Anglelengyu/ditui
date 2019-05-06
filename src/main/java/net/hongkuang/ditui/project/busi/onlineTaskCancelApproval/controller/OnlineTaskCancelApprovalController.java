package net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.controller;

import java.util.List;

import net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.domain.OnlineTaskCancelApproval;
import net.hongkuang.ditui.project.busi.onlineTaskCancelApproval.service.IOnlineTaskCancelApprovalService;
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
@RequestMapping("/busi/onlineTaskCancelApproval")
public class OnlineTaskCancelApprovalController extends BaseController {
    private String prefix = "busi/onlineTask";

    @Autowired
    private IOnlineTaskCancelApprovalService onlineTaskCancelApprovalService;

    @RequiresPermissions("busi:onlineTaskCancelApproval:view")
    @GetMapping()
    public String taskCancelApproval() {
        return prefix + "/onlineTaskCancelApproval";
    }

    /**
     * 查询任务取消审批列表
     */
    @RequiresPermissions("busi:onlineTaskCancelApproval:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        startPage();
        List<OnlineTaskCancelApproval> list = onlineTaskCancelApprovalService.selectOnlineTaskCancelApprovalList(onlineTaskCancelApproval);
        return getDataTable(list);
    }


    /**
     * 导出任务取消审批列表
     */
    @RequiresPermissions("busi:onlineTaskCancelApproval:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        List<OnlineTaskCancelApproval> list = onlineTaskCancelApprovalService.selectOnlineTaskCancelApprovalList(onlineTaskCancelApproval);
        ExcelUtil<OnlineTaskCancelApproval> util = new ExcelUtil<OnlineTaskCancelApproval>(OnlineTaskCancelApproval.class);
        return util.exportExcel(list, "onlineTaskCancelApproval");
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
    @RequiresPermissions("busi:onlineTaskCancelApproval:add")
    @Log(title = "任务取消审批", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        return toAjax(onlineTaskCancelApprovalService.insertOnlineTaskCancelApproval(onlineTaskCancelApproval));
    }

    /**
     * 修改任务取消审批
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        OnlineTaskCancelApproval onlineTaskCancelApproval = onlineTaskCancelApprovalService.selectOnlineTaskCancelApprovalById(id);
        mmap.put("onlineTaskCancelApproval", onlineTaskCancelApproval);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务取消审批
     */
    @RequiresPermissions("busi:onlineTaskCancelApproval:edit")
    @Log(title = "任务取消审批", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OnlineTaskCancelApproval onlineTaskCancelApproval) {
        return toAjax(onlineTaskCancelApprovalService.updateOnlineTaskCancelApproval(onlineTaskCancelApproval));
    }

    /**
     * 删除任务取消审批
     */
    @RequiresPermissions("busi:onlineTaskCancelApproval:remove")
    @Log(title = "任务取消审批", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(onlineTaskCancelApprovalService.deleteOnlineTaskCancelApprovalByIds(ids));
    }

    /**
     * 通过取消
     */
    @RequiresPermissions("busi:onlineTaskCancelApproval:pass")
    @Log(title = "任务取消审批", businessType = BusinessType.PASS)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult pass(String ids) {
        return toAjax(onlineTaskCancelApprovalService.pass(ids));
    }

    /**
     * 驳回取消
     */
    @RequiresPermissions("busi:onlineTaskCancelApproval:reject")
    @Log(title = "任务取消审批", businessType = BusinessType.REJECT)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult reject(String ids) {
        return toAjax(onlineTaskCancelApprovalService.reject(ids));
    }
}

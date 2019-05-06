package net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.controller;

import java.util.List;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.domain.TbTransactionOrderCancelApproval;
import net.hongkuang.ditui.project.busi.tbTransactionOrderCancelApproval.service.ITbTransactionOrderCancelApprovalService;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
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
import net.hongkuang.ditui.project.busi.orderCancelApproval.domain.OrderCancelApproval;
import net.hongkuang.ditui.project.busi.orderCancelApproval.service.IOrderCancelApprovalService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 订单取消审批 信息操作处理
 *
 * @author:zy
 * @date: 2019/4/22
 */
@Controller
@RequestMapping("/busi/tbTransactionOrderCancelApproval")
public class TbTransactionOrderCancelApprovalController extends BaseController {
    private String prefix = "busi/tbTransactionOrderCancelApproval";

    @Autowired
    private ITbTransactionOrderCancelApprovalService tbTransactionOrderCancelApprovalService;
    @Autowired
    private ITeamService teamService;

    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:view")
    @GetMapping()
    public String orderCancelApproval() {
        return prefix + "/tbTransactionOrderCancelApproval";
    }

    /**
     * 查询订单取消审批列表
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            tbTransactionOrderCancelApproval.setTeamId(team.getTeamId());
        }
        startPage();
        List<TbTransactionOrderCancelApproval> list = tbTransactionOrderCancelApprovalService.selectTbTransactionOrderCancelApprovalList(tbTransactionOrderCancelApproval);
        return getDataTable(list);
    }


    /**
     * 导出订单取消审批列表
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        List<TbTransactionOrderCancelApproval> list = tbTransactionOrderCancelApprovalService.selectTbTransactionOrderCancelApprovalList(tbTransactionOrderCancelApproval);
        ExcelUtil<TbTransactionOrderCancelApproval> util = new ExcelUtil<TbTransactionOrderCancelApproval>(TbTransactionOrderCancelApproval.class);
        return util.exportExcel(list, "tbTransactionOrderCancelApproval");
    }

    /**
     * 新增订单取消审批
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单取消审批
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:add")
    @Log(title = "订单取消审批", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        return toAjax(tbTransactionOrderCancelApprovalService.insertTbTransactionOrderCancelApproval(tbTransactionOrderCancelApproval));
    }

    /**
     * 修改订单取消审批
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval = tbTransactionOrderCancelApprovalService.selectTbTransactionOrderCancelApprovalById(id);
        mmap.put("tbTransactionOrderCancelApproval", tbTransactionOrderCancelApproval);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单取消审批
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:edit")
    @Log(title = "订单取消审批", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TbTransactionOrderCancelApproval tbTransactionOrderCancelApproval) {
        return toAjax(tbTransactionOrderCancelApprovalService.updateTbTransactionOrderCancelApproval(tbTransactionOrderCancelApproval));
    }

    /**
     * 删除订单取消审批
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:remove")
    @Log(title = "订单取消审批", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tbTransactionOrderCancelApprovalService.deleteTbTransactionOrderCancelApprovalByIds(ids));
    }

    /**
     * 通过取消
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:pass")
    @Log(title = "订单取消审批", businessType = BusinessType.PASS)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult pass(String ids) {
        return toAjax(tbTransactionOrderCancelApprovalService.pass(ids));
    }

    /**
     * 驳回取消
     */
    @RequiresPermissions("busi:tbTransactionOrderCancelApproval:reject")
    @Log(title = "订单取消审批", businessType = BusinessType.REJECT)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult reject(String ids) {
        return toAjax(tbTransactionOrderCancelApprovalService.reject(ids));
    }

}

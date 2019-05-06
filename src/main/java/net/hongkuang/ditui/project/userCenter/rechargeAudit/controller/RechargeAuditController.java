package net.hongkuang.ditui.project.userCenter.rechargeAudit.controller;

import java.util.ArrayList;
import java.util.List;

import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplate;
import net.hongkuang.ditui.project.userCenter.packageTemplate.service.IPackageTemplateService;
import net.hongkuang.ditui.project.system.role.domain.Role;
import net.hongkuang.ditui.project.system.role.service.IRoleService;
import net.hongkuang.ditui.project.userCenter.rechargeAudit.support.AuditStatus;
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
import net.hongkuang.ditui.project.userCenter.rechargeAudit.domain.RechargeAudit;
import net.hongkuang.ditui.project.userCenter.rechargeAudit.service.IRechargeAuditService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 充值审核 信息操作处理
 *
 * @author yj
 * @date 2019-04-17
 */
@Controller
@RequestMapping("/busi/rechargeAudit")
public class RechargeAuditController extends BaseController {

    private String prefix = "busi/rechargeAudit";

    @Autowired
    private IRechargeAuditService rechargeAuditService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPackageTemplateService packageTemplateService;

    @RequiresPermissions("busi:rechargeAudit:view")
    @GetMapping("/rechargeAudit")
    public String rechargeAudit() {
        return prefix + "/rechargeAudit";
    }

    /**
     * 查询充值审核列表
     */
    @RequiresPermissions("busi:rechargeAudit:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RechargeAudit rechargeAudit) {
        startPage();
        List<RechargeAudit> list = rechargeAuditService.selectRechargeAuditList(rechargeAudit);
        return getDataTable(list);
    }


    /**
     * 导出充值审核列表
     */
    @RequiresPermissions("busi:rechargeAudit:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RechargeAudit rechargeAudit) {
        List<RechargeAudit> list = rechargeAuditService.selectRechargeAuditList(rechargeAudit);
        ExcelUtil<RechargeAudit> util = new ExcelUtil<RechargeAudit>(RechargeAudit.class);
        return util.exportExcel(list, "rechargeAudit");
    }

    /**
     * 新增充值审核
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        List<Role> list = roleService.selectRolesByUserId(ShiroUtils.getUserId());
        List<String> ids = new ArrayList<>();
        list.stream().forEach(role -> ids.add(role.getRoleId().toString()));
        List<PackageTemplate> templateList = packageTemplateService.selectPackageTemplateListByRoleIds(ids);
        modelMap.put("templateList", templateList);
        return prefix + "/add";
    }

    /**
     * 新增保存充值审核
     */
    @RequiresPermissions("busi:rechargeAudit:add")
    @Log(title = "充值审核", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RechargeAudit rechargeAudit) {
        return toAjax(rechargeAuditService.insertRechargeAudit(rechargeAudit));
    }

    /**
     * 修改充值审核
     */
    @GetMapping("/audit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        RechargeAudit rechargeAudit = rechargeAuditService.selectRechargeAuditById(id);
        mmap.put("rechargeAudit", rechargeAudit);
        return prefix + "/edit";
    }

    /**
     * 修改保存充值审核
     */
    @RequiresPermissions("busi:rechargeAudit:edit")
    @Log(title = "充值审核", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    @ResponseBody
    public AjaxResult editSave(RechargeAudit rechargeAudit) {
        rechargeAudit.setStatus(AuditStatus.PASS);
        return toAjax(rechargeAuditService.updateRechargeAudit(rechargeAudit));
    }

    /**
     * 删除充值审核
     */
    @RequiresPermissions("busi:rechargeAudit:remove")
    @Log(title = "充值审核", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(rechargeAuditService.deleteRechargeAuditByIds(ids));
    }

}

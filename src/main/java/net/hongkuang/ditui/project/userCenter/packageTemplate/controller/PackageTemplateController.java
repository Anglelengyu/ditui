package net.hongkuang.ditui.project.userCenter.packageTemplate.controller;

import java.util.List;

import net.hongkuang.ditui.project.system.role.service.IRoleService;
import net.hongkuang.ditui.project.system.user.service.IUserService;
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
import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplate;
import net.hongkuang.ditui.project.userCenter.packageTemplate.service.IPackageTemplateService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 套餐模板 信息操作处理
 *
 * @author yj
 * @date 2019-04-17
 */
@Controller
@RequestMapping("/busi/packageTemplate")
public class PackageTemplateController extends BaseController {
    private String prefix = "busi/packageTemplate";

    @Autowired
    private IPackageTemplateService packageTemplateService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;


    @RequiresPermissions("busi:packageTemplate:view")
    @GetMapping("/packageTemplate")
    public String packageTemplate() {
        return prefix + "/packageTemplate";
    }

    /**
     * 查询套餐模板列表
     */
    @RequiresPermissions("busi:packageTemplate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PackageTemplate packageTemplate) {
        startPage();
        List<PackageTemplate> list = packageTemplateService.selectPackageTemplateList(packageTemplate);
        return getDataTable(list);
    }


    /**
     * 导出套餐模板列表
     */
    @RequiresPermissions("busi:packageTemplate:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PackageTemplate packageTemplate) {
        List<PackageTemplate> list = packageTemplateService.selectPackageTemplateList(packageTemplate);
        ExcelUtil<PackageTemplate> util = new ExcelUtil<PackageTemplate>(PackageTemplate.class);
        return util.exportExcel(list, "packageTemplate");
    }

    /**
     * 新增套餐模板
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.put("roleList", roleService.selectRoleAll());
        return prefix + "/add";
    }

    /**
     * 新增保存套餐模板
     */
    @RequiresPermissions("busi:packageTemplate:add")
    @Log(title = "套餐模板", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PackageTemplate packageTemplate) {
        return toAjax(packageTemplateService.insertPackageTemplate(packageTemplate));
    }

    /**
     * 修改套餐模板
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        PackageTemplate packageTemplate = packageTemplateService.selectPackageTemplateById(id);
        mmap.put("packageTemplate", packageTemplate);
        mmap.put("roleList", roleService.selectRoleAll());
        return prefix + "/edit";
    }

    /**
     * 修改保存套餐模板
     */
    @RequiresPermissions("busi:packageTemplate:edit")
    @Log(title = "套餐模板", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PackageTemplate packageTemplate) {
        return toAjax(packageTemplateService.updatePackageTemplate(packageTemplate));
    }

    /**
     * 删除套餐模板
     */
    @RequiresPermissions("busi:packageTemplate:remove")
    @Log(title = "套餐模板", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(packageTemplateService.deletePackageTemplateByIds(ids));
    }

}

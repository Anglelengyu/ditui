package net.hongkuang.ditui.project.userCenter.employeeAccount.controller;

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
        import net.hongkuang.ditui.project.userCenter.employeeAccount.domain.EmployeeAccount;
        import net.hongkuang.ditui.project.userCenter.employeeAccount.service.IEmployeeAccountService;
        import net.hongkuang.ditui.framework.web.controller.BaseController;
        import net.hongkuang.ditui.framework.web.page.TableDataInfo;
        import net.hongkuang.ditui.framework.web.domain.AjaxResult;
        import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 用户账户 信息操作处理
 *
 * @author yj
 * @date 2019-04-18
 */
@Controller
@RequestMapping("/busi/employeeAccount")
public class EmployeeAccountController extends BaseController {
    private String prefix = "busi/employeeAccount";

    @Autowired
    private IEmployeeAccountService employeeAccountService;

    @RequiresPermissions("busi:employeeAccount:view")
    @GetMapping("/employeeAccount")
    public String employeeAccount() {
        return prefix + "/employeeAccount";
    }

    /**
     * 查询用户账户列表
     */
    @RequiresPermissions("busi:employeeAccount:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(EmployeeAccount employeeAccount) {
        startPage();
        List<EmployeeAccount> list = employeeAccountService.selectEmployeeAccountList(employeeAccount);
        return getDataTable(list);
    }


    /**
     * 导出用户账户列表
     */
    @RequiresPermissions("busi:employeeAccount:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EmployeeAccount employeeAccount) {
        List<EmployeeAccount> list = employeeAccountService.selectEmployeeAccountList(employeeAccount);
        ExcelUtil<EmployeeAccount> util = new ExcelUtil<EmployeeAccount>(EmployeeAccount. class);
        return util.exportExcel(list, "employeeAccount");
    }

    /**
     * 新增用户账户
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户账户
     */
    @RequiresPermissions("busi:employeeAccount:add")
    @Log(title = "用户账户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(EmployeeAccount employeeAccount) {
        return toAjax(employeeAccountService.insertEmployeeAccount(employeeAccount));
    }

    /**
     * 修改用户账户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        EmployeeAccount employeeAccount =employeeAccountService.selectEmployeeAccountById(id);
        mmap.put("employeeAccount", employeeAccount);
        return prefix + "/edit";
    }

    /**
     * 修改保存用户账户
     */
    @RequiresPermissions("busi:employeeAccount:edit")
    @Log(title = "用户账户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(EmployeeAccount employeeAccount) {
        return toAjax(employeeAccountService.updateEmployeeAccount(employeeAccount));
    }

    /**
     * 删除用户账户
     */
    @RequiresPermissions("busi:employeeAccount:remove")
    @Log(title = "用户账户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(employeeAccountService.deleteEmployeeAccountByIds(ids));
    }

}

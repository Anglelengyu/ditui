package net.hongkuang.ditui.project.busi.employee.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.SearchEmployee;
import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;
import net.hongkuang.ditui.project.busi.employee.service.IEmployeeService;
import net.hongkuang.ditui.project.busi.employee.support.EmployeeKeyEnum;
import net.hongkuang.ditui.project.busi.employee.support.EmployeeTypeEnum;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import net.hongkuang.ditui.project.system.role.service.IRoleService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工
 *
 * @author:zy
 * @date: 2019/3/22
 */
@Controller
@RequestMapping("/busi/onlineEmployee")
public class OnlineEmployeeController extends BaseController {

    private String prefix = "busi/onlineEmployee";

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;


    @RequiresPermissions("busi:onlineEmployee:view")
    @GetMapping()
    public String employee() {
        return prefix + "/employee";
    }

    /**
     * 查询员工列表
     */
    @RequiresPermissions("busi:onlineEmployee:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchEmployee employee) {
        startPage();
        employee.setEmployeeType(EmployeeTypeEnum.ONLINE.getCode());
        employee.setEmployeeKey(EmployeeKeyEnum.ONLINE.getCode());
        List<Employee> list = employeeService.selectEmployeeList(employee);
        return getDataTable(list);
    }

    @Log(title = "员工管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("busi:onlineEmployee:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SearchEmployee employee) {
        employee.setEmployeeType(EmployeeTypeEnum.ONLINE.getCode());
        employee.setEmployeeKey(EmployeeKeyEnum.ONLINE.getCode());
        List<Employee> list = employeeService.selectEmployeeList(employee);
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        return util.exportExcel(list, "employee");
    }

    /**
     * 新增员工
     */
    @GetMapping("/add/{teamId}")
    public String add(@PathVariable("teamId") Long teamId, ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        roleService.selectRoleAll().forEach(role -> {
            if (role.getRoleKey().equals(EmployeeKeyEnum.ONLINE.getCode())) {
                mmap.put("role", role);
            }
        });
        mmap.put("teamId", teamId);
        return prefix + "/add";
    }

    /**
     * 新增保存员工
     */
    @RequiresPermissions("busi:onlineEmployee:add")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Employee employee) {
        if (StringUtils.isNotNull(employee.getUserId()) && User.isAdmin(employee.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(employeeService.insertTeamMiddleEmployee(employee));
    }

    /**
     * 修改员工
     */
    @GetMapping("/edit/{employeeId}")
    public String edit(@PathVariable("employeeId") Long employeeId, ModelMap mmap) {
        roleService.selectRoleAll().forEach(role -> {
            if (role.getRoleKey().equals(EmployeeKeyEnum.ONLINE.getCode())) {
                mmap.put("role", role);
            }
        });
        Employee employee = employeeService.getEmployeeByEmployeeId(employeeId);
        mmap.put("user", userService.selectUserById(employee.getUserId()));
        return prefix + "/edit";
    }

    /**
     * 修改保存员工
     */
    @RequiresPermissions("busi:onlineEmployee:edit")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("busi:onlineEmployee:remove")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(employeeService.remove(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}

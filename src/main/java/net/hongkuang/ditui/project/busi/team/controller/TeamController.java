package net.hongkuang.ditui.project.busi.team.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.service.IEmployeeService;
import net.hongkuang.ditui.project.busi.manager.service.IManagerService;
import net.hongkuang.ditui.project.busi.team.domain.SearchTeam;
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
 * 团队
 *
 * @author:zy
 * @date: 2019/3/22
 */
@Controller
@RequestMapping("/busi/team")
public class TeamController extends BaseController {

    private String prefix = "busi/team";

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IManagerService managerService;
    @Autowired
    private IEmployeeService employeeService;


    @RequiresPermissions("busi:team:view")
    @GetMapping()
    public String team() {
        return prefix + "/team";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:team:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchTeam searchTeam) {
        startPage();
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchTeam.setOtherTeamId(team.getTeamId());
        }
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE)) {
            Employee employee = employeeService.getEmployeeByUserId(ShiroUtils.getUserId());
            searchTeam.setEmployeeId(employee.getEmployeeId());
        }
        List<Team> list = teamService.selectTeamList(searchTeam);
        return getDataTable(list);
    }

    @Log(title = "团队管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("busi:team:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SearchTeam team) {
        List<Team> list = teamService.selectTeamList(team);
        ExcelUtil<Team> util = new ExcelUtil<Team>(Team.class);
        return util.exportExcel(list, "team");
    }

    /**
     * 新增团队
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        roleService.selectRoleAll().forEach(role -> {
            if (role.getRoleKey().equals(UserConstants.UserRoles.ROLE_TEAM)) {
                mmap.put("role", role);
            }
        });
        return prefix + "/add";
    }

    /**
     * 新增保存团队
     */
    @RequiresPermissions("busi:team:add")
    @Log(title = "团队管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Team team) {
        if (StringUtils.isNotNull(team.getUserId()) && User.isAdmin(team.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(teamService.save(team));
    }

    /**
     * 查询团队的下级员工
     */
    @RequiresPermissions("busi:team:list")
    @GetMapping("/below/{teamId}")
    public String detail(@PathVariable("teamId") Long teamId, ModelMap mmap) {
        mmap.put("teamId", teamId);
        return "busi/onlineEmployee/employee";
    }

    /**
     * 修改团队
     */
    @GetMapping("/edit/{teamId}")
    public String edit(@PathVariable("teamId") Long teamId, ModelMap mmap) {
        roleService.selectRoleAll().forEach(role -> {
            if (role.getRoleKey().equals(UserConstants.UserRoles.ROLE_TEAM)) {
                mmap.put("role", role);
            }
        });
        Team team = teamService.getTeamByTeamId(teamId);
        mmap.put("user", userService.selectUserById(team.getUserId()));
        return prefix + "/edit";
    }

    /**
     * 修改保存团队
     */
    @RequiresPermissions("busi:team:edit")
    @Log(title = "团队管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("busi:team:remove")
    @Log(title = "团队管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(teamService.remove(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}

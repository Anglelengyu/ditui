package net.hongkuang.ditui.project.busi.roleApplyRelation.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;
import net.hongkuang.ditui.project.busi.employee.service.IEmployeeService;
import net.hongkuang.ditui.project.busi.manager.domain.ManagerMiddleTeam;
import net.hongkuang.ditui.project.busi.manager.service.IManagerService;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.RoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.SearchRoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.service.IRoleApplyRelationService;
import net.hongkuang.ditui.project.busi.roleApplyRelation.support.ApplyApplicantEnum;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.domain.TeamMiddleTeam;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

/**
 * 角色之间的 申请关联列表
 *
 * @author:zy
 * @date: 2019/4/02
 */
@Controller
@RequestMapping("/busi/roleApplyRelation")
public class RoleApplyRelationController extends BaseController {

    private String prefix = "busi/roleApplyRelation";

    @Autowired
    private IRoleApplyRelationService roleApplyRelationService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IManagerService managerService;


    @RequiresPermissions("busi:roleApplyRelation:view")
    @GetMapping()
    public String applyRelation(ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            mmap.put("userId", ShiroUtils.getUserId());
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("userId", team.getTeamId());
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE)) {
            Employee employee = employeeService.getEmployeeByUserId(ShiroUtils.getUserId());
            mmap.put("userId", employee.getEmployeeId());
        }
        return prefix + "/roleApplyRelation";
    }

    /**
     * 查询关联列表
     */
    @RequiresPermissions("busi:roleApplyRelation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchRoleApplyRelation searchRoleApplyRelation) {
        SearchRoleApplyRelation searchRoleApplyRelation1 = new SearchRoleApplyRelation();
        BeanUtils.copyBeanProp(searchRoleApplyRelation1, searchRoleApplyRelation);
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            searchRoleApplyRelation.setActiveApplyId(ShiroUtils.getUserId());
            searchRoleApplyRelation.setActiveApplyDelFlag("0");

            searchRoleApplyRelation1.setPassiveApplyId(ShiroUtils.getUserId());
            searchRoleApplyRelation1.setPassiveApplyDelFlag("0");
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchRoleApplyRelation.setActiveApplyId(team.getTeamId());
            searchRoleApplyRelation.setActiveApplyDelFlag("0");

            searchRoleApplyRelation1.setPassiveApplyId(team.getTeamId());
            searchRoleApplyRelation1.setPassiveApplyDelFlag("0");
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE)) {
            Employee employee = employeeService.getEmployeeByUserId(ShiroUtils.getUserId());
            searchRoleApplyRelation.setActiveApplyId(employee.getEmployeeId());
            searchRoleApplyRelation.setActiveApplyDelFlag("0");

            searchRoleApplyRelation1.setPassiveApplyId(employee.getEmployeeId());
            searchRoleApplyRelation1.setPassiveApplyDelFlag("0");
        }
        startPage();
        List<RoleApplyRelation> list = roleApplyRelationService.selectRoleApplyRelationList(searchRoleApplyRelation);

        searchRoleApplyRelation1.setActiveApplyId(null);
        list.addAll(roleApplyRelationService.selectRoleApplyRelationList(searchRoleApplyRelation1));
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return getDataTable(list);
    }

    /**
     * 新增关联
     */
    @RequiresPermissions("busi:roleApplyRelation:add")
    @Log(title = "新增关联", businessType = BusinessType.UPDATE)
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        return prefix + "/add";
    }

    /**
     * 新增保存申请关联
     */
    @RequiresPermissions("busi:roleApplyRelation:add")
    @Log(title = "申请关联管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(RoleApplyRelation roleApplyRelation) {
        User user = userService.selectUserById(ShiroUtils.getUserId());
        if (roleApplyRelation.getPassiveApplyApplicant() == 2) {
            Team team = teamService.getTeamByUserId(roleApplyRelation.getPassiveApplyId());
            roleApplyRelation.setPassiveApplyId(team.getTeamId());
        } else if (roleApplyRelation.getPassiveApplyApplicant() == 3) {
            Employee employee = employeeService.getEmployeeByUserId(roleApplyRelation.getPassiveApplyId());
            roleApplyRelation.setPassiveApplyId(employee.getEmployeeId());
        }

        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            roleApplyRelation.setActiveApplyApplicant(ApplyApplicantEnum.SHOP_MANAGER.getCode());
            roleApplyRelation.setActiveApplyId(user.getUserId());
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            roleApplyRelation.setActiveApplyApplicant(ApplyApplicantEnum.TEAM.getCode());
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            roleApplyRelation.setActiveApplyId(team.getTeamId());
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE)) {
            roleApplyRelation.setActiveApplyApplicant(ApplyApplicantEnum.EMPLOYEE.getCode());
            Employee employee = employeeService.getEmployeeByUserId(ShiroUtils.getUserId());
            roleApplyRelation.setActiveApplyId(employee.getEmployeeId());
        }
        roleApplyRelation.setActiveApplyUserName(user.getUserName());

        if (roleApplyRelation.getActiveApplyId() == roleApplyRelation.getPassiveApplyId()) {
            return AjaxResult.error("不可关联自己！");
        }
        if (roleApplyRelation.getActiveApplyApplicant() == 1) { //掌柜申请
            if (roleApplyRelation.getPassiveApplyApplicant() == 3) {  //被申请方=掌柜
                return AjaxResult.error("不可申请关联员工！");
            }
            int i = managerService.selectCountManagerMiddleTeamById(roleApplyRelation.getActiveApplyId(), roleApplyRelation.getPassiveApplyId());
            if (i != 0) {
                return AjaxResult.error("已有关联，不可重复关联！");
            }
        } else if (roleApplyRelation.getActiveApplyApplicant() == 2) {   //团队申请
            if (roleApplyRelation.getPassiveApplyApplicant() == 1) {  //被申请方=掌柜
                int i = managerService.selectCountManagerMiddleTeamById(roleApplyRelation.getPassiveApplyId(), roleApplyRelation.getActiveApplyId());
                if (i != 0) {
                    return AjaxResult.error("已有关联，不可重复关联！");
                }
            } else if (roleApplyRelation.getPassiveApplyApplicant() == 2) {  //被申请方=团队
                int i = teamService.selectCountTeamMiddleTeamById(roleApplyRelation.getPassiveApplyId(), roleApplyRelation.getActiveApplyId());
                if (i != 0) {
                    return AjaxResult.error("已有关联，不可重复关联！");
                }
            } else if (roleApplyRelation.getPassiveApplyApplicant() == 3) {  //被申请方=员工
                int i = teamService.selectCountTeamMiddleEmployeeById(roleApplyRelation.getPassiveApplyId(), roleApplyRelation.getActiveApplyId());
                if (i != 0) {
                    return AjaxResult.error("已有关联，不可重复关联！");
                }
            }
        } else if (roleApplyRelation.getActiveApplyApplicant() == 3) {   //员工申请
            if (roleApplyRelation.getPassiveApplyApplicant() == 1) {  //被申请方=掌柜
                return AjaxResult.error("不可申请关联掌柜！");
            }
            int i = teamService.selectCountTeamMiddleEmployeeById(roleApplyRelation.getActiveApplyId(), roleApplyRelation.getPassiveApplyId());
            if (i != 0) {
                return AjaxResult.error("已有关联，不可重复关联！");
            }
        }
        return toAjax(roleApplyRelationService.save(roleApplyRelation));
    }

    /**
     * 根据userNum查询各个角色
     */
    @GetMapping("/queryUser/{userNum}")
    @ResponseBody
    public AjaxResult queryManager(@PathVariable("userNum") String userNum) {
        return AjaxResult.success().put("data", roleApplyRelationService.selectUserRoleApplyRelationByUserNum(userNum));
    }

    /**
     * 处理申请
     */
    @RequiresPermissions("busi:roleApplyRelation:handle")
    @Log(title = "申请处理", businessType = BusinessType.UPDATE)
    @PostMapping("/handle")
    @ResponseBody
    public AjaxResult handle(String ids, Integer status) {
        return roleApplyRelationService.handle(ids, status);
    }

    @RequiresPermissions("busi:roleApplyRelation:remove")
    @Log(title = "申请关联管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(roleApplyRelationService.remove(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}

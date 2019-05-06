package net.hongkuang.ditui.project.busi.manager.controller;

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
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.domain.SearchManager;
import net.hongkuang.ditui.project.busi.manager.service.IManagerService;
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
 * 掌柜
 *
 * @author:zy
 * @date: 2019/3/21
 */
@Controller
@RequestMapping("/busi/manager")
public class ManagerController extends BaseController {

    private String prefix = "busi/manager";

    @Autowired
    private IManagerService managerService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITeamService teamService;


    @RequiresPermissions("busi:manager:view")
    @GetMapping()
    public String manager() {
        return prefix + "/manager";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:manager:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchManager manager) {
        startPage();
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            manager.setTeamId(team.getTeamId());
        }
        List<Manager> list = managerService.selectManagerList(manager);
        return getDataTable(list);
    }

    @Log(title = "掌柜管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("busi:manager:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SearchManager manager) {
        List<Manager> list = managerService.selectManagerList(manager);
        ExcelUtil<Manager> util = new ExcelUtil<Manager>(Manager.class);
        return util.exportExcel(list, "manager");
    }

    /**
     * 新增掌柜
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        roleService.selectRoleAll().forEach(role -> {
            if (role.getRoleKey().equals("shop_manager")) {
                mmap.put("role", role);
            }
        });
        return prefix + "/add";
    }

    /**
     * 新增保存掌柜
     */
    @RequiresPermissions("busi:manager:add")
    @Log(title = "掌柜管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(Manager manager) {
        if (StringUtils.isNotNull(manager.getUserId()) && User.isAdmin(manager.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        User user = new User();
        BeanUtils.copyBeanProp(user, manager);
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改掌柜
     */
    @GetMapping("/edit/{managerId}")
    public String edit(@PathVariable("managerId") Long managerId, ModelMap mmap) {
        roleService.selectRoleAll().forEach(role -> {
            if (role.getRoleKey().equals(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
                mmap.put("role", role);
            }
        });
        mmap.put("manager", managerService.selectManagerByUserId(managerId));
        return prefix + "/edit";
    }

    /**
     * 修改保存掌柜
     */
    @RequiresPermissions("busi:manager:edit")
    @Log(title = "掌柜管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("busi:manager:remove")
    @Log(title = "掌柜管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(managerService.remove(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


}

package net.hongkuang.ditui.project.system.user.controller;

import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.system.role.service.IRoleService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 用户和菜单关联
 *
 * @author:zy
 * @date: 2019/4/1
 */
@Controller
@RequestMapping("/system/userMenu")
public class UserMenuController extends BaseController {
    private String prefix = "system/userMenu";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("system:userMenu:view")
    @GetMapping()
    public String user() {
        return prefix + "/userMenu";
    }

    /**
     * 获取用户和菜单关联
     */
    @GetMapping("/getUserMenu")
    public String edit(ModelMap mmap) {
        mmap.put("user", userService.selectUserById(ShiroUtils.getUserId()));
        mmap.put("roles", roleService.selectRolesByUserId(ShiroUtils.getUserId()));
        return prefix + "/userMenu";
    }

    /**
     * 修改保存用户和菜单
     */
    @RequiresPermissions("system:userMenu:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        user.setUserId(ShiroUtils.getUserId());
        return toAjax(userService.updateUserMenu(user));
    }

}
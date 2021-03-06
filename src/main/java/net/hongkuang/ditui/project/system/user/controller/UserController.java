package net.hongkuang.ditui.project.system.user.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.system.role.service.IRoleService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.domain.UserCenterInfo;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    private String prefix = "system/user";

    private String user_center_prefix = "busi/userCenter";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("system:user:view")
    @GetMapping("/user")
    public String user() {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user) {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(User user) {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(User user) {
        if (StringUtils.isNotNull(user.getUserId()) && User.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(User user) {
        return toAjax(userService.resetUserPwd(user));
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userService.deleteUserByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 获取所有掌柜信息
     */
    @PostMapping("/findManager")
    @ResponseBody
    public TableDataInfo findManager() {
        List<User> list = userService.selectAllManager();
        return getDataTable(list);
    }


    //------------------------------------------------个人中心----------------------------

    /**
     * 成为合伙人
     */
    @Log(title = "成为合伙人", businessType = BusinessType.GRANT)
    @PostMapping("/becomeVip")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult becomeVip(Boolean isSuper, Long userId) {
        return toAjax(userService.becomeVip(isSuper, userId));
    }

    @RequiresPermissions("system:inviteUser:view")
    @GetMapping("/inviteUser")
    public String inviteUser() {
        return prefix + "/inviteUser";
    }

    /**
     * 邀请列表-当前用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("system:inviteUser:list")
    @PostMapping("/inviteList")
    @ResponseBody
    public TableDataInfo inviteList(User user) {
        startPage();
        List<User> list = userService.selectInviteUserList(user);
        return getDataTable(list);
    }


    @RequiresPermissions("system:userCenter:view")
    @GetMapping("/userCenter")
    public String userCenter(ModelMap modelMap, HttpServletRequest request) {
        UserCenterInfo userCenterInfo = new UserCenterInfo();
        User user = userService.selectUserById(ShiroUtils.getUserId());
        BeanUtils.copyProperties(user, userCenterInfo);
        userCenterInfo.setInviterUrl(new UserConstants.buildInviterUrl(request).build());
        modelMap.put("user", userCenterInfo);
        return user_center_prefix + "/userCenter";
    }


}
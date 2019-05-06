package net.hongkuang.ditui.project.system.user.controller;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.system.role.service.IRoleService;
import net.hongkuang.ditui.project.system.user.domain.UserRegister;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 注册
 *
 * @author:yanjie
 * @date: 2019/3/21
 */
@Controller
public class UserRegisterController extends BaseController {

    private String prefix = "";

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;

    @GetMapping("/registerView/{userNum}")
    public String manager(ModelMap mmap,@PathVariable(required = false) String userNum) {
        mmap.put("roleList", roleService.selectRoleAll());
        mmap.put("userNum", userNum);
        return prefix + "/register";
    }

    /**
     * 新增
     */
    @Log(title = "注册", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(UserRegister manager) {
        return toAjax(userService.insertUser(manager));
    }
}

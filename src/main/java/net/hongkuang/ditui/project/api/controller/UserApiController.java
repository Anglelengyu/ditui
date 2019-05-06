package net.hongkuang.ditui.project.api.controller;

import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.config.AccessCheck;
import net.hongkuang.ditui.project.api.dto.UserInfoReqVo;
import net.hongkuang.ditui.project.api.dto.UserLoginVo;
import net.hongkuang.ditui.project.api.service.ISalesmanApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by apple on 2019/1/8.
 */
@RestController
@RequestMapping("api/user")
public class UserApiController {

    @Autowired
    private ISalesmanApiService salesmanApiService;

    @RequestMapping("/login")
    public AjaxResult login(UserLoginVo loginVo) {
        return AjaxResult.success().put("user", salesmanApiService.login(loginVo));
    }

    @AccessCheck
    @RequestMapping("/info")
    public AjaxResult getUserInfo(UserInfoReqVo userInfoReqVo) {
        return AjaxResult.success().put("user", salesmanApiService.getUserInfo(userInfoReqVo));
    }
}

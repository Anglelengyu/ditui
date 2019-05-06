package net.hongkuang.ditui.project.api.service;

import net.hongkuang.ditui.project.api.dto.UserInfoReqVo;
import net.hongkuang.ditui.project.api.dto.UserInfoRespVo;
import net.hongkuang.ditui.project.api.dto.UserLoginRespVo;
import net.hongkuang.ditui.project.api.dto.UserLoginVo;

/**
 * Created by apple on 2019/1/8.
 */
public interface ISalesmanApiService {
    UserLoginRespVo login(UserLoginVo loginVo);

    UserInfoRespVo getUserInfo(UserInfoReqVo userInfoReqVo);
}

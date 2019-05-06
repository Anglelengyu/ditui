package net.hongkuang.ditui.project.api.service.impl;

import net.hongkuang.ditui.common.constant.Constants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.exception.user.UserBlockedException;
import net.hongkuang.ditui.common.exception.user.UserPasswordNotMatchException;
import net.hongkuang.ditui.common.exception.user.UserPasswordRetryLimitExceedException;
import net.hongkuang.ditui.common.utils.MessageUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.framework.manager.AsyncManager;
import net.hongkuang.ditui.framework.manager.factory.AsyncFactory;
import net.hongkuang.ditui.framework.shiro.service.PasswordService;
import net.hongkuang.ditui.project.api.config.UserTokenInfo;
import net.hongkuang.ditui.project.api.dto.UserInfoReqVo;
import net.hongkuang.ditui.project.api.dto.UserInfoRespVo;
import net.hongkuang.ditui.project.api.dto.UserLoginRespVo;
import net.hongkuang.ditui.project.api.dto.UserLoginVo;
import net.hongkuang.ditui.project.api.service.ISalesmanApiService;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.enums.SalemenStatus;
import net.hongkuang.ditui.project.busi.salesman.service.ISalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by apple on 2019/1/8.
 */
@Service
public class ISalesmanApiServiceImpl implements ISalesmanApiService {

    @Autowired
    private ISalesmanService salesmanService;
    @Autowired
    private PasswordService passwordService;

    @Override
    public UserLoginRespVo login(UserLoginVo loginVo) {
        String username = loginVo.getPhone();
        // 查询用户信息
        Salesman user = salesmanService.selectUserByPhoneNumber(username);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (SalemenStatus.DISABLE.getCode().equals(user.getStatus())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new UserBlockedException(user.getRemark());
        }

        try {
            passwordService.validate(user, loginVo.getPassword());
        } catch (UserPasswordNotMatchException e) {
            throw new BusinessException("账户或密码错误，请稍后重试");
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new BusinessException(e.getMessage());
        }
        UserTokenInfo userTokenInfo = new UserTokenInfo(user.getId().toString());
        UserLoginRespVo userLoginRespVo = new UserLoginRespVo();

        userLoginRespVo.setPhone(username);
        userLoginRespVo.setArea(user.getArea());
        userLoginRespVo.setName(user.getName());
        userLoginRespVo.setUserId(user.getId());
        userLoginRespVo.setHead(user.getHead());
        userLoginRespVo.setToken(userTokenInfo.getToken());
        return userLoginRespVo;
    }

    @Override
    public UserInfoRespVo getUserInfo(UserInfoReqVo userInfoReqVo) {
        Salesman salesman = salesmanService.selectSalesmanById(userInfoReqVo.getUserId());
        if (salesman == null) {
            throw new BusinessException("业务员不存在");
        }
        UserInfoRespVo userInfoRespVo = new UserInfoRespVo();
        BeanUtils.copyBeanProp(userInfoRespVo, salesman);
        userInfoRespVo.setToken(userInfoReqVo.getToken());
        return userInfoRespVo;
    }
}

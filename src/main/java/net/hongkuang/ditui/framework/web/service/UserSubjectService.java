package net.hongkuang.ditui.framework.web.service;

import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RuoYi首创 html调用 thymeleaf 实现参数管理
 *
 * @author ruoyi
 */
@Service("userSubject")
public class UserSubjectService {

    public String getLoginUserRoles() {
        return ShiroUtils.getLoginUserRoles().toString();
    }

}

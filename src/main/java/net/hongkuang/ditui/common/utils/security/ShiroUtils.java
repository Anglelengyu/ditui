package net.hongkuang.ditui.common.utils.security;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.project.system.user.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.framework.shiro.realm.UserRealm;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * shiro 工具类
 *
 * @author ruoyi
 */
public class ShiroUtils {
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubject().logout();
    }

    public static User getSysUser() {
        User user = null;
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new User();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    public static void setSysUser(User user) {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }

    public static Long getUserId() {
        return getSysUser().getUserId().longValue();
    }

    public static String getLoginName() {
        return getSysUser().getLoginName();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }


    public static List<String> getLoginUserRoles() {
        Subject subject = SecurityUtils.getSubject();
        String[] userRoles = {
                UserConstants.UserRoles.ROLE_TEAM,
                UserConstants.UserRoles.ROLE_SHOP_MANAGER,
                UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE,
                UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE,
                UserConstants.UserRoles.ROLE_SHOP_REFER
        };
        List<String> list = new ArrayList<>();
        CollectionUtils.arrayToList(userRoles).stream().forEach(role -> {
            if (subject.hasRole(role.toString())) {
                list.add(role.toString());
            }
        });
        return list;
    }
}

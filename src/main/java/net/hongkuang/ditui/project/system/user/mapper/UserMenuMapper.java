package net.hongkuang.ditui.project.system.user.mapper;

import net.hongkuang.ditui.project.system.user.domain.UserMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户和菜单关联 数据层
 *
 * @author:zy
 * @date: 2019/4/1
 */
@Repository
public interface UserMenuMapper {
    /**
     * 通过用户ID删除用户和菜单关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    int deleteUserMenuByUserId(Long userId);


    /**
     * 批量新增用户菜单信息
     *
     * @param userMenuList 用户菜单列表
     * @return 结果
     */
    int batchUserMenu(List<UserMenu> userMenuList);
}

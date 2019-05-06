package net.hongkuang.ditui.framework.web.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.shop.domain.Shop;
import net.hongkuang.ditui.project.busi.shop.service.IShopService;
import net.hongkuang.ditui.project.busi.team.domain.SearchTeam;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RuoYi首创 html调用 thymeleaf 实现参数管理
 *
 * @author ruoyi
 */
@Service("shopkeeper")
public class ShopkeeperService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IShopService shopService;
    @Autowired
    private ITeamService teamService;

    /**
     * 根据键名查询参数配置信息
     *
     * @return 参数键值
     */
    public List<User> getShopkeeper() {
        return userService.selectAllManager();
    }

    public List<Shop> getShopList(String managerId) {
        Shop shop = new Shop();
        if (!StringUtils.isEmpty(managerId)) {
            shop.setManagerId(managerId);
        } else {
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
                shop.setManagerId(ShiroUtils.getUserId() + "");
            }
        }
        return shopService.selectShopList(shop);
    }

    public List<User> getShopRefer() {
        return userService.selectAllShopRefer();
    }

    public List<User> getGroupLeader() {
        return userService.selectAllGroupLeader();
    }

    public List<Team> getTeamList(Long teamId) {
        SearchTeam searchTeam = new SearchTeam();
        if (teamId != null && teamId != 0) {
            searchTeam.setTeamId(teamId);
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchTeam.setTeamId(team.getTeamId());
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            searchTeam.setManagerId(ShiroUtils.getUserId());
        }
        return teamService.selectTeamList(searchTeam);
    }

}

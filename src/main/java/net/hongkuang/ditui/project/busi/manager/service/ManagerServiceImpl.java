package net.hongkuang.ditui.project.busi.manager.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.domain.SearchManager;
import net.hongkuang.ditui.project.busi.manager.mapper.ManagerMapper;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 掌柜 服务层实现
 *
 * @author:zy
 * @date: 2019/3/21
 */
@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private TeamMapper teamMapper;

    @Override
    public List<Manager> selectManagerList(SearchManager manager) {
        return managerMapper.selectManagerList(manager);
    }

    @Override
    public Manager selectManagerByUserId(Long userId) {
        return managerMapper.selectManagerByUserId(userId);
    }

    @Override
    public Manager selectManagerByUserNum(String userNum) {
        return managerMapper.selectManagerByUserNum(userNum);
    }

    @Override
    public int selectCountManagerMiddleTeamById(Long managerId, Long teamId) {
        return managerMapper.selectCountManagerMiddleTeamById(managerId, teamId);
    }

    @Override
    @Transactional
    public int remove(String ids) throws BusinessException {
        Long[] managerIds = Convert.toLongArray(ids);
        int i = 0;
        for (Long managerId : managerIds) {
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
                Team team = teamMapper.selectTeamByUserId(ShiroUtils.getUserId());
                i = managerMapper.deleteManagerMiddleTeam(managerId, team.getTeamId());
            }
        }
        return i;
    }

    @Override
    public List<Manager> selectManagerByTeamId(Long teamId) {
        return managerMapper.selectManagerByTeamId(teamId);
    }
}

package net.hongkuang.ditui.project.busi.team.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.mapper.EmployeeMapper;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.domain.ManagerMiddleTeam;
import net.hongkuang.ditui.project.busi.manager.mapper.ManagerMapper;
import net.hongkuang.ditui.project.busi.team.domain.SearchTeam;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.mapper.TeamMapper;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.mapper.UserMapper;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 团队 服务层实现
 *
 * @author:zy
 * @date: 2019/3/22
 */
@Service
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Team> selectTeamList(SearchTeam team) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            team.setManagerId(ShiroUtils.getUserId());
        }
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE)) {
            Employee employee = employeeMapper.selectEmployeeByUserId(ShiroUtils.getUserId());
            team.setEmployeeId(employee.getEmployeeId());
        }
        return teamMapper.selectTeamList(team);
    }

    @Override
    @Transactional
    public int save(Team team) {
        User user = new User();
        BeanUtils.copyBeanProp(user, team);
        int i = userService.insertUser(user);
        team.setUserId(user.getUserId());
        i = teamMapper.saveTeam(team);

        // 获取用户当前类型
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            ManagerMiddleTeam managerMiddleTeam = new ManagerMiddleTeam();
            managerMiddleTeam.setTeamId(team.getTeamId());
            managerMiddleTeam.setManagerId(ShiroUtils.getUserId());
            i = managerMapper.insertManagerMiddleTeam(managerMiddleTeam);
        }
        return i;
    }

    @Override
    @Transactional
    public int remove(String ids) throws BusinessException {
        Long[] teamIds = Convert.toLongArray(ids);
        int i = 0;
        for (Long teamId : teamIds) {
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
                Manager manager = managerMapper.selectManagerByUserId(ShiroUtils.getUserId());
                i = managerMapper.deleteManagerMiddleTeam(manager.getUserId(), teamId);
            } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE)) {
                Employee employee = employeeMapper.selectEmployeeByUserId(ShiroUtils.getUserId());
                i = teamMapper.deleteTeamMiddleEmployee(employee.getEmployeeId(), teamId);
            } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
                Team team = teamMapper.selectTeamByUserId(ShiroUtils.getUserId());
                i = teamMapper.deleteTeamMiddleTeam(team.getTeamId(), teamId);
                i = teamMapper.deleteTeamMiddleTeam(teamId, team.getTeamId());
            }
        }
        return i;
    }

    @Override
    public Team getTeamByTeamId(Long teamId) {
        return teamMapper.selectTeamByTeamId(teamId);
    }

    @Override
    public Team getTeamByUserId(Long userId) {
        return teamMapper.selectTeamByUserId(userId);
    }

    @Override
    public Team selectTeamByUserNum(String userNum) {
        return teamMapper.selectTeamByUserNum(userNum);
    }

    @Override
    public int selectCountTeamMiddleEmployeeById(Long employeeId, Long teamId) {
        return teamMapper.selectCountTeamMiddleEmployeeById(employeeId, teamId);
    }

    @Override
    public int selectCountTeamMiddleTeamById(Long otherTeamId, Long teamId) {
        return teamMapper.selectCountTeamMiddleTeamById(otherTeamId, teamId);
    }

    @Override
    public List<Team> selectTeamMiddleTeamById(Long teamId) {
        return teamMapper.selectTeamMiddleTeamById(teamId);
    }
}

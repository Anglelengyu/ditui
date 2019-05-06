package net.hongkuang.ditui.project.busi.roleApplyRelation.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;
import net.hongkuang.ditui.project.busi.employee.mapper.EmployeeMapper;
import net.hongkuang.ditui.project.busi.manager.domain.ManagerMiddleTeam;
import net.hongkuang.ditui.project.busi.manager.mapper.ManagerMapper;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.RoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.SearchRoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.UserRoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.mapper.RoleApplyRelationMapper;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.domain.TeamMiddleTeam;
import net.hongkuang.ditui.project.busi.team.mapper.TeamMapper;
import net.hongkuang.ditui.project.system.role.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色之间的 申请关联 服务层实现
 *
 * @author:zy
 * @date: 2019/4/02
 */
@Service
public class RoleApplyRelationServiceImpl implements IRoleApplyRelationService {

    @Autowired
    private RoleApplyRelationMapper roleApplyRelationMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<RoleApplyRelation> selectRoleApplyRelationList(SearchRoleApplyRelation searchRoleApplyRelation) {
        return roleApplyRelationMapper.selectRoleApplyRelationList(searchRoleApplyRelation);
    }

    @Override
    public int save(RoleApplyRelation roleApplyRelation) {
        return roleApplyRelationMapper.saveRoleApplyRelation(roleApplyRelation);
    }

    @Override
    public UserRoleApplyRelation selectUserRoleApplyRelationByUserNum(String userNum) {
        return roleApplyRelationMapper.selectUserRoleApplyRelationByUserNum(userNum);
    }

    @Override
    @Transactional
    public AjaxResult handle(String ids, Integer status) {
        String[] arrayIds = Convert.toStrArray(ids);
        if (status == 1) {
            for (String id : arrayIds) {
                RoleApplyRelation roleApplyRelation = roleApplyRelationMapper.selectRoleApplyRelationById(id);

                if (roleApplyRelation.getActiveApplyApplicant() == 1) { //掌柜申请
                    int i = managerMapper.selectCountManagerMiddleTeamById(roleApplyRelation.getActiveApplyId(), roleApplyRelation.getPassiveApplyId());
                    if (i != 0) {
                        return AjaxResult.error("已有关联，不可重复关联");
                    }
                    ManagerMiddleTeam managerMiddleTeam = new ManagerMiddleTeam();
                    managerMiddleTeam.setManagerId(roleApplyRelation.getActiveApplyId());
                    managerMiddleTeam.setTeamId(roleApplyRelation.getPassiveApplyId());
                    managerMapper.insertManagerMiddleTeam(managerMiddleTeam);
                } else if (roleApplyRelation.getActiveApplyApplicant() == 2) {   //团队申请
                    if (roleApplyRelation.getPassiveApplyApplicant() == 1) {  //被申请方=掌柜
                        int i = managerMapper.selectCountManagerMiddleTeamById(roleApplyRelation.getPassiveApplyId(), roleApplyRelation.getActiveApplyId());
                        if (i != 0) {
                            return AjaxResult.error("已有关联，不可重复关联");
                        }
                        ManagerMiddleTeam managerMiddleTeam = new ManagerMiddleTeam();
                        managerMiddleTeam.setManagerId(roleApplyRelation.getPassiveApplyId());
                        managerMiddleTeam.setTeamId(roleApplyRelation.getActiveApplyId());
                        managerMapper.insertManagerMiddleTeam(managerMiddleTeam);
                    } else if (roleApplyRelation.getPassiveApplyApplicant() == 2) {  //被申请方=团队
                        int i = teamMapper.selectCountTeamMiddleTeamById(roleApplyRelation.getPassiveApplyId(), roleApplyRelation.getActiveApplyId());
                        if (i != 0) {
                            return AjaxResult.error("已有关联，不可重复关联");
                        }
                        TeamMiddleTeam teamMiddleTeam = new TeamMiddleTeam();
                        teamMiddleTeam.setOtherTeamId(roleApplyRelation.getPassiveApplyId());
                        teamMiddleTeam.setTeamId(roleApplyRelation.getActiveApplyId());
                        teamMapper.insertTeamMiddleTeam(teamMiddleTeam);

                        TeamMiddleTeam teamMiddleTeam2 = new TeamMiddleTeam();
                        teamMiddleTeam2.setOtherTeamId(roleApplyRelation.getActiveApplyId());
                        teamMiddleTeam2.setTeamId(roleApplyRelation.getPassiveApplyId());
                        teamMapper.insertTeamMiddleTeam(teamMiddleTeam2);
                    } else if (roleApplyRelation.getPassiveApplyApplicant() == 3) {  //被申请方=员工
                        int i = teamMapper.selectCountTeamMiddleEmployeeById(roleApplyRelation.getPassiveApplyId(), roleApplyRelation.getActiveApplyId());
                        if (i != 0) {
                            return AjaxResult.error("已有关联，不可重复关联");
                        }
                        TeamMiddleEmployee teamMiddleEmployee = new TeamMiddleEmployee();
                        teamMiddleEmployee.setEmployeeId(roleApplyRelation.getPassiveApplyId());
                        teamMiddleEmployee.setTeamId(roleApplyRelation.getActiveApplyId());
                        teamMapper.insertTeamMiddleEmployee(teamMiddleEmployee);
                    }
                } else if (roleApplyRelation.getActiveApplyApplicant() == 3) {   //员工申请
                    int i = teamMapper.selectCountTeamMiddleEmployeeById(roleApplyRelation.getActiveApplyId(), roleApplyRelation.getPassiveApplyId());
                    if (i != 0) {
                        return AjaxResult.error("已有关联，不可重复关联");
                    }
                    TeamMiddleEmployee teamMiddleEmployee = new TeamMiddleEmployee();
                    teamMiddleEmployee.setEmployeeId(roleApplyRelation.getActiveApplyId());
                    teamMiddleEmployee.setTeamId(roleApplyRelation.getPassiveApplyId());
                    teamMapper.insertTeamMiddleEmployee(teamMiddleEmployee);
                }

            }
        }
        roleApplyRelationMapper.handle(arrayIds, status);
        return AjaxResult.success();
    }

    @Override
    public int remove(String ids) throws BusinessException {
        String[] tIds = Convert.toStrArray(ids);
        int i = 0;
        for (String id : tIds) {
            String activeApplyDelFlag = "";
            String passiveApplyDelFlag = "";
            RoleApplyRelation roleApplyRelation = roleApplyRelationMapper.selectRoleApplyRelationById(id);
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
                Team team = teamMapper.selectTeamByUserId(ShiroUtils.getUserId());
                if (roleApplyRelation.getActiveApplyId() == team.getTeamId()) {
                    activeApplyDelFlag = "2";
                } else if (roleApplyRelation.getPassiveApplyId() == team.getTeamId()) {
                    passiveApplyDelFlag = "2";
                }
            } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
                if (roleApplyRelation.getActiveApplyId() == ShiroUtils.getUserId()) {
                    activeApplyDelFlag = "2";
                } else if (roleApplyRelation.getPassiveApplyId() == ShiroUtils.getUserId()) {
                    passiveApplyDelFlag = "2";
                }
            } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE) || ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE)) {
                Employee employee = employeeMapper.selectEmployeeByUserId(ShiroUtils.getUserId());
                if (roleApplyRelation.getActiveApplyId() == employee.getEmployeeId()) {
                    activeApplyDelFlag = "2";
                } else if (roleApplyRelation.getPassiveApplyId() == employee.getEmployeeId()) {
                    passiveApplyDelFlag = "2";
                }
            }
            i = roleApplyRelationMapper.deleteDelFlagById(id, activeApplyDelFlag, passiveApplyDelFlag);
        }

        return i;
    }

}

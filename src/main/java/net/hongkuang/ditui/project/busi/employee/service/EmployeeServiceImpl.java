package net.hongkuang.ditui.project.busi.employee.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.SearchEmployee;
import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;
import net.hongkuang.ditui.project.busi.employee.mapper.EmployeeMapper;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.mapper.TeamMapper;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.mapper.UserMapper;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工 服务层实现
 *
 * @author:zy
 * @date: 2019/3/22
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ITeamService teamService;

    @Override
    public List<Employee> selectEmployeeList(SearchEmployee employee) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            employee.setTeamId(team.getTeamId());
        }
        return employeeMapper.selectEmployeeList(employee);
    }

    @Override
    @Transactional
    public int insertTeamMiddleEmployee(Employee employee) {
        User user = new User();
        BeanUtils.copyBeanProp(user, employee);
        int i = userService.insertUser(user);
        employee.setUserId(user.getUserId());
        i = employeeMapper.saveEmployee(employee);

        TeamMiddleEmployee teamMiddleEmployee = new TeamMiddleEmployee();
        teamMiddleEmployee.setEmployeeId(employee.getEmployeeId());
        if (employee.getTeamId() == null || employee.getTeamId() == 0) {
            employee.setTeamId(ShiroUtils.getUserId());
        }
        teamMiddleEmployee.setTeamId(employee.getTeamId());
        i = teamMapper.insertTeamMiddleEmployee(teamMiddleEmployee);
        return i;
    }

    @Override
    public int insertTeamMiddleEmployee(TeamMiddleEmployee employee) {
        int i = teamMapper.insertTeamMiddleEmployee(employee);
        return i;
    }

    @Override
    public Employee getEmployeeByEmployeeId(Long employeeId) {
        return employeeMapper.selectEmployeeByEmployeeId(employeeId);
    }

    @Override
    public Employee getEmployeeByUserId(Long userId) {
        return employeeMapper.selectEmployeeByUserId(userId);
    }

    @Override
    @Transactional
    public int remove(String ids) throws BusinessException {
        Long[] employeeIds = Convert.toLongArray(ids);
        int i = 0;
        for (Long employeeId : employeeIds) {
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
                Team team = teamMapper.selectTeamByUserId(ShiroUtils.getUserId());
                i = teamMapper.deleteTeamMiddleEmployee(employeeId, team.getTeamId());
            }
        }
        return i;
    }

    @Override
    public Employee selectEmployeeByUserNum(String employeeKey, String userNum) {
        return employeeMapper.selectEmployeeByUserNum(employeeKey, userNum);
    }
}

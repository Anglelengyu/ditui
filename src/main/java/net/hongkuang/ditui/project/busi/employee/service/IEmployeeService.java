package net.hongkuang.ditui.project.busi.employee.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.SearchEmployee;
import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;

import java.util.List;

/**
 * 员工
 *
 * @author:zy
 * @date: 2019/3/22
 */
public interface IEmployeeService {

    /**
     * 查询员工列表
     *
     * @param employee 员工信息
     * @return 员工集合
     */
    public List<Employee> selectEmployeeList(SearchEmployee employee);

    /**
     * 保存团队与员工关系信息
     *
     * @param employee 用户信息
     * @return 结果
     */
    int insertTeamMiddleEmployee(Employee employee);


    int insertTeamMiddleEmployee(TeamMiddleEmployee employee);

    /**
     * 查询单个员工
     *
     * @param employeeId 员工id
     * @return 员工
     */
    public Employee getEmployeeByEmployeeId(Long employeeId);

    /**
     * 查询单个员工
     *
     * @param employeeId 员工id
     * @return 员工
     */
    public Employee getEmployeeByUserId(Long employeeId);

    /**
     * 删除员工
     *
     * @param ids 员工ids
     * @return
     */
    public int remove(String ids) throws BusinessException;

    /**
     * 根据编号查询员工信息
     *
     * @param userNum 用户编号
     * @return 员工信息
     */
    public Employee selectEmployeeByUserNum(String employeeKey, String userNum);

}

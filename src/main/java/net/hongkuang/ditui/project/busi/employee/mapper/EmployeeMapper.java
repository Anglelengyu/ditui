package net.hongkuang.ditui.project.busi.employee.mapper;

import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.SearchEmployee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工 数据层
 *
 * @author:zy
 * @date: 2019/3/22
 */
@Repository
public interface EmployeeMapper {

    /**
     * 查询员工列表
     *
     * @param employee 员工信息
     * @return 员工集合
     */
    List<Employee> selectEmployeeList(SearchEmployee employee);

    /**
     * 新增员工
     *
     * @param employee 员工信息
     * @return 结果
     */
    int saveEmployee(Employee employee);

    /**
     * 查询员工信息
     *
     * @param employeeId 员工ID
     * @return 结果
     */
    Employee selectEmployeeByEmployeeId(Long employeeId);

    /**
     * 查询员工信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    Employee selectEmployeeByUserId(Long userId);

    /**
     * 批量删除员工信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteEmployeeByIds(Long[] ids);

    /**
     * 根据userNum查询员工信息
     *
     * @param userNum 用户编号
     * @return 员工信息
     */
    Employee selectEmployeeByUserNum(@Param("employeeKey") String employeeKey, @Param("userNum") String userNum);
}

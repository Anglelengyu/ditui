package net.hongkuang.ditui.project.userCenter.employeeAccount.service;

        import net.hongkuang.ditui.project.userCenter.employeeAccount.domain.EmployeeAccount;
        import java.util.List;

/**
 * 用户账户 服务层
 *
 * @author yj
 * @date 2019-04-18
 */
public interface IEmployeeAccountService {
    /**
     * 查询用户账户信息
     *
     * @param id 用户账户ID
     * @return 用户账户信息
     */
    public EmployeeAccount selectEmployeeAccountById(Integer id);

    /**
     * 查询用户账户列表
     *
     * @param employeeAccount 用户账户信息
     * @return 用户账户集合
     */
    public List<EmployeeAccount> selectEmployeeAccountList(EmployeeAccount employeeAccount);

    /**
     * 新增用户账户
     *
     * @param employeeAccount 用户账户信息
     * @return 结果
     */
    public int insertEmployeeAccount(EmployeeAccount employeeAccount);

    /**
     * 修改用户账户
     *
     * @param employeeAccount 用户账户信息
     * @return 结果
     */
    public int updateEmployeeAccount(EmployeeAccount employeeAccount);

    /**
     * 删除用户账户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmployeeAccountByIds(String ids);

    /**
     *
     * @param empId
     */
    EmployeeAccount selectEmployeeAccountByUserId(Integer empId);

}

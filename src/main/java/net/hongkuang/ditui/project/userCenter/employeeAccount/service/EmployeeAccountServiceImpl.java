package net.hongkuang.ditui.project.userCenter.employeeAccount.service;

        import java.util.List;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import net.hongkuang.ditui.project.userCenter.employeeAccount.mapper.EmployeeAccountMapper;
        import net.hongkuang.ditui.project.userCenter.employeeAccount.domain.EmployeeAccount;
        import net.hongkuang.ditui.common.support.Convert;

/**
 * 用户账户 服务层实现
 *
 * @author yj
 * @date 2019-04-18
 */
@Service
public class EmployeeAccountServiceImpl implements IEmployeeAccountService {
    @Autowired
    private EmployeeAccountMapper employeeAccountMapper;

    /**
     * 查询用户账户信息
     *
     * @param id 用户账户ID
     * @return 用户账户信息
     */
    @Override
    public EmployeeAccount selectEmployeeAccountById(Integer id) {
        return employeeAccountMapper.selectEmployeeAccountById(id);
    }

    /**
     * 查询用户账户列表
     *
     * @param employeeAccount 用户账户信息
     * @return 用户账户集合
     */
    @Override
    public List<EmployeeAccount> selectEmployeeAccountList(EmployeeAccount employeeAccount) {
        return employeeAccountMapper.selectEmployeeAccountList(employeeAccount);
    }

    /**
     * 新增用户账户
     *
     * @param employeeAccount 用户账户信息
     * @return 结果
     */
    @Override
    public int insertEmployeeAccount(EmployeeAccount employeeAccount) {
        return employeeAccountMapper.insertEmployeeAccount(employeeAccount);
    }

    /**
     * 修改用户账户
     *
     * @param employeeAccount 用户账户信息
     * @return 结果
     */
    @Override
    public int updateEmployeeAccount(EmployeeAccount employeeAccount) {
        return employeeAccountMapper.updateEmployeeAccount(employeeAccount);
    }

    /**
     * 删除用户账户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteEmployeeAccountByIds(String ids) {
        return employeeAccountMapper.deleteEmployeeAccountByIds(Convert.toStrArray(ids));
    }

    @Override
    public EmployeeAccount selectEmployeeAccountByUserId(Integer empId) {
        return employeeAccountMapper.selectEmployeeAccountByUserId(empId);
    }
}

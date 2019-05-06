package net.hongkuang.ditui.project.busi.salesman.service.impl;


import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.shiro.service.PasswordService;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.domain.SalesmanHistory;
import net.hongkuang.ditui.project.busi.salesman.domain.SearchSalesman;
import net.hongkuang.ditui.project.busi.salesman.mapper.SalesmanMapper;
import net.hongkuang.ditui.project.busi.salesman.service.ISalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务员 服务层实现
 *
 * @author yj
 * @date 2018-12-30
 */
@Service
public class SalesmanServiceImpl implements ISalesmanService {
    @Autowired
    private SalesmanMapper salesmanMapper;
    @Autowired
    private PasswordService passwordService;

    /**
     * 查询业务员信息
     *
     * @param id 业务员ID
     * @return 业务员信息
     */
    @Override
    public Salesman selectSalesmanById(Long id) {
        return salesmanMapper.selectSalesmanById(id);
    }

    /**
     * 查询业务员列表
     *
     * @param salesman 业务员信息
     * @return 业务员集合
     */
    @Override
    public List<Salesman> selectSalesmanList(Salesman salesman) {
        // 获取用户当前类型
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_GROUP_LEADER)) {
            salesman.setGroupLeaderId(ShiroUtils.getUserId());
        }
        return salesmanMapper.selectSalesmanList(salesman);
    }

    /**
     * 新增业务员
     *
     * @param salesman 业务员信息
     * @return 结果
     */
    @Override
    public int insertSalesman(Salesman salesman) {
        salesman.randomSalt();
        salesman.setPassword(passwordService.encryptPassword(salesman.getPhone(), salesman.getPassword(), salesman.getSalt()));
        salesman.setCreateBy(ShiroUtils.getLoginName());
        salesman.setSaleId(RandomUtil.genString());
        return salesmanMapper.insertSalesman(salesman);
    }

    /**
     * 修改业务员
     *
     * @param salesman 业务员信息
     * @return 结果
     */
    @Override
    public int updateSalesman(Salesman salesman) {
        return salesmanMapper.updateSalesman(salesman);
    }

    /**
     * 删除业务员对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSalesmanByIds(String ids) {
        return salesmanMapper.deleteSalesmanByIds(Convert.toStrArray(ids));
    }

    /**
     * 检查手机号唯一
     *
     * @param sales
     * @return
     */
    @Override
    public String checkPhoneUnique(Salesman sales) {
        Salesman salesman = salesmanMapper.checkPhoneUnique(sales.getPhone());
        if (StringUtils.isNotNull(salesman) && salesman.getId().longValue() != sales.getId().longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 重置业务员登录密码
     *
     * @param salesman
     * @return
     */
    @Override
    public int resetUserPwd(Salesman salesman) {
        salesman.randomSalt();
        salesman.setUpdateBy(ShiroUtils.getLoginName());
        salesman.setPassword(passwordService.encryptPassword(salesman.getPhone(), salesman.getPassword(), salesman.getSalt()));
        return updateSalesman(salesman);
    }

    /**
     * 根据信息查询业务员完成任务的历史地点
     *
     * @param salesman
     * @return
     */
    @Override
    public List<SalesmanHistory> selectSalesmanHistoryList(SearchSalesman salesman) {
        if (!StringUtils.isEmpty(salesman.getStartTime())) {
            salesman.setStartTime(salesman.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(salesman.getEndTime())) {
            salesman.setEndTime(salesman.getEndTime() + " 23:59:59");
        }
        return salesmanMapper.selectSalesmanHistoryList(salesman);
    }

    @Override
    public Salesman selectUserByPhoneNumber(String phone) {
        Salesman salesman = new Salesman();
        salesman.setPhone(phone);
        List<Salesman> salesmanList = salesmanMapper.selectSalesmanList(salesman);
        return salesmanList.isEmpty() ? null : salesmanList.get(0);
    }

}

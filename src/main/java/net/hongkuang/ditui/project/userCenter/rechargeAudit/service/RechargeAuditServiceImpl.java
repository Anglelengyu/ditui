package net.hongkuang.ditui.project.userCenter.rechargeAudit.service;

import java.util.Date;
import java.util.List;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.domain.CoinsConsumptionLog;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.service.ICoinsConsumptionLogService;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.support.ConsumptionLogStatus;
import net.hongkuang.ditui.project.userCenter.employeeAccount.domain.EmployeeAccount;
import net.hongkuang.ditui.project.userCenter.employeeAccount.service.IEmployeeAccountService;
import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplate;
import net.hongkuang.ditui.project.userCenter.packageTemplate.service.IPackageTemplateService;
import net.hongkuang.ditui.project.userCenter.rechargeAudit.support.AuditStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.project.userCenter.rechargeAudit.mapper.RechargeAuditMapper;
import net.hongkuang.ditui.project.userCenter.rechargeAudit.domain.RechargeAudit;
import net.hongkuang.ditui.common.support.Convert;
import org.springframework.util.ObjectUtils;

/**
 * 充值审核 服务层实现
 *
 * @author yj
 * @date 2019-04-17
 */
@Service
public class RechargeAuditServiceImpl implements IRechargeAuditService {
    @Autowired
    private RechargeAuditMapper rechargeAuditMapper;
    @Autowired
    private ICoinsConsumptionLogService coinsConsumptionLogService;
    @Autowired
    private IEmployeeAccountService employeeAccountService;
    @Autowired
    private IPackageTemplateService packageTemplateService;

    /**
     * 查询充值审核信息
     *
     * @param id 充值审核ID
     * @return 充值审核信息
     */
    @Override
    public RechargeAudit selectRechargeAuditById(Integer id) {
        return rechargeAuditMapper.selectRechargeAuditById(id);
    }

    /**
     * 查询充值审核列表
     *
     * @param rechargeAudit 充值审核信息
     * @return 充值审核集合
     */
    @Override
    public List<RechargeAudit> selectRechargeAuditList(RechargeAudit rechargeAudit) {
        return rechargeAuditMapper.selectRechargeAuditList(rechargeAudit);
    }

    /**
     * 新增充值审核
     *
     * @param rechargeAudit 充值审核信息
     * @return 结果
     */
    @Override
    public int insertRechargeAudit(RechargeAudit rechargeAudit) {
        rechargeAudit.setCreateBy(ShiroUtils.getUserId().toString());
        rechargeAudit.setUpdateBy(ShiroUtils.getUserId().toString());
        rechargeAudit.setCreateTime(new Date());
        rechargeAudit.setUpdateTime(new Date());
        PackageTemplate packageTemplate = packageTemplateService.selectPackageTemplateById(rechargeAudit.getPackageId());
        if(packageTemplate == null){
            throw new BusinessException("当前充值套餐不存在");
        }
        rechargeAudit.setGiveCoin(packageTemplate.getGiveCoin());
        rechargeAudit.setBaseCoin(packageTemplate.getBaseCoin());
        rechargeAudit.setPrice(packageTemplate.getPrice());
        return rechargeAuditMapper.insertRechargeAudit(rechargeAudit);
    }

    /**
     * 修改充值审核
     *
     * @param rechargeAudit 充值审核信息
     * @return 结果
     */
    @Override
    public int updateRechargeAudit(RechargeAudit rechargeAudit) {
        rechargeAudit.setUpdateBy(ShiroUtils.getUserId().toString());
        rechargeAudit.setUpdateTime(new Date());
        int count = rechargeAuditMapper.updateRechargeAudit(rechargeAudit);
        //获取状态判断
        if(rechargeAudit.getStatus() == AuditStatus.REJECT){
            return count;
        }
        if (count > 0) {
            //保存充值记录
            CoinsConsumptionLog log = new CoinsConsumptionLog();
            log.setPrice(rechargeAudit.getPrice());
            log.setBaseCoin(rechargeAudit.getBaseCoin());
            log.setGiveCoin(rechargeAudit.getGiveCoin());
            log.setEmpId(rechargeAudit.getEmpId());
            log.setStatus(ConsumptionLogStatus.RECHARGE);
            log.setCreateBy(ShiroUtils.getUserId().toString());
            log.setCreateTime(new Date());
            log.setUpdateBy(ShiroUtils.getUserId().toString());
            log.setUpdateTime(new Date());
            count = coinsConsumptionLogService.insertCoinsConsumptionLog(log);
            if(count > 0) {
                //记录金币
                EmployeeAccount employeeAccount = employeeAccountService.selectEmployeeAccountByUserId(rechargeAudit.getEmpId());
                if (ObjectUtils.isEmpty(employeeAccount)) {
                    employeeAccount = new EmployeeAccount();
                    employeeAccount.setCreateBy(ShiroUtils.getUserId().toString());
                    employeeAccount.setCreateTime(new Date());
                    employeeAccount.setUpdateBy(ShiroUtils.getUserId().toString());
                    employeeAccount.setUpdateTime(new Date());
                    employeeAccount.setCurrentPrice(rechargeAudit.getBaseCoin() + rechargeAudit.getGiveCoin());
                    employeeAccount.setCountPrice(rechargeAudit.getBaseCoin() + rechargeAudit.getGiveCoin());
                    employeeAccount.setEmpId(rechargeAudit.getEmpId().longValue());
                    employeeAccountService.insertEmployeeAccount(employeeAccount);
                } else {
                    employeeAccount.setUpdateBy(ShiroUtils.getUserId().toString());
                    employeeAccount.setUpdateTime(new Date());
                    employeeAccount.setCurrentPrice(employeeAccount.getCurrentPrice() + rechargeAudit.getBaseCoin() + rechargeAudit.getGiveCoin());
                    employeeAccount.setCountPrice(employeeAccount.getCountPrice() + rechargeAudit.getBaseCoin() + rechargeAudit.getGiveCoin());
                    employeeAccountService.updateEmployeeAccount(employeeAccount);
                }
            }
        }
        return count;
    }

    /**
     * 删除充值审核对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRechargeAuditByIds(String ids) {
        return rechargeAuditMapper.deleteRechargeAuditByIds(Convert.toStrArray(ids));
    }

}

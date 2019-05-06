package net.hongkuang.ditui.project.userCenter.rechargeAudit.mapper;

        import net.hongkuang.ditui.project.userCenter.rechargeAudit.domain.RechargeAudit;
        import java.util.List;

/**
 * 充值审核 数据层
 *
 * @author yj
 * @date 2019-04-17
 */
public interface RechargeAuditMapper {
    /**
     * 查询充值审核信息
     *
     * @param id 充值审核ID
     * @return 充值审核信息
     */
    public RechargeAudit selectRechargeAuditById(Integer id);

    /**
     * 查询充值审核列表
     *
     * @param rechargeAudit 充值审核信息
     * @return 充值审核集合
     */
    public List<RechargeAudit> selectRechargeAuditList(RechargeAudit rechargeAudit);

    /**
     * 新增充值审核
     *
     * @param rechargeAudit 充值审核信息
     * @return 结果
     */
    public int insertRechargeAudit(RechargeAudit rechargeAudit);

    /**
     * 修改充值审核
     *
     * @param rechargeAudit 充值审核信息
     * @return 结果
     */
    public int updateRechargeAudit(RechargeAudit rechargeAudit);

    /**
     * 删除充值审核
     *
     * @param id 充值审核ID
     * @return 结果
     */
    public int deleteRechargeAuditById(Integer id);

    /**
     * 批量删除充值审核
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRechargeAuditByIds(String[] ids);

}
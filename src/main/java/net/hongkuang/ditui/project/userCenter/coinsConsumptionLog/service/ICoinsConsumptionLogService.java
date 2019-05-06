package net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.service;

import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.domain.CoinsConsumptionLog;

import java.util.List;

/**
 * 充值扣费记录 服务层
 *
 * @author yj
 * @date 2019-04-17
 */
public interface ICoinsConsumptionLogService {
    /**
     * 查询充值扣费记录信息
     *
     * @param id 充值扣费记录ID
     * @return 充值扣费记录信息
     */
    public CoinsConsumptionLog selectCoinsConsumptionLogById(Integer id);

    /**
     * 查询充值扣费记录列表
     *
     * @param coinsConsumptionLog 充值扣费记录信息
     * @return 充值扣费记录集合
     */
    public List<CoinsConsumptionLog> selectCoinsConsumptionLogList(CoinsConsumptionLog coinsConsumptionLog);

    /**
     * 新增充值扣费记录
     *
     * @param coinsConsumptionLog 充值扣费记录信息
     * @return 结果
     */
    public int insertCoinsConsumptionLog(CoinsConsumptionLog coinsConsumptionLog);

    /**
     * 修改充值扣费记录
     *
     * @param coinsConsumptionLog 充值扣费记录信息
     * @return 结果
     */
    public int updateCoinsConsumptionLog(CoinsConsumptionLog coinsConsumptionLog);

    /**
     * 删除充值扣费记录信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCoinsConsumptionLogByIds(String ids);

}

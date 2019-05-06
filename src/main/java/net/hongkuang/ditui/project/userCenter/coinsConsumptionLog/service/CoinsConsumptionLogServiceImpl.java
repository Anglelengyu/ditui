package net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.mapper.CoinsConsumptionLogMapper;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.domain.CoinsConsumptionLog;
import net.hongkuang.ditui.common.support.Convert;

/**
 * 充值扣费记录 服务层实现
 *
 * @author yj
 * @date 2019-04-17
 */
@Service
public class CoinsConsumptionLogServiceImpl implements ICoinsConsumptionLogService {

    @Autowired
    private CoinsConsumptionLogMapper coinsConsumptionLogMapper;

    /**
     * 查询充值扣费记录信息
     *
     * @param id 充值扣费记录ID
     * @return 充值扣费记录信息
     */
    @Override
    public CoinsConsumptionLog selectCoinsConsumptionLogById(Integer id) {
        return coinsConsumptionLogMapper.selectCoinsConsumptionLogById(id);
    }

    /**
     * 查询充值扣费记录列表
     *
     * @param coinsConsumptionLog 充值扣费记录信息
     * @return 充值扣费记录集合
     */
    @Override
    public List<CoinsConsumptionLog> selectCoinsConsumptionLogList(CoinsConsumptionLog coinsConsumptionLog) {
        return coinsConsumptionLogMapper.selectCoinsConsumptionLogList(coinsConsumptionLog);
    }

    /**
     * 新增充值扣费记录
     *
     * @param coinsConsumptionLog 充值扣费记录信息
     * @return 结果
     */
    @Override
    public int insertCoinsConsumptionLog(CoinsConsumptionLog coinsConsumptionLog) {
        return coinsConsumptionLogMapper.insertCoinsConsumptionLog(coinsConsumptionLog);
    }

    /**
     * 修改充值扣费记录
     *
     * @param coinsConsumptionLog 充值扣费记录信息
     * @return 结果
     */
    @Override
    public int updateCoinsConsumptionLog(CoinsConsumptionLog coinsConsumptionLog) {
        return coinsConsumptionLogMapper.updateCoinsConsumptionLog(coinsConsumptionLog);
    }

    /**
     * 删除充值扣费记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCoinsConsumptionLogByIds(String ids) {
        return coinsConsumptionLogMapper.deleteCoinsConsumptionLogByIds(Convert.toStrArray(ids));
    }

}

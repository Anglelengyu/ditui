package net.hongkuang.ditui.project.busi.saleTask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.saleTask.domain.SaleTask;
import net.hongkuang.ditui.project.busi.saleTask.service.ISaleTaskService;
import net.hongkuang.ditui.common.support.Convert;

/**
 * 业务员任务 服务层实现
 *
 * @author yj
 * @date 2019-01-11
 */
@Service
public class SaleTaskServiceImpl implements ISaleTaskService {
    @Autowired
    private SaleTaskMapper saleTaskMapper;

    /**
     * 查询业务员任务信息
     *
     * @param id 业务员任务ID
     * @return 业务员任务信息
     */
    @Override
    public SaleTask selectSaleTaskById(Long id) {
        return saleTaskMapper.selectSaleTaskById(id);
    }

    /**
     * 查询业务员任务列表
     *
     * @param saleTask 业务员任务信息
     * @return 业务员任务集合
     */
    @Override
    public List<SaleTask> selectSaleTaskList(SaleTask saleTask) {
        return saleTaskMapper.selectSaleTaskList(saleTask);
    }

    /**
     * 新增业务员任务
     *
     * @param saleTask 业务员任务信息
     * @return 结果
     */
    @Override
    public int insertSaleTask(SaleTask saleTask) {
        return saleTaskMapper.insertSaleTask(saleTask);
    }

    /**
     * 修改业务员任务
     *
     * @param saleTask 业务员任务信息
     * @return 结果
     */
    @Override
    public int updateSaleTask(SaleTask saleTask) {
        return saleTaskMapper.updateSaleTask(saleTask);
    }

    /**
     * 删除业务员任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSaleTaskByIds(String ids) {
        return saleTaskMapper.deleteSaleTaskByIds(Convert.toStrArray(ids));
    }

}

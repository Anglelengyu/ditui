package net.hongkuang.ditui.project.busi.saleTask.mapper;

import net.hongkuang.ditui.project.busi.saleTask.domain.SaleTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 业务员任务 数据层
 *
 * @author yj
 * @date 2019-01-11
 */
@Repository
public interface SaleTaskMapper {
    /**
     * 查询业务员任务信息
     *
     * @param id 业务员任务ID
     * @return 业务员任务信息
     */
    SaleTask selectSaleTaskById(Long id);

    /**
     * 查询业务员任务列表
     *
     * @param saleTask 业务员任务信息
     * @return 业务员任务集合
     */
    List<SaleTask> selectSaleTaskList(SaleTask saleTask);

    /**
     * 新增业务员任务
     *
     * @param saleTask 业务员任务信息
     * @return 结果
     */
    int insertSaleTask(SaleTask saleTask);

    /**
     * 修改业务员任务
     *
     * @param saleTask 业务员任务信息
     * @return 结果
     */
    int updateSaleTask(SaleTask saleTask);

    /**
     * 删除业务员任务
     *
     * @param id 业务员任务ID
     * @return 结果
     */
    int deleteSaleTaskById(Long id);

    /**
     * 批量删除业务员任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSaleTaskByIds(String[] ids);

    int deleteByTaskIds(String[] taskIds);

    int batchInsert(@Param("list") List<SaleTask> saleTaskList);

    /**
     * 获取用户的接单量
     *
     * @param saleId
     * @param taskDate
     * @return
     */
    Integer getSaleTaskNum(@Param("saleId") String saleId, @Param("taskDate") String taskDate);
}
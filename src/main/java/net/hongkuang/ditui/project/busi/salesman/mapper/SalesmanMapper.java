package net.hongkuang.ditui.project.busi.salesman.mapper;

import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.domain.SalesmanHistory;
import net.hongkuang.ditui.project.busi.salesman.domain.SearchSalesman;

import java.util.List;

/**
 * 业务员 数据层
 *
 * @author yj
 * @date 2018-12-30
 */
public interface SalesmanMapper {
    /**
     * 查询业务员信息
     *
     * @param id 业务员ID
     * @return 业务员信息
     */
    Salesman selectSalesmanById(Long id);

    /**
     * 查询业务员列表
     *
     * @param salesman 业务员信息
     * @return 业务员集合
     */
    List<Salesman> selectSalesmanList(Salesman salesman);

    /**
     * 新增业务员
     *
     * @param salesman 业务员信息
     * @return 结果
     */
    int insertSalesman(Salesman salesman);

    /**
     * 修改业务员
     *
     * @param salesman 业务员信息
     * @return 结果
     */
    int updateSalesman(Salesman salesman);

    /**
     * 删除业务员
     *
     * @param id 业务员ID
     * @return 结果
     */
    int deleteSalesmanById(Long id);

    /**
     * 批量删除业务员
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSalesmanByIds(String[] ids);

    Salesman checkPhoneUnique(String phone);

    List<Salesman> selectSalesmanBySaleIdList(List<String> saleIdList);

    /**
     * 根据信息查询业务员完成任务的历史地点
     *
     * @param salesman
     * @return
     */
    List<SalesmanHistory> selectSalesmanHistoryList(SearchSalesman salesman);
}
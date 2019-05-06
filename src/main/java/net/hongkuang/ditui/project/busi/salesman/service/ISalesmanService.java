package net.hongkuang.ditui.project.busi.salesman.service;

import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.domain.SalesmanHistory;
import net.hongkuang.ditui.project.busi.salesman.domain.SearchSalesman;

import java.util.List;

/**
 * 业务员 服务层
 *
 * @author yj
 * @date 2018-12-30
 */
public interface ISalesmanService {
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
     * 删除业务员信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSalesmanByIds(String ids);

    /**
     * 检查手机号唯一
     *
     * @param sales
     * @return
     */
    String checkPhoneUnique(Salesman sales);

    /**
     * 重置业务员登录密码
     *
     * @param salesman
     * @return
     */
    int resetUserPwd(Salesman salesman);

    /**
     * 根据信息查询业务员完成任务的历史地点
     *
     * @param salesman
     * @return
     */
    List<SalesmanHistory> selectSalesmanHistoryList(SearchSalesman salesman);

    Salesman selectUserByPhoneNumber(String phone);
}

package net.hongkuang.ditui.project.busi.manager.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.domain.SearchManager;

import java.util.List;

/**
 * 掌柜
 *
 * @author:zy
 * @date: 2019/3/21
 */
public interface IManagerService {

    /**
     * 查询掌柜列表
     *
     * @param manager 掌柜信息
     * @return 掌柜集合
     */
    public List<Manager> selectManagerList(SearchManager manager);

    /**
     * 根据userId查询掌柜信息
     *
     * @param userId 用户ID
     * @return 掌柜信息
     */
    public Manager selectManagerByUserId(Long userId);

    /**
     * 根据编号查询掌柜信息
     *
     * @param userNum 用户编号
     * @return 掌柜信息
     */
    public Manager selectManagerByUserNum(String userNum);

    /**
     * 查询数量
     *
     * @param
     * @return
     */
    public int selectCountManagerMiddleTeamById(Long managerId, Long teamId);

    /**
     * 删除掌柜
     *
     * @param ids 员工ids
     * @return
     */
    public int remove(String ids) throws BusinessException;

    /**
     * 查询团队关联掌柜列表
     */
    public List<Manager> selectManagerByTeamId(Long teamId);

}

package net.hongkuang.ditui.project.busi.manager.mapper;

import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.domain.ManagerMiddleTeam;
import net.hongkuang.ditui.project.busi.manager.domain.SearchManager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 掌柜 数据层
 *
 * @author:zy
 * @date: 2019/3/21
 */
@Repository
public interface ManagerMapper {

    /**
     * 查询掌柜列表
     *
     * @param manager 掌柜信息
     * @return 掌柜集合
     */
    List<Manager> selectManagerList(SearchManager manager);

    /**
     * 根据userId查询掌柜信息
     *
     * @param userId 用户id
     * @return 掌柜信息
     */
    Manager selectManagerByUserId(Long userId);

    /**
     * 根据userNum查询掌柜信息
     *
     * @param userNum 用户编号
     * @return 掌柜信息
     */
    Manager selectManagerByUserNum(String userNum);

    /**
     * 新增掌柜与团队关系信息
     *
     * @param managerMiddleTeam 用户信息
     * @return 结果
     */
    int insertManagerMiddleTeam(ManagerMiddleTeam managerMiddleTeam);

    /**
     * 批量删除掌柜与团队关系信息
     *
     * @param managerId 需要删除的数据ID
     * @return 结果
     */
    int deleteManagerMiddleTeam(@Param("managerId") Long managerId, @Param("teamId") Long teamId);

    /**
     * 查询掌柜与团队关系信息数量
     *
     * @param
     * @return 结果
     */
    int selectCountManagerMiddleTeamById(@Param("managerId") Long managerId, @Param("teamId") Long teamId);

    /**
     * 查询团队关联的掌柜列表
     */
    List<Manager> selectManagerByTeamId(Long teamId);
}

package net.hongkuang.ditui.project.busi.team.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.project.busi.team.domain.SearchTeam;
import net.hongkuang.ditui.project.busi.team.domain.Team;

import java.util.List;

/**
 * 团队
 *
 * @author:zy
 * @date: 2019/3/22
 */
public interface ITeamService {

    /**
     * 查询团队列表
     *
     * @param team 团队信息
     * @return 团队集合
     */
    public List<Team> selectTeamList(SearchTeam team);

    /**
     * 新增团队
     *
     * @param team 团队信息
     * @return 团队集合
     */
    public int save(Team team);

    /**
     * 删除团队
     *
     * @param ids 团队ids
     * @return
     */
    public int remove(String ids) throws BusinessException;

    /**
     * 查询单个团队
     *
     * @param teamId 团队id
     * @return 团队
     */
    public Team getTeamByTeamId(Long teamId);

    /**
     * 查询单个团队
     *
     * @param userId 用户id
     * @return 团队
     */
    public Team getTeamByUserId(Long userId);

    /**
     * 根据编号查询团队信息
     *
     * @param userNum 用户编号
     * @return 团队信息
     */
    public Team selectTeamByUserNum(String userNum);

    /**
     * 查询数量
     *
     * @param
     * @return
     */
    public int selectCountTeamMiddleEmployeeById(Long employeeId, Long teamId);

    /**
     * 查询团队与团队关系信息数量
     *
     * @param
     * @return 结果
     */
    int selectCountTeamMiddleTeamById(Long otherTeamId, Long teamId);

    /**
     * 根据团队id查询合作团队信息
     *
     * @param teamId 用户id
     * @return 团队信息
     */
    public List<Team> selectTeamMiddleTeamById(Long teamId);
}

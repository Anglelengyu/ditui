package net.hongkuang.ditui.project.busi.team.mapper;

import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;
import net.hongkuang.ditui.project.busi.team.domain.SearchTeam;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.domain.TeamMiddleTeam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 团队 数据层
 *
 * @author:zy
 * @date: 2019/3/22
 */
@Repository
public interface TeamMapper {

    /**
     * 查询团队列表
     *
     * @param team 团队信息
     * @return 团队集合
     */
    List<Team> selectTeamList(SearchTeam team);

    /**
     * 新增团队
     *
     * @param team 团队信息
     * @return 团队集合
     */
    int saveTeam(Team team);

    /**
     * 查询团队信息
     *
     * @param teamId 团队ID
     * @return 结果
     */
    Team selectTeamByTeamId(Long teamId);

    /**
     * 查询团队信息
     *
     * @param userId 用户ID
     * @return 结果
     */
    Team selectTeamByUserId(Long userId);

    /**
     * 批量删除团队信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTeamByIds(Long[] ids);

    /**
     * 新增团队与员工关系信息
     *
     * @param teamMiddleEmployee 用户信息
     * @return 结果
     */
    int insertTeamMiddleEmployee(TeamMiddleEmployee teamMiddleEmployee);

    /**
     * 新增团队与团队关系信息
     *
     * @param teamMiddleTeam 用户信息
     * @return 结果
     */
    int insertTeamMiddleTeam(TeamMiddleTeam teamMiddleTeam);

    /**
     * 批量删除团队与员工关系信息
     *
     * @param employeeId 需要删除的数据ID
     * @return 结果
     */
    int deleteTeamMiddleEmployee(@Param("employeeId") Long employeeId, @Param("teamId") Long teamId);

    /**
     * 批量删除团队与团队关系信息
     *
     * @param teamId 需要删除的数据ID
     * @return 结果
     */
    int deleteTeamMiddleTeam(@Param("otherTeamId") Long otherTeamId, @Param("teamId") Long teamId);

    /**
     * 根据userNum查询团队信息
     *
     * @param userNum 用户编号
     * @return 团队信息
     */
    Team selectTeamByUserNum(@Param("userNum") String userNum);

    /**
     * 查询员工与团队关系信息数量
     *
     * @param
     * @return 结果
     */
    int selectCountTeamMiddleEmployeeById(@Param("employeeId") Long employeeId, @Param("teamId") Long teamId);

    /**
     * 查询团队与团队关系信息数量
     *
     * @param
     * @return 结果
     */
    int selectCountTeamMiddleTeamById(@Param("otherTeamId") Long otherTeamId, @Param("teamId") Long teamId);

    /**
     * 根据团队id查询合作团队信息
     *
     * @param teamId 团队id
     * @return 团队信息
     */
    List<Team> selectTeamMiddleTeamById(@Param("teamId") Long teamId);
}

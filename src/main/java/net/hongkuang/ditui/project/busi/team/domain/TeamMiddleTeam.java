package net.hongkuang.ditui.project.busi.team.domain;

import java.util.Date;

/**
 * @author:zy
 * @date: 2019/4/02
 */
public class TeamMiddleTeam {

    private Long id;
    /**
     * 另一团队ID
     */
    private Long otherTeamId;

    /**
     * 团队ID
     */
    private Long teamId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getOtherTeamId() {
        return otherTeamId;
    }

    public void setOtherTeamId(Long otherTeamId) {
        this.otherTeamId = otherTeamId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

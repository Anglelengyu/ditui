package net.hongkuang.ditui.project.busi.manager.domain;

import java.util.Date;

/**
 * @author:zy
 * @date: 2019/3/21
 */
public class ManagerMiddleTeam {

    private Long id;
    /**
     * 掌柜ID
     */
    private Long managerId;

    /**
     * 团队ID
     */
    private Long teamId;

    /**
     * 是否团队推荐掌柜 默认不是
     */
    private String recommend = "0";

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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

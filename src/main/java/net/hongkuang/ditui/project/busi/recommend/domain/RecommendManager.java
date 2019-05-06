package net.hongkuang.ditui.project.busi.recommend.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/3/29
 */
public class RecommendManager extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 推荐人名称
     */
    private String name;

    /**
     * 推荐掌柜
     */
    private List<Manager> managers;

    /**
     * 备注
     */
    private String remark;

    /**
     * 团队ID
     */
    private Long teamId;

    private String managerIds;

    public String getManagerIds() {
        return managerIds;
    }

    public void setManagerIds(String managerIds) {
        this.managerIds = managerIds;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}

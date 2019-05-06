package net.hongkuang.ditui.project.busi.recommend.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;

/**
 * @author:zy
 * @date: 2019/3/29
 */
public class RecommendMiddleManager extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 推荐id
     */
    private Long recommendManagerId;

    /**
     * 掌柜ID
     */
    private Long managerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecommendManagerId() {
        return recommendManagerId;
    }

    public void setRecommendManagerId(Long recommendManagerId) {
        this.recommendManagerId = recommendManagerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}

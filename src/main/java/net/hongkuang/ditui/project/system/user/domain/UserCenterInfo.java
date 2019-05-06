package net.hongkuang.ditui.project.system.user.domain;

/**
 * @ProjectName: ditui
 * @ClassName: UserCenterInfo
 * @Author: YanJie
 * @Description:
 * @Date: 2019/4/21 15:30
 * @Version: 1.0
 */
public class UserCenterInfo extends User {

    /**
     * 邀请链接
     */
    private String inviterUrl;

    /**
     * 可用余额
     */
    private Integer availablePrice;

    /**
     * 冻结余额
     */
    private Integer frozenPrice;


    public String getInviterUrl() {
        return inviterUrl;
    }

    public void setInviterUrl(String inviterUrl) {
        this.inviterUrl = inviterUrl;
    }

    public Integer getAvailablePrice() {
        return availablePrice;
    }

    public void setAvailablePrice(Integer availablePrice) {
        this.availablePrice = availablePrice;
    }

    public Integer getFrozenPrice() {
        return frozenPrice;
    }

    public void setFrozenPrice(Integer frozenPrice) {
        this.frozenPrice = frozenPrice;
    }
}

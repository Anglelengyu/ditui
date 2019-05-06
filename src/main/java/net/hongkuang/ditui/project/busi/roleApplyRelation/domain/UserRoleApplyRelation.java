package net.hongkuang.ditui.project.busi.roleApplyRelation.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;

/**
 * 角色之间的申请关联
 *
 * @author:zy
 * @date: 2019/4/02
 */
public class UserRoleApplyRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * userId
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户编号
     */
    private String userNum;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户类型
     */
    private String userTypeName;

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

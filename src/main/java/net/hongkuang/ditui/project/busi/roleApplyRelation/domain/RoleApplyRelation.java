package net.hongkuang.ditui.project.busi.roleApplyRelation.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;

/**
 * 角色之间的申请关联
 *
 * @author:zy
 * @date: 2019/4/02
 */
public class RoleApplyRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 申请人ID
     */
    private Long activeApplyId;

    /**
     * 被申请人ID
     */
    private Long passiveApplyId;

    /**
     * 申请方 1-掌柜 2-团队 3-员工
     */
    private Integer activeApplyApplicant;

    /**
     * 被申请方 1-掌柜 2-团队 3-员工
     */
    private Integer passiveApplyApplicant;

    /**
     * 帐号状态（0待处理 1同意  2不同意）
     */
    private String status;

    /**
     * 申请人删除标志（0代表存在 2代表删除）
     */
    private String activeApplyDelFlag;

    /**
     * 被申请人删除标志（0代表存在 2代表删除）
     */
    private String passiveApplyDelFlag;

    /**
     * 申请人昵称
     */
    private String activeApplyUserName;

    /**
     * 被申请人昵称
     */
    private String passiveApplyUserName;

    /**
     * 申请备注
     */
    private String applyRemark;


    public String getActiveApplyUserName() {
        return activeApplyUserName;
    }

    public void setActiveApplyUserName(String activeApplyUserName) {
        this.activeApplyUserName = activeApplyUserName;
    }

    public String getPassiveApplyUserName() {
        return passiveApplyUserName;
    }

    public void setPassiveApplyUserName(String passiveApplyUserName) {
        this.passiveApplyUserName = passiveApplyUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActiveApplyId() {
        return activeApplyId;
    }

    public void setActiveApplyId(Long activeApplyId) {
        this.activeApplyId = activeApplyId;
    }

    public Long getPassiveApplyId() {
        return passiveApplyId;
    }

    public void setPassiveApplyId(Long passiveApplyId) {
        this.passiveApplyId = passiveApplyId;
    }

    public Integer getActiveApplyApplicant() {
        return activeApplyApplicant;
    }

    public void setActiveApplyApplicant(Integer activeApplyApplicant) {
        this.activeApplyApplicant = activeApplyApplicant;
    }

    public Integer getPassiveApplyApplicant() {
        return passiveApplyApplicant;
    }

    public void setPassiveApplyApplicant(Integer passiveApplyApplicant) {
        this.passiveApplyApplicant = passiveApplyApplicant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActiveApplyDelFlag() {
        return activeApplyDelFlag;
    }

    public void setActiveApplyDelFlag(String activeApplyDelFlag) {
        this.activeApplyDelFlag = activeApplyDelFlag;
    }

    public String getPassiveApplyDelFlag() {
        return passiveApplyDelFlag;
    }

    public void setPassiveApplyDelFlag(String passiveApplyDelFlag) {
        this.passiveApplyDelFlag = passiveApplyDelFlag;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }
}

package net.hongkuang.ditui.project.userCenter.packageTemplate.domain;


/**
 * 套餐角色关联表 PackageTemplateJoinRole
 *
 * @author yj
 * @date 2019-04-17
 */
public class PackageTemplateJoinRole {

    /**
     *
     */
    private Integer roleId;
    /**
     *
     */
    private Integer packageTemplateId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPackageTemplateId() {
        return packageTemplateId;
    }

    public void setPackageTemplateId(Integer packageTemplateId) {
        this.packageTemplateId = packageTemplateId;
    }
}

package net.hongkuang.ditui.project.userCenter.packageTemplate.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;


/**
 * 套餐模板表 sys_package_template
 *
 * @author yj
 * @date 2019-04-17
 */
public class PackageTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;

    private String name;
    /**
     * 描述
     */
    private String packageDesc;
    /**
     * 充值金额
     */
    private Long price;
    /**
     * 基础币量
     */
    private Long baseCoin;
    /**
     * 赠送币量
     */
    private Long giveCoin;
    /**
     * 排序值
     */
    private Integer idx;
    /**
     * 关联表
     */
    private List<PackageTemplateJoinRole> joinRoleList;

    /**
     * 角色名
     */
    private String roleNames;

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public String getPackageDesc() {
        return packageDesc;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setBaseCoin(Long baseCoin) {
        this.baseCoin = baseCoin;
    }

    public Long getBaseCoin() {
        return baseCoin;
    }

    public void setGiveCoin(Long giveCoin) {
        this.giveCoin = giveCoin;
    }

    public Long getGiveCoin() {
        return giveCoin;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getIdx() {
        return idx;
    }

    public List<PackageTemplateJoinRole> getJoinRoleList() {
        return joinRoleList;
    }

    public void setJoinRoleList(List<PackageTemplateJoinRole> joinRoleList) {
        this.joinRoleList = joinRoleList;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("packageDesc", getPackageDesc())
                .append("price", getPrice())
                .append("baseCoin", getBaseCoin())
                .append("giveCoin", getGiveCoin())
                .append("idx", getIdx())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

package net.hongkuang.ditui.project.busi.salesman.domain;


import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

/**
 * 业务员表 busi_salesman
 *
 * @author yj
 * @date 2018-12-30
 */
public class Salesman extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "业务员编号")
    private Long id;
    /**
     * 业务组长
     */
    @Excel(name = "业务组长")
    private String groupLeaderName;
    /*
    * 业务组长id
    * */
    private Long groupLeaderId;
    /**
     * 业务员姓名
     */
    @Excel(name = "业务员姓名")
    private String name;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;
    /**
     * 头像
     */
    private String head;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密盐
     */
    private String salt;
    /**
     * 所在地区
     */
    @Excel(name = "所在地区")
    private String area;
    /**
     * 佣金
     */
    @Excel(name = "佣金")
    private Long commission;
    /**
     * 本金
     */
    private Long corpus;
    /**
     * 状态1正常0禁用
     */
    @Excel(name = "业务员状态", readConverterExp = "0=正常,1=停用")
    private Integer status;
    /**
     * 逻辑id
     */
    private String saleId;
    /**
     * 扩展
     */
    private String ext1;
    /**
     * 扩展
     */
    private String ext2;
    private Integer orderLimit;

    public Long getGroupLeaderId() {
        return groupLeaderId;
    }

    public void setGroupLeaderId(Long groupLeaderId) {
        this.groupLeaderId = groupLeaderId;
    }

    public Integer getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Long getCommission() {
        return commission;
    }

    public void setCorpus(Long corpus) {
        this.corpus = corpus;
    }

    public Long getCorpus() {
        return corpus;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt2() {
        return ext2;
    }

    public void setGroupLeaderName(String groupLeaderName) {
        this.groupLeaderName = groupLeaderName;
    }

    public String getGroupLeaderName() {
        return groupLeaderName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 生成随机盐
     */
    public void randomSalt() {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("phone", getPhone())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("head", getHead())
                .append("area", getArea())
                .append("commission", getCommission())
                .append("corpus", getCorpus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("saleId", getSaleId())
                .append("ext1", getExt1())
                .append("ext2", getExt2())
                .append("groupLeaderName", getGroupLeaderName())
                .toString();
    }
}

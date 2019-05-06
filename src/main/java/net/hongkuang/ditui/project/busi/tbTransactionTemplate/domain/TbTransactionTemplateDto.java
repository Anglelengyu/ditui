package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 淘宝成交模版
 *
 * @author:zy
 * @date: 2019/4/3
 */
public class TbTransactionTemplateDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /* 模版id */
    private Long id;

    /* 模版简称 */
    private String templateName;

    /* 掌柜ID */
    private Long managerId;

    /* 掌柜昵称 */
    private String managerUserName;

    /* 平台 */
    private String platform;

    /* 指定团队 */
    private Long teamId;

    /* 指定团队名称 */
    private String teamName;

    /* 店铺ID */
    private String shopId;

    /* 店铺名称 */
    private String shopName;

    /* 商品简称 */
    private String goodsName;

    /* 商品ID */
    private String goodsId;

    /* 商品主图 */
    private String goodsImg;

    /* 下单要求 */
    private String orderRequire;

    /* 订单金额 */
    private BigDecimal unitPrice;

    /* 订单金额（元）*/
    private String unitPriceYuan;

    /* 订单佣金 */
    private BigDecimal commissionPrice;

    /* 订单佣金（元）*/
    private Long commissionPriceYuan;

    /* 掌柜要求 */
    private String managerRequire;

    /* 团队的掌柜要求 */
    private String teamToManagerRequire;

    /* 团队要求 */
    private String teamRequire;

    /* 放单方式 （1-线上立返 2-线上货返 3-地推立返）*/
    private Integer executeMethod;

    /**
     * 放单方式备注
     */
    private String executeMethodRemark;

    /* 状态（0-待提交1-待审核2-审核通过3-审核未通过）*/
    private Integer status;

    /* 是否向买家提问 0-否 1-是*/
    private Integer question;

    /* 问题关联ID */
    private Long questionId;

    /* 是否启动订单跟踪系统 （0-否 1-是）*/
    private Integer orderTrack;

    /* 是否启动返利识别系统（0-否 1-是） */
    private Integer orderRebate;

    /* 总单量 */
    private Integer totalNumber;

    /* 总本金 */
    private BigDecimal totalUnitPrice;

    /* 总本金 */
    private String totalUnitPriceYuan;

    /* 总佣金 */
    private BigDecimal totalCommissionPrice;

    /* 总佣金 */
    private String totalCommissionPriceYuan;

    /* 组单分类 */
    private String groupClassifys;

    /* 进店方式（1-关键词 2-链接 3-浏览）*/
    private Integer goShopWay;

    /* 团队备注 */
    private String teamRemark;

    /* 掌柜备注 */
    private String managerRemark;

    /* 问题关联 */
    private TbTransactionQuestion tbTransactionQuestion;

    /* 关键词列表 */
    private List<TbTransactionKeyWords> tbTransactionKeyWords;


    public String getExecuteMethodRemark() {
        return executeMethodRemark;
    }

    public void setExecuteMethodRemark(String executeMethodRemark) {
        this.executeMethodRemark = executeMethodRemark;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTeamToManagerRequire() {
        return teamToManagerRequire;
    }

    public void setTeamToManagerRequire(String teamToManagerRequire) {
        this.teamToManagerRequire = teamToManagerRequire;
    }

    public String getTeamRequire() {
        return teamRequire;
    }

    public void setTeamRequire(String teamRequire) {
        this.teamRequire = teamRequire;
    }

    public String getTotalUnitPriceYuan() {
        return totalUnitPriceYuan;
    }

    public void setTotalUnitPriceYuan(String totalUnitPriceYuan) {
        this.totalUnitPriceYuan = totalUnitPriceYuan;
    }

    public String getTotalCommissionPriceYuan() {
        return totalCommissionPriceYuan;
    }

    public void setTotalCommissionPriceYuan(String totalCommissionPriceYuan) {
        this.totalCommissionPriceYuan = totalCommissionPriceYuan;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getManagerRemark() {
        return managerRemark;
    }

    public void setManagerRemark(String managerRemark) {
        this.managerRemark = managerRemark;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTeamRemark() {
        return teamRemark;
    }

    public void setTeamRemark(String teamRemark) {
        this.teamRemark = teamRemark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TbTransactionQuestion getTbTransactionQuestion() {
        return tbTransactionQuestion;
    }

    public void setTbTransactionQuestion(TbTransactionQuestion tbTransactionQuestion) {
        this.tbTransactionQuestion = tbTransactionQuestion;
    }

    public List<TbTransactionKeyWords> getTbTransactionKeyWords() {
        return tbTransactionKeyWords;
    }

    public void setTbTransactionKeyWords(List<TbTransactionKeyWords> tbTransactionKeyWords) {
        this.tbTransactionKeyWords = tbTransactionKeyWords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerUserName() {
        return managerUserName;
    }

    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getOrderRequire() {
        return orderRequire;
    }

    public void setOrderRequire(String orderRequire) {
        this.orderRequire = orderRequire;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceYuan() {
        return unitPriceYuan;
    }

    public void setUnitPriceYuan(String unitPriceYuan) {
        this.unitPriceYuan = unitPriceYuan;
    }

    public BigDecimal getCommissionPrice() {
        return commissionPrice;
    }

    public void setCommissionPrice(BigDecimal commissionPrice) {
        this.commissionPrice = commissionPrice;
    }

    public void setTotalUnitPrice(BigDecimal totalUnitPrice) {
        this.totalUnitPrice = totalUnitPrice;
    }

    public void setTotalCommissionPrice(BigDecimal totalCommissionPrice) {
        this.totalCommissionPrice = totalCommissionPrice;
    }

    public Long getCommissionPriceYuan() {
        return commissionPriceYuan;
    }

    public void setCommissionPriceYuan(Long commissionPriceYuan) {
        this.commissionPriceYuan = commissionPriceYuan;
    }

    public String getManagerRequire() {
        return managerRequire;
    }

    public void setManagerRequire(String managerRequire) {
        this.managerRequire = managerRequire;
    }

    public Integer getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(Integer executeMethod) {
        this.executeMethod = executeMethod;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getOrderTrack() {
        return orderTrack;
    }

    public void setOrderTrack(Integer orderTrack) {
        this.orderTrack = orderTrack;
    }

    public Integer getOrderRebate() {
        return orderRebate;
    }

    public void setOrderRebate(Integer orderRebate) {
        this.orderRebate = orderRebate;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public BigDecimal getTotalUnitPrice() {
        return totalUnitPrice;
    }

    public BigDecimal getTotalCommissionPrice() {
        return totalCommissionPrice;
    }

    public String getGroupClassifys() {
        return groupClassifys;
    }

    public void setGroupClassifys(String groupClassifys) {
        this.groupClassifys = groupClassifys;
    }

    public Integer getGoShopWay() {
        return goShopWay;
    }

    public void setGoShopWay(Integer goShopWay) {
        this.goShopWay = goShopWay;
    }
}

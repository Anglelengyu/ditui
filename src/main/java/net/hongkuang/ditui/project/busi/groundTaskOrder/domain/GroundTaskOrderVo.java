package net.hongkuang.ditui.project.busi.groundTaskOrder.domain;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionBuyerRequire;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;

/**
 * 淘宝任务订单
 *
 * @author:zy
 * @date: 2019/4/24
 */
public class GroundTaskOrderVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Excel(name = "订单ID")
    private Long id;
    /**
     * 逻辑id
     */
    private String orderId;

    /**
     * 掌柜ID
     */
    @Excel(name = "掌柜ID")
    private Long managerId;

    /**
     * 掌柜昵称
     */
    @Excel(name = "掌柜昵称")
    private String managerUserName;

    /**
     * 平台
     */
    @Excel(name = "平台")
    private String platform;

    /**
     * 指定团队
     */
    @Excel(name = "团队ID")
    private Long teamId;

    /**
     * 指定团队名称
     */
    @Excel(name = "团队昵称")
    private String teamName;

    /**
     * 接单团队
     */
    private Long receiptTeamId;

    /**
     * 接单团队名称
     */
    private String receiptTeamName;

    /**
     * 店铺ID
     */
    @Excel(name = "店铺ID")
    private String shopId;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String shopName;

    /**
     * 商品简称
     */
    @Excel(name = "商品简称")
    private String goodsName;

    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    private String goodsId;

    /**
     * 商品主图
     */
    private String goodsImg;

    /**
     * 关键词名称
     */
    @Excel(name = "关键词名称")
    private String keyWordName;

    /**
     * 手机端
     */
    private Integer hasApp;

    /**
     * 电脑端
     */
    private Integer hasPc;

    /**
     * 收藏
     */
    private Integer hasCollection;

    /**
     * 加购
     */
    private Integer hasCart;

    /**
     * 收藏+加购
     */
    private Integer hasCollectionCart;

    /**
     * 下单要求
     */
    @Excel(name = "下单要求")
    private String orderRequire;

    /**
     * 订单金额
     */
    private Long unitPrice;

    /**
     * 订单金额（元）
     */
    @Excel(name = "订单金额")
    private String unitPriceYuan;

    /**
     * 订单佣金
     */
    private Long commissionPrice;

    /**
     * 订单佣金（元）
     */
    @Excel(name = "订单佣金")
    private String commissionPriceYuan;

    /* 掌柜要求 */
    private String managerRequire;

    /* 团队的掌柜要求 */
    private String teamToManagerRequire;

    /* 团队要求 */
    private String teamRequire;

    /**
     * 放单方式 （1-线上立返 2-线上货返 3-地推立返）
     */
    private Integer executeMethod;

    /**
     * 状态（0-未接单1-已接单 2-已完成 4-订单取消）
     */
    private Integer status;

    /**
     * 状态0-待生成 1-未分配 2-已分配 3-历史
     */
    private Integer allocatStatus;

    private Integer reckonAllocatStatus;

    /**
     * 是否向买家提问 0-否 1-是
     */
    private Integer question;

    /**
     * 问题关联ID
     */
    private Long questionId;

    /**
     * 是否启动订单跟踪系统 （0-否 1-是）
     */
    private Integer orderTrack;

    /**
     * 是否启动返利识别系统（0-否 1-是）
     */
    private Integer orderRebate;

    /**
     * 组单分类
     */
    private String groupClassifys;

    /**
     * 进店方式（1-关键词 2-链接 3-浏览）
     */
    private Integer goShopWay;

    /**
     * 结算方式 1-立返本佣 2-立佣货本 3-货返本佣
     */
    private Integer settlementWay;

    /**
     * 团队备注
     */
    private String teamRemark;

    /**
     * 掌柜备注
     */
    private String managerRemark;

    /**
     * 实际支付金额
     */
    private Long payment;

    /**
     * 实际支付金额（元）
     */
    private String paymentYuan;

    /**
     * 淘宝订单编号
     */
    private String orderNo;

    /**
     * 买家ID
     */
    private String buyerNick;
    private String createBy;
    private String updateBy;

    /**
     * 买家要求ID
     */
    private Long buyerRequireId;

    /**
     * 问题关联
     */
    private TbTransactionQuestion tbTransactionQuestion;

    /**
     * 买家要求
     */
    private TbTransactionBuyerRequire tbTransactionBuyerRequire;

    /** extend 扩展字段*/
    /**
     * 任务ID
     */
    @Excel(name = "任务ID")
    private String taskId;

    /**
     * 任务开始时间
     */
    private String realTimeStart;

    /**
     * 任务结束时间
     */
    private String realTimeEnd;

    private Integer allotType;

    /**
     * 资料提交时间
     */
    private String referTime;

    public String getReferTime() {
        return referTime;
    }

    public void setReferTime(String referTime) {
        this.referTime = referTime;
    }

    public Integer getAllotType() {
        return allotType;
    }

    public void setAllotType(Integer allotType) {
        this.allotType = allotType;
    }

    public String getRealTimeStart() {
        return realTimeStart;
    }

    public void setRealTimeStart(String realTimeStart) {
        this.realTimeStart = realTimeStart;
    }

    public String getRealTimeEnd() {
        return realTimeEnd;
    }

    public void setRealTimeEnd(String realTimeEnd) {
        this.realTimeEnd = realTimeEnd;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getReckonAllocatStatus() {
        return reckonAllocatStatus;
    }

    public void setReckonAllocatStatus(Integer reckonAllocatStatus) {
        this.reckonAllocatStatus = reckonAllocatStatus;
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

    public Long getBuyerRequireId() {
        return buyerRequireId;
    }

    public void setBuyerRequireId(Long buyerRequireId) {
        this.buyerRequireId = buyerRequireId;
    }

    public Long getReceiptTeamId() {
        return receiptTeamId;
    }

    public void setReceiptTeamId(Long receiptTeamId) {
        this.receiptTeamId = receiptTeamId;
    }

    public String getReceiptTeamName() {
        return receiptTeamName;
    }

    public void setReceiptTeamName(String receiptTeamName) {
        this.receiptTeamName = receiptTeamName;
    }

    public TbTransactionBuyerRequire getTbTransactionBuyerRequire() {
        return tbTransactionBuyerRequire;
    }

    public void setTbTransactionBuyerRequire(TbTransactionBuyerRequire tbTransactionBuyerRequire) {
        this.tbTransactionBuyerRequire = tbTransactionBuyerRequire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getKeyWordName() {
        return keyWordName;
    }

    public void setKeyWordName(String keyWordName) {
        this.keyWordName = keyWordName;
    }

    public Integer getHasApp() {
        return hasApp;
    }

    public void setHasApp(Integer hasApp) {
        this.hasApp = hasApp;
    }

    public Integer getHasPc() {
        return hasPc;
    }

    public void setHasPc(Integer hasPc) {
        this.hasPc = hasPc;
    }

    public Integer getHasCollection() {
        return hasCollection;
    }

    public void setHasCollection(Integer hasCollection) {
        this.hasCollection = hasCollection;
    }

    public Integer getHasCart() {
        return hasCart;
    }

    public void setHasCart(Integer hasCart) {
        this.hasCart = hasCart;
    }

    public Integer getHasCollectionCart() {
        return hasCollectionCart;
    }

    public void setHasCollectionCart(Integer hasCollectionCart) {
        this.hasCollectionCart = hasCollectionCart;
    }

    public String getOrderRequire() {
        return orderRequire;
    }

    public void setOrderRequire(String orderRequire) {
        this.orderRequire = orderRequire;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceYuan() {
        return unitPriceYuan;
    }

    public void setUnitPriceYuan(String unitPriceYuan) {
        this.unitPriceYuan = unitPriceYuan;
    }

    public Long getCommissionPrice() {
        return commissionPrice;
    }

    public void setCommissionPrice(Long commissionPrice) {
        this.commissionPrice = commissionPrice;
    }

    public String getCommissionPriceYuan() {
        return commissionPriceYuan;
    }

    public void setCommissionPriceYuan(String commissionPriceYuan) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(Integer settlementWay) {
        this.settlementWay = settlementWay;
    }

    public String getTeamRemark() {
        return teamRemark;
    }

    public void setTeamRemark(String teamRemark) {
        this.teamRemark = teamRemark;
    }

    public String getManagerRemark() {
        return managerRemark;
    }

    public void setManagerRemark(String managerRemark) {
        this.managerRemark = managerRemark;
    }

    public TbTransactionQuestion getTbTransactionQuestion() {
        return tbTransactionQuestion;
    }

    public void setTbTransactionQuestion(TbTransactionQuestion tbTransactionQuestion) {
        this.tbTransactionQuestion = tbTransactionQuestion;
    }

    public Integer getAllocatStatus() {
        return allocatStatus;
    }

    public void setAllocatStatus(Integer allocatStatus) {
        this.allocatStatus = allocatStatus;
    }

    public Long getPayment() {
        return payment;
    }

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public String getPaymentYuan() {
        return paymentYuan;
    }

    public void setPaymentYuan(String paymentYuan) {
        this.paymentYuan = paymentYuan;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}

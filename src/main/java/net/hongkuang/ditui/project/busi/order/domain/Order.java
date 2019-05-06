package net.hongkuang.ditui.project.busi.order.domain;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;

import java.util.Date;


/**
 * 订单表 busi_order
 *
 * @author yj
 * @date 2018-12-30
 */
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    private String orderId;
    /**
     * 类目
     */
    @Excel(name = "类目名称")
    private String category;
    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    private String shopName;
    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String goodsName;
    /**
     * 关键词
     */
    @Excel(name = "关键字")
    private String keyWord;
    /**
     * 备注
     */
    @Excel(name = "下单选项")
    private String remarks;
    /**
     * 订单图片
     */
    private String orderImg;
    /**
     * 商品本金
     */
    private Long unitPrice;
    @Excel(name = "商品本金")
    private String unitPriceYuan;
    /**
     * 商品佣金
     */
    private Long commission;
    @Excel(name = "商品佣金")
    private String commissionYuan;
    /**
     * 状态1未完成2已完成3已接单4订单取消
     */
    private Integer status;
    /**
     * 状态1未分配2已分配3历史
     */
    private Integer allocatStatus;
    /**
     * 计算使用状态1未分配2已分配3历史
     */
    private Integer reckonAllocatStatus;
    /**  */
    private String ext1;
    /**  */
    private String ext2;
    /**
     * 实际支付金额
     */
    private Long payment;
    @Excel(name = "实际支付金额")
    private String paymentYuan;
    /**
     * 淘宝订单编号
     */
    @Excel(name = "订单编号")
    private String orderNo;
    /**
     * 买家ID
     */
    @Excel(name = "买家ID")
    private String buyerNick;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;


    /** extend 扩展字段*/
    /**
     * 任务ID
     */
    @Excel(name = "任务ID")
    private String taskId;
    @Excel(name = "业务员姓名")
    private String saleName;
    @Excel(name = "业务员所在地区")
    private String saleArea;
    private Long saleCommission;
    @Excel(name = "业务员佣金")
    private String saleCommissionYuan;
    /**
     * 任务完成时间
     */
    private Date taskCompletionTime;

    @Excel(name = "订单标注")
    private String markStatusStr;
    /**
     * 订单标注状态
     */
    private Integer markStatus;
    @Excel(name = "订单标注内容")
    /** 订单标注内容*/
    private String markComment;


    public Integer getReckonAllocatStatus() {
        return reckonAllocatStatus;
    }

    public void setReckonAllocatStatus(Integer reckonAllocatStatus) {
        this.reckonAllocatStatus = reckonAllocatStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setOrderImg(String orderImg) {
        this.orderImg = orderImg;
    }

    public String getOrderImg() {
        return orderImg;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }

    public Long getCommission() {
        return commission;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
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

    public void setPayment(Long payment) {
        this.payment = payment;
    }

    public Long getPayment() {
        return payment;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUnitPriceYuan() {
        return unitPriceYuan;
    }

    public void setUnitPriceYuan(String unitPriceYuan) {
        this.unitPriceYuan = unitPriceYuan;
    }

    public String getCommissionYuan() {
        return commissionYuan;
    }

    public void setCommissionYuan(String commissionYuan) {
        this.commissionYuan = commissionYuan;
    }

    public Integer getAllocatStatus() {
        return allocatStatus;
    }

    public void setAllocatStatus(Integer allocatStatus) {
        this.allocatStatus = allocatStatus;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    public Long getSaleCommission() {
        return saleCommission;
    }

    public void setSaleCommission(Long saleCommission) {
        this.saleCommission = saleCommission;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getTaskCompletionTime() {
        return taskCompletionTime;
    }

    public void setTaskCompletionTime(Date taskCompletionTime) {
        this.taskCompletionTime = taskCompletionTime;
    }

    public Integer getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(Integer markStatus) {
        this.markStatus = markStatus;
    }

    public String getMarkComment() {
        return markComment;
    }

    public void setMarkComment(String markComment) {
        this.markComment = markComment;
    }

    public String getPaymentYuan() {
        return paymentYuan;
    }

    public void setPaymentYuan(String paymentYuan) {
        this.paymentYuan = paymentYuan;
    }

    public String getSaleCommissionYuan() {
        return saleCommissionYuan;
    }

    public void setSaleCommissionYuan(String saleCommissionYuan) {
        this.saleCommissionYuan = saleCommissionYuan;
    }

    public String getMarkStatusStr() {
        return markStatusStr;
    }

    public void setMarkStatusStr(String markStatusStr) {
        this.markStatusStr = markStatusStr;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", category='" + category + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", remarks='" + remarks + '\'' +
                ", orderImg='" + orderImg + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitPriceYuan='" + unitPriceYuan + '\'' +
                ", commission=" + commission +
                ", commissionYuan='" + commissionYuan + '\'' +
                ", status=" + status +
                ", allocatStatus=" + allocatStatus +
                ", ext1='" + ext1 + '\'' +
                ", ext2='" + ext2 + '\'' +
                ", payment=" + payment +
                ", orderNo='" + orderNo + '\'' +
                ", buyerNick='" + buyerNick + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", saleName='" + saleName + '\'' +
                ", saleArea='" + saleArea + '\'' +
                ", saleCommission=" + saleCommission +
                ", taskId='" + taskId + '\'' +
                ", taskCompletionTime=" + taskCompletionTime +
                ", markStatus=" + markStatus +
                ", markComment='" + markComment + '\'' +
                '}';
    }
}

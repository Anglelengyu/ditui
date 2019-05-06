package net.hongkuang.ditui.project.busi.task.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.busi.order.domain.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 任务表 busi_task
 *
 * @author yj
 * @date 2018-12-30
 */
public class Task extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    private String taskId;
    /**
     * 销售人员id
     */
    private String saleId;
    /**
     * 订单数量
     */
    private Integer orderNum;
    /**
     * 任务本金
     */
    private Long taskCommission;
    /**
     * 状态1未分配2已分配
     */
    private Integer status;
    /**  */
    private String ext1;
    /**  */
    private String ext2;
    /**  */
    private String createBy;
    /**  */
    private String updateBy;
    private Integer taskStatus;
    /**
     * 任务本金
     */
    private Long taskCorpus;

    private String goodsName;

    private BigDecimal lng;
    private BigDecimal lat;

    private Date completionTime;

    private Integer allotType;
    private String realTimeStart;
    private String realTimeEnd;


    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getTaskCorpus() {
        return taskCorpus;
    }

    public void setTaskCorpus(Long taskCorpus) {
        this.taskCorpus = taskCorpus;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setTaskCommission(Long taskCommission) {
        this.taskCommission = taskCommission;
    }

    public Long getTaskCommission() {
        return taskCommission;
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

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", saleId='" + saleId + '\'' +
                ", orderNum=" + orderNum +
                ", taskCommission=" + taskCommission +
                ", status=" + status +
                ", ext1='" + ext1 + '\'' +
                ", ext2='" + ext2 + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskCorpus=" + taskCorpus +
                ", goodsName='" + goodsName + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", completionTime=" + completionTime +
                ", allotType=" + allotType +
                ", realTimeStart='" + realTimeStart + '\'' +
                ", realTimeEnd='" + realTimeEnd + '\'' +
                ", orders=" + orders +
                '}';
    }
}

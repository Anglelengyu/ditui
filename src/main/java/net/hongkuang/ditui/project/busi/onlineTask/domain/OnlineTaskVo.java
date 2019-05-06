package net.hongkuang.ditui.project.busi.onlineTask.domain;

import net.hongkuang.ditui.framework.aspectj.lang.annotation.Excel;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import net.hongkuang.ditui.project.busi.order.domain.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OnlineTaskVo extends BaseEntity {
    @Excel(name = "员工姓名")
    private String employeeName;
    @Excel(name = "所在地区")
    private String employeeArea;
    /**
     * 员工手机号
     */
    private String employeePhone;
    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    @Excel(name = "任务编号")
    private String taskId;
    @Excel(name = "分配方式", readConverterExp = "1=实时任务,2=储备任务")
    private Integer allotType;
    @Excel(name = "商品名称")
    private String goodsName;
    @Excel(name = "组单类型")
    private String groupClassifys;
    private String platform;
    /**
     * 任务本金
     */
    private Long taskCorpus;
    /**
     * 任务本金
     */
    @Excel(name = "任务本金")
    private String taskCorpusYuan;
    /**
     * 员工id
     */
    private String employeeId;
    /**
     * 订单数量
     */
    @Excel(name = "订单数量")
    private Integer orderNum;
    /**
     * 任务佣金
     */
    private Long taskCommissionPrice;
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
    @Excel(name = "任务完成时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date completionTime;
    @Excel(name = "订单状态", readConverterExp = "1=未接单,2=已接单,3=取消中,4=已完成")
    private Integer taskStatus;


    private BigDecimal lng;
    private BigDecimal lat;


    private String realTimeStart;
    private String realTimeEnd;

    /**
     * 团队备注
     */
    private String teamRemark;

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getTeamRemark() {
        return teamRemark;
    }

    public void setTeamRemark(String teamRemark) {
        this.teamRemark = teamRemark;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGroupClassifys() {
        return groupClassifys;
    }

    public void setGroupClassifys(String groupClassifys) {
        this.groupClassifys = groupClassifys;
    }

    public String getTaskCorpusYuan() {
        return taskCorpusYuan;
    }

    public void setTaskCorpusYuan(String taskCorpusYuan) {
        this.taskCorpusYuan = taskCorpusYuan;
    }

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

    public Long getTaskCommissionPrice() {
        return taskCommissionPrice;
    }

    public void setTaskCommissionPrice(Long taskCommissionPrice) {
        this.taskCommissionPrice = taskCommissionPrice;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeArea() {
        return employeeArea;
    }

    public void setEmployeeArea(String employeeArea) {
        this.employeeArea = employeeArea;
    }


    @Override
    public String toString() {
        return "TaskVo{" +
                "employeeName='" + employeeName + '\'' +
                ", employeeArea='" + employeeArea + '\'' +
                ", id=" + id +
                ", taskId='" + taskId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", orderNum=" + orderNum +
                ", taskCommissionPrice=" + taskCommissionPrice +
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

package net.hongkuang.ditui.project.busi.employee.domain;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/3/22
 */
public class SearchEmployee extends Employee {
    private String name;
    private String startTime;
    private String endTime;

    private List<String> shopIdList;
    private Integer hasExtend;

    private String taskCompletionStartTime;
    private String taskCompletionEndTime;

    private List<String> sortGoodsNameList;

    private Long managerId;

    private Long teamId;

    /**
     * 员工类型(online_employee 线上, ground_employee 地推)
     *
     * @return
     */
    private String employeeKey;

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getEmployeeKey() {
        return employeeKey;
    }

    public void setEmployeeKey(String employeeKey) {
        this.employeeKey = employeeKey;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getHasExtend() {
        return hasExtend;
    }

    public void setHasExtend(Integer hasExtend) {
        this.hasExtend = hasExtend;
    }

    public List<String> getShopIdList() {
        return shopIdList;
    }

    public void setShopIdList(List<String> shopIdList) {
        this.shopIdList = shopIdList;
    }

    public String getTaskCompletionStartTime() {
        return taskCompletionStartTime;
    }

    public void setTaskCompletionStartTime(String taskCompletionStartTime) {
        this.taskCompletionStartTime = taskCompletionStartTime;
    }

    public String getTaskCompletionEndTime() {
        return taskCompletionEndTime;
    }

    public void setTaskCompletionEndTime(String taskCompletionEndTime) {
        this.taskCompletionEndTime = taskCompletionEndTime;
    }

    public List<String> getSortGoodsNameList() {
        return sortGoodsNameList;
    }

    public void setSortGoodsNameList(List<String> sortGoodsNameList) {
        this.sortGoodsNameList = sortGoodsNameList;
    }


    @Override
    public String toString() {
        return "SearchOrder{" +
                "name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", shopIdList=" + shopIdList +
                ", hasExtend=" + hasExtend +
                ", taskCompletionStartTime='" + taskCompletionStartTime + '\'' +
                ", taskCompletionEndTime='" + taskCompletionEndTime + '\'' +
                ", sortGoodsNameList=" + sortGoodsNameList +
                '}';
    }
}

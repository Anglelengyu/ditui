package net.hongkuang.ditui.project.busi.team.domain;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/3/22
 */
public class SearchTeam extends Team {
    private String name;
    private String startTime;
    private String endTime;

    private List<String> shopIdList;
    private Integer hasExtend;

    private String taskCompletionStartTime;
    private String taskCompletionEndTime;

    private List<String> sortGoodsNameList;

    private Long managerId;

    private Long employeeId;

    private Long teamId;

    private String recommend;

    private Long otherTeamId;


    public Long getOtherTeamId() {
        return otherTeamId;
    }

    public void setOtherTeamId(Long otherTeamId) {
        this.otherTeamId = otherTeamId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
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

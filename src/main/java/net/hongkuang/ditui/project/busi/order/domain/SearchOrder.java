package net.hongkuang.ditui.project.busi.order.domain;

import java.util.List;

/**
 * Created by apple on 2019/1/1.
 */
public class SearchOrder extends Order {
    private String name;
    private String startTime;
    private String endTime;

    private List<String> shopIdList;
    private Integer hasExtend;

    private String taskCompletionStartTime;
    private String taskCompletionEndTime;

    private List<String> sortGoodsNameList;

    private Long shopManagerId;

    private Long salesmanId;

    public Long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
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

    public Long getShopManagerId() {
        return shopManagerId;
    }

    public void setShopManagerId(Long shopManagerId) {
        this.shopManagerId = shopManagerId;
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
                ", shopManagerId=" + shopManagerId +
                '}';
    }
}

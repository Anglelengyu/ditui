package net.hongkuang.ditui.project.busi.orderTemplate.domain;

import net.hongkuang.ditui.project.busi.order.domain.Order;

import java.util.List;

/**
 * @author:zy
 * @date: 2019/2/25
 */
public class SearchOrderTemplate extends Order {
    private String name;
    private String startTime;
    private String endTime;

    private List<String> shopIdList;
    private Integer hasExtend;

    private String taskCompletionStartTime;
    private String taskCompletionEndTime;

    private List<String> sortGoodsNameList;

    private Long shopManagerId;

    private Integer shopPlatform;


    public Integer getShopPlatform() {
        return shopPlatform;
    }

    public void setShopPlatform(Integer shopPlatform) {
        this.shopPlatform = shopPlatform;
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

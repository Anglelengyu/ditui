package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

/**
 * @author:zy
 * @date: 2019/4/4
 */
public class SearchTbTransactionKeyWords extends TbTransactionKeyWords {
    private String name;
    private String startTime;
    private String endTime;


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
}

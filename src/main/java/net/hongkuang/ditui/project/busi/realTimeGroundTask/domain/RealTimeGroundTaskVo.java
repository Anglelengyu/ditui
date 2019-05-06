package net.hongkuang.ditui.project.busi.realTimeGroundTask.domain;

/**
 * @author:zy
 * @date: 2019/4/22 0022
 */
public class RealTimeGroundTaskVo {

    private String startTime;

    private String stopTime;

    /**
     * 今日单量
     */
    private String todayNum;

    /**
     * 未完成单量
     */
    private String unFinishNum;

    public String getUnFinishNum() {
        return unFinishNum;
    }

    public void setUnFinishNum(String unFinishNum) {
        this.unFinishNum = unFinishNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(String todayNum) {
        this.todayNum = todayNum;
    }
}

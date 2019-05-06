package net.hongkuang.ditui.project.busi.order.dto;

/**
 * Created by apple on 2019/1/2.
 */
public class GenTaskDto {
    // 生成匹配方式
    private Integer genType;
    //任务数量
    private Integer genTaskNum;
    //剩余订单
    private Integer genGsurplusNum;
    //团队ID
    private Long teamId;
    private String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Integer getGenType() {
        return genType;
    }

    public void setGenType(Integer genType) {
        this.genType = genType;
    }

    public Integer getGenTaskNum() {
        return genTaskNum;
    }

    public void setGenTaskNum(Integer genTaskNum) {
        this.genTaskNum = genTaskNum;
    }

    public Integer getGenGsurplusNum() {
        return genGsurplusNum;
    }

    public void setGenGsurplusNum(Integer genGsurplusNum) {
        this.genGsurplusNum = genGsurplusNum;
    }

    @Override
    public String toString() {
        return "GenTaskDto{" +
                "genType=" + genType +
                '}';
    }
}

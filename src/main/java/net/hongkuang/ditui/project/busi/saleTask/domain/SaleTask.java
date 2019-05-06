package net.hongkuang.ditui.project.busi.saleTask.domain;

import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 业务员任务表 busi_sale_task
 *
 * @author yj
 * @date 2019-01-11
 */
public class SaleTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**  */
    private String saleTaskId;
    /**  */
    private String saleId;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 状态1已完成2未完成3取消中4已取消
     */
    private Integer status;
    /**  */
    private String ext1;
    /**  */
    private String ext2;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSaleTaskId(String saleTaskId) {
        this.saleTaskId = saleTaskId;
    }

    public String getSaleTaskId() {
        return saleTaskId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("saleTaskId", getSaleTaskId())
                .append("saleId", getSaleId())
                .append("taskId", getTaskId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("ext1", getExt1())
                .append("ext2", getExt2())
                .toString();
    }

    public static SaleTask build(String taskId, String userId) {
        SaleTask saleTask = new SaleTask();
        saleTask.setSaleTaskId(RandomUtil.genString());
        saleTask.setSaleId(userId);
        saleTask.setTaskId(taskId);
        saleTask.setStatus(1);
        return saleTask;
    }
}

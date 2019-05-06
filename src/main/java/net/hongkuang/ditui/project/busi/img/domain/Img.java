package net.hongkuang.ditui.project.busi.img.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 移动端上传图片表 busi_img
 *
 * @author yj
 * @date 2019-01-09
 */
public class Img extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 逻辑id
     */
    private String imgId;
    /**  */
    private String path;
    /**
     * 图片名称
     */
    private String name;
    /**
     * 关联外键id
     */
    private String taskId;
    /**  */
    /**
     * 状态1正常0禁用
     */
    private Integer status;
    /**  */
    private String ext1;
    /**  */
    private String ext2;
    /**  */
    /**  */
    private String createBy;
    /**  */
    private String updateBy;
    /**
     * 4其他图片1评价图片2收货地址3场景图片
     */
    private Integer imgIndex;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getImgId() {
        return imgId;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public void setImgIndex(Integer imgIndex) {
        this.imgIndex = imgIndex;
    }

    public Integer getImgIndex() {
        return imgIndex;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("imgId", getImgId())
                .append("path", getPath())
                .append("name", getName())
                .append("taskId", getTaskId())
                .append("createTime", getCreateTime())
                .append("status", getStatus())
                .append("ext1", getExt1())
                .append("ext2", getExt2())
                .append("updateTime", getUpdateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("imgIndex", getImgIndex())
                .toString();
    }
}

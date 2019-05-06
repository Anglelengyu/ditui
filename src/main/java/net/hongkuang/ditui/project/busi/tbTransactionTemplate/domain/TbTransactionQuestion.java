package net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;

/**
 * 淘宝问题
 *
 * @author:zy
 * @date: 2019/4/3
 */
public class TbTransactionQuestion extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 主图视频
     */
    private String mainVideo;

    /**
     * 主图浏览
     */
    private String mainBrowse;

    /**
     * 详情视频
     */
    private String detailVideo;

    /**
     * 详情浏览
     */
    private String detailBrowse;

    /**
     * 评价浏览
     */
    private String evaluateBrowse;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainVideo() {
        return mainVideo;
    }

    public void setMainVideo(String mainVideo) {
        this.mainVideo = mainVideo;
    }

    public String getMainBrowse() {
        return mainBrowse;
    }

    public void setMainBrowse(String mainBrowse) {
        this.mainBrowse = mainBrowse;
    }

    public String getDetailVideo() {
        return detailVideo;
    }

    public void setDetailVideo(String detailVideo) {
        this.detailVideo = detailVideo;
    }

    public String getDetailBrowse() {
        return detailBrowse;
    }

    public void setDetailBrowse(String detailBrowse) {
        this.detailBrowse = detailBrowse;
    }

    public String getEvaluateBrowse() {
        return evaluateBrowse;
    }

    public void setEvaluateBrowse(String evaluateBrowse) {
        this.evaluateBrowse = evaluateBrowse;
    }
}

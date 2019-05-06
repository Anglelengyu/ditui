package net.hongkuang.ditui.project.busi.img.service;

import net.hongkuang.ditui.project.busi.img.domain.Img;

import java.util.List;

/**
 * 移动端上传图片 服务层
 *
 * @author yj
 * @date 2019-01-09
 */
public interface IImgService {
    /**
     * 查询移动端上传图片信息
     *
     * @param id 移动端上传图片ID
     * @return 移动端上传图片信息
     */
    Img selectImgById(Long id);

    /**
     * 查询移动端上传图片列表
     *
     * @param img 移动端上传图片信息
     * @return 移动端上传图片集合
     */
    List<Img> selectImgList(Img img);

    /**
     * 新增移动端上传图片
     *
     * @param img 移动端上传图片信息
     * @return 结果
     */
    int insertImg(Img img);

    /**
     * 修改移动端上传图片
     *
     * @param img 移动端上传图片信息
     * @return 结果
     */
    int updateImg(Img img);

    /**
     * 删除移动端上传图片信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteImgByIds(String ids);

    /**
     * 根据任务名称获取图片信息
     *
     * @param taskId
     * @return
     */
    List<Img> selectByTaskId(final String taskId);

}

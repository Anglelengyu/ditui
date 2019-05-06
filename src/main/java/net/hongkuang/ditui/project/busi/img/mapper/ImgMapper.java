package net.hongkuang.ditui.project.busi.img.mapper;

import net.hongkuang.ditui.project.busi.img.domain.Img;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 移动端上传图片 数据层
 *
 * @author yj
 * @date 2019-01-09
 */
@Repository
public interface ImgMapper {
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
     * 删除移动端上传图片
     *
     * @param id 移动端上传图片ID
     * @return 结果
     */
    int deleteImgById(Long id);

    /**
     * 批量删除移动端上传图片
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteImgByIds(String[] ids);

    /**
     * 根据任务获取图片信息
     *
     * @param taskId
     * @return
     */
    List<Img> selectByTaskId(String taskId);

    void insertBatchImg(List<Img> imgs);
}
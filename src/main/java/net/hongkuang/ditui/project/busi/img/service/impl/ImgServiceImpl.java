package net.hongkuang.ditui.project.busi.img.service.impl;

import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.mapper.ImgMapper;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 移动端上传图片 服务层实现
 *
 * @author yj
 * @date 2019-01-09
 */
@Service
public class ImgServiceImpl implements IImgService {
    @Autowired
    private ImgMapper imgMapper;

    /**
     * 查询移动端上传图片信息
     *
     * @param id 移动端上传图片ID
     * @return 移动端上传图片信息
     */
    @Override
    public Img selectImgById(Long id) {
        return imgMapper.selectImgById(id);
    }

    /**
     * 查询移动端上传图片列表
     *
     * @param img 移动端上传图片信息
     * @return 移动端上传图片集合
     */
    @Override
    public List<Img> selectImgList(Img img) {
        return imgMapper.selectImgList(img);
    }

    /**
     * 新增移动端上传图片
     *
     * @param img 移动端上传图片信息
     * @return 结果
     */
    @Override
    public int insertImg(Img img) {
        return imgMapper.insertImg(img);
    }

    /**
     * 修改移动端上传图片
     *
     * @param img 移动端上传图片信息
     * @return 结果
     */
    @Override
    public int updateImg(Img img) {
        return imgMapper.updateImg(img);
    }

    /**
     * 删除移动端上传图片对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteImgByIds(String ids) {
        return imgMapper.deleteImgByIds(Convert.toStrArray(ids));
    }

    /**
     * 根据任务id获取图片信息
     *
     * @param taskId
     * @return
     */
    @Override
    public List<Img> selectByTaskId(String taskId) {

        List<Img> imgs = imgMapper.selectByTaskId(taskId);
        return imgs;
    }

}

package net.hongkuang.ditui.project.system.notice.service;

import net.hongkuang.ditui.project.system.notice.domain.Notice;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author ruoyi
 */
public interface INoticeService {
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    Notice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    int insertNotice(Notice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    int updateNotice(Notice notice);

    /**
     * 删除公告信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteNoticeByIds(String ids);

    /**
     * 根据通知区域获取最新的一条通知消息
     *
     * @param showArea
     * @return
     */
    Notice selectTop1NoticeByShowArea(Integer showArea);
}

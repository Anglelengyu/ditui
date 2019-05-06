package net.hongkuang.ditui.project.busi.realTimeOnlineSetting.service;

import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.domain.RealTimeOnlineSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;

import java.util.List;
import java.util.Map;

/**
 * 实时任务时间设置 服务层
 *
 * @author:zy
 * @date: 2019/4/18
 */
public interface IRealTimeOnlineSettingService {
    /**
     * 查询实时任务时间设置信息
     *
     * @param id 实时任务时间设置ID
     * @return 实时任务时间设置信息
     */
    RealTimeOnlineSetting selectRealTimeOnlineSettingById(Long id);

    /**
     * 查询实时任务时间设置列表
     *
     * @param realTimeOnlineSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    List<RealTimeOnlineSetting> selectRealTimeOnlineSettingList(RealTimeOnlineSetting realTimeOnlineSetting);

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeOnlineSetting 实时任务时间设置信息
     * @return 结果
     */
    int insertRealTimeOnlineSetting(RealTimeOnlineSetting realTimeOnlineSetting);

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeOnlineSetting 实时任务时间设置信息
     * @return 结果
     */
    int updateRealTimeOnlineSetting(RealTimeOnlineSetting realTimeOnlineSetting);

    /**
     * 删除实时任务时间设置信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeOnlineSettingByIds(String ids);

    List<RealTimeOnlineSetting> selectByOnlineTaskDate(String taskDate,Long teamId);

    Integer selectTaskNumTotalByOnlineTaskDate(String taskDate,Long teamId);

    int addOrEditRealOnlineSetting(Map<String, Object> map,Long teamId);

    List<RealTimeRespVo> selectByTaskDateForApi(String taskDate, String startTime, String stopTime, String currentTime);
}

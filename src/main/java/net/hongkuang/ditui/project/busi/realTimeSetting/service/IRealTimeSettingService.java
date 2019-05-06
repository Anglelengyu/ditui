package net.hongkuang.ditui.project.busi.realTimeSetting.service;

import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;

import java.util.List;
import java.util.Map;

/**
 * 实时任务时间设置 服务层
 *
 * @author yj
 * @date 2019-01-01
 */
public interface IRealTimeSettingService {
    /**
     * 查询实时任务时间设置信息
     *
     * @param id 实时任务时间设置ID
     * @return 实时任务时间设置信息
     */
    RealTimeSetting selectRealTimeSettingById(Long id);

    /**
     * 查询实时任务时间设置列表
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    List<RealTimeSetting> selectRealTimeSettingList(RealTimeSetting realTimeSetting);

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    int insertRealTimeSetting(RealTimeSetting realTimeSetting);

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    int updateRealTimeSetting(RealTimeSetting realTimeSetting);

    /**
     * 删除实时任务时间设置信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeSettingByIds(String ids);

    List<RealTimeSetting> selectByTaskDate(String taskDate);

    int addOrEditRealSetting(Map<String, Object> map);

    List<RealTimeRespVo> selectByTaskDateForApi(String taskDate, String startTime, String stopTime, String currentTime);
}

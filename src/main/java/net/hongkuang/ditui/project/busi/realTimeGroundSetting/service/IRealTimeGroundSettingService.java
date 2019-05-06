package net.hongkuang.ditui.project.busi.realTimeGroundSetting.service;

import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain.RealTimeGroundSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;

import java.util.List;
import java.util.Map;

/**
 * 实时任务时间设置 服务层
 *
 * @author:zy
 * @date: 2019/4/18
 */
public interface IRealTimeGroundSettingService {
    /**
     * 查询实时任务时间设置信息
     *
     * @param id 实时任务时间设置ID
     * @return 实时任务时间设置信息
     */
    RealTimeGroundSetting selectRealTimeGroundSettingById(Long id);

    /**
     * 查询实时任务时间设置列表
     *
     * @param realTimeGroundSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    List<RealTimeGroundSetting> selectRealTimeGroundSettingList(RealTimeGroundSetting realTimeGroundSetting);

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeGroundSetting 实时任务时间设置信息
     * @return 结果
     */
    int insertRealTimeGroundSetting(RealTimeGroundSetting realTimeGroundSetting);

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeGroundSetting 实时任务时间设置信息
     * @return 结果
     */
    int updateRealTimeGroundSetting(RealTimeGroundSetting realTimeGroundSetting);

    /**
     * 删除实时任务时间设置信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeGroundSettingByIds(String ids);

    List<RealTimeGroundSetting> selectByGroundTaskDate(String taskDate,Long teamId);

    Integer selectTaskNumTotalByGroundTaskDate(String taskDate,Long teamId);

    int addOrEditRealGroundSetting(Map<String, Object> map,Long teamId);

    List<RealTimeRespVo> selectByTaskDateForApi(String taskDate, String startTime, String stopTime, String currentTime);
}

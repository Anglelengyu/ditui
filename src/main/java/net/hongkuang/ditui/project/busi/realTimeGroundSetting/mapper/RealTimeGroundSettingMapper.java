package net.hongkuang.ditui.project.busi.realTimeGroundSetting.mapper;

import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain.RealTimeGroundSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实时任务时间设置 数据层
 *
 * @author:zy
 * @date: 2019/4/18
 */
@Repository
public interface RealTimeGroundSettingMapper {
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
     * @param realTimeSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    List<RealTimeGroundSetting> selectRealTimeGroundSettingList(RealTimeGroundSetting realTimeSetting);

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    int insertRealTimeGroundSetting(RealTimeGroundSetting realTimeSetting);

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    int updateRealTimeGroundSetting(RealTimeGroundSetting realTimeSetting);

    /**
     * 删除实时任务时间设置
     *
     * @param id 实时任务时间设置ID
     * @return 结果
     */
    int deleteRealTimeGroundSettingById(Long id);

    /**
     * 批量删除实时任务时间设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeGroundSettingByIds(String[] ids);

    List<RealTimeGroundSetting> selectByGroundTaskDate(@Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    Integer selectTaskNumTotalByGroundTaskDate(@Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    /**
     * @param taskDate
     */
    void deleteByGroundTaskDate(String taskDate);

    int insertRealTimeGroundSettingBatch(List<RealTimeGroundSetting> insertSettings);

    int countByGroundTaskDate(@Param("taskDate") String taskDate);

    List<RealTimeRespVo> selectByGroundTaskDateForApi(@Param("taskDate") String taskDate, @Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("currentTime") String currentTime);
}
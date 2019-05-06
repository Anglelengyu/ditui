package net.hongkuang.ditui.project.busi.realTimeOnlineSetting.mapper;

import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.domain.RealTimeOnlineSetting;
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
public interface RealTimeOnlineSettingMapper {
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
     * @param realTimeSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    List<RealTimeOnlineSetting> selectRealTimeOnlineSettingList(RealTimeOnlineSetting realTimeSetting);

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    int insertRealTimeOnlineSetting(RealTimeOnlineSetting realTimeSetting);

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    int updateRealTimeOnlineSetting(RealTimeOnlineSetting realTimeSetting);

    /**
     * 删除实时任务时间设置
     *
     * @param id 实时任务时间设置ID
     * @return 结果
     */
    int deleteRealTimeOnlineSettingById(Long id);

    /**
     * 批量删除实时任务时间设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeOnlineSettingByIds(String[] ids);

    List<RealTimeOnlineSetting> selectByOnlineTaskDate(@Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    Integer selectTaskNumTotalByOnlineTaskDate(@Param("taskDate") String taskDate,@Param("teamId")Long teamId);

    /**
     * @param taskDate
     */
    void deleteByOnlineTaskDate(String taskDate);

    int insertRealTimeOnlineSettingBatch(List<RealTimeOnlineSetting> insertSettings);

    int countByOnlineTaskDate(@Param("taskDate") String taskDate);

    List<RealTimeRespVo> selectByOnlineTaskDateForApi(@Param("taskDate") String taskDate, @Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("currentTime") String currentTime);
}
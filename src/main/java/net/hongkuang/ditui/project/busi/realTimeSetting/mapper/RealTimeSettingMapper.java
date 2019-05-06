package net.hongkuang.ditui.project.busi.realTimeSetting.mapper;

import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实时任务时间设置 数据层
 *
 * @author yj
 * @date 2019-01-01
 */
@Repository
public interface RealTimeSettingMapper {
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
     * 删除实时任务时间设置
     *
     * @param id 实时任务时间设置ID
     * @return 结果
     */
    int deleteRealTimeSettingById(Long id);

    /**
     * 批量删除实时任务时间设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRealTimeSettingByIds(String[] ids);

    List<RealTimeSetting> selectByTaskDate(String taskDate);

    /**
     * @param taskDate
     */
    void deleteByTaskDate(String taskDate);

    int insertRealTimeSettingBatch(List<RealTimeSetting> insertSettings);

    int countByTaskDate(@Param("taskDate") String taskDate);

    List<RealTimeRespVo> selectByTaskDateForApi(@Param("taskDate") String taskDate, @Param("startTime") String startTime, @Param("stopTime") String stopTime, @Param("currentTime") String currentTime);
}
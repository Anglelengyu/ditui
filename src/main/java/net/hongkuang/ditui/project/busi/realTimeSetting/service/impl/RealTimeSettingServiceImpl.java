package net.hongkuang.ditui.project.busi.realTimeSetting.service.impl;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.mapper.RealTimeSettingMapper;
import net.hongkuang.ditui.project.busi.realTimeSetting.service.IRealTimeSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实时任务时间设置 服务层实现
 *
 * @author yj
 * @date 2019-01-01
 */
@Service
public class RealTimeSettingServiceImpl implements IRealTimeSettingService {
    @Autowired
    private RealTimeSettingMapper realTimeSettingMapper;

    /**
     * 查询实时任务时间设置信息
     *
     * @param id 实时任务时间设置ID
     * @return 实时任务时间设置信息
     */
    @Override
    public RealTimeSetting selectRealTimeSettingById(Long id) {
        return realTimeSettingMapper.selectRealTimeSettingById(id);
    }

    /**
     * 查询实时任务时间设置列表
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    @Override
    public List<RealTimeSetting> selectRealTimeSettingList(RealTimeSetting realTimeSetting) {
        return realTimeSettingMapper.selectRealTimeSettingList(realTimeSetting);
    }

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    @Override
    public int insertRealTimeSetting(RealTimeSetting realTimeSetting) {
        return realTimeSettingMapper.insertRealTimeSetting(realTimeSetting);
    }

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeSetting 实时任务时间设置信息
     * @return 结果
     */
    @Override
    public int updateRealTimeSetting(RealTimeSetting realTimeSetting) {
        return realTimeSettingMapper.updateRealTimeSetting(realTimeSetting);
    }

    /**
     * 删除实时任务时间设置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRealTimeSettingByIds(String ids) {
        return realTimeSettingMapper.deleteRealTimeSettingByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<RealTimeSetting> selectByTaskDate(String taskDate) {
        return realTimeSettingMapper.selectByTaskDate(taskDate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int addOrEditRealSetting(Map<String, Object> map) {
        String taskDate = map.get("taskDate").toString();
//        realTimeSettingMapper.deleteByTaskDate(taskDate);
        List<RealTimeSetting> insertSettings = new ArrayList<>();
        List<Map<String, Object>> settings = (List<Map<String, Object>>) map.get("settings");
        AtomicReference<RealTimeSetting> atomSet = new AtomicReference<>();
        settings.forEach(setting -> {
            atomSet.set(new RealTimeSetting());
            if (StringUtils.isNotNull(setting.get("id"))) {
                String idStr = String.valueOf(setting.get("id"));
                if (StringUtils.isNotEmpty(idStr)) {
                    atomSet.get().setId(Long.valueOf(idStr));
                }
            }
            atomSet.get().setCreateBy(ShiroUtils.getLoginName());
            atomSet.get().setRealTimeId(RandomUtil.genString());
            atomSet.get().setStartTimeNode(setting.get("s").toString());
            atomSet.get().setStopTimeNode(setting.get("t").toString());
            if (!setting.get("taskNum").toString().matches("\\d+")) {
                throw new BusinessException(atomSet.get().getStartTimeNode()
                        + "~" + atomSet.get().getStopTimeNode() + "，分配任务数量非法数字，请进行正确填写");
            }
            atomSet.get().setTaskNum(Integer.valueOf(setting.get("taskNum").toString()));
            atomSet.get().setTaskDate(taskDate);
            atomSet.get().setStatus(0);
            atomSet.get().setUpdateBy(ShiroUtils.getLoginName());
            if (atomSet.get().getId() != null) {
                RealTimeSetting timeSetting = realTimeSettingMapper.selectRealTimeSettingById(atomSet.get().getId());
                if (null == timeSetting) {
                    insertSettings.add(atomSet.get());
                } else {
                    realTimeSettingMapper.updateRealTimeSetting(atomSet.get());
                }
            } else {
                insertSettings.add(atomSet.get());
            }
        });
        if (insertSettings.size() > 0) {
            realTimeSettingMapper.insertRealTimeSettingBatch(insertSettings);
        }
        return 1;
    }

    @Override
    public List<RealTimeRespVo> selectByTaskDateForApi(String taskDate, String startTime, String stopTime, String currentTime) {
        return realTimeSettingMapper.selectByTaskDateForApi(taskDate, startTime, stopTime, currentTime);
    }

}

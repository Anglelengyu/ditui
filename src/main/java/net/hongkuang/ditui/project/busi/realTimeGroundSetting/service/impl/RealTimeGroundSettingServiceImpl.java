package net.hongkuang.ditui.project.busi.realTimeGroundSetting.service.impl;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.domain.RealTimeGroundSetting;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.mapper.RealTimeGroundSettingMapper;
import net.hongkuang.ditui.project.busi.realTimeGroundSetting.service.IRealTimeGroundSettingService;
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
 * @author:zy
 * @date: 2019/4/18
 */
@Service
public class RealTimeGroundSettingServiceImpl implements IRealTimeGroundSettingService {
    @Autowired
    private RealTimeGroundSettingMapper realTimeGroundSettingMapper;

    /**
     * 查询实时任务时间设置信息
     *
     * @param id 实时任务时间设置ID
     * @return 实时任务时间设置信息
     */
    @Override
    public RealTimeGroundSetting selectRealTimeGroundSettingById(Long id) {
        return realTimeGroundSettingMapper.selectRealTimeGroundSettingById(id);
    }

    /**
     * 查询实时任务时间设置列表
     *
     * @param realTimeGroundSetting 实时任务时间设置信息
     * @return 实时任务时间设置集合
     */
    @Override
    public List<RealTimeGroundSetting> selectRealTimeGroundSettingList(RealTimeGroundSetting realTimeGroundSetting) {
        return realTimeGroundSettingMapper.selectRealTimeGroundSettingList(realTimeGroundSetting);
    }

    /**
     * 新增实时任务时间设置
     *
     * @param realTimeGroundSetting 实时任务时间设置信息
     * @return 结果
     */
    @Override
    public int insertRealTimeGroundSetting(RealTimeGroundSetting realTimeGroundSetting) {
        return realTimeGroundSettingMapper.insertRealTimeGroundSetting(realTimeGroundSetting);
    }

    /**
     * 修改实时任务时间设置
     *
     * @param realTimeGroundSetting 实时任务时间设置信息
     * @return 结果
     */
    @Override
    public int updateRealTimeGroundSetting(RealTimeGroundSetting realTimeGroundSetting) {
        return realTimeGroundSettingMapper.updateRealTimeGroundSetting(realTimeGroundSetting);
    }

    /**
     * 删除实时任务时间设置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRealTimeGroundSettingByIds(String ids) {
        return realTimeGroundSettingMapper.deleteRealTimeGroundSettingByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<RealTimeGroundSetting> selectByGroundTaskDate(String taskDate,Long teamId) {
        return realTimeGroundSettingMapper.selectByGroundTaskDate(taskDate,teamId);
    }

    @Override
    public Integer selectTaskNumTotalByGroundTaskDate(String taskDate, Long teamId) {
        return realTimeGroundSettingMapper.selectTaskNumTotalByGroundTaskDate(taskDate,teamId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int addOrEditRealGroundSetting(Map<String, Object> map,Long teamId) {

        String taskDate = map.get("taskDate").toString();
//        realTimeSettingMapper.deleteByTaskDate(taskDate);
        List<RealTimeGroundSetting> insertSettings = new ArrayList<>();
        List<Map<String, Object>> settings = (List<Map<String, Object>>) map.get("settings");
        AtomicReference<RealTimeGroundSetting> atomSet = new AtomicReference<>();
        settings.forEach(setting -> {
            atomSet.set(new RealTimeGroundSetting());
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
            atomSet.get().setTeamId(teamId);
            if (atomSet.get().getId() != null) {
                RealTimeGroundSetting timeGroundSetting = realTimeGroundSettingMapper.selectRealTimeGroundSettingById(atomSet.get().getId());
                if (null == timeGroundSetting) {
                    insertSettings.add(atomSet.get());
                } else {
                    realTimeGroundSettingMapper.updateRealTimeGroundSetting(atomSet.get());
                }
            } else {
                insertSettings.add(atomSet.get());
            }
        });
        if (insertSettings.size() > 0) {
            realTimeGroundSettingMapper.insertRealTimeGroundSettingBatch(insertSettings);
        }
        return 1;
    }

    @Override
    public List<RealTimeRespVo> selectByTaskDateForApi(String taskDate, String startTime, String stopTime, String currentTime) {
        return realTimeGroundSettingMapper.selectByGroundTaskDateForApi(taskDate, startTime, stopTime, currentTime);
    }

}

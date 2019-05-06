package net.hongkuang.ditui.project.busi.realTimeOnlineSetting.task;

import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.LogUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.mapper.RealTimeSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:zy
 * @date: 2019/4/18
 */
@Component("realTimeOnlineSettingTask")
public class RealTimeOnlineSettingTask {

    @Autowired
    private RealTimeSettingMapper realTimeSettingMapper;

    public void initRealTimeSettingNoParam() {
        LogUtils.ACCESS_LOG.info("实时任务节点，初始化开始");
        try {
            // 兼容处理
            String nowDateStr = DateUtils.getDate();
            if (realTimeSettingMapper.countByTaskDate(nowDateStr) > 0) {
                LogUtils.ACCESS_LOG.info("实时任务节点，已初始化完毕");
                return;
            }

            // 优先复制昨日配置 (数据库最新一天配置)
            String date = DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.addDays(DateUtils.getNowDate(), -1));
            List<RealTimeSetting> realTimeSettingList = realTimeSettingMapper.selectByTaskDate(date);
            if (realTimeSettingList == null || realTimeSettingList.isEmpty()) {
                LogUtils.ACCESS_LOG.info("实时任务节点，初始化失败 {}", date);
                return;
            }
            Date now = DateUtils.getNowDate();

            List<RealTimeSetting> newRealTimeSettingList = new ArrayList<>(realTimeSettingList.size());
            for (RealTimeSetting realTimeSetting : realTimeSettingList) {
                RealTimeSetting newRealTimeSetting = new RealTimeSetting();
                BeanUtils.copyBeanProp(newRealTimeSetting, realTimeSetting);
                newRealTimeSetting.setId(null);
                newRealTimeSetting.setCreateTime(now);
                newRealTimeSetting.setUpdateTime(now);
                newRealTimeSetting.setTaskDate(nowDateStr);
                newRealTimeSettingList.add(newRealTimeSetting);
            }

            realTimeSettingMapper.insertRealTimeSettingBatch(newRealTimeSettingList);
        } catch (Exception e) {
            LogUtils.ERROR_LOG.info("实时任务节点，初始化异常", e);
        }
        LogUtils.ACCESS_LOG.info("实时任务节点，初始化结束");
    }
}

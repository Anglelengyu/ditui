package net.hongkuang.ditui.project.api.controller;

import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.config.AccessCheck;
import net.hongkuang.ditui.project.api.dto.RealTimeRespVo;
import net.hongkuang.ditui.project.busi.realTimeSetting.service.IRealTimeSettingService;
import net.hongkuang.ditui.project.busi.realTimeTask.domain.RealTimeTask;
import net.hongkuang.ditui.project.busi.realTimeTask.service.IRealTimeTaskService;
import net.hongkuang.ditui.project.busi.reserveTask.service.IReserveTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2019/1/8.
 */
@RestController
@RequestMapping("api/realTimeTask")
public class RealTimeTaskApiController {

    @Autowired
    private IRealTimeSettingService realTimeSettingService;

    @Autowired
    private IRealTimeTaskService realTimeTaskService;

    @Autowired
    private IReserveTaskService reserveTaskService;

    @AccessCheck
    @PostMapping("/getRealTimeListAndGrabStatus")
    public AjaxResult getRealTimeListAndGrabStatus() {
        String taskDate = DateUtils.getDate();
        String currentTime = DateUtils.dateTimeNow("HH:mm");
        // 检查哪个时间节点还有订单可领取
        RealTimeTask realTimeTask = realTimeTaskService.selectCurrentTimeTaskCount(taskDate, currentTime);
        // 获取今天日期的时间设置
        List<RealTimeRespVo> respVos = null;
        if (realTimeTask == null) {
            respVos = realTimeSettingService.selectByTaskDateForApi(taskDate, null, null, currentTime);
        } else {
            respVos = realTimeSettingService.selectByTaskDateForApi(taskDate, realTimeTask.getStartTime(), realTimeTask.getStopTime(), currentTime);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("serverDate", taskDate);
        // 获取储备任务抢单标识
        Integer reserveTaskNum = reserveTaskService.selectCountByReserveDate(taskDate);
        if (realTimeTask != null) {
            result.put("grabFlag", realTimeTask != null ? true : false);
            result.put("reserveFlag", false);
        }
        if (realTimeTask == null && reserveTaskNum > 0) {
            result.put("grabFlag", false);
            result.put("reserveFlag", true);
        }
        if (realTimeTask == null && reserveTaskNum == 0) {
            result.put("grabFlag", false);
            result.put("reserveFlag", false);
        }
        result.put("realTimes", respVos);
        return AjaxResult.success().put("data", result);
    }

}

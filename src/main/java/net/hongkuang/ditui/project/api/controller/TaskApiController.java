package net.hongkuang.ditui.project.api.controller;

import net.hongkuang.ditui.common.utils.spring.SpringUtils;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.config.AccessCheck;
import net.hongkuang.ditui.project.api.dto.*;
import net.hongkuang.ditui.project.api.service.ITodoTaskService;
import net.hongkuang.ditui.project.busi.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by apple on 2019/1/11.
 */
@RestController
@RequestMapping("/api/task")
public class TaskApiController {

    @Autowired
    private ITaskService taskService;

    @AccessCheck
    @RequestMapping("/getNotCompleteTask")
    public AjaxResult getNotCompleteTask(SimpleTaskReqVo taskReqVo) {
        return AjaxResult.success().put("data", taskService.getNotCompleteTask(taskReqVo));
    }

    // 进行分配任务
    @AccessCheck
    @PostMapping("/todo/alloc/{taskType}")
    public AjaxResult allocTodoTask(TodoTaskReqVo todoTaskReqVo, @PathVariable("taskType") String taskType) {
        ITodoTaskService todoTaskService = SpringUtils.getBean(taskType + "TaskService");
        return AjaxResult.success().put("data", todoTaskService.allocTodoTaskList(todoTaskReqVo));
    }

    @AccessCheck
    @PostMapping("/detail")
    public AjaxResult detail(SimpleTaskReqVo taskReqVo) {
        return AjaxResult.success().put("data", taskService.getTaskDetail(taskReqVo));
    }

    @AccessCheck
    @PostMapping("/list")
    public AjaxResult list(TaskSearchReqVo taskReqVo) {
        return AjaxResult.success().put("data", taskService.selectTaskList(taskReqVo));
    }

    @AccessCheck
    @PostMapping("/cancel")
    public AjaxResult cancel(TaskCancelReqVo taskCancelReqVo) {
        taskService.cancel(taskCancelReqVo);
        return AjaxResult.success("取消成功");
    }

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中
     */
    @AccessCheck
    @PostMapping("/checkStatus")
    public AjaxResult checkStatus(String userId) {
        try {
            Map<String, Object> status = taskService.checkStatus(userId);
            return AjaxResult.success().put("data", status);
        } catch (Exception e) {
            return AjaxResult.error("数据校验失败,请检查网络链接");
        }
    }

    /**
     * 检查当前是否有订单正在取消中或者有任务在取消中 或者有任务未完成
     *
     * @return status = 1 有任务正在进行中 2有任务正在取消中 3有订单正在取消中
     */
    @AccessCheck
    @PostMapping("/submitTask")
    public AjaxResult submitTask(TaskSubmitReqVo taskSubmit) {
        try {
            int success = taskService.submitTask(taskSubmit);
            return success > 0 ? AjaxResult.success() : AjaxResult.error("任务提交失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("数据校验失败,请检查网络链接");
        }
    }
}

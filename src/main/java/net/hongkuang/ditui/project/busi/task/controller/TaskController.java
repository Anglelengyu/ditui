package net.hongkuang.ditui.project.busi.task.controller;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.service.IOrderService;
import net.hongkuang.ditui.project.busi.realTimeSetting.domain.RealTimeSetting;
import net.hongkuang.ditui.project.busi.realTimeSetting.service.IRealTimeSettingService;
import net.hongkuang.ditui.project.busi.reserveTask.service.IReserveTaskService;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.salesman.service.ISalesmanService;
import net.hongkuang.ditui.project.busi.task.domain.SearchTask;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.domain.TaskVo;
import net.hongkuang.ditui.project.busi.task.service.IAutoAllocTaskService;
import net.hongkuang.ditui.project.busi.task.service.ITaskService;
import net.hongkuang.ditui.project.busi.taskOrder.dto.TaskOrderDto;
import net.hongkuang.ditui.project.busi.taskOrder.service.ITaskOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 任务 信息操作处理
 *
 * @author yj
 * @date 2018-12-30
 */
@Controller
@RequestMapping("/busi/task")
public class TaskController extends BaseController {
    private String prefix = "busi/task";

    @Autowired
    private IRealTimeSettingService realTimeSettingService;
    @Autowired
    private IReserveTaskService reserveTaskService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private ITaskOrderService taskOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IAutoAllocTaskService autoAllocTaskService;
    @Autowired
    private IImgService imgService;
    @Autowired
    private ISalesmanService salesmanService;

    @RequiresPermissions("busi:task:view")
    @GetMapping()
    public String task() {
        return prefix + "/task";
    }

    @RequiresPermissions("busi:task:real:setting")
    @GetMapping("/realSetting")
    public String realSetting(ModelMap mmap) {
        String taskDate = DateUtils.getDate();
        List<RealTimeSetting> list = realTimeSettingService.selectByTaskDate(taskDate);
        mmap.put("timeSetting", list);
        mmap.put("taskDate", list.get(0).getTaskDate());
        return prefix + "/realSetting";
    }

    /**
     * 查询任务列表
     */
    @RequiresPermissions("busi:task:already")
    @PostMapping("/already")
    @ResponseBody
    public TableDataInfo already(SearchTask task) {
        startPage();
        task.setStatus(OrderAllocatStatus.COMPLETE.getCode());
        List<TaskVo> list = taskService.selectTaskList(task);
        return getDataTable(list);
    }

    @RequiresPermissions("busi:task:already")
    @GetMapping("/already")
    public String already() {
        return prefix + "/already";
    }

    /**
     * 查询任务列表
     */
    @RequiresPermissions("busi:task:history")
    @PostMapping("/history")
    @ResponseBody
    public TableDataInfo history(SearchTask task) {
        startPage();
        task.setStatus(OrderAllocatStatus.HISTORY.getCode());
        List<TaskVo> list = taskService.selectTaskList(task);
        return getDataTable(list);
    }

    @RequiresPermissions("busi:task:history")
    @GetMapping("/history")
    public String history() {
        return prefix + "/history";
    }


    @RequiresPermissions("busi:task:show")
    @GetMapping("/showTaskDetails/{id}")
    public String showTaskDetails(@PathVariable Long id, ModelMap mmap) {
        Task task = taskService.selectTaskById(id);
        List<Order> orders = orderService.selectOrderListByOrderId(task.getTaskId());
        List<Img> imgs = imgService.selectByTaskId(task.getTaskId());
        task.setOrders(orders);
        mmap.put("task", task);
        mmap.put("imgs", imgs);
        return prefix + "/showDetails";
    }

    @RequiresPermissions("busi:task:show")
    @GetMapping("/getTaskDetails/{id}")
    @ResponseBody
    public AjaxResult getTaskDetails(@PathVariable Long id) {
        Task task = taskService.selectTaskById(id);
        List<Order> orders = orderService.selectOrderListByOrderId(task.getTaskId());
        task.setOrders(orders);
        return AjaxResult.success().put("task", task);
    }

    /**
     * 查询任务列表
     */
    @RequiresPermissions("busi:task:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchTask task) {
        startPage();
        task.setStatus(OrderAllocatStatus.UNFINISHED.getCode());
        List<TaskVo> list = taskService.selectTaskList(task);
        return getDataTable(list);
    }

    /**
     * 将任务自动分配至时间节点
     */
    @RequiresPermissions("busi:task:auto:real")
    @PostMapping("/automaticAssign")
    @ResponseBody
    public AjaxResult automaticAssign(Task task) {
        // 任务自动分配
        return toAjax(autoAllocTaskService.automaticAssign());
    }

    /**
     * 将任务自动分配至时间节点
     */
    @RequiresPermissions("busi:task:movetohistory")
    @PostMapping("/moveToHistory")
    @ResponseBody
    public AjaxResult moveToHistory(Integer type, String taskId) {
        // 任务自动分配
        return toAjax(taskService.moveToHistory(type, taskId));
    }

    /**
     * 导出任务列表
     */
    @RequiresPermissions("busi:task:real:setting")
    @PostMapping("/addOrEditRealSetting")
    @ResponseBody
    public AjaxResult addOrEditRealSetting(@RequestBody Map<String, Object> map) {
        return toAjax(realTimeSettingService.addOrEditRealSetting(map));
    }

    /**
     * 导出任务列表  已分配
     */
    @RequiresPermissions("busi:task:export")
    @PostMapping("/alreadyExport")
    @ResponseBody
    public AjaxResult alreadyExport(SearchTask task) {
        task.setStatus(OrderAllocatStatus.COMPLETE.getCode());
        List<TaskVo> list = taskService.selectTaskList(task);
        list.forEach(taskVo ->
                taskVo.setTaskCorpusYuan(UnitUtils.unitYuanString(taskVo.getTaskCorpus()))
        );
        ExcelUtil<TaskVo> util = new ExcelUtil<>(TaskVo.class);
        return util.exportExcel(list, "task");
//        return success();
    }


    /**
     * 导出任务列表   未分配
     */
    @RequiresPermissions("busi:task:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SearchTask task) {
        task.setStatus(OrderAllocatStatus.UNFINISHED.getCode());
        List<TaskVo> list = taskService.selectTaskList(task);
        list.forEach(taskVo ->
                taskVo.setTaskCorpusYuan(UnitUtils.unitYuanString(taskVo.getTaskCorpus()))
        );
        ExcelUtil<TaskVo> util = new ExcelUtil<>(TaskVo.class);
        return util.exportExcel(list, "task");
//        return success();
    }

    /**
     * 导出任务列表   历史任务
     */
    @RequiresPermissions("busi:task:export")
    @PostMapping("/historyExport")
    @ResponseBody
    public AjaxResult historyExport(SearchTask task) {
        task.setStatus(OrderAllocatStatus.HISTORY.getCode());
        List<TaskVo> list = taskService.selectTaskList(task);
        list.forEach(taskVo ->
                taskVo.setTaskCorpusYuan(UnitUtils.unitYuanString(taskVo.getTaskCorpus()))
        );
        ExcelUtil<TaskVo> util = new ExcelUtil<>(TaskVo.class);
        return util.exportExcel(list, "task");
//        return success();
    }

    /**
     * 新增任务
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存任务
     */
    @RequiresPermissions("busi:task:add")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Task task) {
        return toAjax(taskService.insertTask(task));
    }

    /**
     * 修改任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Task task = taskService.selectTaskById(id);
        mmap.put("task", task);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务
     */
    @RequiresPermissions("busi:task:edit")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Task task) {
        return toAjax(taskService.updateTask(task));
    }

    /**
     * 删除任务
     */
    @RequiresPermissions("busi:task:remove")
    @Log(title = "任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(taskService.deleteTaskByIds(ids));
    }

    /**
     * 任务分配至储备任务
     */
    @RequiresPermissions("busi:task:list:reserve")
    @Log(title = "储备任务", businessType = BusinessType.ALLOT)
    @PostMapping("/toReserve")
    @ResponseBody
    public AjaxResult toReserve(String taskId) {
        List<String> taskIds = Arrays.asList(taskId.split(","));
        int rs = reserveTaskService.insertBatchReserveTask(taskIds);
        if (rs > 0) {
            return toAjax(taskService.updateBatchTaskStatusAllot(taskIds));
        }
        return error();
    }

    /**
     * 修改任务
     */
    @GetMapping("/toReal")
    public String toReal(ModelMap mmap) {
        String taskDate = DateUtils.getDate();
        List<RealTimeSetting> timeSettings = realTimeSettingService.selectByTaskDate(taskDate);
        mmap.put("timeSettings", timeSettings);
        return prefix + "/manualRealTime";
    }

    /**
     * 手动分任务给业务员
     */
    @GetMapping("/toSale/{taskId}")
    public String toSale(@PathVariable("taskId") String taskId, ModelMap mmap) {
        mmap.put("taskId", taskId);
        List<Salesman> sales = salesmanService.selectSalesmanList(new Salesman());
        mmap.put("sales", sales);
        return prefix + "/toSale";
    }

    /**
     * 修改任务
     */
    @RequiresPermissions("busi:task:hand:sale")
    @Log(title = "手动分配任务任务", businessType = BusinessType.ALLOT)
    @PostMapping("/toSale")
    @ResponseBody
    public AjaxResult toSale(String saleId, String taskId, String userId) {
        try {
            return toAjax(taskService.toSale(taskId, saleId, userId));
        } catch (BusinessException e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改任务
     */
    @RequiresPermissions("busi:task:list:real")
    @Log(title = "手动实时任务", businessType = BusinessType.ALLOT)
    @PostMapping("/toReal")
    @ResponseBody
    public AjaxResult toRealSetting(String startTime, String stopTime, String taskIds) {
        String taskDate = DateUtils.getDate();
        return toAjax(taskService.toReal(taskDate, startTime, stopTime, taskIds));
    }

    /**
     * 新增订单
     */
    @GetMapping("/addOrder/{taskId}")
    public String addOrder(@PathVariable("taskId") String taskId, ModelMap mmap) {
        mmap.put("taskId", taskId);
        return prefix + "/addOrder";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("busi:task:addOrder")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping("/addOrder")
    @ResponseBody
    public AjaxResult addOrder(TaskOrderDto taskOrderDto) {
        return toAjax(taskOrderService.insertTaskOrder(taskOrderDto));
    }

    /**
     * 删除任务订单
     */
    @RequiresPermissions("busi:task:remove")
    @Log(title = "任务订单", businessType = BusinessType.DELETE)
    @PostMapping("/removeTaskOrder/{taskId}")
    @ResponseBody
    public AjaxResult removeTaskOrder(@PathVariable("taskId") String taskId, String ids) {
        return toAjax(taskOrderService.deleteTaskOrderByIds(taskId, ids));
    }

    @RequiresPermissions("busi:task:show")
    @GetMapping("/showBuyInfo/{id}")
    public String showBuyInfo(@PathVariable Long id, ModelMap mmap) {

        return prefix + "/showBuyInfo";
    }
}

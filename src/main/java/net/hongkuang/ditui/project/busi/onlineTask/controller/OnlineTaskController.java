package net.hongkuang.ditui.project.busi.onlineTask.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.employee.domain.Employee;
import net.hongkuang.ditui.project.busi.employee.domain.SearchEmployee;
import net.hongkuang.ditui.project.busi.employee.service.IEmployeeService;
import net.hongkuang.ditui.project.busi.employee.support.EmployeeKeyEnum;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.domain.OnlineTaskVo;
import net.hongkuang.ditui.project.busi.onlineTask.domain.SearchOnlineTask;
import net.hongkuang.ditui.project.busi.onlineTask.service.IAutoAllocOnlineTaskService;
import net.hongkuang.ditui.project.busi.onlineTask.service.IOnlineTaskService;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.dto.OnlineTaskOrderDto;
import net.hongkuang.ditui.project.busi.onlineTaskOrder.service.IOnlineTaskOrderService;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import net.hongkuang.ditui.project.busi.order.domain.SearchTbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrderDto;
import net.hongkuang.ditui.project.busi.order.dto.GenTaskDto;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.service.ITbTransactionOrderService;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.domain.RealTimeOnlineSetting;
import net.hongkuang.ditui.project.busi.realTimeOnlineSetting.service.IRealTimeOnlineSettingService;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.domain.RealTimeOnlineTaskVo;
import net.hongkuang.ditui.project.busi.realTimeOnlineTask.service.IRealTimeOnlineTaskService;
import net.hongkuang.ditui.project.busi.reserveOnlineTask.service.IReserveOnlineTaskService;
import net.hongkuang.ditui.project.busi.salesman.domain.Salesman;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 地推订单管理
 * @author:zy
 * @date: 2019/4/15
 */
@Controller
@RequestMapping("/busi/onlineTask")
public class OnlineTaskController extends BaseController {
    private String prefix = "busi/onlineTask";

    @Autowired
    private ITbTransactionOrderService tbTransactionOrderService;

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IOnlineTaskService onlineTaskService;
    @Autowired
    private IImgService imgService;
    @Autowired
    private IOnlineTaskOrderService onlineTaskOrderService;
    @Autowired
    private IAutoAllocOnlineTaskService autoAllocOnlineTaskService;
    @Autowired
    private IRealTimeOnlineSettingService realTimeOnlineSettingService;
    @Autowired
    private IReserveOnlineTaskService reserveOnlineTaskService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IRealTimeOnlineTaskService realTimeOnlineTaskService;


    @RequiresPermissions("busi:onlineTask:waitBuild:view")
    @GetMapping("/waitBuild")
    public String waitBuild() {
        return prefix + "/waitBuild";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:onlineTask:waitBuild:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchTbTransactionOrder searchTbTransactionOrder) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchTbTransactionOrder.setTeamId(team.getTeamId());
        }
        startPage();
        List<TbTransactionOrder> list = tbTransactionOrderService.selectTbTransactionOrderList(searchTbTransactionOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("busi:onlineTask:waitBuild:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SearchTbTransactionOrder searchTbTransactionOrder) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchTbTransactionOrder.setTeamId(team.getTeamId());
        }
        List<TbTransactionOrder> list = tbTransactionOrderService.selectTbTransactionOrderList(searchTbTransactionOrder);
        list.forEach(order1 -> {
            order1.setUnitPriceYuan(UnitUtils.unitYuanString(order1.getUnitPrice(), "--"));
            order1.setCommissionPriceYuan(UnitUtils.unitYuanString(order1.getCommissionPrice(), "--"));
            order1.setPaymentYuan(UnitUtils.unitYuanString(order1.getPayment(), "--"));
        });
        ExcelUtil<TbTransactionOrder> util = new ExcelUtil<>(TbTransactionOrder.class);
        return util.exportExcel(list, "tbTransactionOrder");
    }

    /**
     * 显示备注
     */
    @GetMapping("/showRemark/{id}")
    public String showRemark(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("tbTransactionOrder", tbTransactionOrderService.selectTbTransactionOrderById(id));
        return prefix +"/waitBuildTeamRemark";
    }

    /**
     * 显示备注
     */
    @RequiresPermissions("busi:order:edit")
    @Log(title = "模版备注管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editRemark")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editRemark(TbTransactionOrder tbTransactionOrder) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrderRemark(tbTransactionOrder));
    }


    /**
     * 判断订单是否已经分配
     */
    @RequiresPermissions("busi:onlineTask:waitBuild:distributionTeam")
    @PostMapping("/judgeIsDistribution")
    @ResponseBody
    public AjaxResult judgeIsDistribution(String ids) {
        return toAjax(tbTransactionOrderService.selectTbTransactionOrderIsDistribution(Convert.toStrArray(ids)));
    }

    /**
     * 合作团队列表
     */
    @RequiresPermissions("busi:onlineTask:waitBuild:distributionTeam")
    @GetMapping("/distributionTeam")
    public String distributionTeam(ModelMap mmap) {
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        mmap.put("teamList", teamService.selectTeamMiddleTeamById(teamId));
        return prefix + "/distributionTeam";
    }

    /**
     * 分配给合作团队
     */
    @RequiresPermissions("busi:onlineTask:waitBuild:distributionTeam")
    @ResponseBody
    @PostMapping("/distributionTeam")
    public AjaxResult distributionTeamSave(String ids,Long teamId, String teamName, ModelMap mmap) {
        return toAjax(tbTransactionOrderService.distributionTeam(Convert.toStrArray(ids), teamId, teamName));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("busi:onlineTask:waitBuild:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tbTransactionOrderService.deleteTbTransactionOrderByIds(ids));
    }

    /**
     * 修改订单
     */
    @GetMapping("/waitBuild/edit/{id}")
    public String waitBuildEdit(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionOrder tbTransactionOrder = tbTransactionOrderService.selectTbTransactionOrderById(id);
        tbTransactionOrder.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionOrder.getCommissionPrice()));
        tbTransactionOrder.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionOrder.getUnitPrice()));

        if (tbTransactionOrder.getTbTransactionQuestion() == null) {
            tbTransactionOrder.setTbTransactionQuestion(new TbTransactionQuestion());
        }
        mmap.put("tbTransactionOrder", tbTransactionOrder);
        mmap.put("managerId", tbTransactionOrder.getManagerId());
        return prefix + "/waitBuild_edit";
    }

    /**
     * 修改保存模版
     */
    @RequiresPermissions("busi:tbTransactionTemplate:edit")
    @Log(title = "模版关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/waitBuild/edit")
    @ResponseBody
    public AjaxResult waitBuildSave(TbTransactionOrderDto tbTransactionOrderDto) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrder(tbTransactionOrderDto));
    }

    /**
     * 生成任务订单
     */
    @GetMapping("/gen")
    public String gen(ModelMap mmap) {
        return prefix + "/gen";
    }

    /**
     * 获取生成任务相关数据
     */
    @PostMapping("/genTypeAndOrderTotalNum")
    @ResponseBody
    public AjaxResult genTypeAndOrderTotalNum(String ids) {
        Map map = new HashMap<>();
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        map.put("genType", tbTransactionOrderService.getGenType(teamId, Convert.toStrArray(ids)));
        map.put("orderTotalNum", tbTransactionOrderService.getOrderTotalNum(teamId, Convert.toStrArray(ids)));
        return AjaxResult.success().put("data",map);
    }


    /**
     * 生成任务
     */
//    @RequiresPermissions("busi:order:gentask")
    @Log(title = "订单", businessType = BusinessType.GEN)
    @PostMapping("/genTask")
    @ResponseBody
    public AjaxResult genTask(GenTaskDto genTaskDto) {
        if (genTaskDto.getGenType() == null || genTaskDto.getGenType() <= 0) {
            throw new BusinessException("无效的生成模式");
        }
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            genTaskDto.setTeamId(team.getTeamId());
        }
        int taskResult = tbTransactionOrderService.genTask(genTaskDto);
        return toAjax(taskResult).put("taskResult", taskResult);
    }

    /**
     * 获取生成任务数量
     */
    @PostMapping("/getTaskNum")
    @ResponseBody
    public AjaxResult getTaskNum(Integer genType,String ids) {
        if (genType == null || genType <= 0) {
            throw new BusinessException("无效的生成模式");
        }
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        GenTaskDto genTaskDto = tbTransactionOrderService.getTaskNum(genType, teamId, Convert.toStrArray(ids));
        return AjaxResult.success().put("data", genTaskDto);
    }

    @RequiresPermissions("busi:onlineTask:waitDistribution:view")
    @GetMapping("/waitDistribution")
    public String task() {
        return prefix + "/waitDistribution";
    }

    /**
     * 查询任务列表
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:list")
    @PostMapping("/waitDistribution/list")
    @ResponseBody
    public TableDataInfo waitDistributionList(SearchOnlineTask task) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            task.setTeamId(team.getTeamId());
        }
        startPage();
        task.setStatus(TbTransactionOrderAllocatStatus.UNFINISHED.getCode());
        List<OnlineTaskVo> list = onlineTaskService.selectTaskList(task);
        return getDataTable(list);
    }

    /**
     * 显示备注
     */
    @GetMapping("/waitDistribution/showRemark/{id}")
    public String waitDistributionShowRemark(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("onlineTask", onlineTaskService.selectOnlineTaskById(id));
        return prefix + "/waitDistributionTeamRemark";
    }

    /**
     * 显示备注
     */
    @Log(title = "任务团队备注管理", businessType = BusinessType.UPDATE)
    @PostMapping("/waitDistribution/editRemark")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult waitDistributionEditRemark(OnlineTask onlineTask) {
        return toAjax(onlineTaskService.updateOnlineTaskTeamRemark(onlineTask));
    }

    /**
     * 删除任务
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:remove")
    @Log(title = "任务", businessType = BusinessType.DELETE)
    @PostMapping("/waitDistribution/remove")
    @ResponseBody
    public AjaxResult waitDistributionRemove(String ids) {
        return toAjax(onlineTaskService.deleteOnlineTaskByIds(ids));
    }

    @RequiresPermissions("busi:onlineTask:waitDistribution:show")
    @GetMapping("/waitDistribution/showTaskDetails/{id}")
    public String showTaskDetails(@PathVariable Long id, ModelMap mmap) {
        OnlineTask onlineTask = onlineTaskService.selectOnlineTaskById(id);
        List<TbTransactionOrder> tbTransactionOrders = tbTransactionOrderService.selectTbTransactionOrderByTaskId(onlineTask.getTaskId());
        List<Img> imgs = imgService.selectByTaskId(onlineTask.getTaskId());
        onlineTask.setOrders(tbTransactionOrders);
        mmap.put("onlineTask", onlineTask);
        mmap.put("imgs", imgs);
        return prefix + "/showDetails";
    }

    /**
     * 删除任务订单
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:remove")
    @Log(title = "任务订单", businessType = BusinessType.DELETE)
    @PostMapping("/waitDistribution/removeTaskOrder/{taskId}")
    @ResponseBody
    public AjaxResult removeTaskOrder(@PathVariable("taskId") String taskId, String ids) {
        return toAjax(onlineTaskOrderService.deleteOnlineTaskOrderByIds(taskId, ids));
    }

    /**
     * 新增订单
     */
    @GetMapping("/waitDistribution/addOrder/{taskId}")
    public String addOrder(@PathVariable("taskId") String taskId, ModelMap mmap) {
        mmap.put("taskId", taskId);
        return prefix + "/addOnlineOrder";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:addOrder")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping("/waitDistribution/addOrder")
    @ResponseBody
    public AjaxResult addOrder(OnlineTaskOrderDto onlineTaskOrderDto) {
        return toAjax(onlineTaskOrderService.insertOnlineTaskOrder(onlineTaskOrderDto));
    }

    /**
     * 任务分配至储备任务
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:list:reserve")
    @Log(title = "储备任务", businessType = BusinessType.ALLOT)
    @PostMapping("/waitDistribution/toReserve")
    @ResponseBody
    public AjaxResult toReserve(String taskId) {
        List<String> taskIds = Arrays.asList(taskId.split(","));
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        int rs = reserveOnlineTaskService.insertBatchReserveTask(taskIds,teamId);
        if (rs > 0) {
            return toAjax(onlineTaskService.updateBatchOnlineTaskStatusAllot(taskIds));
        }
        return error();
    }

    /**
     * 手动分配任务给线下员工
     */
    @GetMapping("/waitDistribution/toSale")
    public String toSale( ModelMap mmap) {
        SearchEmployee searchEmployee = new SearchEmployee();
        searchEmployee.setEmployeeKey(EmployeeKeyEnum.GROUND.getCode());
        List<Employee> employees = employeeService.selectEmployeeList(searchEmployee);
        mmap.put("employees", employees);
        return prefix + "/toSale";
    }

    /**
     * 手动分配任务给线下员工
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:hand:sale")
    @Log(title = "手动分配任务", businessType = BusinessType.ALLOT)
    @PostMapping("/waitDistribution/toSale")
    @ResponseBody
    public AjaxResult toSale(String employeeId, String taskIds, String userId) {
        try {
            Long teamId = null;
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
                Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
                teamId = team.getTeamId();
            }
            return toAjax(onlineTaskService.toSale(Convert.toStrArray(taskIds), employeeId, userId,teamId));
        } catch (BusinessException e) {
            return error(e.getMessage());
        }
    }

    @RequiresPermissions("busi:onlineTask:waitDistribution:real:setting")
    @GetMapping("/waitDistribution/realSetting")
    public String realSetting(ModelMap mmap,String taskDate) {
        if(StringUtils.isEmpty(taskDate)){
            taskDate = DateUtils.getDate();
        }
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        List<RealTimeOnlineSetting> list = realTimeOnlineSettingService.selectByOnlineTaskDate(taskDate,teamId);
//        List<RealTimeOnlineSetting> list = new ArrayList<RealTimeOnlineSetting>();
        Integer undividedTotal = realTimeOnlineSettingService.selectTaskNumTotalByOnlineTaskDate(taskDate,teamId);
        List<RealTimeOnlineTaskVo> realTimeOnlineTaskVoList = realTimeOnlineTaskService.selectRealTimeOnlineTaskTodayCount(taskDate,teamId);

        List<RealTimeOnlineTaskVo> realTimeOnlineTaskVoUnFinishList = realTimeOnlineTaskService.selectRealTimeOnlineTaskUnFinishNumCount(taskDate,teamId,TaskTaskStatus.COMPLETED.getCode());

        mmap.put("timeSetting", list);
        mmap.put("realTimeOnlineTaskVoList", realTimeOnlineTaskVoList);
        mmap.put("realTimeOnlineTaskVoUnFinishList", realTimeOnlineTaskVoUnFinishList);
        mmap.put("undividedTotal", undividedTotal==null?0:undividedTotal);
        mmap.put("taskDate", taskDate);
        return prefix + "/realSetting";
    }

    /**
     * 分配到实时任务
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:real:setting")
    @PostMapping("/waitDistribution/addOrEditRealSetting")
    @ResponseBody
    public AjaxResult addOrEditRealSetting(@RequestBody Map<String, Object> map) {
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        realTimeOnlineSettingService.addOrEditRealOnlineSetting(map,teamId);
        int i = autoAllocOnlineTaskService.automaticAssign(Convert.toLongArray(map.get("ids").toString()),teamId,map.get("taskDate").toString());
        return toAjax(i);
    }

    @RequiresPermissions("busi:onlineTask:waitAccepted:view")
    @GetMapping("/waitAccepted")
    public String already() {
        return prefix + "/waitAccepted";
    }

    /**
     * 未接单任务列表
     */
    @RequiresPermissions("busi:onlineTask:waitAccepted:list")
    @PostMapping("/waitAccepted")
    @ResponseBody
    public TableDataInfo already(SearchOnlineTask task) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            task.setTeamId(team.getTeamId());
        }
        startPage();
        task.setStatus(TbTransactionOrderAllocatStatus.COMPLETE.getCode());
        task.setTaskStatus(TaskTaskStatus.WAIT.getCode());
        List<OnlineTaskVo> list = onlineTaskService.selectTaskList(task);
        return getDataTable(list);
    }

    @RequiresPermissions("busi:onlineTask:alreadyAccepted:view")
    @GetMapping("/alreadyAccepted")
    public String alreadyAccepted() {
        return prefix + "/alreadyAccepted";
    }

    /**
     * 已接单任务列表
     */
    @RequiresPermissions("busi:onlineTask:alreadyAccepted:list")
    @PostMapping("/alreadyAccepted")
    @ResponseBody
    public TableDataInfo alreadyAccepted(SearchOnlineTask task) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            task.setTeamId(team.getTeamId());
        }
        startPage();
        task.setStatus(TbTransactionOrderAllocatStatus.COMPLETE.getCode());
        task.setTaskStatus(TaskTaskStatus.RECEIVED.getCode());
        List<OnlineTaskVo> list = onlineTaskService.selectTaskList(task);
        return getDataTable(list);
    }

    @RequiresPermissions("busi:onlineTask:alreadyFinish:view")
    @GetMapping("/alreadyFinish")
    public String alreadyFinish() {
        return prefix + "/alreadyFinish";
    }

    /**
     * 已完成任务列表
     */
    @RequiresPermissions("busi:onlineTask:alreadyFinish:list")
    @PostMapping("/alreadyFinish")
    @ResponseBody
    public TableDataInfo alreadyFinish(SearchOnlineTask task) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            task.setTeamId(team.getTeamId());
        }
        startPage();
        task.setStatus(TbTransactionOrderAllocatStatus.COMPLETE.getCode());
        task.setTaskStatus(TaskTaskStatus.COMPLETED.getCode());
        List<OnlineTaskVo> list = onlineTaskService.selectTaskList(task);
        return getDataTable(list);
    }

    /**
     * 显示备注
     */
    @GetMapping("/showTaskRemark/{id}")
    public String showTaskRemark(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("tbTransactionOrder", tbTransactionOrderService.selectTbTransactionOrderById(id));
        return prefix +"/taskTeamRemark";
    }

    /**
     * 显示备注
     */
    @Log(title = "模版备注管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editTaskRemark")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editTaskRemark(TbTransactionOrder tbTransactionOrder) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrderRemark(tbTransactionOrder));
    }

    @GetMapping("/alreadyDistribution/showTaskDetails/{id}")
    public String showAlreadyDistributionTaskDetails(@PathVariable Long id, ModelMap mmap) {
        OnlineTask onlineTask = onlineTaskService.selectOnlineTaskById(id);
        List<TbTransactionOrder> tbTransactionOrders = tbTransactionOrderService.selectTbTransactionOrderByTaskId(onlineTask.getTaskId());
        List<Img> imgs = imgService.selectByTaskId(onlineTask.getTaskId());
        onlineTask.setOrders(tbTransactionOrders);
        mmap.put("onlineTask", onlineTask);
        mmap.put("imgs", imgs);
        return prefix + "/showAlreadyDistributionTaskDetails";
    }

    /**
     * 判断任务是否已经分配
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:distributionTeam")
    @PostMapping("/waitDistribution/taskIsDistribution")
    @ResponseBody
    public AjaxResult taskIsDistribution(String ids) {
        return toAjax(onlineTaskService.selectOnlineTaskIsDistribution(Convert.toStrArray(ids)));
    }

    /**
     * 合作团队列表
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:distributionTeam")
    @GetMapping("/waitDistribution/distributionTeam")
    public String taskDistributionTeam(ModelMap mmap) {
        Long teamId = null;
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            teamId = team.getTeamId();
        }
        mmap.put("teamList", teamService.selectTeamMiddleTeamById(teamId));
        return prefix + "/taskDistributionTeam";
    }

    /**
     * 分配给合作团队
     */
    @RequiresPermissions("busi:onlineTask:waitDistribution:distributionTeam")
    @ResponseBody
    @PostMapping("/waitDistribution/distributionTeam")
    public AjaxResult taskDistributionTeamSave(String taskIds, Long teamId, String teamName, ModelMap mmap) {
        return toAjax(onlineTaskService.taskDistributionTeam(Convert.toStrArray(taskIds), teamId, teamName));
    }

}

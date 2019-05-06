package net.hongkuang.ditui.project.busi.groundTaskOrder.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.groundTask.domain.GroundTask;
import net.hongkuang.ditui.project.busi.groundTask.service.IGroundTaskService;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.GroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.groundTaskOrder.domain.SearchGroundTaskOrderVo;
import net.hongkuang.ditui.project.busi.groundTaskOrder.service.IGroundTaskOrderService;
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrderDto;
import net.hongkuang.ditui.project.busi.order.service.ITbTransactionOrderService;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 地推订单管理
 * @author:zy
 * @date: 2019/4/24
 */
@Controller
@RequestMapping("/busi/groundTaskOrder")
public class GroundTaskOrderController extends BaseController {
    private String prefix = "busi/groundTaskOrder";

    @Autowired
    private ITbTransactionOrderService tbTransactionOrderService;

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IGroundTaskService groundTaskService;
    @Autowired
    private IGroundTaskOrderService groundTaskOrderService;
    @Autowired
    private IImgService imgService;

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:groundTaskOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchGroundTaskOrderVo searchGroundTaskOrderVo) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchGroundTaskOrderVo.setTeamId(team.getTeamId());
        }
        startPage();
        List<GroundTaskOrderVo> list = groundTaskOrderService.selectGroundTaskOrderVoByTaskIds(searchGroundTaskOrderVo);
        return getDataTable(list);
    }

    /**
     * 员工未接单
     * @return
     */
    @RequiresPermissions("busi:groundTaskOrder:unFinished:view")
    @GetMapping("/unFinished")
    public String unFinished(ModelMap mmap) {
        mmap.put("role",ShiroUtils.getLoginUserRoles());
        return prefix + "/unFinished";
    }

    /**
     * 员工已接单
     * @return
     */
    @RequiresPermissions("busi:groundTaskOrder:complete:view")
    @GetMapping("/complete")
    public String complete(ModelMap mmap) {
        mmap.put("role",ShiroUtils.getLoginUserRoles());
        return prefix + "/complete";
    }

    /**
     * 今日已完成订单
     * @return
     */
    @RequiresPermissions("busi:groundTaskOrder:toDayFinished:view")
    @GetMapping("/toDayFinished")
    public String toDayFinish(ModelMap mmap) {
        mmap.put("role",ShiroUtils.getLoginUserRoles());
        mmap.put("referStartTime", DateUtils.getDate());
        mmap.put("referEndTime",DateUtils.getDate());
        return prefix + "/toDayFinished";
    }

    /**
     * 历史已完成订单
     * @return
     */
    @RequiresPermissions("busi:groundTaskOrder:historyFinished:view")
    @GetMapping("/historyFinished")
    public String historyFinished(ModelMap mmap) {
        mmap.put("role",ShiroUtils.getLoginUserRoles());
        return prefix + "/historyFinished";
    }

    /**
     * 修改订单
     */
    @GetMapping("/edit/{id}")
    public String waitBuildEdit(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionOrder tbTransactionOrder = tbTransactionOrderService.selectTbTransactionOrderById(id);
        tbTransactionOrder.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionOrder.getCommissionPrice()));
        tbTransactionOrder.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionOrder.getUnitPrice()));

        if (tbTransactionOrder.getTbTransactionQuestion() == null) {
            tbTransactionOrder.setTbTransactionQuestion(new TbTransactionQuestion());
        }
        mmap.put("tbTransactionOrder", tbTransactionOrder);
        mmap.put("managerId", tbTransactionOrder.getManagerId());
        return prefix + "/edit";
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("busi:groundTaskOrder:edit")
    @Log(title = "修改订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TbTransactionOrderDto tbTransactionOrderDto) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrder(tbTransactionOrderDto));
    }

    /**
     * 修改订单
     */
    @GetMapping("/showDetails/{id}")
    public String showDetails(@PathVariable("id") String id, ModelMap mmap) {
        GroundTask groundTask = groundTaskService.selectGroundTaskByOrderId(id);
        List<TbTransactionOrder> tbTransactionOrderList = new ArrayList<>();
        TbTransactionOrder tbTransactionOrder = tbTransactionOrderService.selectTbTransactionOrderByOrderId(id);
        tbTransactionOrderList.add(tbTransactionOrder);
        List<Img> imgs = imgService.selectByTaskId(groundTask.getTaskId());
        groundTask.setOrders(tbTransactionOrderList);
        mmap.put("groundTask", groundTask);
        mmap.put("imgs", imgs);
        return prefix + "/showDetails";
    }

    /**
     * 显示备注
     */
    @GetMapping("/showRemark/{id}")
    public String showTaskRemark(@PathVariable("id") Long id, ModelMap mmap) {
        String ret = "/managerRemark";
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            ret = "/teamRemark";
        }
        mmap.put("tbTransactionOrder", tbTransactionOrderService.selectTbTransactionOrderById(id));
        return prefix + ret;
    }

    /**
     * 显示备注
     */
    @Log(title = "模版备注管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editRemark")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editTaskRemark(TbTransactionOrder tbTransactionOrder) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrderRemark(tbTransactionOrder));
    }

}

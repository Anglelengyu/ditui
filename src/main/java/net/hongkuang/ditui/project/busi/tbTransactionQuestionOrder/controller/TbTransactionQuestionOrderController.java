package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.DateUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrderDto;
import net.hongkuang.ditui.project.busi.order.service.ITbTransactionOrderService;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.ITbTransactionQuestionOrderService;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.SearchTbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.TbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 淘宝问题订单 信息操作处理
 *
 * @author zy
 * @date 2019-4-26
 */
@Controller
@RequestMapping("/busi/tbTransactionQuestionOrder")
public class TbTransactionQuestionOrderController extends BaseController {
    private String prefix = "busi/tbTransactionQuestionOrder";

    @Autowired
    private ITbTransactionQuestionOrderService tbTransactionQuestionOrderService;
    @Autowired
    private ITbTransactionOrderService tbTransactionOrderService;

    @RequiresPermissions("busi:tbTransactionQuestionOrder:view")
    @GetMapping()
    public String order() {
        return prefix + "/tbTransactionQuestionOrder";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:tbTransactionQuestionOrder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchTbTransactionQuestionOrder searchTbTransactionQuestionOrder) {
        startPage();
        List<TbTransactionQuestionOrder> list = tbTransactionQuestionOrderService.selectTbTransactionQuestionOrderList(searchTbTransactionQuestionOrder);
        return getDataTable(list);
    }

    @RequiresPermissions("busi:tbTransactionQuestionOrder:view")
    @GetMapping("/transferQuestionOrder")
    public String transferQuestionOrder() {
        return prefix + "/transferQuestionOrder";
    }

    /**
     * 提交问题订单
     */
    @Log(title = "问题订单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/transferQuestionOrder")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editTransferQuestionOrder(String ids,String questionOrderRemark) {
        String role = "";
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            role = "团队用户";
        }else{
            role = "掌柜用户";
        }
        return toAjax(tbTransactionQuestionOrderService.saveTbTransactionQuestionOrderList(Convert.toStrArray(ids),role,questionOrderRemark));
    }

    /**
     * 显示问题描述
     */
    @GetMapping("/showQuestion/{id}")
    public String showQuestion(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionQuestionOrder tbTransactionQuestionOrder = tbTransactionQuestionOrderService.selectTbTransactionQuestionOrderById(id);

        mmap.put("tbTransactionQuestionOrder", tbTransactionQuestionOrder);
        mmap.put("questionOrderUpdateTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,tbTransactionQuestionOrder.getQuestionOrderUpdateTime()));
        return prefix +"/showQuestion";
    }

    /**
     * 修改问题订单状态和问题
     */
    @Log(title = "模版备注管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editTbTransactionQuestionOrder")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editQuestionOrderStatus(TbTransactionQuestionOrder tbTransactionQuestionOrder) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            tbTransactionQuestionOrder.setQuestionOrderRole("团队用户");
        }else{
            tbTransactionQuestionOrder.setQuestionOrderRole("掌柜用户");
        }
        return toAjax(tbTransactionQuestionOrderService.updateTbTransactionQuestionOrder(tbTransactionQuestionOrder));
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
    @RequiresPermissions("busi:tbTransactionQuestionOrder:edit")
    @Log(title = "修改订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TbTransactionOrderDto tbTransactionOrderDto) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrder(tbTransactionOrderDto));
    }

    /**
     * 显示备注
     */
    @GetMapping("/showRemark/{id}")
    public String showRemark(@PathVariable("id") Long id, ModelMap mmap) {
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
    public AjaxResult editRemark(TbTransactionOrder tbTransactionOrder) {
        return toAjax(tbTransactionOrderService.updateTbTransactionOrderRemark(tbTransactionOrder));
    }


}

package net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.controller;

import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.ITbTransactionQuestionOrderService;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.SearchTbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionQuestionOrder.domain.TbTransactionQuestionOrder;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.TbTransactionQuestion;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    public AjaxResult editTaskRemark(String orderIds,String questionOrderRemark) {
        return toAjax(tbTransactionQuestionOrderService.saveTbTransactionQuestionOrderList(Convert.toStrArray(orderIds),questionOrderRemark));
    }

}

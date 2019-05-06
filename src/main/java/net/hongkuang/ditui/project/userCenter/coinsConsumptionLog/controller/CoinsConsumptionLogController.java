package net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.domain.CoinsConsumptionLog;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.service.ICoinsConsumptionLogService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 充值扣费记录 信息操作处理
 *
 * @author yj
 * @date 2019-04-17
 */
@Controller
@RequestMapping("/busi/coinsConsumptionLog")
public class CoinsConsumptionLogController extends BaseController {
    private String prefix = "busi/coinsConsumptionLog";

    @Autowired
    private ICoinsConsumptionLogService coinsConsumptionLogService;

    @RequiresPermissions("busi:coinsConsumptionLog:view")
    @GetMapping("/coinsConsumptionLog")
    public String coinsConsumptionLog() {
        return prefix + "/coinsConsumptionLog";
    }

    /**
     * 查询充值扣费记录列表
     */
    @RequiresPermissions("busi:coinsConsumptionLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CoinsConsumptionLog coinsConsumptionLog) {
        startPage();
        List<CoinsConsumptionLog> list = coinsConsumptionLogService.selectCoinsConsumptionLogList(coinsConsumptionLog);
        return getDataTable(list);
    }


    /**
     * 导出充值扣费记录列表
     */
    @RequiresPermissions("busi:coinsConsumptionLog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CoinsConsumptionLog coinsConsumptionLog) {
        List<CoinsConsumptionLog> list = coinsConsumptionLogService.selectCoinsConsumptionLogList(coinsConsumptionLog);
        ExcelUtil<CoinsConsumptionLog> util = new ExcelUtil<CoinsConsumptionLog>(CoinsConsumptionLog.class);
        return util.exportExcel(list, "coinsConsumptionLog");
    }

    /**
     * 新增充值扣费记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存充值扣费记录
     */
    @RequiresPermissions("busi:coinsConsumptionLog:add")
    @Log(title = "充值扣费记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CoinsConsumptionLog coinsConsumptionLog) {
        return toAjax(coinsConsumptionLogService.insertCoinsConsumptionLog(coinsConsumptionLog));
    }

    /**
     * 修改充值扣费记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        CoinsConsumptionLog coinsConsumptionLog = coinsConsumptionLogService.selectCoinsConsumptionLogById(id);
        mmap.put("coinsConsumptionLog", coinsConsumptionLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存充值扣费记录
     */
    @RequiresPermissions("busi:coinsConsumptionLog:edit")
    @Log(title = "充值扣费记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CoinsConsumptionLog coinsConsumptionLog) {
        return toAjax(coinsConsumptionLogService.updateCoinsConsumptionLog(coinsConsumptionLog));
    }

    /**
     * 删除充值扣费记录
     */
    @RequiresPermissions("busi:coinsConsumptionLog:remove")
    @Log(title = "充值扣费记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(coinsConsumptionLogService.deleteCoinsConsumptionLogByIds(ids));
    }

}

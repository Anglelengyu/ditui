package net.hongkuang.ditui.project.busi.reserveOnlineTask.controller;

import java.util.List;

import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.project.busi.reserveOnlineTask.domain.ReserveOnlineTask;
import net.hongkuang.ditui.project.busi.reserveOnlineTask.service.IReserveOnlineTaskService;
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
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;

/**
 * 储备任务 信息操作处理
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Controller
@RequestMapping("/busi/reserveOnlineTask")
public class ReserveOnlineTaskController extends BaseController {
    private String prefix = "busi/reserveOnlineTask";

    @Autowired
    private IReserveOnlineTaskService reserveTaskService;

    @RequiresPermissions("busi:reserveTask:view")
    @GetMapping()
    public String reserveTask() {
        return prefix + "/reserveTask";
    }

    /**
     * 查询储备任务列表
     */
    @RequiresPermissions("busi:reserveTask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReserveOnlineTask reserveTask) {
        startPage();
        List<ReserveOnlineTask> list = reserveTaskService.selectReserveTaskList(reserveTask);
        return getDataTable(list);
    }


    /**
     * 导出储备任务列表
     */
    @RequiresPermissions("busi:reserveTask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReserveOnlineTask reserveTask) {
        List<ReserveOnlineTask> list = reserveTaskService.selectReserveTaskList(reserveTask);
        ExcelUtil<ReserveOnlineTask> util = new ExcelUtil<ReserveOnlineTask>(ReserveOnlineTask.class);
        return util.exportExcel(list, "reserveTask");
    }

    /**
     * 新增储备任务
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存储备任务
     */
    @RequiresPermissions("busi:reserveTask:add")
    @Log(title = "储备任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ReserveOnlineTask reserveTask) {
        return toAjax(reserveTaskService.insertReserveTask(reserveTask));
    }

    /**
     * 修改储备任务
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ReserveOnlineTask reserveTask = reserveTaskService.selectReserveTaskById(id);
        mmap.put("reserveTask", reserveTask);
        return prefix + "/edit";
    }

    /**
     * 修改保存储备任务
     */
    @RequiresPermissions("busi:reserveTask:edit")
    @Log(title = "储备任务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ReserveOnlineTask reserveTask) {
        return toAjax(reserveTaskService.updateReserveTask(reserveTask));
    }

    /**
     * 删除储备任务
     */
    @RequiresPermissions("busi:reserveTask:remove")
    @Log(title = "储备任务", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(reserveTaskService.deleteReserveTaskByIds(ids));
    }

}

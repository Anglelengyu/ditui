package net.hongkuang.ditui.project.busi.recommend.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.manager.service.IManagerService;
import net.hongkuang.ditui.project.busi.recommend.domain.RecommendManager;
import net.hongkuang.ditui.project.busi.recommend.domain.SearchRecommendManager;
import net.hongkuang.ditui.project.busi.recommend.service.IRecommendManagerService;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推荐掌柜
 *
 * @author:zy
 * @date: 2019/3/29
 */
@Controller
@RequestMapping("/busi/recommend/manager")
public class RecommendManagerController extends BaseController {

    private String prefix = "busi/recommend/manager";

    @Autowired
    private ITeamService teamService;
    @Autowired
    private IRecommendManagerService recommendManagerService;
    @Autowired
    private IManagerService managerService;


    @RequiresPermissions("busi:recommend:manager:view")
    @GetMapping()
    public String manager() {
        return prefix + "/manager";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:recommend:manager:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchRecommendManager searchRecommendManager) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchRecommendManager.setTeamId(team.getTeamId());
        }
        startPage();
        List<RecommendManager> list = recommendManagerService.selectRecommendManagerList(searchRecommendManager);
        return getDataTable(list);
    }

    /**
     * 新增推荐掌柜
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("teamId", team.getTeamId());
            mmap.put("managerList", managerService.selectManagerByTeamId(team.getTeamId()));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存推荐掌柜
     */
    @RequiresPermissions("busi:recommend:manager:add")
    @Log(title = "推荐掌柜管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(RecommendManager recommendManager) {
        return toAjax(recommendManagerService.insertRecommendManager(recommendManager));
    }

    /**
     * 修改推荐掌柜
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("teamId", team.getTeamId());
            mmap.put("managerList", managerService.selectManagerByTeamId(team.getTeamId()));
        }
        mmap.put("recommendManager", recommendManagerService.selectRecommendManagerById(id));
        return prefix + "/edit";
    }

    /**
     * 修改保存掌柜
     */
    @RequiresPermissions("busi:recommend:manager:edit")
    @Log(title = "推荐掌柜管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(RecommendManager recommendManager) {
        return toAjax(recommendManagerService.updateRecommendManager(recommendManager));
    }

    @RequiresPermissions("busi:recommend:manager:remove")
    @Log(title = "推荐掌柜管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(recommendManagerService.remove(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 显示备注
     */
    @GetMapping("/showRemark/{id}")
    public String showRemark(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("recommendManager", recommendManagerService.selectRecommendManagerById(id));
        return prefix + "/remark";
    }

    /**
     * 显示备注
     */
    @RequiresPermissions("busi:recommend:manager:edit")
    @Log(title = "推荐掌柜管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editRemark")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editRemark(RecommendManager recommendManager) {
        return toAjax(recommendManagerService.updateRecommendManager(recommendManager));
    }

    /**
     * 显示推荐掌柜
     */
    @GetMapping("/showManager/{id}")
    public String showManager(@PathVariable("id") Long id, ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("teamId", team.getTeamId());
            mmap.put("managerList", managerService.selectManagerByTeamId(team.getTeamId()));
        }
        mmap.put("recommendManager", recommendManagerService.selectRecommendManagerById(id));
        return prefix + "/recommendManager";
    }

    /**
     * 显示推荐掌柜
     */
    @RequiresPermissions("busi:recommend:manager:edit")
    @Log(title = "推荐掌柜管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editManager")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editManager(RecommendManager recommendManager) {
        return toAjax(recommendManagerService.updateRecommendManager(recommendManager));
    }


}

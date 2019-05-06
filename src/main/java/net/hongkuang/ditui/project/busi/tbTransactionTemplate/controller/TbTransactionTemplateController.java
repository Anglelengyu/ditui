package net.hongkuang.ditui.project.busi.tbTransactionTemplate.controller;

import com.google.common.collect.Lists;
import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.service.IManagerService;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.*;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.service.ITbKeyWordsService;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.service.ITbTransactionTemplateService;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 淘宝模版
 *
 * @author:zy
 * @date: 2019/4/4
 */
@Controller
@RequestMapping("/busi/tbTransactionTemplate")
public class TbTransactionTemplateController extends BaseController {

    private String prefix = "busi/tbTransactionTemplate";

    @Autowired
    private ITbTransactionTemplateService tbTemplateService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IManagerService managerService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITbKeyWordsService tbKeyWordsService;

    @RequiresPermissions("busi:tbTransactionTemplate:view")
    @GetMapping()
    public String tbTransactionTemplate(ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            mmap.put("status", 0);
        } else {
            mmap.put("status", 1);
        }
        return prefix + "/tbTransactionTemplate";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchTbTransactionTemplate searchTbTransactionTemplate) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            searchTbTransactionTemplate.setTeamId(team.getTeamId());
        } else if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            Manager manager = managerService.selectManagerByUserId(ShiroUtils.getUserId());
            searchTbTransactionTemplate.setManagerId(manager.getUserId());
        }
        startPage();
        List<TbTransactionTemplate> list = tbTemplateService.selectTbTransactionTemplateList(searchTbTransactionTemplate);
        return getDataTable(list);
    }

    /**
     * 新增淘宝模版
     */
    @GetMapping("/add")
    public String add( ModelMap mmap) {
        User user = userService.selectUserById(ShiroUtils.getUserId());
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("加购");
        itemHeaders.add("收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("appNumber");
        itemFields.add("pcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("collectionCartNumber");
        mmap.put("user", user);
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        return prefix + "/add";
    }

    /**
     * 新增保存淘宝模版
     */
    @RequiresPermissions("busi:tbTransactionTemplate:add")
    @Log(title = "淘宝模版管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(TbTransactionTemplateDto tbTransactionTemplateDto) {
        return toAjax(tbTemplateService.insertTbTransactionTemplate(tbTransactionTemplateDto));
    }

    /**
     * 模版关键词列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:list")
    @GetMapping("/keyWordsList/{id}")
    public String keyWordsList(@PathVariable Long id, ModelMap mmap) {
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("加购");
        itemHeaders.add("收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("appNumber");
        itemFields.add("pcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("collectionCartNumber");
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);

        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        tbTransactionTemplate.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getCommissionPrice()));
        tbTransactionTemplate.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getUnitPrice()));
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        return prefix + "/keyWordsList";
    }

    /**
     * 修改保存模版关键词
     */
    @RequiresPermissions("busi:tbTransactionTemplate:edit")
    @Log(title = "模版关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TbTransactionTemplateDto tbTransactionTemplateDto) {
        return toAjax(tbKeyWordsService.updateTbTransactionKeyWords(tbTransactionTemplateDto));
    }

    /**
     * 修改模版
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        tbTransactionTemplate.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getCommissionPrice()));
        tbTransactionTemplate.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("加购");
        itemHeaders.add("收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("appNumber");
        itemFields.add("pcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("collectionCartNumber");
        if (tbTransactionTemplate.getTbTransactionQuestion() == null) {
            tbTransactionTemplate.setTbTransactionQuestion(new TbTransactionQuestion());
        }
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        return prefix + "/edit";
    }

    /**
     * 提交模版
     */
    @GetMapping("/refer/{id}")
    public String refer(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        tbTransactionTemplate.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getCommissionPrice()));
        tbTransactionTemplate.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("加购");
        itemHeaders.add("收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("appNumber");
        itemFields.add("pcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("collectionCartNumber");
        if (tbTransactionTemplate.getTbTransactionQuestion() == null) {
            tbTransactionTemplate.setTbTransactionQuestion(new TbTransactionQuestion());
        }
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        return prefix + "/refer";
    }

    /**
     * 提交保存模版关键词
     */
    @RequiresPermissions("busi:tbTransactionTemplate:refer")
    @Log(title = "模版关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/refer")
    @ResponseBody
    public AjaxResult referSave(TbTransactionTemplateDto tbTransactionTemplateDto) {
        return toAjax(tbTemplateService.referTbTransactionTemplate(tbTransactionTemplateDto));
    }

    /**
     * 提交模版
     */
    @GetMapping("/againRefer/{id}")
    public String againRefer(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        tbTransactionTemplate.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getCommissionPrice()));
        tbTransactionTemplate.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("加购");
        itemHeaders.add("收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("appNumber");
        itemFields.add("pcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("collectionCartNumber");
        if (tbTransactionTemplate.getTbTransactionQuestion() == null) {
            tbTransactionTemplate.setTbTransactionQuestion(new TbTransactionQuestion());
        }
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        return prefix + "/refer_again";
    }

    /**
     * 重新提交保存模版
     */
    @RequiresPermissions("busi:tbTransactionTemplate:refer")
    @Log(title = "模版", businessType = BusinessType.UPDATE)
    @PostMapping("/againRefer")
    @ResponseBody
    public AjaxResult againReferSave(TbTransactionTemplateDto tbTransactionTemplateDto) {
        return toAjax(tbTemplateService.againReferTbTransactionTemplate(tbTransactionTemplateDto));
    }


    /**
     * 查询待审核订单列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:waitcheck:view")
    @RequestMapping("/waitcheck")
    public String waitcheck(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, ModelMap mmap) {
        mmap.put("status", 1);
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("teamId", team.getTeamId());
        }
        return prefix + "/tbTransactionTemplate_waitcheck";
    }

    /**
     * 查询审核未通过订单列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:noadoptcheck:view")
    @RequestMapping("/noadoptcheck")
    public String noadoptcheck(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("status", 3);
            mmap.put("teamId", team.getTeamId());
        } else {
            mmap.put("status", 3);
        }
        return prefix + "/tbTransactionTemplate_noadoptcheck";
    }

    /**
     * 待审核修改模版
     */
    @GetMapping("/waitcheck/edit/{id}")
    public String waitcheckEdit(@PathVariable("id") Long id, ModelMap mmap) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        tbTransactionTemplate.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getCommissionPrice()));
        tbTransactionTemplate.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("加购");
        itemHeaders.add("收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("appNumber");
        itemFields.add("pcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("collectionCartNumber");
        if (tbTransactionTemplate.getTbTransactionQuestion() == null) {
            tbTransactionTemplate.setTbTransactionQuestion(new TbTransactionQuestion());
        }
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        mmap.put("managerId", tbTransactionTemplate.getManagerId());
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        return prefix + "/waitcheck_edit";
    }

    /**
     * 修改保存模版
     */
    @RequiresPermissions("busi:tbTransactionTemplate:edit")
    @Log(title = "模版关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/waitcheck/edit")
    @ResponseBody
    public AjaxResult waitcheckEditSave(TbTransactionTemplateDto tbTransactionTemplateDto) {
        return toAjax(tbTemplateService.updateWaitcheckTbTransactionTemplate(tbTransactionTemplateDto));
    }

    /**
     * 通过订单
     */
    @RequiresPermissions("busi:tbTransactionTemplate:waitcheck:check")
    @Log(title = "淘宝模版", businessType = BusinessType.UPDATE)
    @PostMapping("/executeCheck")
    @ResponseBody
    public AjaxResult executeCheck(String ids, Integer status) {
        return toAjax(tbTemplateService.executeCheck(ids, status));
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
        mmap.put("tbTransactionTemplate", tbTemplateService.selectTbTransactionTemplateById(id));
        return prefix + ret;
    }

    /**
     * 显示备注
     */
    @RequiresPermissions("busi:tbTransactionTemplate:edit")
    @Log(title = "模版备注管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editRemark")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editRemark(TbTransactionTemplateDto tbTransactionTemplateDto) {
        return toAjax(tbTemplateService.updateTbTransactionTemplateRemark(tbTransactionTemplateDto));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("busi:tbTransactionTemplate:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tbTemplateService.deleteTbTransactionTemplateByIds(ids));
    }

    /**
     * 校验模版名称
     */
    @PostMapping("/checkTemplateNameUnique")
    @ResponseBody
    public String checkTemplateNameUnique(TbTransactionTemplate tbTransactionTemplate) {
        return tbTemplateService.checkTemplateNameUnique(tbTransactionTemplate.getTemplateName(), tbTransactionTemplate.getManagerId());
    }

    /**
     * 查询待拆分订单列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:adoptcheck:view")
    @RequestMapping("/adoptcheck")
    public String adoptcheck(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_TEAM)) {
            Team team = teamService.getTeamByUserId(ShiroUtils.getUserId());
            mmap.put("teamId", team.getTeamId());
        }
        mmap.put("status", 2);
        return prefix + "/tbTransactionTemplate_adoptcheck";
    }

    /**
     * 模版关键词列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:adoptcheck:splitTemplate")
    @GetMapping("/keyWordsSplitList/{id}")
    public String keyWordsSplitList(@PathVariable Long id, ModelMap mmap) {
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("单量");
        itemHeaders.add("拆分单量");
        itemHeaders.add("手机端");
        itemHeaders.add("拆分手机端");
        itemHeaders.add("电脑端");
        itemHeaders.add("拆分电脑端");
        itemHeaders.add("收藏");
        itemHeaders.add("拆分收藏");
        itemHeaders.add("加购");
        itemHeaders.add("拆分加购");
        itemHeaders.add("收藏+加购");
        itemHeaders.add("拆分收藏+加购");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("name");
        itemFields.add("totalNumber");
        itemFields.add("splitTotalNumber");
        itemFields.add("appNumber");
        itemFields.add("splitAppNumber");
        itemFields.add("pcNumber");
        itemFields.add("splitPcNumber");
        itemFields.add("collectionNumber");
        itemFields.add("splitCollectionNumber");
        itemFields.add("cartNumber");
        itemFields.add("splitCartNumber");
        itemFields.add("collectionCartNumber");
        itemFields.add("splitCollectionCartNumber");
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);

        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        tbTransactionTemplate.setCommissionPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getCommissionPrice()));
        tbTransactionTemplate.setUnitPriceYuan(UnitUtils.unitYuanString(tbTransactionTemplate.getUnitPrice()));
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        return prefix + "/keyWordsSplitList";
    }

    /**
     * 拆分模版
     */
    @RequiresPermissions("busi:tbTransactionTemplate:edit")
    @Log(title = "模版关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/splitTemplate")
    @ResponseBody
    public AjaxResult splitTemplateEditSave(TbSplitTransactionTemplateDto tbSplitTransactionTemplateDto) {
        return toAjax(tbTemplateService.splitTemplate(tbSplitTransactionTemplateDto));
    }

    /**
     * 获取关键词数量
     */
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @PostMapping("/getTbTransactionTemplateKeyWordsTotal")
    @ResponseBody
    public AjaxResult getTbTransactionTemplateKeyWordsTotal(String ids) {
        return tbTemplateService.getTbTransactionTemplateKeyWordsTotal(ids);
    }

    /**
     * 拆分订单
     */
    @RequiresPermissions("busi:tbTransactionTemplate:adoptcheck:splitOrder")
    @Log(title = "模版关键词", businessType = BusinessType.UPDATE)
    @PostMapping("/splitOrder")
    @ResponseBody
    public AjaxResult splitOrder(String ids) {
        return toAjax(tbTemplateService.splitOrder(ids));
    }

    /**
     * 判断模版是否已经分配
     */
    @RequiresPermissions("busi:tbTransactionTemplate:adoptcheck:distributionTeam")
    @GetMapping("/judgeIsDistribution/{id}")
    @ResponseBody
    public AjaxResult judgeIsDistribution(@PathVariable Long id) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        boolean flag = true;
        if (tbTransactionTemplate.getReceiptTeamId() != null && tbTransactionTemplate.getReceiptTeamId() != 0) {
            flag = false;
        }
        return toAjax(flag);
    }

    /**
     * 合作团队列表
     */
    @RequiresPermissions("busi:tbTransactionTemplate:adoptcheck:distributionTeam")
    @GetMapping("/distributionTeam/{id}")
    public String distributionTeam(@PathVariable Long id, ModelMap mmap) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        mmap.put("teamList", teamService.selectTeamMiddleTeamById(tbTransactionTemplate.getTeamId()));
        return prefix + "/distributionTeam";
    }

    /**
     * 分配给合作团队
     */
    @RequiresPermissions("busi:tbTransactionTemplate:adoptcheck:distributionTeam")
    @ResponseBody
    @PostMapping("/distributionTeam/{id}")
    public AjaxResult distributionTeamSave(@PathVariable Long id, Long teamId, String teamName, ModelMap mmap) {
        TbTransactionTemplate tbTransactionTemplate = tbTemplateService.selectTbTransactionTemplateById(id);
        mmap.put("tbTransactionTemplate", tbTransactionTemplate);
        mmap.put("teamList", teamService.selectTeamMiddleTeamById(tbTransactionTemplate.getTeamId()));
        return toAjax(tbTemplateService.distributionTeam(id, teamId, teamName));
    }

}

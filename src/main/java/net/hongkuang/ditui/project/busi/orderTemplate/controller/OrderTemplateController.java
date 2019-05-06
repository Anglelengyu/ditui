package net.hongkuang.ditui.project.busi.orderTemplate.controller;

import com.google.common.collect.Lists;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplate;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateDto;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateKeyWords;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.SearchOrderTemplate;
import net.hongkuang.ditui.project.busi.orderTemplate.service.IOrderTemplateKeyWordsService;
import net.hongkuang.ditui.project.busi.orderTemplate.service.IOrderTemplateService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单模版类
 *
 * @author:zy
 * @date: 2019/2/25
 */
@Controller
@RequestMapping("/busi/order/template")
public class OrderTemplateController extends BaseController {
    private String prefix = "busi/order/template";

    @Autowired
    private IOrderTemplateService orderTemplateService;
    @Autowired
    private IOrderTemplateKeyWordsService orderTemplateKeyWordsService;
    @Autowired
    private IUserService userService;

    @RequiresPermissions("busi:order:template:view")
    @GetMapping()
    public String template() {
        return prefix + "/template";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("busi:order:template:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SearchOrderTemplate order) {
        startPage();
        List<OrderTemplate> list = orderTemplateService.selectOrderTemplateList(order);
        return getDataTable(list);
    }

    /**
     * 模版关键词列表
     */
    @RequiresPermissions("busi:order:template:list")
    @GetMapping("/keyWordsList/{id}")
    public String keyWordsList(@PathVariable Long id, ModelMap mmap) {
        List<OrderTemplateKeyWords> orderTemplateKeyWords = orderTemplateKeyWordsService.selectOrderTemplateKeyWordsList(id);
        mmap.put("orderTemplateKeyWords", orderTemplateKeyWords);
        return prefix + "/keyWordsList";
    }

    /**
     * 新增模版
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        User user = userService.selectUserById(ShiroUtils.getUserId());
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("数量");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("keywords");
        itemFields.add("num");
        mmap.put("user", user);
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        return prefix + "/add";
    }

    /**
     * 新增保存模版
     */
    @RequiresPermissions("busi:order:template:import")
    @Log(title = "订单模版", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderTemplateDto orderDto) {
        return toAjax(orderTemplateService.insertOrderTemplate(orderDto));
    }

    /**
     * 修改模版
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        OrderTemplate order = orderTemplateService.selectOrderTemplateById(id);
        order.setCommissionYuan(UnitUtils.unitYuanString(order.getCommission()));
        order.setUnitPriceYuan(UnitUtils.unitYuanString(order.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("数量");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("keywords");
        itemFields.add("num");
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        mmap.put("order", order);
        return prefix + "/edit";
    }

    /**
     * 修改保存模版
     */
    @RequiresPermissions("busi:order:template:edit")
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderTemplateDto orderTemplateDto) {
        return toAjax(orderTemplateService.updateOrderTemplate(orderTemplateDto));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("busi:order:template:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(orderTemplateService.deleteOrderTemplateByIds(ids));
    }

    /**
     * 提交模版
     */
    @GetMapping("/refer/{id}")
    public String refer(@PathVariable("id") Long id, ModelMap mmap) {
        OrderTemplate order = orderTemplateService.selectOrderTemplateById(id);
        order.setCommissionYuan(UnitUtils.unitYuanString(order.getCommission()));
        order.setUnitPriceYuan(UnitUtils.unitYuanString(order.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("数量");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("keywords");
        itemFields.add("num");
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        mmap.put("order", order);
        return prefix + "/refer";
    }

    /**
     * 提交模版
     */
    @RequiresPermissions("busi:order:template:edit")
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @PostMapping("/refer")
    @ResponseBody
    public AjaxResult referSave(OrderTemplateDto orderTemplateDto) {
        return toAjax(orderTemplateService.referOrderTemplate(orderTemplateDto));
    }

    /**
     * 查询待审核订单列表
     */
    @RequiresPermissions("busi:order:template:waitcheck:view")
    @RequestMapping("/waitcheck")
    public String waitcheck(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, ModelMap mmap) {
        mmap.put("shopManagerId", shopManagerId);
        mmap.put("shopId", shopId);
        return prefix + "/orderTemplate_waitcheck";
    }

    /**
     * 查询审核通过订单列表
     */
    @RequiresPermissions("busi:order:template:adoptcheck:view")
    @RequestMapping("/adoptcheck")
    public String adoptcheck(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, ModelMap mmap) {
        mmap.put("shopManagerId", shopManagerId);
        mmap.put("shopId", shopId);
        return prefix + "/orderTemplate_adoptcheck";
    }

    /**
     * 查询审核未通过订列表
     */
    @RequiresPermissions("busi:order:template:noadoptcheck:view")
    @RequestMapping("/noadoptcheck")
    public String noadoptcheck(@RequestParam(required = false) Long shopManagerId, @RequestParam(required = false) String shopId, ModelMap mmap) {
        mmap.put("shopManagerId", shopManagerId);
        mmap.put("shopId", shopId);
        return prefix + "/orderTemplate_noadoptcheck";
    }

    /**
     * 通过订单
     */
    @RequiresPermissions("busi:order:template:waitcheck:check")
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @PostMapping("/executeCheck")
    @ResponseBody
    public AjaxResult executeCheck(String ids, Integer status) {
        return toAjax(orderTemplateService.executeCheck(ids, status));
    }

    /**
     * 重新提交模版
     */
    @GetMapping("/againRefer/{id}")
    public String againRefer(@PathVariable("id") Long id, ModelMap mmap) {
        OrderTemplate order = orderTemplateService.selectOrderTemplateById(id);
        order.setCommissionYuan(UnitUtils.unitYuanString(order.getCommission()));
        order.setUnitPriceYuan(UnitUtils.unitYuanString(order.getUnitPrice()));
        List<String> itemHeaders = Lists.newArrayList();
        itemHeaders.add("关键词");
        itemHeaders.add("数量");
        List<String> itemFields = Lists.newArrayList();
        itemFields.add("keywords");
        itemFields.add("num");
        mmap.put("itemHeaders", itemHeaders);
        mmap.put("itemFields", itemFields);
        mmap.put("order", order);
        return prefix + "/refer_again";
    }

    /**
     * 重新提交模版
     */
    @RequiresPermissions("busi:order:template:noadoptcheck:again")
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @PostMapping("/againRefer")
    @ResponseBody
    public AjaxResult againReferSave(OrderTemplateDto orderTemplateDto) {
        return toAjax(orderTemplateService.againReferOrderTemplate(orderTemplateDto));
    }

    /**
     * 获取关键词数量
     */
    @RequiresPermissions("busi:order:template:adoptcheck:split")
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @GetMapping("/getOrderTemplateKeyWordsTotal/{id}")
    @ResponseBody
    public AjaxResult getOrderTemplateKeyWordsTotal(@PathVariable("id") Long id) {
        return AjaxResult.success().put("num", orderTemplateKeyWordsService.getOrderTemplateKeyWordsCount(id));
    }

    /**
     * 拆分订单
     */
    @RequiresPermissions("busi:order:template:adoptcheck:split")
    @Log(title = "订单模版", businessType = BusinessType.UPDATE)
    @PostMapping("/split/{id}")
    @ResponseBody
    public AjaxResult split(@PathVariable("id") Long id, Integer num) {
        return toAjax(orderTemplateService.split(id, num));
    }

}

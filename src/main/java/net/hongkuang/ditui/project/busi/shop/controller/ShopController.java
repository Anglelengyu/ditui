package net.hongkuang.ditui.project.busi.shop.controller;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.Log;
import net.hongkuang.ditui.framework.aspectj.lang.enums.BusinessType;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.service.IManagerService;
import net.hongkuang.ditui.project.busi.shop.domain.Shop;
import net.hongkuang.ditui.project.busi.shop.service.IShopService;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 店铺 信息操作处理
 *
 * @author yj
 * @date 2018-12-30
 */
@Controller
@RequestMapping("/busi/shop")
public class ShopController extends BaseController {
    private String prefix = "busi/shop";

    @Autowired
    private IShopService shopService;

    private IUserService userService;
    @Autowired
    private IManagerService managerService;

    @RequiresPermissions("busi:shop:view")
    @GetMapping()
    public String shop() {
        return prefix + "/shop";
    }

    @RequiresPermissions("busi:shop:refer:view")
    @GetMapping("/refer")
    public String referShop() {
        return prefix + "/referShop";
    }

    /**
     * 查询店铺列表
     */
    @RequiresPermissions("busi:shop:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Shop shop) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            Manager manager = managerService.selectManagerByUserId(ShiroUtils.getUserId());
            shop.setManagerId(manager.getUserId() + "");
        }
        startPage();
        List<Shop> list = shopService.selectShopList(shop);
        return getDataTable(list);
    }

    /**
     * 查询推荐店铺列表
     */
    @RequiresPermissions("busi:shop:refer:list")
    @PostMapping("/referList")
    @ResponseBody
    public TableDataInfo referList(Shop shop) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            Manager manager = managerService.selectManagerByUserId(ShiroUtils.getUserId());
            shop.setManagerId(manager.getUserId() + "");
        }
        startPage();
        List<Shop> list = shopService.selectReferShopList(shop);
        return getDataTable(list);
    }

    /**
     * 导出店铺列表
     */
    @Log(title = "店铺管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("busi:shop:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Shop shop) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            Manager manager = managerService.selectManagerByUserId(ShiroUtils.getUserId());
            shop.setManagerId(manager.getUserId() + "");
        }
        List<Shop> list = shopService.selectShopList(shop);
        ExcelUtil<Shop> util = new ExcelUtil<Shop>(Shop.class);
        return util.exportExcel(list, "shop");
    }

    /**
     * 导出推荐店铺列表
     */
    @Log(title = "店铺管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("busi:shop:export")
    @PostMapping("/referExport")
    @ResponseBody
    public AjaxResult referExport(Shop shop) {
        List<Shop> list = shopService.selectReferShopList(shop);
        ExcelUtil<Shop> util = new ExcelUtil<Shop>(Shop.class);
        return util.exportExcel(list, "shop");
    }

    /**
     * 新增店铺
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            Manager manager = managerService.selectManagerByUserId(ShiroUtils.getUserId());
            mmap.put("user", manager);
        }
        return prefix + "/add";
    }

    /**
     * 新增保存店铺
     */
    @RequiresPermissions("busi:shop:add")
    @Log(title = "店铺", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Shop shop) {
        return toAjax(shopService.insertShop(shop));
    }

    /**
     * 修改店铺
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Shop shop = shopService.selectShopById(id);
        mmap.put("shop", shop);
        return prefix + "/edit";
    }

    /**
     * 修改保存店铺
     */
    @RequiresPermissions("busi:shop:edit")
    @Log(title = "店铺", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Shop shop) {
        return toAjax(shopService.updateShop(shop));
    }

    /**
     * 删除店铺
     */
    @RequiresPermissions("busi:shop:remove")
    @Log(title = "店铺", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(shopService.deleteShopByIds(ids));
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectUser/{userId}")
    public String selectMenuTree(@PathVariable("userId") Long userId, ModelMap mmap) {
        if (userId != 0) {
            mmap.put("user", userService.selectUserById(userId));
        } else {
            mmap.put("user", new User());
        }
        return prefix + "/user";
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public String checkNameUnique(Shop shop) {
        return shopService.checkNameUnique(shop);
    }

    /**
     * 校验旺旺名称唯一
     */
    @PostMapping("/checkWangwangUnique")
    @ResponseBody
    public String checkWangwangUnique(Shop shop) {
        return shopService.checkWangwangUnique(shop);
    }

}

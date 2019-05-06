package net.hongkuang.ditui.project.busi.img.controller;

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
import net.hongkuang.ditui.project.busi.img.domain.Img;
import net.hongkuang.ditui.project.busi.img.service.IImgService;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.framework.web.page.TableDataInfo;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;

/**
 * 移动端上传图片 信息操作处理
 *
 * @author yj
 * @date 2019-01-09
 */
@Controller
@RequestMapping("/busi/img")
public class ImgController extends BaseController {
    private String prefix = "busi/img";

    @Autowired
    private IImgService imgService;

    @RequiresPermissions("busi:img:view")
    @GetMapping()
    public String img() {
        return prefix + "/img";
    }

    /**
     * 查询移动端上传图片列表
     */
    @RequiresPermissions("busi:img:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Img img) {
        startPage();
        List<Img> list = imgService.selectImgList(img);
        return getDataTable(list);
    }


    /**
     * 导出移动端上传图片列表
     */
    @RequiresPermissions("busi:img:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Img img) {
        List<Img> list = imgService.selectImgList(img);
        ExcelUtil<Img> util = new ExcelUtil<Img>(Img.class);
        return util.exportExcel(list, "img");
    }

    /**
     * 新增移动端上传图片
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存移动端上传图片
     */
    @RequiresPermissions("busi:img:add")
    @Log(title = "移动端上传图片", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Img img) {
        return toAjax(imgService.insertImg(img));
    }

    /**
     * 修改移动端上传图片
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Img img = imgService.selectImgById(id);
        mmap.put("img", img);
        return prefix + "/edit";
    }

    /**
     * 修改保存移动端上传图片
     */
    @RequiresPermissions("busi:img:edit")
    @Log(title = "移动端上传图片", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Img img) {
        return toAjax(imgService.updateImg(img));
    }

    /**
     * 删除移动端上传图片
     */
    @RequiresPermissions("busi:img:remove")
    @Log(title = "移动端上传图片", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(imgService.deleteImgByIds(ids));
    }

}

package net.hongkuang.ditui.project.api.controller;

import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.service.IExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 平台相关API
 *
 * @author:zy
 * @date: 2019/4/9
 */
@RestController
@RequestMapping("/external/api")
public class ExternalApiController {

    @Autowired
    private IExternalApiService externalApiService;

    @GetMapping("/searchGoodsDetail/{goodsId}")
    @ResponseBody
    public AjaxResult searchGoodsDetail(@PathVariable("goodsId") String goodsId) {
        try {
            return externalApiService.searchGoodsDetail(goodsId);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}

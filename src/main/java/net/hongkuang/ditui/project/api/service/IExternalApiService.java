package net.hongkuang.ditui.project.api.service;


import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.Goods;

/**
 * 平台相关API
 *
 * @author:zy
 * @date: 2019/4/9
 */
public interface IExternalApiService {

    AjaxResult searchGoodsDetail(String goodsId);

}

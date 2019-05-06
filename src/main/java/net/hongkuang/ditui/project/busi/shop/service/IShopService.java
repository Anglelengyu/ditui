package net.hongkuang.ditui.project.busi.shop.service;

import net.hongkuang.ditui.project.busi.shop.domain.Shop;

import java.util.List;

/**
 * 店铺 服务层
 *
 * @author yj
 * @date 2018-12-30
 */
public interface IShopService {
    /**
     * 查询店铺信息
     *
     * @param id 店铺ID
     * @return 店铺信息
     */
    Shop selectShopById(Long id);

    /**
     * 查询店铺列表
     *
     * @param shop 店铺信息
     * @return 店铺集合
     */
    List<Shop> selectShopList(Shop shop);

    /**
     * 查询推荐店铺列表
     *
     * @param shop 店铺信息
     * @return 店铺集合
     */
    List<Shop> selectReferShopList(Shop shop);

    /**
     * 新增店铺
     *
     * @param shop 店铺信息
     * @return 结果
     */
    int insertShop(Shop shop);

    /**
     * 修改店铺
     *
     * @param shop 店铺信息
     * @return 结果
     */
    int updateShop(Shop shop);

    /**
     * 删除店铺信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteShopByIds(String ids);

    /**
     * 检查店铺名称唯一
     *
     * @param shop
     * @return
     */
    String checkNameUnique(Shop shop);

    String checkWangwangUnique(Shop shop);
}

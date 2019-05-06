package net.hongkuang.ditui.project.busi.shop.mapper;

import net.hongkuang.ditui.project.api.dto.OrderRespDto;
import net.hongkuang.ditui.project.busi.shop.domain.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店铺 数据层
 *
 * @author yj
 * @date 2018-12-30
 */
public interface ShopMapper {
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
     * 删除店铺
     *
     * @param id 店铺ID
     * @return 结果
     */
    int deleteShopById(Long id);

    /**
     * 批量删除店铺
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteShopByIds(String[] ids);

    int checkNameUnique(String name);

    Shop selectShopByName(String shopName);

    List<String> selectShopIdByManagerId(@Param("managerId") Long managerId);

    OrderRespDto selectShopByOrderId(String orderId);

    int checkWangwangUnique(String wangwang);
}
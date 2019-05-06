package net.hongkuang.ditui.project.busi.shop.service.impl;

import net.hongkuang.ditui.common.constant.Constants;
import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.shop.domain.Shop;
import net.hongkuang.ditui.project.busi.shop.mapper.ShopMapper;
import net.hongkuang.ditui.project.busi.shop.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 店铺 服务层实现
 *
 * @author yj
 * @date 2018-12-30
 */
@Service
public class ShopServiceImpl implements IShopService {
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 查询店铺信息
     *
     * @param id 店铺ID
     * @return 店铺信息
     */
    @Override
    public Shop selectShopById(Long id) {
        return shopMapper.selectShopById(id);
    }

    /**
     * 查询店铺列表
     *
     * @param shop 店铺信息
     * @return 店铺集合
     */
    @Override
    public List<Shop> selectShopList(Shop shop) {
        // 获取用户当前类型
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            shop.setManagerId(ShiroUtils.getUserId().toString());
        }
        return shopMapper.selectShopList(shop);
    }

    /**
     * 查询推荐店铺列表
     *
     * @param shop 店铺信息
     * @return 店铺集合
     */
    @Override
    public List<Shop> selectReferShopList(Shop shop) {
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_REFER)) {
            shop.setReferUserId(ShiroUtils.getUserId().toString());
        }
        return shopMapper.selectShopList(shop);
    }

    /**
     * 新增店铺
     *
     * @param shop 店铺信息
     * @return 结果
     */
    @Override
    public int insertShop(Shop shop) {
        shop.setShopId(RandomUtil.genString());
        shop.setCreateBy(ShiroUtils.getLoginName());
        return shopMapper.insertShop(shop);
    }

    /**
     * 修改店铺
     *
     * @param shop 店铺信息
     * @return 结果
     */
    @Override
    public int updateShop(Shop shop) {
        shop.setUpdateBy(ShiroUtils.getLoginName());
        return shopMapper.updateShop(shop);
    }

    /**
     * 删除店铺对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteShopByIds(String ids) {
        return shopMapper.deleteShopByIds(Convert.toStrArray(ids));
    }

    @Override
    public String checkNameUnique(Shop shop) {
        int count = shopMapper.checkNameUnique(shop.getName());
        if (count > 0) {
            return Constants.SHOP_NAME_NOT_UNIQUE;
        }
        return Constants.SHOP_NAME_UNIQUE;
    }

    @Override
    public String checkWangwangUnique(Shop shop) {
        int count = shopMapper.checkWangwangUnique(shop.getWangwang());
        if (count > 0) {
            return Constants.SHOP_NAME_NOT_UNIQUE;
        }
        return Constants.SHOP_NAME_UNIQUE;
    }

}

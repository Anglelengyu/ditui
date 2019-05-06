package net.hongkuang.ditui.project.busi.orderTemplate.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.idSegment.service.IIdSegmentService;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplate;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateDto;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.OrderTemplateKeyWords;
import net.hongkuang.ditui.project.busi.orderTemplate.domain.SearchOrderTemplate;
import net.hongkuang.ditui.project.busi.orderTemplate.enums.OrderTemplateStatus;
import net.hongkuang.ditui.project.busi.orderTemplate.mapper.OrderTemplateKeyWordsMapper;
import net.hongkuang.ditui.project.busi.orderTemplate.mapper.OrderTemplateMapper;
import net.hongkuang.ditui.project.busi.shop.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单模版 服务层实现
 *
 * @author:zy
 * @date: 2019/2/25
 */
@Service
public class OrderTemplateServiceImpl implements IOrderTemplateService {
    private final int BATCH_ORDER_SIZE = 50;

    @Autowired
    private OrderTemplateMapper orderTemplateMapper;
    @Autowired
    private OrderTemplateKeyWordsMapper orderTemplateKeyWordsMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private IIdSegmentService idSegmentService;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
    public OrderTemplate selectOrderTemplateById(Long id) {
        return orderTemplateMapper.selectOrderTemplateById(id);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    @Override
    public List<OrderTemplate> selectOrderTemplateList(SearchOrderTemplate order) {
        order.setShopIdList(getShopIdList(order.getShopManagerId(), order.getShopId()));
        // remove shopId
        if (StringUtils.isNotEmpty(order.getShopId())) {
            order.setShopId(null);
        }
        if (!StringUtils.isEmpty(order.getStartTime())) {
            order.setStartTime(order.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(order.getEndTime())) {
            order.setEndTime(order.getEndTime() + " 23:59:59");
        }
        if (!StringUtils.isEmpty(order.getTaskCompletionStartTime())) {
            order.setTaskCompletionStartTime(order.getTaskCompletionStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(order.getTaskCompletionEndTime())) {
            order.setTaskCompletionEndTime(order.getTaskCompletionEndTime() + " 23:59:59");
        }
        List<OrderTemplate> orderList = null;
        if (order.getHasExtend() == null) {
            orderList = orderTemplateMapper.selectOrderTemplateList(order);
        } else {
        }
        return orderList;
    }

    /**
     * 新增订单
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertOrderTemplate(OrderTemplateDto orderDto) {
        OrderTemplate order = new OrderTemplate();
        BeanUtils.copyBeanProp(order, orderDto);
        order.setUnitPrice(UnitUtils.unitYuan(orderDto.getUnitPrice()));
        order.setCommission(UnitUtils.unitYuan(orderDto.getCommission()));
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setStatus(OrderTemplateStatus.WAITREFER.getCode());
        int orderId = orderTemplateMapper.insertOrderTemplate(order);
        order.getOrderTemplateKeyWords().forEach(orderTemplateKeyWords -> {
            orderTemplateKeyWords.setOrderTemplateId(order.getId());
            orderTemplateKeyWordsMapper.insertOrderTemplateKeyWords(orderTemplateKeyWords);
        });
        return orderId;
    }

    /**
     * 修改订单
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateOrderTemplate(OrderTemplateDto orderDto) {
        if (orderDto.getId() == null) {
            return 1;
        }
        OrderTemplate order = new OrderTemplate();
        BeanUtils.copyBeanProp(order, orderDto);
        if (!StringUtils.isEmpty(orderDto.getUnitPriceYuan())) {
            order.setUnitPrice(UnitUtils.unitYuan(new BigDecimal(orderDto.getUnitPriceYuan())));
        }
        if (!StringUtils.isEmpty(orderDto.getCommissionYuan())) {
            order.setCommission(UnitUtils.unitYuan(new BigDecimal(orderDto.getCommissionYuan())));
        }
        order.setUpdateTime(new Date());
        orderTemplateKeyWordsMapper.deleteOrderTemplateKeyWordsByTemplateId(order.getId());
        order.getOrderTemplateKeyWords().forEach(orderTemplateKeyWords -> {
            orderTemplateKeyWords.setOrderTemplateId(order.getId());
            orderTemplateKeyWordsMapper.insertOrderTemplateKeyWords(orderTemplateKeyWords);
        });
        return orderTemplateMapper.updateOrderTemplate(order);
    }

    public List<String> getShopIdList(Long shopManagerId, String shopId) {
        if (StringUtils.isNotEmpty(shopId)) {
            return Arrays.asList(shopId);
        }
        if (shopManagerId != null) {
            List<String> shopList = shopMapper.selectShopIdByManagerId(shopManagerId);
            if (shopList == null || shopList.isEmpty()) {
                return Arrays.asList("-1");
            }
            return shopList;
        }
        // 获取用户当前类型
        if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
            List<String> shopList = shopMapper.selectShopIdByManagerId(ShiroUtils.getUserId());
            if (shopList == null || shopList.isEmpty()) {
                return Arrays.asList("-1");
            }
            return shopList;
        }
        return null;
    }

    /**
     * 删除模版对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteOrderTemplateByIds(String ids) {
        orderTemplateKeyWordsMapper.deleteOrderTemplateKeyWordsByTemplateIds(Convert.toStrArray(ids));
        return orderTemplateMapper.deleteOrderTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 提交订单(去审核)
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int referOrderTemplate(OrderTemplateDto orderDto) {
        OrderTemplate order = new OrderTemplate();
        BeanUtils.copyBeanProp(order, orderDto);
        order.setUnitPrice(UnitUtils.unitYuan(new BigDecimal(orderDto.getUnitPriceYuan())));
        order.setCommission(UnitUtils.unitYuan(new BigDecimal(orderDto.getCommissionYuan())));
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setStatus(OrderTemplateStatus.WAITCHECK.getCode());
        order.setId(null);
        int orderId = orderTemplateMapper.insertOrderTemplate(order);
        order.getOrderTemplateKeyWords().forEach(orderTemplateKeyWords -> {
            orderTemplateKeyWords.setOrderTemplateId(order.getId());
            orderTemplateKeyWords.setId(null);
            orderTemplateKeyWordsMapper.insertOrderTemplateKeyWords(orderTemplateKeyWords);
        });
        return orderId;
    }

    /**
     * 修改订单状态
     *
     * @param status 状态
     * @return 结果
     */
    @Override
    @Transactional
    public int executeCheck(String ids, Integer status) {
        return orderTemplateMapper.updateOrderTemplateStatus(Convert.toStrArray(ids), status);
    }

    /**
     * 重新提交订单
     *
     * @param orderDto 订单信息
     * @return 结果
     */
    @Override
    @Transactional
    public int againReferOrderTemplate(OrderTemplateDto orderDto) {
        OrderTemplate order = new OrderTemplate();
        BeanUtils.copyBeanProp(order, orderDto);
        order.setUnitPrice(UnitUtils.unitYuan(new BigDecimal(orderDto.getUnitPriceYuan())));
        order.setCommission(UnitUtils.unitYuan(new BigDecimal(orderDto.getCommissionYuan())));
        order.setUpdateTime(new Date());
        order.setStatus(OrderTemplateStatus.WAITCHECK.getCode());
        int orderId = orderTemplateMapper.updateOrderTemplate(order);
        orderTemplateKeyWordsMapper.deleteOrderTemplateKeyWordsByTemplateId(order.getId());
        order.getOrderTemplateKeyWords().forEach(orderTemplateKeyWords -> {
            orderTemplateKeyWords.setOrderTemplateId(order.getId());
            orderTemplateKeyWords.setId(null);
            orderTemplateKeyWordsMapper.insertOrderTemplateKeyWords(orderTemplateKeyWords);
        });
        return orderId;
    }

    /**
     * 拆分订单
     *
     * @param num 拆分数量
     * @return 结果
     */
    @Override
    @Transactional
    public int split(Long id, Integer num) {
        OrderTemplate orderTemplate = orderTemplateMapper.selectOrderTemplateById(id);
        if (orderTemplate == null) {
            throw new BusinessException("订单信息不存在");
        }
        Order order = new Order();
        BeanUtils.copyBeanProp(order, orderTemplate);
        Date now = new Date();
        order.setCreateTime(new Date());
        order.setUpdateTime(now);
        order.setCreateBy(ShiroUtils.getLoginName());
        order.setUpdateBy(ShiroUtils.getLoginName());
        order.setStatus(OrderStatus.UNFINISHED.getCode());
        order.setAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
        order.setReckonAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
        List<OrderTemplateKeyWords> orderTemplateKeyWordsList = orderTemplateKeyWordsMapper.selectOrderTemplateKeyWordsList(id);
        if (orderTemplateKeyWordsList == null || orderTemplateKeyWordsList.size() <= 0) {
            throw new BusinessException("订单关键词信息不存在");
        }
        //记录关键词列表
        List<OrderTemplateKeyWords> delectOrderTemplateKeyWords = new ArrayList<>();
        OrderTemplateKeyWords updateOrderTemplateKeyWords = null;
        List<Order> batchOrder = new ArrayList<>();
        int orderNum = 0;
        for (OrderTemplateKeyWords orderTemplateKeyWords1 : orderTemplateKeyWordsList) {
            Integer orderTemplateKeyWords1Num = orderTemplateKeyWords1.getNum();
            for (int i = 0; i < orderTemplateKeyWords1Num; i++) {
                if (orderNum >= num) {
                    if (orderTemplateKeyWords1.getNum() > 0) {
                        updateOrderTemplateKeyWords = orderTemplateKeyWords1;
                    }
                    break;
                }
                order.setKeyWord(orderTemplateKeyWords1.getName());
                Order newOrder = new Order();
                BeanUtils.copyBeanProp(newOrder, order);
                newOrder.setOrderId(idSegmentService.genOrderId());
                batchOrder.add(newOrder);
                orderNum++;
                orderTemplateKeyWords1.setNum(orderTemplateKeyWords1.getNum() - 1);
            }
            if (orderTemplateKeyWords1.getNum() == 0) {
                delectOrderTemplateKeyWords.add(orderTemplateKeyWords1);
            }
            if (orderNum >= num) {
                break;
            }
        }
        boolean flag = false;
        for (OrderTemplateKeyWords orderTemplateKeyWords1 : orderTemplateKeyWordsList) {
            if (orderTemplateKeyWords1.getNum() != 0) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            orderTemplateMapper.updateOrderTemplateStatus(Convert.toStrArray(String.valueOf(id)), OrderTemplateStatus.SPLIT.getCode());
        }
        if (delectOrderTemplateKeyWords.size() != 0) {
            orderTemplateKeyWordsMapper.deleteOrderTemplateKeyWords(delectOrderTemplateKeyWords);
        }
        if (updateOrderTemplateKeyWords != null) {
            orderTemplateKeyWordsMapper.updateOrderTemplateKeyWords(updateOrderTemplateKeyWords);
        }

        int n = orderMapper.insertBatchOrder(batchOrder);
        return n;
    }
}

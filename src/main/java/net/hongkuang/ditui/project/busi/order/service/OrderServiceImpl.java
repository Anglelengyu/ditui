package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.LogUtils;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.file.FileUtils;
import net.hongkuang.ditui.common.utils.poi.ExcelUtil;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.config.RuoYiConfig;
import net.hongkuang.ditui.project.busi.idSegment.service.IIdSegmentService;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.SearchOrder;
import net.hongkuang.ditui.project.busi.order.dto.*;
import net.hongkuang.ditui.project.busi.order.enums.OrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.OrderStatus;
import net.hongkuang.ditui.project.busi.order.enums.TaskTaskStatus;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.busi.orderCancelApproval.mapper.OrderCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.saleOrder.mapper.SaleOrderMapper;
import net.hongkuang.ditui.project.busi.saleTask.mapper.SaleTaskMapper;
import net.hongkuang.ditui.project.busi.shop.domain.Shop;
import net.hongkuang.ditui.project.busi.shop.mapper.ShopMapper;
import net.hongkuang.ditui.project.busi.task.domain.Task;
import net.hongkuang.ditui.project.busi.task.mapper.TaskMapper;
import net.hongkuang.ditui.project.busi.taskCancelApproval.mapper.TaskCancelApprovalMapper;
import net.hongkuang.ditui.project.busi.taskOrder.mapper.TaskOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单 服务层实现
 *
 * @author yj
 * @date 2018-12-30
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private final int BATCH_ORDER_SIZE = 50;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private IOrderGenTaskService orderGenTaskService;
    @Autowired
    private IOrderExtendInfoService orderExtendInfoService;
    @Autowired
    private TaskOrderMapper taskOrderMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private IIdSegmentService idSegmentService;
    @Autowired
    private SaleOrderMapper saleOrderMapper;
    @Autowired
    private SaleTaskMapper saleTaskMapper;
    @Autowired
    private OrderCancelApprovalMapper orderCancelApprovalMapper;
    @Autowired
    private TaskCancelApprovalMapper taskCancelApprovalMapper;

    private ReentrantLock genTaskLock = new ReentrantLock();

    /**
     * 查询订单信息
     *
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
    public Order selectOrderById(Long id) {
        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单信息
     * @return 订单集合
     */
    @Override
    public List<Order> selectOrderList(SearchOrder order) {
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
        List<Order> orderList = null;
        if (order.getHasExtend() == null) {
            orderList = orderMapper.selectOrderList(order);
        } else {
            orderList = orderMapper.selectExtendOrderList(order);
//			System.out.println(ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER));
            if (ShiroUtils.getSubject().hasRole(UserConstants.UserRoles.ROLE_SHOP_MANAGER)) {
                orderList.forEach(o -> o.setSaleCommission(null));
            }
        }
        return orderList;
    }

    private void appendingExtendInfoList(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) {
            return;
        }
        List<String> orderIdList = new ArrayList<>(orderList.size());
        for (Order order : orderList) {
            orderIdList.add(order.getOrderId());
        }
        List<OrderExtendInfo> orderExtendInfoList = orderExtendInfoService.selectOrderExtendInfo(orderIdList);
        if (orderExtendInfoList == null || orderExtendInfoList.isEmpty()) {
            return;
        }
        Map<String, OrderExtendInfo> extendInfoMap = new HashMap<>();
        for (OrderExtendInfo orderExtendInfo : orderExtendInfoList) {
            extendInfoMap.put(orderExtendInfo.getOrderId(), orderExtendInfo);
        }
        // 获取扩展信息 (销售人信息，任务订单信息)
        for (Order order : orderList) {
            OrderExtendInfo orderExtendInfo = extendInfoMap.get(order.getOrderId());
            if (orderExtendInfo == null) {
                continue;
            }
            BeanUtils.copyBeanProp(order, orderExtendInfo);
        }
    }

    /**
     * 新增订单
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    /**
     * 删除订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteOrderByIds(String ids) {
        List<Order> orderList = orderMapper.selectOrderListByIds(Convert.toStrArray(ids));
        List<String> orderIdList = new ArrayList<>();
        int taskResult = 0;
        List<String> saleOrderIdList = new ArrayList<>();
        List<String> cancelOrderIdList = new ArrayList<>();
        List<String> removeTaskIdList = new ArrayList<>();
        for (Order order : orderList) {
            orderIdList.add(order.getOrderId());
            Task task = taskMapper.selectTaskByOrderId(order.getOrderId());
            if (task == null) {
                taskResult++;
                continue;
            }
            if (order.getStatus().compareTo(TaskTaskStatus.WAIT.getCode()) > 0) {
                saleOrderIdList.add(order.getOrderId());
            }
            if (order.getStatus().compareTo(TaskTaskStatus.CANCEL.getCode()) == 0) {
                cancelOrderIdList.add(order.getOrderId());
            }
            if (task.getOrderNum() - 1 <= 0) {
                // 删除任务
                removeTaskIdList.add(task.getTaskId());
                taskResult++;
            } else {
                Task updateTask = new Task();
                updateTask.setId(task.getId());
                updateTask.setTaskId(task.getTaskId());
                updateTask.setTaskCommission(task.getTaskCommission() - order.getCommission());
                updateTask.setTaskCorpus(task.getTaskCorpus() - order.getUnitPrice());
                updateTask.setOrderNum(task.getOrderNum() - 1);
                taskResult += taskMapper.updateTask(updateTask);
            }
        }
        if (!removeTaskIdList.isEmpty()) {
            String[] removeTaskIdArray = removeTaskIdList.toArray(new String[removeTaskIdList.size()]);
            // 删除任务取消
            taskCancelApprovalMapper.deleteTaskCancelApprovalByTaskIdList(removeTaskIdList);
            // 删除业务员任务
            saleTaskMapper.deleteByTaskIds(removeTaskIdArray);
            // 删除任务
            int removeTaskResult = taskMapper.deleteTaskByTaskIds(removeTaskIdArray);
            if (removeTaskResult != removeTaskIdList.size()) {
                throw new BusinessException("订单删除失败，删除关联任务失败");
            }
        }
        if (!cancelOrderIdList.isEmpty()) {
            // 删除任务取消
            int cancelOrderResult = orderCancelApprovalMapper.deleteOrderCancelApprovalByOrderIdList(cancelOrderIdList);
            if (cancelOrderResult != cancelOrderIdList.size()) {
                LogUtils.getAccessLog().info("订单删除失败，删除关联取消订单申请失败, {}", cancelOrderIdList);
                // throw new BusinessException("订单删除失败，删除关联取消订单申请失败");
            }
        }
        if (!saleOrderIdList.isEmpty()) {
            // 删除业务员接单
            int saleOrderResult = saleOrderMapper.deleteSaleOrderByOrderIdList(saleOrderIdList);
            if (saleOrderResult != saleOrderIdList.size()) {
                LogUtils.getAccessLog().info("订单删除失败，删除关联销售订单失败, {}", saleOrderIdList);
                // throw new BusinessException("订单删除失败，删除关联销售订单失败");
            }
        }
        int taskOrderResult = taskOrderMapper.deleteTaskOrderByOrderIdList(orderIdList);
        int result = orderMapper.deleteOrderByIds(Convert.toStrArray(ids));
        if (taskResult != result) {
            throw new BusinessException("订单删除失败");
        }
        return result;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int importExcel(ImportOrderDto importOrderDto) {
        int result = 0;
        for (String file : importOrderDto.getFiles()) {
            if (StringUtils.isEmpty(file) || !FileUtils.isExists(RuoYiConfig.getUploadFolderPath(), file)) {
                throw new BusinessException("无效的上传订单Excel，请重新上传");
            }
        }
        int fileIndex = 0;
        for (String file : importOrderDto.getFiles()) {
            fileIndex++;
            InputStream fileInputStream = null;
            List<OrderDto> orderDtoList = null;
            try {
                fileInputStream = FileUtils.readByFile(RuoYiConfig.getUploadFolderPath(), file);
                ExcelUtil<OrderDto> orderDtoExcelUtil = new ExcelUtil<>(OrderDto.class);
                orderDtoList = orderDtoExcelUtil.importExcelIncludeImage(StringUtils.EMPTY
                        , fileInputStream, file.substring(file.lastIndexOf(".")));
            } catch (Exception e) {
                LogUtils.getErrorLog().error("order importExcel error", e);
                throw new BusinessException("第" + fileIndex + "个Excel解析订单Excel文件失败");
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        LogUtils.getErrorLog().error("order importExcel close error", e);
                    }
                }
            }
            try {
                // check 检查店铺是否存在
                for (OrderDto orderDto : orderDtoList) {
                    Shop shop = shopMapper.selectShopByName(orderDto.getShopName());
                    if (shop == null) {
                        throw new BusinessException("第" + fileIndex + "个Excel" + orderDto.getShopName() + "店铺不存在，请进行配置该店铺");
                    }
                    orderDto.setShopId(shop.getShopId());

                    if (StringUtils.isEmpty(orderDto.getOrderImg())) {
                        throw new BusinessException("第" + fileIndex + "个Excel" + "订单图片格式，请检查图片插入单元格位置");
                    }
                }
                // 批量插入订单
                for (OrderDto orderDto : orderDtoList) {
                    result += batchInsertOrder(orderDto);
                }
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                LogUtils.getErrorLog().error("order insert DB error", e);
                throw new BusinessException("第" + fileIndex + "个Excel" + "批量导入订单异常");
            }
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int genTask(GenTaskDto genTaskDto) {
        if (genTaskLock.isLocked()) {
            throw new BusinessException("正在有任务订单生成，请勿进行操作，待生成完毕后，可再次操作");
        }

        genTaskLock.lock();

        try {
            List<String> shopIdList = getShopIdList(null, null);

            // 获取目前待匹配商品的样式个数
            int unFinishGoodsCategoryCount = orderMapper.getGoodsCategoryCount(OrderStatus.UNFINISHED.getCode(), shopIdList);
            // 超过待匹配数量
            if (unFinishGoodsCategoryCount < genTaskDto.getGenType()) {
                throw new BusinessException("目前未完成订单产品类目数量不足，请选择其他生成方式");
            }
            int taskResult = 0;
            while (true) {
                List<UnfinishGoodsDto> unfinishGoodsCategoryDtos = orderGenTaskService.getUnFinishGoodsCategoryList(shopIdList);
                // 无匹配
                if (unfinishGoodsCategoryDtos == null || unfinishGoodsCategoryDtos.size() == 0) {
                    break;
                }
                // 小于匹配
                if (unfinishGoodsCategoryDtos.size() < genTaskDto.getGenType()) {
                    break;
                }
                // 获取匹配数量
                UnfinishGoodsDto firstUnFinishGoodsDto = unfinishGoodsCategoryDtos.get(0);
                List<List<Order>> finishOrderLists = new ArrayList<>(genTaskDto.getGenType());
                for (int i = 0; i < genTaskDto.getGenType(); i++) {
                    List<Order> finishOrderList = orderGenTaskService.gen(unfinishGoodsCategoryDtos.get(i).getCategory(), firstUnFinishGoodsDto.getCategoryCount(), shopIdList);
                    finishOrderLists.add(finishOrderList);
                }
                taskResult += orderGenTaskService.batchInsert(finishOrderLists);
            }
            // 插入任务
            return taskResult;
        } finally {
            genTaskLock.unlock();
        }
    }

    @Override
    public int getGenType() {
        List<String> shopIdList = getShopIdList(null, null);
        return orderMapper.getGoodsCategoryCount(OrderStatus.UNFINISHED.getCode(), shopIdList);
    }

    @Override
    public List<Order> selectOrderListByOrderId(String taskId) {
        return orderMapper.selectOrderListByOrderId(taskId);
    }

    @Override
    public int migrationOrderByIds(String ids) {
        return orderMapper.migrationOrderByIds(Convert.toStrArray(ids));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public GenTaskDto getTaskNum(Integer genType) {
        GenTaskDto genTaskDto = new GenTaskDto();
        if (genTaskLock.isLocked()) {
            throw new BusinessException("正在有任务订单生成，请勿进行操作，待生成完毕后，可再次操作");
        }
        genTaskLock.lock();
        try {
            List<String> shopIdList = getShopIdList(null, null);
            // 获取目前待匹配商品的样式个数
            int unFinishGoodsCategoryCount = orderMapper.getGoodsCategoryCountForReckonAllocatStatus(OrderStatus.UNFINISHED.getCode(), shopIdList);
            // 超过待匹配数量
            if (unFinishGoodsCategoryCount < genType) {
                throw new BusinessException("目前未完成订单产品类目数量不足，请选择其他生成方式");
            }
            SearchOrder searchOrder = new SearchOrder();
            searchOrder.setAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
            List<Order> unFinishedList = orderMapper.selectUnFinishedList(searchOrder);
            int taskNum = 0;
            while (true) {
                List<UnfinishGoodsDto> unfinishGoodsCategoryDtos = orderGenTaskService.getUnFinishGoodsCategoryListForReckonAllocatStatus(shopIdList);
                // 无匹配
                if (unfinishGoodsCategoryDtos == null || unfinishGoodsCategoryDtos.size() == 0) {
                    break;
                }
                // 小于匹配
                if (unfinishGoodsCategoryDtos.size() < genType) {
                    break;
                }
                // 获取匹配数量
                UnfinishGoodsDto firstUnFinishGoodsDto = unfinishGoodsCategoryDtos.get(0);
                List<List<Order>> finishOrderLists = new ArrayList<>();
                for (int i = 0; i < genType; i++) {
                    List<Order> finishOrderList = orderGenTaskService.getTaskNum(unfinishGoodsCategoryDtos.get(i).getCategory(), firstUnFinishGoodsDto.getCategoryCount(), shopIdList);
                    finishOrderLists.add(finishOrderList);
                }
                taskNum += finishOrderLists.get(0).size();
            }
            List<Long> orderIdList = new ArrayList<>();
            for (Order order : unFinishedList) {
                orderIdList.add(order.getId());
            }
            orderMapper.updateOrderReckonAllocatStatus(orderIdList, OrderAllocatStatus.UNFINISHED.getCode());
            genTaskDto.setGenTaskNum(taskNum);
            genTaskDto.setGenGsurplusNum(unFinishedList.size() - taskNum * genType);
            // 插入任务
            return genTaskDto;
        } finally {
            genTaskLock.unlock();
        }
    }

    private int batchInsertOrder(OrderDto orderDto) {
        int orderCount = orderDto.getOrderCount();
        Order order = new Order();
        BeanUtils.copyBeanProp(order, orderDto);
        order.setStatus(OrderStatus.UNFINISHED.getCode());
        order.setUnitPrice(UnitUtils.unitYuan(orderDto.getUnitPrice()));
        order.setCommission(UnitUtils.unitYuan(orderDto.getCommission()));
        Date now = new Date();
        order.setCreateTime(new Date());
        order.setUpdateTime(now);
        order.setCreateBy(ShiroUtils.getLoginName());
        order.setUpdateBy(ShiroUtils.getLoginName());
        order.setAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());
        order.setReckonAllocatStatus(OrderAllocatStatus.UNFINISHED.getCode());

        int n = 0;
        List<Order> batchOrder = new ArrayList<>(BATCH_ORDER_SIZE);
        for (int i = 0; i < orderCount; i++) {
            Order newOrder = new Order();
            BeanUtils.copyBeanProp(newOrder, order);
            newOrder.setOrderId(idSegmentService.genOrderId());
            // 批次插入
            batchOrder.add(newOrder);
            if ((i + 1) % BATCH_ORDER_SIZE == 0 || (i + 1) == orderCount) {
                // 取模或者最后一次
                n += orderMapper.insertBatchOrder(batchOrder);
                batchOrder.clear();
            }
        }
        return n;
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
}

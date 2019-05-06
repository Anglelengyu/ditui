package net.hongkuang.ditui.project.busi.order.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.busi.order.domain.*;
import net.hongkuang.ditui.project.busi.order.dto.GenTaskDto;
import net.hongkuang.ditui.project.busi.order.dto.TbTransactionWaitBuildGoodsDto;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionBuyerRequireMapper;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.shop.mapper.ShopMapper;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 淘宝订单 服务层实现
 *
 * @author:zy
 * @date: 2019/4/12
 */
@Service
public class TbTransactionOrderServiceImpl implements ITbTransactionOrderService {

    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private TbTransactionQuestionMapper tbTransactionQuestionMapper;
    @Autowired
    private TbTransactionBuyerRequireMapper tbTransactionBuyerRequireMapper;
    @Autowired
    private ITbTransactionOrderGenTaskService tbTransactionOrderGenTaskService;
    @Autowired
    private ShopMapper shopMapper;

    private ReentrantLock genTaskLock = new ReentrantLock();

    @Override
    public List<TbTransactionOrder> selectTbTransactionOrderList(SearchTbTransactionOrder searchTbTransactionOrder) {

        if (!StringUtils.isEmpty(searchTbTransactionOrder.getStartTime())) {
            searchTbTransactionOrder.setStartTime(searchTbTransactionOrder.getStartTime() + " 00:00:00");
        }
        if (!StringUtils.isEmpty(searchTbTransactionOrder.getEndTime())) {
            searchTbTransactionOrder.setEndTime(searchTbTransactionOrder.getEndTime() + " 23:59:59");
        }
        return tbTransactionOrderMapper.selectTbTransactionOrderList(searchTbTransactionOrder);
    }

    @Override
    public TbTransactionOrder selectTbTransactionOrderById(Long id) {
        return tbTransactionOrderMapper.selectTbTransactionOrderById(id);
    }

    @Override
    public TbTransactionOrder selectTbTransactionOrderByOrderId(String orderId) {
        return tbTransactionOrderMapper.selectTbTransactionOrderByOrderId(orderId);
    }

    @Override
    public int updateTbTransactionOrderRemark(TbTransactionOrder tbTransactionOrder) {
        return tbTransactionOrderMapper.updateTbTransactionOrderRemark(tbTransactionOrder);
    }

    /**
     * 删除订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteTbTransactionOrderByIds(String ids) {
        tbTransactionQuestionMapper.deleteTbTransactionQuestionByOrderIds(Convert.toStrArray(ids));
        tbTransactionBuyerRequireMapper.deleteTbTransactionBuyerRequireByIds(Convert.toStrArray(ids));
        return tbTransactionOrderMapper.deleteTbTransactionOrderByIds(Convert.toStrArray(ids));
    }

    @Override
    public int updateTbTransactionOrder(TbTransactionOrderDto tbTransactionOrderDto) {
        TbTransactionOrder tbTransactionOrder = new TbTransactionOrder();
        BeanUtils.copyBeanProp(tbTransactionOrder, tbTransactionOrderDto);
        tbTransactionOrder.setUnitPrice(UnitUtils.unitYuan(tbTransactionOrderDto.getUnitPrice()));
        tbTransactionOrder.setCommissionPrice(UnitUtils.unitYuan(tbTransactionOrderDto.getCommissionPrice()));

        if (tbTransactionOrder.getQuestion() != null && tbTransactionOrder.getQuestion() == 1) {
            if (tbTransactionOrder.getQuestionId() != null) {
                tbTransactionQuestionMapper.updateTbTransactionQuestion(tbTransactionOrder.getTbTransactionQuestion());
            } else {
                tbTransactionQuestionMapper.saveTbTransactionQuestion(tbTransactionOrder.getTbTransactionQuestion());
                tbTransactionOrder.setQuestionId(tbTransactionOrder.getTbTransactionQuestion().getId());
            }
        } else {
            if (tbTransactionOrder.getQuestionId() != null) {
                tbTransactionQuestionMapper.deleteTbTransactionQuestionById(tbTransactionOrder.getQuestionId());
                tbTransactionOrder.setQuestionId(0l);
            }
        }
        return tbTransactionOrderMapper.updateTbTransactionOrder(tbTransactionOrder);
    }

    @Override
    public int getGenType(Long teamId,String[] ids) {
        return tbTransactionOrderMapper.getGroupClassifysCount(TbTransactionOrderAllocatStatus.WAITBUILD.getCode(),teamId,ids);
    }

    @Override
    public int getOrderTotalNum(Long teamId,String[] ids) {
        return tbTransactionOrderMapper.getOrderTotalNum(TbTransactionOrderAllocatStatus.WAITBUILD.getCode(),teamId,ids);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int genTask(GenTaskDto genTaskDto) {
        if (genTaskLock.isLocked()) {
            throw new BusinessException("正在有任务订单生成，请勿进行操作，待生成完毕后，可再次操作");
        }

        genTaskLock.lock();

        try {
            // 获取目前待匹配商品的样式个数
            int waitBuildGroupClassifysCount = tbTransactionOrderMapper.getGroupClassifysCountForReckonAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode(),genTaskDto.getTeamId(),genTaskDto.getIds());
            // 超过待匹配数量
            if (waitBuildGroupClassifysCount < genTaskDto.getGenType()) {
                throw new BusinessException("目前未完成订单产品类目数量不足，请选择其他生成方式");
            }
            int taskResult = 0;
            while (true) {
                List<TbTransactionWaitBuildGoodsDto> waitBuildGroupClassifysDtos = tbTransactionOrderGenTaskService.getWaitBuildGroupClassifysListForReckonAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode(), genTaskDto.getTeamId(),genTaskDto.getIds());
                // 无匹配
                if (waitBuildGroupClassifysDtos == null || waitBuildGroupClassifysDtos.size() == 0) {
                    break;
                }
                // 小于匹配
                if (waitBuildGroupClassifysDtos.size() < genTaskDto.getGenType()) {
                    break;
                }
                // 获取匹配数量
                TbTransactionWaitBuildGoodsDto firstTbTransactionWaitBuildGoodsDto = waitBuildGroupClassifysDtos.get(0);
                List<List<TbTransactionOrder>> finishOrderLists = new ArrayList<>(genTaskDto.getGenType());
                for (int i = 0; i < genTaskDto.getGenType(); i++) {
                    List<TbTransactionOrder> finishOrderList = tbTransactionOrderGenTaskService.gen(waitBuildGroupClassifysDtos.get(i).getGroupClassifys(), firstTbTransactionWaitBuildGoodsDto.getGroupClassifysCount(), genTaskDto.getTeamId(), genTaskDto.getIds());
                    finishOrderLists.add(finishOrderList);
                }
                taskResult += tbTransactionOrderGenTaskService.batchInsert(finishOrderLists,genTaskDto.getTeamId());
            }
            // 插入任务
            return taskResult;
        } finally {
            genTaskLock.unlock();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public GenTaskDto getTaskNum(Integer genType,Long teamId,String[] ids) {
        GenTaskDto genTaskDto = new GenTaskDto();
        if (genTaskLock.isLocked()) {
            throw new BusinessException("正在有任务订单生成，请勿进行操作，待生成完毕后，可再次操作");
        }
        genTaskLock.lock();
        try {
            // 获取目前待生成商品的样式个数
            int waitBuildGroupClassifysCount = tbTransactionOrderMapper.getGroupClassifysCountForReckonAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode(), teamId, ids);
            // 超过待生成数量
            if (waitBuildGroupClassifysCount < genType) {
                throw new BusinessException("目前未完成订单产品类目数量不足，请选择其他生成方式");
            }
            SearchTbTransactionOrder searchOrder = new SearchTbTransactionOrder();
            searchOrder.setAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
            searchOrder.setTeamId(teamId);
            searchOrder.setIds(ids);
            List<TbTransactionOrder> waitBuildList = tbTransactionOrderMapper.selectTbTrasactionWaitBuildList(searchOrder);
            int taskNum = 0;
            while (true) {
                List<TbTransactionWaitBuildGoodsDto> tbTransactionWaitBuildGoodsDtos = tbTransactionOrderGenTaskService.getWaitBuildGroupClassifysListForReckonAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode(), teamId,ids);
                // 无匹配
                if (tbTransactionWaitBuildGoodsDtos == null || tbTransactionWaitBuildGoodsDtos.size() == 0) {
                    break;
                }
                // 小于匹配
                if (tbTransactionWaitBuildGoodsDtos.size() < genType) {
                    break;
                }
                // 获取匹配数量
                TbTransactionWaitBuildGoodsDto firstTbTransactionWaitBuildGoodsDto = tbTransactionWaitBuildGoodsDtos.get(0);
                List<List<TbTransactionOrder>> finishOrderLists = new ArrayList<>();
                for (int i = 0; i < genType; i++) {
                    List<TbTransactionOrder> finishOrderList = tbTransactionOrderGenTaskService.getTaskNum(tbTransactionWaitBuildGoodsDtos.get(i).getGroupClassifys(), firstTbTransactionWaitBuildGoodsDto.getGroupClassifysCount(), teamId,ids);
                    finishOrderLists.add(finishOrderList);
                }
                taskNum += finishOrderLists.get(0).size();
            }
            List<Long> orderIdList = new ArrayList<>();
            for (TbTransactionOrder order : waitBuildList) {
                orderIdList.add(order.getId());
            }
            tbTransactionOrderMapper.updateTbTransactionOrderReckonAllocatStatus(orderIdList, TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
            genTaskDto.setGenTaskNum(taskNum);
            genTaskDto.setGenGsurplusNum(waitBuildList.size() - taskNum * genType);
            // 插入任务
            return genTaskDto;
        } finally {
            genTaskLock.unlock();
        }
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

    @Override
    public List<TbTransactionOrder> selectTbTransactionOrderByTaskId(String taskId) {
        return tbTransactionOrderMapper.selectTbTransactionOrderByTaskId(taskId);
    }

    @Override
    public boolean selectTbTransactionOrderIsDistribution(String[] ids) {
        List<TbTransactionOrder> tbTransactionOrderList = tbTransactionOrderMapper.selectTbTransactionOrderListByIds(ids);
        boolean flag = true;
        for(TbTransactionOrder tbTransactionOrder : tbTransactionOrderList){
            if (tbTransactionOrder.getReceiptTeamId() != null && tbTransactionOrder.getReceiptTeamId() != 0) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int distributionTeam(String[] ids, Long teamId, String teamName) {
        List<TbTransactionOrder> tbTransactionOrderList = tbTransactionOrderMapper.selectTbTransactionOrderListByIds(ids);
        boolean flag = true;
        for(TbTransactionOrder tbTransactionOrder : tbTransactionOrderList){
            if (tbTransactionOrder.getReceiptTeamId() != null && tbTransactionOrder.getReceiptTeamId() != 0) {
                flag = false;
            }
        }
        if(!flag){
            return 0;
        }
        return tbTransactionOrderMapper.distributionTeam(ids,teamId,teamName);
    }

}

package net.hongkuang.ditui.project.busi.tbTransactionTemplate.service;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.IntegerUtils;
import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.busi.idSegment.service.IIdSegmentService;
import net.hongkuang.ditui.project.busi.order.domain.TbTransactionOrder;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderAllocatStatus;
import net.hongkuang.ditui.project.busi.order.enums.TbTransactionOrderStatus;
import net.hongkuang.ditui.project.busi.order.mapper.TbTransactionOrderMapper;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.*;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.enums.TbTransactionTemplateStatus;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionKeyWordsMapper;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionQuestionMapper;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 淘宝模版 服务层实现
 *
 * @author:zy
 * @date: 2019/4/3
 */
@Service
public class TbTransactionTemplateServiceImpl implements ITbTransactionTemplateService {
    private final int BATCH_ORDER_SIZE = 50;

    @Autowired
    private TbTransactionTemplateMapper tbTransactionTemplateMapper;
    @Autowired
    private TbTransactionKeyWordsMapper tbTransactionKeyWordsMapper;
    @Autowired
    private TbTransactionQuestionMapper tbTransactionQuestionMapper;
    @Autowired
    private TbTransactionOrderMapper tbTransactionOrderMapper;
    @Autowired
    private IIdSegmentService idSegmentService;

    @Override
    public List<TbTransactionTemplate> selectTbTransactionTemplateList(SearchTbTransactionTemplate searchTbTransactionTemplate) {
        return tbTransactionTemplateMapper.selectTbTransactionTemplateList(searchTbTransactionTemplate);
    }

    @Override
    public TbTransactionTemplate selectTbTransactionTemplateById(Long id) {
        return tbTransactionTemplateMapper.selectTbTransactionTemplateById(id);
    }

    @Override
    @Transactional
    public int insertTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto) {
        TbTransactionTemplate tbTransactionTemplate = new TbTransactionTemplate();
        BeanUtils.copyBeanProp(tbTransactionTemplate, tbTransactionTemplateDto);
        tbTransactionTemplate.setTotalUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalUnitPrice()));
        tbTransactionTemplate.setTotalCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalCommissionPrice()));

        tbTransactionTemplate.setUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getUnitPrice()));
        tbTransactionTemplate.setCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getCommissionPrice()));

        if (tbTransactionTemplate.getQuestion() != null && tbTransactionTemplate.getQuestion() == 1) {
            tbTransactionQuestionMapper.saveTbTransactionQuestion(tbTransactionTemplate.getTbTransactionQuestion());
            tbTransactionTemplate.setQuestionId(tbTransactionTemplate.getTbTransactionQuestion().getId());
        }
        tbTransactionTemplate.setStatus(TbTransactionTemplateStatus.WAITREFER.getCode());
        int i = tbTransactionTemplateMapper.saveTbTransactionTemplate(tbTransactionTemplate);

        tbTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
            tbTransactionKeyWords.setTemplateId(tbTransactionTemplate.getId());
            tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
        });
        return i;
    }

    @Override
    @Transactional
    public int updateTbTransactionTemplate(TbTransactionTemplate tbTransactionTemplate) {
        return tbTransactionTemplateMapper.updateTbTransactionTemplate(tbTransactionTemplate);
    }

    @Override
    @Transactional
    public int referTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto) {

        TbTransactionTemplate tbTransactionTemplate = new TbTransactionTemplate();
        BeanUtils.copyBeanProp(tbTransactionTemplate, tbTransactionTemplateDto);
        tbTransactionTemplate.setTotalUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalUnitPrice()));
        tbTransactionTemplate.setTotalCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalCommissionPrice()));

        tbTransactionTemplate.setUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getUnitPrice()));
        tbTransactionTemplate.setCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getCommissionPrice()));

        if (tbTransactionTemplate.getQuestion() != null && tbTransactionTemplate.getQuestion() == 1) {
            tbTransactionQuestionMapper.saveTbTransactionQuestion(tbTransactionTemplate.getTbTransactionQuestion());
            tbTransactionTemplate.setQuestionId(tbTransactionTemplate.getTbTransactionQuestion().getId());
        } else {
            if (tbTransactionTemplate.getQuestionId() != null) {
                tbTransactionQuestionMapper.deleteTbTransactionQuestionById(tbTransactionTemplate.getQuestionId());
                tbTransactionTemplate.setQuestionId(0l);
            }
        }

        tbTransactionTemplate.setStatus(TbTransactionTemplateStatus.WAITCHECK.getCode());
        int i = tbTransactionTemplateMapper.saveTbTransactionTemplate(tbTransactionTemplate);

        tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateId(tbTransactionTemplate.getId());
        tbTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
            tbTransactionKeyWords.setTemplateId(tbTransactionTemplate.getId());
            tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
        });
        return i;
    }

    @Override
    @Transactional
    public int againReferTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto) {

        TbTransactionTemplate tbTransactionTemplate = new TbTransactionTemplate();
        BeanUtils.copyBeanProp(tbTransactionTemplate, tbTransactionTemplateDto);
        tbTransactionTemplate.setTotalUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalUnitPrice()));
        tbTransactionTemplate.setTotalCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalCommissionPrice()));

        tbTransactionTemplate.setUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getUnitPrice()));
        tbTransactionTemplate.setCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getCommissionPrice()));

        if (tbTransactionTemplate.getQuestion() != null && tbTransactionTemplate.getQuestion() == 1) {
            if (tbTransactionTemplate.getQuestionId() != null && tbTransactionTemplate.getQuestionId() != 0) {
                tbTransactionTemplate.getTbTransactionQuestion().setId(tbTransactionTemplate.getQuestionId());
                tbTransactionQuestionMapper.updateTbTransactionQuestion(tbTransactionTemplate.getTbTransactionQuestion());
            } else {
                tbTransactionQuestionMapper.saveTbTransactionQuestion(tbTransactionTemplate.getTbTransactionQuestion());
                tbTransactionTemplate.setQuestionId(tbTransactionTemplate.getTbTransactionQuestion().getId());
            }
        } else {
            if (tbTransactionTemplate.getQuestionId() != null) {
                tbTransactionQuestionMapper.deleteTbTransactionQuestionById(tbTransactionTemplate.getQuestionId());
                tbTransactionTemplate.setQuestionId(0l);
            }
        }

        tbTransactionTemplate.setStatus(TbTransactionTemplateStatus.WAITCHECK.getCode());
        int i = tbTransactionTemplateMapper.updateTbTransactionTemplate(tbTransactionTemplate);

        tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateId(tbTransactionTemplate.getId());
        tbTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
            tbTransactionKeyWords.setTemplateId(tbTransactionTemplate.getId());
            tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
        });
        return i;
    }

    @Override
    public int updateWaitcheckTbTransactionTemplate(TbTransactionTemplateDto tbTransactionTemplateDto) {
        TbTransactionTemplate tbTransactionTemplate = new TbTransactionTemplate();
        BeanUtils.copyBeanProp(tbTransactionTemplate, tbTransactionTemplateDto);
        tbTransactionTemplate.setTotalUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalUnitPrice()));
        tbTransactionTemplate.setTotalCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getTotalCommissionPrice()));

        tbTransactionTemplate.setUnitPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getUnitPrice()));
        tbTransactionTemplate.setCommissionPrice(UnitUtils.unitYuan(tbTransactionTemplateDto.getCommissionPrice()));
        if (tbTransactionTemplate.getQuestion() != null && tbTransactionTemplate.getQuestion() == 1) {
            if (tbTransactionTemplate.getQuestionId() != null) {
                tbTransactionQuestionMapper.updateTbTransactionQuestion(tbTransactionTemplate.getTbTransactionQuestion());
            } else {
                tbTransactionQuestionMapper.saveTbTransactionQuestion(tbTransactionTemplate.getTbTransactionQuestion());
                tbTransactionTemplate.setQuestionId(tbTransactionTemplate.getTbTransactionQuestion().getId());
            }
        } else {
            if (tbTransactionTemplate.getQuestionId() != null) {
                tbTransactionQuestionMapper.deleteTbTransactionQuestionById(tbTransactionTemplate.getQuestionId());
                tbTransactionTemplate.setQuestionId(0l);
            }
        }
        int i = tbTransactionTemplateMapper.updateTbTransactionTemplate(tbTransactionTemplate);
        tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateId(tbTransactionTemplate.getId());
        tbTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
            tbTransactionKeyWords.setTemplateId(tbTransactionTemplate.getId());
            tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
        });
        return i;
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
        return tbTransactionTemplateMapper.updateTbTransactionTemplateStatus(Convert.toStrArray(ids), status);
    }

    /**
     * 删除模版对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteTbTransactionTemplateByIds(String ids) {
        tbTransactionQuestionMapper.deleteTbTransactionQuestionByTemplateIds(Convert.toStrArray(ids));
        tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateIds(Convert.toStrArray(ids));
        return tbTransactionTemplateMapper.deleteTbTransactionTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 校验模版名称是否唯一
     *
     * @param templateName 模版名称
     * @return 结果
     */
    @Override
    public String checkTemplateNameUnique(String templateName, Long managerId) {
        int count = tbTransactionTemplateMapper.checkTemplateNameUnique(templateName, managerId);
        if (count > 0) {
            return UserConstants.TEMPLATE_NAME_NOT_UNIQUE;
        }
        return UserConstants.TEMPLATE_NAME_UNIQUE;
    }

    @Override
    @Transactional
    public int splitTemplate(TbSplitTransactionTemplateDto tbSplitTransactionTemplateDto) {
        TbTransactionTemplate tbTransactionTemplate = tbTransactionTemplateMapper.selectTbTransactionTemplateById(tbSplitTransactionTemplateDto.getId());
        if (tbTransactionTemplate == null) {
            return 0;
        }
        tbTransactionTemplate.setExecuteMethod(tbSplitTransactionTemplateDto.getExecuteMethod());
        TbTransactionTemplate tbBeforeTransactionTemplate = new TbTransactionTemplate();
        TbTransactionTemplate tbAfterTransactionTemplate = new TbTransactionTemplate();
        BeanUtils.copyBeanProp(tbBeforeTransactionTemplate, tbTransactionTemplate);
        BeanUtils.copyBeanProp(tbAfterTransactionTemplate, tbTransactionTemplate);
        tbBeforeTransactionTemplate.setId(null);
        tbAfterTransactionTemplate.setId(null);
        tbBeforeTransactionTemplate.setTotalNumber(tbSplitTransactionTemplateDto.getTotalNumberTotal());
        tbAfterTransactionTemplate.setTotalNumber(tbSplitTransactionTemplateDto.getSplitTotalNumberTotal());

        tbBeforeTransactionTemplate.setTotalUnitPrice(tbBeforeTransactionTemplate.getTotalNumber() * tbBeforeTransactionTemplate.getUnitPrice());
        tbBeforeTransactionTemplate.setTotalCommissionPrice(tbBeforeTransactionTemplate.getTotalNumber() * tbBeforeTransactionTemplate.getCommissionPrice());
        tbAfterTransactionTemplate.setTotalUnitPrice(tbAfterTransactionTemplate.getTotalNumber() * tbAfterTransactionTemplate.getUnitPrice());
        tbAfterTransactionTemplate.setTotalCommissionPrice(tbAfterTransactionTemplate.getTotalNumber() * tbAfterTransactionTemplate.getCommissionPrice());

        List<TbTransactionKeyWords> tbBeforeTransactionKeyWordsList = new ArrayList<>();
        List<TbTransactionKeyWords> tbAfterTransactionKeyWordsList = new ArrayList<>();
        for (TbTransactionKeyWordsDto tbTransactionKeyWordsDto : tbSplitTransactionTemplateDto.getTbTransactionKeyWords()) {
            Integer totalNumber = tbTransactionKeyWordsDto.getTotalNumber();
            Integer splitTotalNumber = tbTransactionKeyWordsDto.getSplitTotalNumber();
            if (totalNumber <= 0) {
                continue;
            }
            if (totalNumber - splitTotalNumber > 0) {
                TbTransactionKeyWords tbBeforeTransactionKeyWords = new TbTransactionKeyWords();
                tbBeforeTransactionKeyWords.setName(tbTransactionKeyWordsDto.getName());
                tbBeforeTransactionKeyWords.setTotalNumber(IntegerUtils.toInt(tbTransactionKeyWordsDto.getTotalNumber()) - IntegerUtils.toInt(tbTransactionKeyWordsDto.getSplitTotalNumber()));
                tbBeforeTransactionKeyWords.setAppNumber(IntegerUtils.toInt(tbTransactionKeyWordsDto.getAppNumber()) - IntegerUtils.toInt(tbTransactionKeyWordsDto.getSplitAppNumber()));
                tbBeforeTransactionKeyWords.setPcNumber(IntegerUtils.toInt(tbTransactionKeyWordsDto.getPcNumber()) - IntegerUtils.toInt(tbTransactionKeyWordsDto.getSplitPcNumber()));
                tbBeforeTransactionKeyWords.setCollectionNumber(IntegerUtils.toInt(tbTransactionKeyWordsDto.getCollectionNumber()) - IntegerUtils.toInt(tbTransactionKeyWordsDto.getSplitCollectionNumber()));
                tbBeforeTransactionKeyWords.setCartNumber(IntegerUtils.toInt(tbTransactionKeyWordsDto.getCartNumber()) - IntegerUtils.toInt(tbTransactionKeyWordsDto.getSplitCartNumber()));
                tbBeforeTransactionKeyWords.setCollectionCartNumber(IntegerUtils.toInt(tbTransactionKeyWordsDto.getCollectionCartNumber()) - IntegerUtils.toInt(tbTransactionKeyWordsDto.getCollectionCartNumber()));
                tbBeforeTransactionKeyWordsList.add(tbBeforeTransactionKeyWords);
            }

            if (splitTotalNumber <= 0) {
                continue;
            }
            TbTransactionKeyWords tbAfterTransactionKeyWords = new TbTransactionKeyWords();
            tbAfterTransactionKeyWords.setName(tbTransactionKeyWordsDto.getName());
            tbAfterTransactionKeyWords.setTotalNumber(tbTransactionKeyWordsDto.getSplitTotalNumber());
            tbAfterTransactionKeyWords.setAppNumber(tbTransactionKeyWordsDto.getSplitAppNumber());
            tbAfterTransactionKeyWords.setPcNumber(tbTransactionKeyWordsDto.getSplitPcNumber());
            tbAfterTransactionKeyWords.setCollectionNumber(tbTransactionKeyWordsDto.getSplitCollectionNumber());
            tbAfterTransactionKeyWords.setCartNumber(tbTransactionKeyWordsDto.getSplitCartNumber());
            tbAfterTransactionKeyWords.setCollectionCartNumber(tbTransactionKeyWordsDto.getSplitCollectionCartNumber());
            tbAfterTransactionKeyWordsList.add(tbAfterTransactionKeyWords);
        }
        tbBeforeTransactionTemplate.setTbTransactionKeyWords(tbBeforeTransactionKeyWordsList);
        tbAfterTransactionTemplate.setTbTransactionKeyWords(tbAfterTransactionKeyWordsList);
        if (tbBeforeTransactionKeyWordsList.size() != 0) {
            if (tbBeforeTransactionTemplate.getQuestionId() != null && tbBeforeTransactionTemplate.getQuestionId() != 0) {
                tbBeforeTransactionTemplate.getTbTransactionQuestion().setId(null);
                tbTransactionQuestionMapper.saveTbTransactionQuestion(tbBeforeTransactionTemplate.getTbTransactionQuestion());
                tbBeforeTransactionTemplate.setQuestionId(tbBeforeTransactionTemplate.getTbTransactionQuestion().getId());
            } else {
                if (tbBeforeTransactionTemplate.getQuestionId() != null) {
                    tbTransactionQuestionMapper.deleteTbTransactionQuestionById(tbBeforeTransactionTemplate.getQuestionId());
                    tbBeforeTransactionTemplate.setQuestionId(0l);
                }
            }
            int i = tbTransactionTemplateMapper.saveTbTransactionTemplate(tbBeforeTransactionTemplate);
            tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateId(tbBeforeTransactionTemplate.getId());
            tbBeforeTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
                tbTransactionKeyWords.setTemplateId(tbBeforeTransactionTemplate.getId());
                tbTransactionKeyWords.setId(null);
                tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
            });
        }
        if (tbAfterTransactionKeyWordsList.size() != 0) {
            if (tbAfterTransactionTemplate.getQuestionId() != null && tbAfterTransactionTemplate.getQuestionId() != 0) {
                tbAfterTransactionTemplate.getTbTransactionQuestion().setId(null);
                tbTransactionQuestionMapper.saveTbTransactionQuestion(tbAfterTransactionTemplate.getTbTransactionQuestion());
                tbAfterTransactionTemplate.setQuestionId(tbAfterTransactionTemplate.getTbTransactionQuestion().getId());
            } else {
                if (tbAfterTransactionTemplate.getQuestionId() != null) {
                    tbTransactionQuestionMapper.deleteTbTransactionQuestionById(tbAfterTransactionTemplate.getQuestionId());
                    tbAfterTransactionTemplate.setQuestionId(0l);
                }
            }
            int i = tbTransactionTemplateMapper.saveTbTransactionTemplate(tbAfterTransactionTemplate);
            tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateId(tbAfterTransactionTemplate.getId());
            tbAfterTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
                tbTransactionKeyWords.setTemplateId(tbAfterTransactionTemplate.getId());
                tbTransactionKeyWords.setId(null);
                tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
            });
        }
        String ids = String.valueOf(tbTransactionTemplate.getId());
        return tbTransactionTemplateMapper.deleteTbTransactionTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询模版关键词数量
     *
     * @param ids 模版IDs
     * @return 关键词集合
     */
    @Override
    public AjaxResult getTbTransactionTemplateKeyWordsTotal(String ids) {
        int count = tbTransactionTemplateMapper.getTbTransactionTemplateNoGroupClassifys(Convert.toStrArray(ids));
        if(count>0){
            return AjaxResult.error("您还未填写组单分类，请前往填写");
        }
        return AjaxResult.success().put("num", tbTransactionTemplateMapper.getTbTransactionTemplateKeyWordsTotal(Convert.toStrArray(ids)));
    }

    /**
     * 拆分订单
     *
     * @param ids 模版IDs
     * @return 关键词集合
     */
    @Override
    @Transactional
    public int splitOrder(String ids) {
        String[] arrayIds = Convert.toStrArray(ids);

        int re = 0;
        for (String templateId : arrayIds) {
            TbTransactionTemplate tbTransactionTemplate = tbTransactionTemplateMapper.selectTbTransactionTemplateById(Long.valueOf(templateId));
            Long questionId = tbTransactionTemplate.getQuestionId();
            TbTransactionQuestion tbTransactionQuestion = null;
            if (questionId != null && questionId != 0) {
                tbTransactionQuestion = tbTransactionQuestionMapper.selectTbTransactionQuestionById(questionId);
                tbTransactionQuestion.setId(null);
            }
            int n = 0;
            List<TbTransactionOrder> tbTransactionOrderList = new ArrayList<>(BATCH_ORDER_SIZE);
            for (TbTransactionKeyWords tbTransactionKeyWords : tbTransactionTemplate.getTbTransactionKeyWords()) {
                for (int i = 0; i < tbTransactionKeyWords.getTotalNumber(); i++) {
                    TbTransactionOrder tbTransactionOrder = new TbTransactionOrder();
                    BeanUtils.copyBeanProp(tbTransactionOrder, tbTransactionTemplate);
                    tbTransactionOrder.setStatus(TbTransactionOrderStatus.UNFINISHED.getCode());
                    tbTransactionOrder.setId(null);
                    tbTransactionOrder.setOrderId(idSegmentService.genOrderId());
                    tbTransactionOrder.setAllocatStatus(TbTransactionOrderAllocatStatus.WAITBUILD.getCode());
                    tbTransactionOrder.setCreateBy(ShiroUtils.getLoginName());
                    tbTransactionOrder.setUpdateBy(ShiroUtils.getLoginName());
                    if (tbTransactionQuestion != null) {
                        tbTransactionQuestionMapper.saveTbTransactionQuestion(tbTransactionQuestion);
                        tbTransactionOrder.setQuestion(1);
                        tbTransactionOrder.setQuestionId(tbTransactionQuestion.getId());
                    }
                    if (tbTransactionKeyWords.getAppNumber() != null && tbTransactionKeyWords.getAppNumber() != 0) {
                        tbTransactionOrder.setHasApp(1);
                        tbTransactionOrder.setKeyWordName(tbTransactionKeyWords.getName());
                        tbTransactionKeyWords.setAppNumber(tbTransactionKeyWords.getAppNumber() - 1);
                    } else if (tbTransactionKeyWords.getPcNumber() != null && tbTransactionKeyWords.getPcNumber() != 0) {
                        tbTransactionOrder.setHasPc(1);
                        tbTransactionOrder.setKeyWordName(tbTransactionKeyWords.getName());
                        tbTransactionKeyWords.setPcNumber(tbTransactionKeyWords.getPcNumber() - 1);
                    }

                    if (tbTransactionKeyWords.getCollectionNumber() != null && tbTransactionKeyWords.getCollectionNumber() != 0) {
                        tbTransactionOrder.setHasCollection(1);
                        tbTransactionKeyWords.setCollectionNumber(tbTransactionKeyWords.getCollectionNumber() - 1);
                    } else if (tbTransactionKeyWords.getCartNumber() != null && tbTransactionKeyWords.getCartNumber() != 0) {
                        tbTransactionOrder.setHasCart(1);
                        tbTransactionKeyWords.setCartNumber(tbTransactionKeyWords.getCartNumber() - 1);
                    } else if (tbTransactionKeyWords.getCollectionCartNumber() != null && tbTransactionKeyWords.getCollectionCartNumber() != 0) {
                        tbTransactionOrder.setHasCollectionCart(1);
                        tbTransactionKeyWords.setCollectionCartNumber(tbTransactionKeyWords.getCollectionCartNumber() - 1);
                    }
                    tbTransactionOrderList.add(tbTransactionOrder);

                    if ((i + 1) % BATCH_ORDER_SIZE == 0 || (i + 1) == tbTransactionKeyWords.getTotalNumber()) {
                        // 取模或者最后一次
                        n += tbTransactionOrderMapper.saveBatchTbTransactionOrder(tbTransactionOrderList);
                        tbTransactionOrderList.clear();
                    }
                }
            }
            re += n;
        }
        return this.deleteTbTransactionTemplateByIds(ids);
    }

    @Override
    public int distributionTeam(Long id, Long teamId, String teamName) {
        TbTransactionTemplate tbTransactionTemplate = tbTransactionTemplateMapper.selectTbTransactionTemplateById(id);
        if (tbTransactionTemplate.getReceiptTeamId() != null) {
            return 0;
        }
        return tbTransactionTemplateMapper.distributionTeam(id, teamId, teamName);
    }

    @Override
    public int updateTbTransactionTemplateRemark(TbTransactionTemplateDto tbTransactionTemplateDto) {
        TbTransactionTemplate tbAfterTransactionTemplate = new TbTransactionTemplate();
        BeanUtils.copyBeanProp(tbAfterTransactionTemplate,tbTransactionTemplateDto);
        return tbTransactionTemplateMapper.updateTbTransactionTemplateRemark(tbAfterTransactionTemplate);
    }
}

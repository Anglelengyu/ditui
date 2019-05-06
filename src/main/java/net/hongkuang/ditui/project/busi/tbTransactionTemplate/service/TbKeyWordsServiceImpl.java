package net.hongkuang.ditui.project.busi.tbTransactionTemplate.service;

import net.hongkuang.ditui.common.utils.UnitUtils;
import net.hongkuang.ditui.common.utils.bean.BeanUtils;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.*;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionKeyWordsMapper;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionQuestionMapper;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.mapper.TbTransactionTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 淘宝关键词 服务层实现
 *
 * @author:zy
 * @date: 2019/4/3
 */
@Service
public class TbKeyWordsServiceImpl implements ITbKeyWordsService {

    @Autowired
    private TbTransactionKeyWordsMapper tbTransactionKeyWordsMapper;
    @Autowired
    private ITbTransactionTemplateService tbTransactionTemplateService;
    @Autowired
    private TbTransactionTemplateMapper tbTransactionTemplateMapper;
    @Autowired
    private TbTransactionQuestionMapper tbTransactionQuestionMapper;

    @Override
    public List<TbTransactionKeyWords> selectTbTransactionKeyWordsList(Long templateId) {
        SearchTbTransactionKeyWords searchTbTransactionKeyWords = new SearchTbTransactionKeyWords();
        searchTbTransactionKeyWords.setTemplateId(templateId);
        return tbTransactionKeyWordsMapper.selectTbTransactionKeyWordsList(searchTbTransactionKeyWords);
    }

    @Override
    @Transactional
    public int updateTbTransactionKeyWords(TbTransactionTemplateDto tbTransactionTemplateDto) {

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
        tbTransactionTemplateService.updateTbTransactionTemplate(tbTransactionTemplate);

        int i = tbTransactionKeyWordsMapper.deleteTbTransactionKeyWordsByTemplateId(tbTransactionTemplate.getId());
        tbTransactionTemplate.getTbTransactionKeyWords().forEach(tbTransactionKeyWords -> {
            tbTransactionKeyWords.setTemplateId(tbTransactionTemplate.getId());
            tbTransactionKeyWordsMapper.saveTbTransactionKeyWords(tbTransactionKeyWords);
        });
        return i;
    }
}

package net.hongkuang.ditui.project.busi.recommend.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.project.busi.recommend.domain.RecommendManager;
import net.hongkuang.ditui.project.busi.recommend.domain.SearchRecommendManager;

import java.util.List;

/**
 * 推荐掌柜
 *
 * @author:zy
 * @date: 2019/3/29
 */
public interface IRecommendManagerService {

    /**
     * 查询推荐掌柜列表
     *
     * @param searchRecommendManager 推荐信息
     * @return 推荐掌柜集合
     */
    public List<RecommendManager> selectRecommendManagerList(SearchRecommendManager searchRecommendManager);

    /**
     * 根据Id查询推荐掌柜信息
     *
     * @param id
     * @return 推荐掌柜信息
     */
    public RecommendManager selectRecommendManagerById(Long id);

    /**
     * 保存推荐掌柜信息
     *
     * @param recommendManager 信息
     * @return 结果
     */
    int insertRecommendManager(RecommendManager recommendManager);

    /**
     * 保存推荐掌柜信息
     *
     * @param recommendManager 信息
     * @return 结果
     */
    int updateRecommendManager(RecommendManager recommendManager);

    /**
     * 删除推荐掌柜
     *
     * @param ids ids
     * @return
     */
    public int remove(String ids) throws BusinessException;

}

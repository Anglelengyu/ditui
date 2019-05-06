package net.hongkuang.ditui.project.busi.recommend.mapper;

import net.hongkuang.ditui.project.busi.recommend.domain.RecommendManager;
import net.hongkuang.ditui.project.busi.recommend.domain.RecommendMiddleManager;
import net.hongkuang.ditui.project.busi.recommend.domain.SearchRecommendManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 推荐掌柜 数据层
 *
 * @author:zy
 * @date: 2019/3/29
 */
@Repository
public interface RecommendManagerMapper {

    /**
     * 查询推荐掌柜列表
     *
     * @param searchRecommendManager 推荐掌柜信息
     * @return 推荐掌柜集合
     */
    List<RecommendManager> selectRecommendManagerList(SearchRecommendManager searchRecommendManager);

    /**
     * 根据Id查询推荐掌柜信息
     *
     * @param id
     * @return 掌柜信息
     */
    RecommendManager selectRecommendManagerById(Long id);

    /**
     * 新增推荐掌柜
     *
     * @param recommendManager
     * @return 掌柜信息
     */
    int insertRecommendManager(RecommendManager recommendManager);

    /**
     * 新增推荐掌柜与团队关联
     *
     * @param recommendMiddleManager
     * @return 掌柜信息
     */
    int insertRecommendMiddleManager(RecommendMiddleManager recommendMiddleManager);

    /**
     * 更新推荐掌柜与团队关联
     *
     * @param recommendManager
     * @return 掌柜信息
     */
    int updateRecommendManager(RecommendManager recommendManager);

    /**
     * 删除推荐掌柜与团队关联
     *
     * @param ids
     * @return 掌柜信息
     */
    int deleteRecommendMiddleManagerByIds(Long[] ids);

    /**
     * 删除推荐掌柜与团队关联
     *
     * @param ids
     * @return 掌柜信息
     */
    int deleteRecommendManagerByIds(Long[] ids);

}

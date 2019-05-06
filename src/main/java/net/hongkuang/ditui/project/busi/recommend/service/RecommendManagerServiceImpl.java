package net.hongkuang.ditui.project.busi.recommend.service;

import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.project.busi.manager.domain.Manager;
import net.hongkuang.ditui.project.busi.manager.mapper.ManagerMapper;
import net.hongkuang.ditui.project.busi.recommend.domain.RecommendManager;
import net.hongkuang.ditui.project.busi.recommend.domain.RecommendMiddleManager;
import net.hongkuang.ditui.project.busi.recommend.domain.SearchRecommendManager;
import net.hongkuang.ditui.project.busi.recommend.mapper.RecommendManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 推荐掌柜 服务层实现
 *
 * @author:zy
 * @date: 2019/3/29
 */
@Service
public class RecommendManagerServiceImpl implements IRecommendManagerService {

    @Autowired
    private RecommendManagerMapper recommendManagerMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public List<RecommendManager> selectRecommendManagerList(SearchRecommendManager searchRecommendManager) {
        return recommendManagerMapper.selectRecommendManagerList(searchRecommendManager);
    }

    @Override
    public RecommendManager selectRecommendManagerById(Long id) {
        RecommendManager recommendManager = recommendManagerMapper.selectRecommendManagerById(id);

        List<Manager> managers = recommendManager.getManagers();
        String ids = "";
        if (managers != null) {
            for (int i = 0; i < managers.size(); i++) {
                ids += managers.get(0).getUserId() + ",";
            }
            recommendManager.setManagerIds(ids);
        }

        return recommendManager;
    }

    @Override
    @Transactional
    public int insertRecommendManager(RecommendManager recommendManager) {
        int i = recommendManagerMapper.insertRecommendManager(recommendManager);
        Long[] managerIds = Convert.toLongArray(recommendManager.getManagerIds());
        for (Long managerId : managerIds) {
            RecommendMiddleManager recommendMiddleManager = new RecommendMiddleManager();
            recommendMiddleManager.setManagerId(managerId);
            recommendMiddleManager.setRecommendManagerId(recommendManager.getId());
            i = recommendManagerMapper.insertRecommendMiddleManager(recommendMiddleManager);
        }
        return i;
    }

    @Override
    @Transactional
    public int updateRecommendManager(RecommendManager recommendManager) {
        Long[] managerIds = Convert.toLongArray(recommendManager.getManagerIds());
        Long[] ids = new Long[]{recommendManager.getId()};
        if (recommendManager.getManagerIds() != null && !recommendManager.getManagerIds().equals("")) {
            int i = recommendManagerMapper.deleteRecommendMiddleManagerByIds(ids);
            for (Long managerId : managerIds) {
                RecommendMiddleManager recommendMiddleManager = new RecommendMiddleManager();
                recommendMiddleManager.setManagerId(managerId);
                recommendMiddleManager.setRecommendManagerId(recommendManager.getId());
                i = recommendManagerMapper.insertRecommendMiddleManager(recommendMiddleManager);
            }
        }

        return recommendManagerMapper.updateRecommendManager(recommendManager);
    }

    @Override
    @Transactional
    public int remove(String ids) throws BusinessException {
        Long[] recommendManagerIds = Convert.toLongArray(ids);
        int i = recommendManagerMapper.deleteRecommendMiddleManagerByIds(recommendManagerIds);
        i = recommendManagerMapper.deleteRecommendManagerByIds(recommendManagerIds);
        return i;
    }
}

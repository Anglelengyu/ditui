package net.hongkuang.ditui.project.busi.onlineEmployeeTask.service.impl;

import java.util.List;

import net.hongkuang.ditui.project.busi.onlineEmployeeTask.domain.OnlineEmployeeTask;
import net.hongkuang.ditui.project.busi.onlineEmployeeTask.mapper.OnlineEmployeeTaskMapper;
import net.hongkuang.ditui.project.busi.onlineEmployeeTask.service.IOnlineEmployeeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.common.support.Convert;

/**
 * 线下员工任务 服务层实现
 *
 * @author:zy
 * @date: 2019/4/19
 */
@Service
public class OnlineEmployeeTaskServiceImpl implements IOnlineEmployeeTaskService {
    @Autowired
    private OnlineEmployeeTaskMapper onlineEmployeeTaskMapper;

    /**
     * 查询线下员工任务信息
     *
     * @param id 线下员工任务ID
     * @return 线下员工任务信息
     */
    @Override
    public OnlineEmployeeTask selectOnlineEmployeeTaskById(Long id) {
        return onlineEmployeeTaskMapper.selectOnlineEmployeeTaskById(id);
    }

    /**
     * 查询线下员工任务列表
     *
     * @param onlineEmployeeTask 线下员工任务信息
     * @return 线下员工任务集合
     */
    @Override
    public List<OnlineEmployeeTask> selectOnlineEmployeeTaskList(OnlineEmployeeTask onlineEmployeeTask) {
        return onlineEmployeeTaskMapper.selectOnlineEmployeeTaskList(onlineEmployeeTask);
    }

    /**
     * 新增线下员工任务
     *
     * @param onlineEmployeeTask 线下员工任务信息
     * @return 结果
     */
    @Override
    public int insertOnlineEmployeeTask(OnlineEmployeeTask onlineEmployeeTask) {
        return onlineEmployeeTaskMapper.insertOnlineEmployeeTask(onlineEmployeeTask);
    }

    /**
     * 修改线下员工任务
     *
     * @param onlineEmployeeTask 线下员工任务信息
     * @return 结果
     */
    @Override
    public int updateOnlineEmployeeTask(OnlineEmployeeTask onlineEmployeeTask) {
        return onlineEmployeeTaskMapper.updateOnlineEmployeeTask(onlineEmployeeTask);
    }

    /**
     * 删除线下员工任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOnlineEmployeeTaskByIds(String ids) {
        return onlineEmployeeTaskMapper.deleteOnlineEmployeeTaskByIds(Convert.toStrArray(ids));
    }

}

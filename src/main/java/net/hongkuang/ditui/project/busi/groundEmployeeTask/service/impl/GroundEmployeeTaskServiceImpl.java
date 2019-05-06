package net.hongkuang.ditui.project.busi.groundEmployeeTask.service.impl;

import java.util.List;

import net.hongkuang.ditui.project.busi.groundEmployeeTask.domain.GroundEmployeeTask;
import net.hongkuang.ditui.project.busi.groundEmployeeTask.mapper.GroundEmployeeTaskMapper;
import net.hongkuang.ditui.project.busi.groundEmployeeTask.service.IGroundEmployeeTaskService;
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
public class GroundEmployeeTaskServiceImpl implements IGroundEmployeeTaskService {
    @Autowired
    private GroundEmployeeTaskMapper groundEmployeeTaskMapper;

    /**
     * 查询线下员工任务信息
     *
     * @param id 线下员工任务ID
     * @return 线下员工任务信息
     */
    @Override
    public GroundEmployeeTask selectGroundEmployeeTaskById(Long id) {
        return groundEmployeeTaskMapper.selectGroundEmployeeTaskById(id);
    }

    /**
     * 查询线下员工任务列表
     *
     * @param groundEmployeeTask 线下员工任务信息
     * @return 线下员工任务集合
     */
    @Override
    public List<GroundEmployeeTask> selectGroundEmployeeTaskList(GroundEmployeeTask groundEmployeeTask) {
        return groundEmployeeTaskMapper.selectGroundEmployeeTaskList(groundEmployeeTask);
    }

    /**
     * 新增线下员工任务
     *
     * @param groundEmployeeTask 线下员工任务信息
     * @return 结果
     */
    @Override
    public int insertGroundEmployeeTask(GroundEmployeeTask groundEmployeeTask) {
        return groundEmployeeTaskMapper.insertGroundEmployeeTask(groundEmployeeTask);
    }

    /**
     * 修改线下员工任务
     *
     * @param groundEmployeeTask 线下员工任务信息
     * @return 结果
     */
    @Override
    public int updateGroundEmployeeTask(GroundEmployeeTask groundEmployeeTask) {
        return groundEmployeeTaskMapper.updateGroundEmployeeTask(groundEmployeeTask);
    }

    /**
     * 删除线下员工任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGroundEmployeeTaskByIds(String ids) {
        return groundEmployeeTaskMapper.deleteGroundEmployeeTaskByIds(Convert.toStrArray(ids));
    }

}

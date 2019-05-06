package net.hongkuang.ditui.project.busi.groundTask.service;

/**
 * 自动分配实时任务
 * @author:zy
 * @date: 2019/4/18
 */
public interface IAutoAllocGroundTaskService {

    int automaticAssign(Long[] ids,Long teamId,String taskDate);



}

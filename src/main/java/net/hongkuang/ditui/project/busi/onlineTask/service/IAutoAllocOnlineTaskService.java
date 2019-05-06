package net.hongkuang.ditui.project.busi.onlineTask.service;

/**
 * 自动分配实时任务
 * @author:zy
 * @date: 2019/4/18
 */
public interface IAutoAllocOnlineTaskService {

    int automaticAssign(Long[] ids,Long teamId,String taskDate);



}

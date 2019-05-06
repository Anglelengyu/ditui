package net.hongkuang.ditui.project.api.service;

import net.hongkuang.ditui.project.api.dto.TodoTaskReqVo;
import net.hongkuang.ditui.project.api.dto.TodoTaskRespVo;

import java.util.List;

/**
 * Created by apple on 2019/1/12.
 */
public interface ITodoTaskService {
    List<TodoTaskRespVo> allocTodoTaskList(TodoTaskReqVo taskReqVo);
}

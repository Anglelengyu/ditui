package net.hongkuang.ditui.project.busi.roleApplyRelation.service;


import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.RoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.SearchRoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.UserRoleApplyRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色之间的申请关联
 *
 * @author:zy
 * @date: 2019/4/02
 */
public interface IRoleApplyRelationService {

    /**
     * 查询关联列表
     *
     * @param searchRoleApplyRelation 信息
     * @return 集合
     */
    public List<RoleApplyRelation> selectRoleApplyRelationList(SearchRoleApplyRelation searchRoleApplyRelation);

    /**
     * 新增关联
     *
     * @param roleApplyRelation 信息
     * @return 集合
     */
    public int save(RoleApplyRelation roleApplyRelation);

    /**
     * 根据编号查询用户信息
     *
     * @param userNum 用户编号
     * @return 用户信息
     */
    public UserRoleApplyRelation selectUserRoleApplyRelationByUserNum(String userNum);

    /**
     * 处理申请
     *
     * @param status 信息
     * @return 结果
     */
    public AjaxResult handle(@Param("ids") String ids, @Param("status") Integer status);

    /**
     * 批量删除关联
     *
     * @param ids 关联ids
     * @return
     */
    public int remove(String ids) throws BusinessException;

}

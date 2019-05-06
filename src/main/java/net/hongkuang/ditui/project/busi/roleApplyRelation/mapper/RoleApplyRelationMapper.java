package net.hongkuang.ditui.project.busi.roleApplyRelation.mapper;

import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.RoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.SearchRoleApplyRelation;
import net.hongkuang.ditui.project.busi.roleApplyRelation.domain.UserRoleApplyRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色之间的申请关联 数据层
 *
 * @author:zy
 * @date: 2019/4/02
 */
@Repository
public interface RoleApplyRelationMapper {

    /**
     * 查询列表
     *
     * @param searchRoleApplyRelation 信息
     * @return 集合
     */
    List<RoleApplyRelation> selectRoleApplyRelationList(SearchRoleApplyRelation searchRoleApplyRelation);

    /**
     * 查询单行
     *
     * @param id 信息
     * @return
     */
    RoleApplyRelation selectRoleApplyRelationById(String id);

    /**
     * 新增关联
     *
     * @param roleApplyRelation 信息
     * @return
     */
    int saveRoleApplyRelation(RoleApplyRelation roleApplyRelation);

    /**
     * 处理申请
     *
     * @param status 信息
     * @return 结果
     */
    int handle(@Param("ids") String[] ids, @Param("status") Integer status);

    /**
     * 查询单行
     *
     * @param userNum 信息
     * @return
     */
    UserRoleApplyRelation selectUserRoleApplyRelationByUserNum(String userNum);

    /**
     * 删除关联
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    int deleteDelFlagById(@Param("id") String id, @Param("activeApplyDelFlag") String activeApplyDelFlag, @Param("passiveApplyDelFlag") String passiveApplyDelFlag);

}

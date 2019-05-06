package net.hongkuang.ditui.project.userCenter.packageTemplate.service;

import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplateJoinRole;

import java.util.List;

/**
 * 套餐模板关联角色 服务层
 *
 * @author yj
 * @date 2019-04-17
 */
public interface IPackageTemplateJoinRoleService {
    /**
     * 查询
     *
     * @param packageTemplateId 套餐模板ID
     * @return
     */
    public List<PackageTemplateJoinRole> selectByPackageTemplateId(Integer packageTemplateId);

    /**
     * 新增
     *
     * @param packageTemplateJoinRoleList 套餐模板信息
     * @return 结果
     */
    public int insertPackageTemplateJoinRole(List<PackageTemplateJoinRole> packageTemplateJoinRoleList);

    /**
     * 删除
     *
     * @param packageTemplateIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteByPackageTemplateIds(String packageTemplateIds);

}

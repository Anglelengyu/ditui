package net.hongkuang.ditui.project.userCenter.packageTemplate.mapper;

import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplateJoinRole;

import java.util.List;

/**
 * @ProjectName: ditui
 * @ClassName: PackageTemplateJoinRoleMapper
 * @Author: YanJie
 * @Description:
 * @Date: 2019/4/17 23:41
 * @Version: 1.0
 */
public interface PackageTemplateJoinRoleMapper {

    public List<PackageTemplateJoinRole> selectByPackageTemplateId(Integer packageTemplateId);

    int insertPackageTemplateJoinRole(List<PackageTemplateJoinRole> packageTemplateJoinRoleList);

    int deleteByPackageTemplateIds(String[] packageTemplateIds);

}

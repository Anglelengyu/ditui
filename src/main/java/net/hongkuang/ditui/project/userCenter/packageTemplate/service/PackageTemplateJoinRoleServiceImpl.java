package net.hongkuang.ditui.project.userCenter.packageTemplate.service;

import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplateJoinRole;
import net.hongkuang.ditui.project.userCenter.packageTemplate.mapper.PackageTemplateJoinRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 套餐模板关联角色 服务层
 *
 * @author yj
 * @date 2019-04-17
 */
@Service
public class PackageTemplateJoinRoleServiceImpl implements IPackageTemplateJoinRoleService {

    @Autowired
    private PackageTemplateJoinRoleMapper packageTemplateJoinRoleMapper;


    @Override
    public List<PackageTemplateJoinRole> selectByPackageTemplateId(Integer packageTemplateId) {
        return packageTemplateJoinRoleMapper.selectByPackageTemplateId(packageTemplateId);
    }

    @Override
    public int insertPackageTemplateJoinRole(List<PackageTemplateJoinRole> packageTemplateJoinRoleList) {
        return packageTemplateJoinRoleMapper.insertPackageTemplateJoinRole(packageTemplateJoinRoleList);
    }

    @Override
    public int deleteByPackageTemplateIds(String packageTemplateIds) {
        return packageTemplateJoinRoleMapper.deleteByPackageTemplateIds(Convert.toStrArray(packageTemplateIds));
    }
}

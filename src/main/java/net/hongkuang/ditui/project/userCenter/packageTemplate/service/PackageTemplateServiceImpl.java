package net.hongkuang.ditui.project.userCenter.packageTemplate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Joiner;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.project.system.role.mapper.RoleMapper;
import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplateJoinRole;
import net.hongkuang.ditui.project.userCenter.packageTemplate.mapper.PackageTemplateJoinRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.hongkuang.ditui.project.userCenter.packageTemplate.mapper.PackageTemplateMapper;
import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplate;
import net.hongkuang.ditui.common.support.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 套餐模板 服务层实现
 *
 * @author yj
 * @date 2019-04-17
 */
@Service
public class PackageTemplateServiceImpl implements IPackageTemplateService {

    @Autowired
    private PackageTemplateMapper packageTemplateMapper;

    @Autowired
    private PackageTemplateJoinRoleMapper packageTemplateJoinRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询套餐模板信息
     *
     * @param id 套餐模板ID
     * @return 套餐模板信息
     */
    @Override
    public PackageTemplate selectPackageTemplateById(Integer id) {
        return packageTemplateMapper.selectPackageTemplateById(id);
    }

    /**
     * 查询套餐模板列表
     *
     * @param packageTemplate 套餐模板信息
     * @return 套餐模板集合
     */
    @Override
    public List<PackageTemplate> selectPackageTemplateList(PackageTemplate packageTemplate) {
        List<PackageTemplate> templateList = packageTemplateMapper.selectPackageTemplateList(packageTemplate);
        templateList.stream().forEach(temp -> {
            temp.setJoinRoleList(packageTemplateJoinRoleMapper.selectByPackageTemplateId(temp.getId()));
            temp.getJoinRoleList().stream().forEach(vo ->
                    temp.setRoleNames(roleMapper.selectRoleById(vo.getRoleId().longValue()).getRoleName().trim() + ",")
            );
        });
        return templateList;
    }

    /**
     * 新增套餐模板
     *
     * @param packageTemplate 套餐模板信息
     * @return 结果
     */
    @Override
    public int insertPackageTemplate(PackageTemplate packageTemplate) {
        packageTemplate.setCreateBy(ShiroUtils.getUserId().toString());
        packageTemplate.setUpdateBy(ShiroUtils.getUserId().toString());
        packageTemplate.setCreateTime(new Date());
        packageTemplate.setUpdateTime(new Date());
        int count = packageTemplateMapper.insertPackageTemplate(packageTemplate);
        if (count > 0) {
            List<PackageTemplateJoinRole> saveList = buildList(packageTemplate.getJoinRoleList(), packageTemplate.getId());
            return packageTemplateJoinRoleMapper.insertPackageTemplateJoinRole(saveList);
        }
        return 0;
    }

    /**
     * 修改套餐模板
     *
     * @param packageTemplate 套餐模板信息
     * @return 结果
     */
    @Override
    public int updatePackageTemplate(PackageTemplate packageTemplate) {
        packageTemplate.setUpdateBy(ShiroUtils.getUserId().toString());
        packageTemplate.setUpdateTime(new Date());
        int count = packageTemplateMapper.updatePackageTemplate(packageTemplate);
        //删除保存
        packageTemplateJoinRoleMapper.deleteByPackageTemplateIds(Convert.toStrArray(packageTemplate.getId().toString()));
        List<PackageTemplateJoinRole> saveList = buildList(packageTemplate.getJoinRoleList(), packageTemplate.getId());
        return packageTemplateJoinRoleMapper.insertPackageTemplateJoinRole(saveList);
    }

    /**
     * 删除套餐模板对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePackageTemplateByIds(String ids) {
        int count = packageTemplateMapper.deletePackageTemplateByIds(Convert.toStrArray(ids));
        if (count > 0)
            count = packageTemplateJoinRoleMapper.deleteByPackageTemplateIds(Convert.toStrArray(ids));
        return count;
    }

    @Override
    public List<PackageTemplate> selectPackageTemplateListByRoleIds(List<String> ids) {
        return packageTemplateMapper.selectPackageTemplateListByRoleIds(ids.toArray(new String[ids.size()]));
    }

    private List<PackageTemplateJoinRole> buildList(List<PackageTemplateJoinRole> joinRoleList, Integer id) {
        List<PackageTemplateJoinRole> saveList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(joinRoleList)) {
            joinRoleList.stream().forEach(temp -> temp.setPackageTemplateId(id));
            joinRoleList.stream().forEach(temp -> Optional.ofNullable(temp).ifPresent(pack -> {
                if (pack.getPackageTemplateId() != null && pack.getRoleId() != null) {
                    saveList.add(pack);
                }
            }));
        }
        return saveList;
    }

}

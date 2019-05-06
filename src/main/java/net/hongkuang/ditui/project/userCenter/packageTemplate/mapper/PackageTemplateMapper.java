package net.hongkuang.ditui.project.userCenter.packageTemplate.mapper;

        import net.hongkuang.ditui.project.userCenter.packageTemplate.domain.PackageTemplate;
        import java.util.List;

/**
 * 套餐模板 数据层
 *
 * @author yj
 * @date 2019-04-17
 */
public interface PackageTemplateMapper {
    /**
     * 查询套餐模板信息
     *
     * @param id 套餐模板ID
     * @return 套餐模板信息
     */
    public PackageTemplate selectPackageTemplateById(Integer id);

    /**
     * 查询套餐模板列表
     *
     * @param packageTemplate 套餐模板信息
     * @return 套餐模板集合
     */
    public List<PackageTemplate> selectPackageTemplateList(PackageTemplate packageTemplate);

    /**
     * 新增套餐模板
     *
     * @param packageTemplate 套餐模板信息
     * @return 结果
     */
    public int insertPackageTemplate(PackageTemplate packageTemplate);

    /**
     * 修改套餐模板
     *
     * @param packageTemplate 套餐模板信息
     * @return 结果
     */
    public int updatePackageTemplate(PackageTemplate packageTemplate);

    /**
     * 删除套餐模板
     *
     * @param id 套餐模板ID
     * @return 结果
     */
    public int deletePackageTemplateById(Integer id);

    /**
     * 批量删除套餐模板
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePackageTemplateByIds(String[] ids);

    /**
     *
     * @param ids
     * @return
     */
    List<PackageTemplate> selectPackageTemplateListByRoleIds(String[] ids);
}
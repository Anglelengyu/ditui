package net.hongkuang.ditui.project.system.user.service.impl;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.exception.BusinessException;
import net.hongkuang.ditui.common.support.Convert;
import net.hongkuang.ditui.common.utils.AddressUtils;
import net.hongkuang.ditui.common.utils.RandomUtil;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.security.ShiroUtils;
import net.hongkuang.ditui.framework.aspectj.lang.annotation.DataScope;
import net.hongkuang.ditui.framework.shiro.service.PasswordService;
import net.hongkuang.ditui.project.busi.employee.domain.TeamMiddleEmployee;
import net.hongkuang.ditui.project.busi.employee.service.IEmployeeService;
import net.hongkuang.ditui.project.busi.team.domain.Team;
import net.hongkuang.ditui.project.busi.team.service.ITeamService;
import net.hongkuang.ditui.project.system.user.domain.UserRegister;
import net.hongkuang.ditui.project.system.role.domain.Role;
import net.hongkuang.ditui.project.system.role.domain.RoleMenu;
import net.hongkuang.ditui.project.system.role.mapper.RoleMapper;
import net.hongkuang.ditui.project.system.role.mapper.RoleMenuMapper;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.domain.UserMenu;
import net.hongkuang.ditui.project.system.user.domain.UserRole;
import net.hongkuang.ditui.project.system.user.mapper.UserMapper;
import net.hongkuang.ditui.project.system.user.mapper.UserMenuMapper;
import net.hongkuang.ditui.project.system.user.mapper.UserRoleMapper;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserMenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IEmployeeService employeeService;


    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(tableAlias = "u")
    public List<User> selectUserList(User user) {
        // 生成数据权限过滤条件
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(tableAlias = "u")
    public List<User> selectInviteUserList(User user) {
        //必须要是成为VIP的
        User queryUser = userMapper.selectUserById(ShiroUtils.getUserId());
        if (queryUser != null && queryUser.getVipStatus() != null && queryUser.getVipStatus()) {
            user.setInviterId(ShiroUtils.getUserId().intValue());
            user.setVipTime(queryUser.getVipTime());
            return userMapper.selectUserList(user);
        }
      return Collections.emptyList();
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public User selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws BusinessException {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            if (User.isAdmin(userId)) {
                throw new BusinessException("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        user.setUserNum(RandomUtil.genString());
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        user = getUserMenuIds(user);
        // 新增用户与菜单
        insertUserMenu(user);
        return rows;
    }

    /**
     * 新增保存用户信息
     *
     * @param userRegister
     * @return 结果
     */
    @Override
    public int insertUser(UserRegister userRegister) {
        if (StringUtils.isBlank(userRegister.getSmsCode())) {
            throw new BusinessException("短信验证码不能为空！");
        }
        //获取session中的验证码进行比对
        // TODO
//        if(StringUtils.isBlank(userRegister.getSmsCode())){
//            throw new BusinessException("短信验证码不能为空！");
//        }
        String ip = AddressUtils.getRealAddressByIP(ShiroUtils.getIp());
        Role role = roleMapper.selectRoleById(userRegister.getRole().getRoleId());
        userRegister.setRole(role);
        if (StringUtils.isNotBlank(userRegister.getEmail())) {
            if (userMapper.checkEmailUnique(userRegister.getEmail()) != null) {
                throw new BusinessException("当前邮箱地址已存在");
            }
        }
        if (StringUtils.isNotBlank(userRegister.getLoginName())) {
            if (userMapper.checkLoginNameUnique(userRegister.getLoginName()) > 0) {
                throw new BusinessException("当前登录账号已存在");
            }
        }
        if (StringUtils.isNotBlank(userRegister.getPhonenumber())) {
            if (userMapper.checkPhoneUnique(userRegister.getPhonenumber()) != null) {
                throw new BusinessException("当前手机号码已存在");
            }
        }
        if (StringUtils.isNotBlank(ip)) {
            if (userMapper.checkIpUnique(ip, userRegister.getRole().getRoleId().toString()) > 0) {
                throw new BusinessException("当前IP已注册相同角色的其他号码");
            }
        }
        User user = new User();
        BeanUtils.copyProperties(userRegister, user);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        user.setRoles(roleList);
        user.setRoleIds(new Long[]{role.getRoleId()});

        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(userRegister.getLoginName());
        user.setUserNum(RandomUtil.genString());
        user.setLoginIp(ip);
        if (StringUtils.isBlank(userRegister.getUserNum())) {
            //如果推荐人不为空，保存推荐人信息
            User inviterUser = userMapper.selectByUserNum(userRegister.getUserNum());
            user.setInviterId(Optional.ofNullable(inviterUser).get().getUserId().intValue());
        }

        // 新增用户信息
        int rows = userMapper.insertUser(user);
        if (!ObjectUtils.isEmpty(role)) {
            switch (userRegister.getRole().getRoleKey()) {
                case UserConstants.UserRoles.ROLE_SHOP_MANAGER:
                    break;
                case UserConstants.UserRoles.ROLE_SHOP_REFER:
                    break;
                case UserConstants.UserRoles.ROLE_GROUP_LEADER:
                    break;
                case UserConstants.UserRoles.ROLE_TEAM:
                    break;
                case UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE:
                case UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE:
                    if (StringUtils.isBlank(userRegister.getUserNum())) {
                        throw new BusinessException("关联码不能为空");
                    }
                    //根据关联码查询团队账号
                    Team team = teamService.selectTeamByUserNum(userRegister.getUserNum());
                    if (ObjectUtils.isEmpty(team)) {
                        team = new Team();
                        team.setTeamId(ShiroUtils.getUserId());
                    }
                    //保存于团队的关联关系
                    TeamMiddleEmployee teamMiddleEmployee = new TeamMiddleEmployee();
                    teamMiddleEmployee.setUpdateTime(new Date());
                    teamMiddleEmployee.setTeamId(team.getTeamId());
                    teamMiddleEmployee.setEmployeeId(user.getUserId());
                    teamMiddleEmployee.setCreateTime(new Date());
                    rows = employeeService.insertTeamMiddleEmployee(teamMiddleEmployee);
                    break;
                default:
                    break;
            }
        }
        // 新增用户与角色管理
        insertUserRole(user);
        user = getUserMenuIds(user);
        // 新增用户与菜单
        insertUserMenu(user);

        return rows;
    }


    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user) {
        Long userId = user.getUserId();
        user.setUpdateBy(ShiroUtils.getLoginName());
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        user = getUserMenuIds(user);
        // 更新用户与菜单
        updateUserMenu(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user) {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user) {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long roleId : user.getRoleIds()) {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(User user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    @Override
    public List<User> selectAllManager() {
        List<User> users = userMapper.selectShopManagerUser();
        return users;
    }

    /*
     * 获取店铺推荐人列表
     * */
    @Override
    public List<User> selectAllShopRefer() {
        List<User> users = userMapper.selectShopReferUser();
        return users;
    }

    /*
     * 获取业务组长列表
     * */
    @Override
    public List<User> selectAllGroupLeader() {
        List<User> users = userMapper.selectGroupLeader();
        return users;
    }

    public User getUserMenuIds(User user) {
        List<Long> list = new ArrayList<Long>();
        for (Long roleId : user.getRoleIds()) {
            List<RoleMenu> roleMenuList = roleMenuMapper.selectRoleMenuByRoleId(roleId);
            for (RoleMenu roleMenu : roleMenuList) {
                list.add(roleMenu.getMenuId());
            }
        }
        user.setMenuIds(list.toArray(new Long[list.size()]));
        return user;
    }

    /**
     * 新增保存用户菜单信息
     *
     * @param user 用户菜单信息
     * @return 结果
     */
    @Override
    public int insertUserMenu(User user) {
        ShiroUtils.clearCachedAuthorizationInfo();
        int rows = 1;
        // 新增用户与角色管理
        List<UserMenu> list = new ArrayList<UserMenu>();
        for (Long menuId : user.getMenuIds()) {
            UserMenu um = new UserMenu();
            um.setUserId(user.getUserId());
            um.setMenuId(menuId);
            list.add(um);
        }
        if (list.size() > 0) {
            rows = menuMapper.batchUserMenu(list);
        }
        return rows;
    }

    /**
     * 修改保存用户菜单信息
     *
     * @param user 用户菜单信息
     * @return 结果
     */
    @Override
    public int updateUserMenu(User user) {
        ShiroUtils.clearCachedAuthorizationInfo();
        // 删除角色与菜单关联
        menuMapper.deleteUserMenuByUserId(user.getUserId());
        return insertUserMenu(user);
    }

    @Override
    public int becomeVip(Boolean isSuper, Long userId) {
        User user = new User();
        user.setUserId(userId);
        if (!isSuper) {
            user.setVipStatus(Boolean.TRUE);
        } else {
            user.setSuperVipStatus(Boolean.TRUE);
        }
        return userMapper.becomeVip(user);
    }
}

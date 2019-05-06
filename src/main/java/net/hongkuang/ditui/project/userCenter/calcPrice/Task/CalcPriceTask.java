package net.hongkuang.ditui.project.userCenter.calcPrice.Task;

import com.google.common.collect.Lists;
import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.common.utils.Arith;
import net.hongkuang.ditui.project.busi.order.mapper.OrderMapper;
import net.hongkuang.ditui.project.system.role.domain.Role;
import net.hongkuang.ditui.project.system.role.mapper.RoleMapper;
import net.hongkuang.ditui.project.system.user.domain.User;
import net.hongkuang.ditui.project.system.user.mapper.UserMapper;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.domain.CoinsConsumptionLog;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.mapper.CoinsConsumptionLogMapper;
import net.hongkuang.ditui.project.userCenter.coinsConsumptionLog.support.ConsumptionLogStatus;
import net.hongkuang.ditui.project.userCenter.employeeAccount.domain.EmployeeAccount;
import net.hongkuang.ditui.project.userCenter.employeeAccount.mapper.EmployeeAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ProjectName: ditui
 * @ClassName: CalcPriceTask
 * @Author: YanJie
 * @Description:
 * @Date: 2019/4/21 17:03
 * @Version: 1.0
 */
@Component("calcPriceTask")
public class CalcPriceTask {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private EmployeeAccountMapper accountMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CoinsConsumptionLogMapper consumptionLogMapper;

    @Value("${platform.partner.ordinaryLimit}")
    private Integer ordinaryLimit;
    @Value("${platform.partner.part-timeLimit}")
    private Integer partTimeLimit;
    @Value("${platform.partner.full-timeLimit}")
    private Integer fullTimeLimit;
    @Value("${platform.partner.strengthLimit}")
    private Integer strengthLimit;
    @Value("${platform.partner.superLimit}")
    private Integer superLimit;

    @Value("${platform.partner.levelOne}")
    private Integer levelOne;
    @Value("${platform.partner.levelTwo}")
    private Integer levelTwo;
    @Value("${platform.partner.levelFive}")
    private Integer levelFive;

    /**
     * 计算邀请提现
     */
    public void initCalcPriceNoParam() {
        final List<User> users = userMapper.selectUserList(new User());
        users.stream().forEach(user -> {
            if (user != null && user.getVipStatus() != null && user.getVipStatus()) {
                List<User> list = this.buildInfo(user);
                //查询下一级-在所有一级都已经计算完成后-孙子级
                if (!CollectionUtils.isEmpty(list)) {
                    for (User manager : list) {
                        this.buildInfo(manager);
                    }
                }
            }
        });
    }

    private List<User> buildInfo(User user) {
        //查询邀请人数-注册时间必须>=成为VIP的时间
        User queryUser = new User();
        queryUser.setInviterId(user.getUserId().intValue());
        queryUser.setCreateTime(user.getVipTime());
        List<User> inviterUsers = userMapper.selectUserList(queryUser);
        if (!inviterUsers.isEmpty()) {
            List<User> managerUser = Lists.newArrayList();
            List<User> teamUser = Lists.newArrayList();
            List<User> otherUser = Lists.newArrayList();
            inviterUsers.stream().forEach(inviterUser -> {
                //查询基础消费记录-只有充值之后的会员才被统计
                EmployeeAccount employeeAccount = accountMapper.selectEmployeeAccountByUserId(inviterUser.getUserId().intValue());
                //充值比例1:1，历史记录超过100为有效用户
                if (employeeAccount != null && employeeAccount.getCountPrice() >= 100) {
                    List<Role> roles = roleMapper.selectRolesByUserId(user.getUserId());
                    switch (roles.get(0).getRoleKey()) {
                        case UserConstants.UserRoles.ROLE_SHOP_MANAGER:
                            managerUser.add(inviterUser);
                            break;
                        case UserConstants.UserRoles.ROLE_TEAM:
                            teamUser.add(inviterUser);
                            break;
                        default:
                            otherUser.add(inviterUser);
                            break;
                    }
                } else {
                    otherUser.add(inviterUser);
                }
            });
            int inviteNum = managerUser.size() + teamUser.size() * 3;
            AtomicLong price = new AtomicLong(0L);
            Integer limit = 0;
            if (user.getSuperVipStatus() != null && user.getSuperVipStatus()) {
                limit = superLimit;
            } else {
                if (inviteNum >= levelOne && inviteNum <= levelTwo) {
                    limit = partTimeLimit;
                } else if (inviteNum > levelTwo && inviteNum <= levelFive) {
                    limit = fullTimeLimit;
                } else if (inviteNum > levelFive) {
                    limit = strengthLimit;
                } else {
                    limit = ordinaryLimit;
                }
            }
            double finalPrice = 0;
            //查询每个人的充值情况
            managerUser.addAll(teamUser);
            for (User manager : managerUser) {
                LocalDateTime today = LocalDateTime.now();
                today = today.minusMonths(1);
                LocalDateTime firstDay = today.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
                LocalDateTime lastDay = today.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
                CoinsConsumptionLog log = new CoinsConsumptionLog();
                log.setEmpId(manager.getUserId().intValue());
                log.setStatus(ConsumptionLogStatus.RECHARGE);
//                        log.setStartTime(firstDay);
//                        log.setEndTime(lastDay);
                List<CoinsConsumptionLog> logs = consumptionLogMapper.selectCoinsConsumptionLogList(log);
                logs.forEach(logVo -> {
                    price.updateAndGet(v -> v + logVo.getPrice());
                });
                //最终提成
                finalPrice += Arith.mul(price.longValue(), Arith.div(limit, 100));
            }
            //保存


            if (user.getSuperVipStatus() != null && user.getSuperVipStatus()) {
                return managerUser;
            }
        }
        return Collections.EMPTY_LIST;
    }
}

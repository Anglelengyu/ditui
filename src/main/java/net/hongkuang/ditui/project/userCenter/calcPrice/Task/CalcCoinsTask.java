package net.hongkuang.ditui.project.userCenter.calcPrice.Task;

import net.hongkuang.ditui.common.constant.UserConstants;
import net.hongkuang.ditui.project.busi.order.domain.Order;
import net.hongkuang.ditui.project.busi.order.domain.SearchOrder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: ditui
 * @ClassName: CalcPriceTask
 * @Author: YanJie
 * @Description:
 * @Date: 2019/4/21 17:03
 * @Version: 1.0
 */
@Component("calcCoinsTask")
public class CalcCoinsTask {

    private static final Logger logger = LoggerFactory.getLogger(CalcCoinsTask.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EmployeeAccountMapper accountMapper;
    @Autowired
    private CoinsConsumptionLogMapper consumptionLogMapper;
    @Value("${platform.user.trialTime}")
    private Integer trialTime;
    @Value("${platform.user.managerLimit}")
    private Integer managerLimit;
    @Value("${platform.user.teamLimit}")
    private Integer teamLimit;
    @Value("${platform.user.employeeLimit}")
    private Integer employeeLimit;

    /**
     * 计算金币
     */
    public void initCalcCoinsNoParam() {
        logger.info("================================>{} 定时扣币任务 START<======================", LocalDateTime.now());
        final List<User> users = userMapper.selectUserList(new User());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        users.stream().forEach(user -> {
            if (user.getUserId() != null && 1L == user.getUserId()) {
                logger.info("isAdmin！");
                return;
            }
            //是否超过试用期
            Instant instant = user.getCreateTime().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);

            //6==试用期时间
            Long time = LocalDateTime.now().minusDays(trialTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Long createTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (createTime > time) {
                logger.info("{} 当前用户还在试用期！{}", user.getUserId(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreateTime()));
                return;
            }
            SearchOrder searchOrder = new SearchOrder();
            LocalDateTime today = LocalDateTime.now();
            today = today.minusMonths(1);
            LocalDateTime firstDay = today.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime lastDay = today.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
            searchOrder.setStartTime(firstDay.format(formatter));
            searchOrder.setEndTime(lastDay.format(formatter));
            searchOrder.setSalesmanId(user.getUserId());
            List<Order> orders = orderMapper.selectOrderList(searchOrder);
            Integer orderCount = orders.size();
            Integer deductionCoins = 0;
            List<Role> roles = roleMapper.selectRolesByUserId(user.getUserId());
            switch (roles.get(0).getRoleKey()) {
                case UserConstants.UserRoles.ROLE_SHOP_MANAGER:
                    if (orderCount > managerLimit) {
                        deductionCoins = orderCount;
                    }
                    break;
                case UserConstants.UserRoles.ROLE_TEAM:
                    if (orderCount > teamLimit) {
                        deductionCoins = orderCount;
                    }
                    break;
                case UserConstants.UserRoles.ROLE_GROUND_EMPLOYEE:
                case UserConstants.UserRoles.ROLE_ONLINE_EMPLOYEE:
                    if (orderCount > employeeLimit) {
                        deductionCoins = orderCount;
                    }
                    break;
                default:
                    logger.info("当前用户不是需要扣费的角色，{}，{}", user.getUserId(), roles.get(0).getRoleName());
            }
            if (deductionCoins == 0) {
                return;
            }
            EmployeeAccount employeeAccount = accountMapper.selectEmployeeAccountByUserId(user.getUserId().intValue());
            if (ObjectUtils.isEmpty(employeeAccount)) {
                //可能只是试用期之后就不使用了的
                logger.info("{} 当前用户没有账户", user.getUserId());
                return;
            }
            //update account
            EmployeeAccount saveAccount = new EmployeeAccount();
            saveAccount.setId(employeeAccount.getId());
            saveAccount.setCurrentPrice(employeeAccount.getCurrentPrice() - deductionCoins.longValue());
            saveAccount.setRechargePrice(employeeAccount.getRechargePrice() + deductionCoins.longValue());
            saveAccount.setCountPrice(employeeAccount.getCountPrice());
            saveAccount.setEmpId(user.getUserId());
            saveAccount.setUpdateBy("SYSTEM_TASK");
            saveAccount.setUpdateTime(new Date());
            accountMapper.updateEmployeeAccount(saveAccount);
            //add log
            CoinsConsumptionLog log = new CoinsConsumptionLog();
            log.setPrice(0L);
            log.setBaseCoin(deductionCoins.longValue());
            log.setGiveCoin(0L);
            log.setEmpId(user.getUserId().intValue());
            log.setStatus(ConsumptionLogStatus.DEDUCTION);
            log.setCreateBy("SYSTEM_TASK");
            log.setCreateTime(new Date());
            log.setUpdateBy("SYSTEM_TASK");
            log.setUpdateTime(new Date());
            consumptionLogMapper.insertCoinsConsumptionLog(log);
        });
        logger.info("================================>{} 定时扣币任务 END<======================", LocalDateTime.now());
    }

}

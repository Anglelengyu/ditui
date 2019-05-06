package net.hongkuang.ditui.project.system.user.controller;

import net.hongkuang.ditui.common.constant.Constants;
import net.hongkuang.ditui.framework.config.RememberMeCheck;
import net.hongkuang.ditui.framework.config.RuoYiConfig;
import net.hongkuang.ditui.framework.web.controller.BaseController;
import net.hongkuang.ditui.project.system.menu.domain.Menu;
import net.hongkuang.ditui.project.system.menu.service.IMenuService;
import net.hongkuang.ditui.project.system.notice.domain.Notice;
import net.hongkuang.ditui.project.system.notice.service.INoticeService;
import net.hongkuang.ditui.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 首页 业务处理
 *
 * @author ruoyi
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 系统首页
    @RememberMeCheck
    @GetMapping("/index")
    public String index(ModelMap mmap) {
        // 取身份信息
        User user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap) {
        Notice notice = noticeService.selectTop1NoticeByShowArea(Constants.NOTICE_PC);
        mmap.put("notice", notice);
        mmap.put("version", ruoYiConfig.getVersion());
        return "main";
    }
}

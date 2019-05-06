package net.hongkuang.ditui.project.api.controller;

import net.hongkuang.ditui.common.constant.Constants;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.config.AccessCheck;
import net.hongkuang.ditui.project.api.dto.UserInfoReqVo;
import net.hongkuang.ditui.project.api.dto.UserLoginVo;
import net.hongkuang.ditui.project.api.service.ISalesmanApiService;
import net.hongkuang.ditui.project.system.notice.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by apple on 2019/1/8.
 */
@RestController
@RequestMapping("api/notice")
public class NoticeApiController {

    @Autowired
    private INoticeService noticeService;

    @AccessCheck
    @PostMapping("/getNotice")
    public AjaxResult getNotice() {
        return AjaxResult.success().put("data", noticeService.selectTop1NoticeByShowArea(Constants.NOTICE_MOBILE));
    }

    @AccessCheck
    @PostMapping("/getNotice/{id}")
    public AjaxResult details(@PathVariable("id") final Long id) {
        return AjaxResult.success().put("data", noticeService.selectNoticeById(id));
    }
}

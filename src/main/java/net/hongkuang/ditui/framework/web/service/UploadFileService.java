package net.hongkuang.ditui.framework.web.service;

import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.framework.config.RuoYiConfig;
import net.hongkuang.ditui.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RuoYi首创 html调用 thymeleaf 实现上传文件服务获取管理
 *
 * @author ruoyi
 */
@Service("uploadfile")
public class UploadFileService {

    @Autowired
    private IUserService userService;

    /**
     * 根据键名查询参数配置信息
     *
     * @return 参数键值
     */
    public String getFileUrl(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        if (fileName.indexOf("http") == 0) {
            return fileName;
        }
        return RuoYiConfig.getAccessFileUrl() + "/" + fileName;
    }

    public String getAccessFileUrl() {
        return RuoYiConfig.getAccessFileUrl();
    }
}

package net.hongkuang.ditui.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "hk")
public class RuoYiConfig {
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;
    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;
    /**
     * 获取文件访问地址
     */
    private static String accessFileUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    public static String getAvatarPath() {
        return profile + "avatar/";
    }

    public static String getUploadFolderPath() {
        return profile + "/files/";
    }

    public static String getDownloadPath() {
        return profile + "download/";
    }

    public void setAccessFileUrl(String accessFileUrl) {
        RuoYiConfig.accessFileUrl = accessFileUrl;
    }

    public static String getAccessFileUrl() {
        return accessFileUrl;
    }
}

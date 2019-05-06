package net.hongkuang.ditui.project.api.config;

import net.hongkuang.ditui.project.api.handler.AESUtil;

/**
 * Created by apple on 2019/1/8.
 */
public class UserTokenInfo {
    private String userId;
    private Long expireTime;

    public UserTokenInfo() {

    }

    public UserTokenInfo(String userId) {
        this.userId = userId;
        // 1年有效期
        this.expireTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 365L;
    }

    public UserTokenInfo(String userId, Long expireTime) {
        this.userId = userId;
        // 2小时有效期
        this.expireTime = expireTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String toEntryString() {
        return this.userId + "#" + this.expireTime;
    }

    public static UserTokenInfo parse(String token) {
        String decryptToken = AESUtil.decrypt(token);
        String[] userTokenStr = decryptToken.split("#");
        return new UserTokenInfo(userTokenStr[0], Long.parseLong(userTokenStr[1]));
    }

    public String getToken() {
        return AESUtil.encrypt(this.toEntryString());
    }

    @Override
    public String toString() {
        return "UserTokenInfo{" +
                "userId='" + userId + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}

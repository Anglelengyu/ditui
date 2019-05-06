package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/8.
 */
public class UserInfoReqVo {
    private Long userId;
    private String token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserInfoReqVo{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}

package net.hongkuang.ditui.project.api.dto;

/**
 * Created by apple on 2019/1/8.
 */
public class UserLoginRespVo {
    private Long userId;
    private String name;
    private String head;
    private String area;
    private String phone;
    private String token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserLoginRespVo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", head='" + head + '\'' +
                ", area='" + area + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

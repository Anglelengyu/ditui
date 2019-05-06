package net.hongkuang.ditui.project.api.config;

/**
 * Created by apple on 2019/1/11.
 */
public class UserHolder {
    private static ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();


    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }
}

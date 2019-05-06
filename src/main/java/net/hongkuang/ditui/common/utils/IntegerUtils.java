package net.hongkuang.ditui.common.utils;

/**
 * @author:zy
 * @date: 2019/4/11 0011
 */
public class IntegerUtils {

    public static Integer toInt(Integer number) {
        if (number == null) {
            return 0;
        }
        if (number < 0) {
            return 0;
        }
        return number;
    }

}

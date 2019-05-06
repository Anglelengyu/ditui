package net.hongkuang.ditui.common.utils;

import java.math.BigDecimal;

/**
 * Created by apple on 2019/1/1.
 */
public class UnitUtils {

    public static final BigDecimal HUNDRED = new BigDecimal(100);

    public static Long unitYuan(BigDecimal fenUnitPrice) {
        if (fenUnitPrice == null) {
            throw new IllegalArgumentException("fenUnitPrice is invalid");
        }
        return fenUnitPrice.multiply(HUNDRED).longValue();
    }

    public static Long unitYuan(Long fenUnitPrice) {
        if (fenUnitPrice == null) {
            throw new IllegalArgumentException("fenUnitPrice is invalid");
        }
        return fenUnitPrice / (HUNDRED).longValue();
    }

    public static String unitYuanString(Long fenUnitPrice) {
        if (fenUnitPrice == null) {
            throw new IllegalArgumentException("fenUnitPrice is invalid");
        }
        if (fenUnitPrice == 0L) {
            return "0.00";
        }
        return String.format("%.2f", fenUnitPrice / (HUNDRED).doubleValue());
    }

    public static String unitYuanString(Long fenUnitPrice, String defaultValue) {
        if (fenUnitPrice == null) {
            return defaultValue;
        }
        if (fenUnitPrice == 0L) {
            return "0.00";
        }
        return String.format("%.2f", fenUnitPrice / (HUNDRED).doubleValue());
    }

    public static Long unitFen(Long yuanUnitPrice) {
        if (yuanUnitPrice == null) {
            throw new IllegalArgumentException("fenUnitPrice is invalid");
        }
        return yuanUnitPrice * HUNDRED.longValue();
    }

    public static Long unitFen(BigDecimal yuanUnitPrice) {
        if (yuanUnitPrice == null) {
            throw new IllegalArgumentException("fenUnitPrice is invalid");
        }
        return yuanUnitPrice.multiply(HUNDRED).longValue();
    }
}

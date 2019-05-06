package net.hongkuang.ditui.common.utils;


import java.util.UUID;

/**
 * 工具类-》基础工具类-》 随机字符串工具
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class RandomUtil {

    private RandomUtil() {
        throw new Error("工具类不能实例化！");
    }

    private static SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator.Factory().create(10, 10L);

    /**
     * 返回一个定长的随机纯数字字符串(只包含数字)
     *
     * @return 返回一个定长的随机纯数字字符串(只包含数字)
     */
    public static String genString() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 生成值的长度
     * @return 定长的纯0字符串
     */
    public static String generateZeroString(final int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0 超出将后面部分切掉
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 返回根据数字生成的随机字符串
     */
    public static String toFixdLengthString(final long num, final int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length())).append(strNum);
        } else {
            return String.valueOf(num).substring(0, fixdlenth);
        }
        return sb.toString();
    }


    private static String[] randomValues = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "u", "t", "s", "o", "x", "v",
            "p", "q", "r", "w", "y", "z"};

    private static String[] randomNums = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
    };

    public static String getStr(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (randomValues.length - 1);
            str.append(randomValues[number.intValue()]);
        }
        return str.toString();
    }

    public static String getNum(int lenght) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < lenght; i++) {
            Double number = Math.random() * (randomNums.length - 1);
            str.append(randomNums[number.intValue()]);
        }
        return str.toString();
    }

    public static String genOrderId() {
//		// 时间戳
//		String timestamp = DateUtils.dateTimeNow("yyyyMMddHHmmsss");
//		// 订单由 时间戳 + 4位随机
//		return timestamp + getNum(4);
        return snowFlakeGenerator.nextId() + "";
    }
}

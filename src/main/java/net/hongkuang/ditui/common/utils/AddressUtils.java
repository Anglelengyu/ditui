package net.hongkuang.ditui.common.utils;

import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.common.utils.http.HttpUtils;
import net.hongkuang.ditui.framework.config.RuoYiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";
    public static final String IP_URL_360 = "http://ip.360.cn/IPQuery/ipquery";

    public static String getRealAddressByIP(String ip) {
        String address = "";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (RuoYiConfig.isAddressEnabled()) {
//            String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
//            if (StringUtils.isEmpty(rspStr))
//            {
//                log.error("获取地理位置异常 {}", ip);
//                return address;
//            }
//            JSONObject obj = JSONObject.parseObject(rspStr);
//            JSONObject data = obj.getObject("data", JSONObject.class);
//            String region = data.getString("region");
//            String city = data.getString("city");
//            address = region + " " + city;

            String rspStr = HttpUtils.sendPost(IP_URL_360, "ip=" + ip);
            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String data = obj.getObject("data", String.class);
            address = data;
        }
        return address;
    }
}

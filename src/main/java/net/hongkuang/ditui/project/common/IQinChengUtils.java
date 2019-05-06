package net.hongkuang.ditui.project.common;


import com.alibaba.fastjson.JSONObject;
import net.hongkuang.ditui.common.constant.QinChengEnum;
import net.hongkuang.ditui.common.utils.RSAEncrypt;
import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.http.HttpUtil;
import net.hongkuang.ditui.project.api.dto.QingChengCheckOrder;
import net.hongkuang.ditui.project.busi.task.dto.CheckOrderResult;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 亲橙接口  根据订单号及卖家姓名获取订单信息
 */
@Component
public class IQinChengUtils {


    private static Logger log = LoggerFactory.getLogger(IQinChengUtils.class.getName());
//    private static String filepath = "G:\\sap\\hongkuang\\src\\main\\resources\\keys";

    private static String filepath;

    @Value("${hk.keyPath}")
    public void setFilepath(String keyPath) {
        this.filepath = keyPath;
    }

    private static String qinChengHost = "http://saas.iqincheng.com";

    /**
     * 根据订单标号及卖家姓名获取订单信息
     *
     * @param orderNo
     * @param sellerNick
     */
    public static String getOrder(String orderNo, String sellerNick) {
        try {
            log.info("获取订单信息");
            Base64 base64 = new Base64();
//            String param = "tid="+orderNo+"&seller_nick="+sellerNick;
            JSONObject param = new JSONObject();
            param.put("tid", orderNo);
            param.put("seller_nick", sellerNick);
            log.info("请求报文：{}", param.toString());
            // 请求数据加密
            byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),
                    param.toString().getBytes());
            String cipher = new String(base64.encode(cipherData));
            String result = HttpUtil.sendPost(qinChengHost + "/order", cipher);

            if (!StringUtils.isEmpty(result)) {
                byte[] cipherRs = RSAEncrypt.decryptByPublicKey(base64.decode(result), RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)));
                String data = new String(cipherRs);
                return data;
            }
            return "";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String result = getOrder("331510242241439689", "成都真妃木业有限公司");
        if (!StringUtils.isEmpty(result)) {
            JSONObject json = JSONObject.parseObject(result);
            List<CheckOrderResult> checkOrderResults = new ArrayList<>();
            System.out.println(json);
//            Integer resultCode = json.getInteger("code");
//                String result = IQinChengUtils.getOrder(orderCheckDto.getOrderNo(),shop.getWangwang());
//                JSONObject json = JSONObject.parseObject(result);
            Integer resultCode = json.getInteger("code");
            if (resultCode == QinChengEnum.FAILURE.getCode()) {
//                    results.add("订单"+orderCheckDto.getIndex()+"获取订单信息失败,请检查订单编号");
            } else if (resultCode == QinChengEnum.UNAUTHORIZED_USER.getCode()) {
                CheckOrderResult checkOrderResult = new CheckOrderResult();
                checkOrderResult.setStatus(2);
//                    checkOrderResult.setTid(orderCheckDto.get);
                checkOrderResult.setOrderId("");
                checkOrderResult.setMsg(QinChengEnum.UNAUTHORIZED_USER.getMsg());
                checkOrderResults.add(checkOrderResult);
            } else if (resultCode == QinChengEnum.SUCCESS.getCode()) {
                QingChengCheckOrder qingChengCheckOrder = json.getObject("data", QingChengCheckOrder.class);
                qingChengCheckOrder.setStatus("");
                qingChengCheckOrder.setMsg("实际支付金额与订单金额不符");
                System.out.println(qingChengCheckOrder.toString());
                System.out.println(qingChengCheckOrder.getPayment().multiply(new BigDecimal(100)).longValue());
            } else {
                System.out.println(json);
            }


        }
    }
}

package net.hongkuang.ditui.project.api.service.impl;

import net.hongkuang.ditui.common.utils.StringUtils;
import net.hongkuang.ditui.common.utils.file.FileUploadUtils;
import net.hongkuang.ditui.common.utils.http.HttpUtil;
import net.hongkuang.ditui.framework.config.RuoYiConfig;
import net.hongkuang.ditui.framework.web.domain.AjaxResult;
import net.hongkuang.ditui.project.api.config.ExternalApiConstants;
import net.hongkuang.ditui.project.api.service.IExternalApiService;
import net.hongkuang.ditui.project.busi.tbTransactionTemplate.domain.Goods;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zy
 * @date: 2019/4/9
 */
@Service
public class ExternalApiServiceImpl implements IExternalApiService {

    private Gson gson = new Gson();

    @Override
    public AjaxResult searchGoodsDetail(String goodsId) {
        Goods goods = new Goods();
        try {
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("apikey", ExternalApiConstants.API_KEY_99);
            sendMap.put("itemid", goodsId);
            String returnStr = HttpUtil.sendGet(ExternalApiConstants.TB_GOODS_DETAIL_URL + "?apikey=" + ExternalApiConstants.API_KEY_99 + "&itemid=" + goodsId);
            if (StringUtils.isEmpty(returnStr)) {
                return AjaxResult.error("商品ID有误");
            }
            Map returnMap = gson.fromJson(returnStr, Map.class);
            if (returnMap == null || returnMap.size() <= 0) {
                return AjaxResult.error("商品ID有误");
            }
            if (returnMap.get("retcode") == null || !returnMap.get("retcode").equals("0000")) {
                return AjaxResult.error("商品ID有误");
            }
            Map dataMap = (Map) returnMap.get("data");
            Map sellerMap = (Map) dataMap.get("seller");
            Map itemMap = (Map) dataMap.get("item");
            List images = (List) itemMap.get("images");

            goods.setGoodsId(goodsId);
            goods.setGoodsImg(String.valueOf(images.get(0)));
            goods.setShopId(String.valueOf(sellerMap.get("shopId")));
            goods.setShopName(String.valueOf(sellerMap.get("shopName")));
            goods.setGoodsName(String.valueOf(itemMap.get("title")));

            File file = new File(goods.getGoodsImg());

            String fileName = FileUploadUtils.encodingFilename(file.getName(), file.getName().substring(file.getName().lastIndexOf(".")));

            downloadImage(goods.getGoodsImg(), RuoYiConfig.getUploadFolderPath() + fileName);
            goods.setGoodsImg(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success().put("data", goods);
    }

    private void downloadImage(String url, String savePath) {
        try {
            HttpGet httpget = new HttpGet();
            httpget.setURI(URI.create("http:" + url));
            HttpResponse response = HttpUtil.createHttpClient().execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                byte[] byteArray = EntityUtils.toByteArray(entity);
                File file = new File(savePath);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    file.delete();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(byteArray);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {

        }
    }
}

package net.hongkuang.ditui.framework.web.service;

import net.hongkuang.ditui.common.utils.UnitUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * RuoYi首创 html调用 thymeleaf 实现上传文件服务获取管理
 *
 * @author ruoyi
 */
@Service("unit")
public class UnitAmountService {

    // DecimalFormat 非线程安全  使用ThreadLocal方式初始化
    private static ThreadLocal<DecimalFormat> YUAN_DECIMAL_FORMAT = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("#.00");
        }
    };

    public String fen2yuan(BigDecimal fen) {
        return YUAN_DECIMAL_FORMAT.get().format(UnitUtils.unitYuan(fen));
    }
}

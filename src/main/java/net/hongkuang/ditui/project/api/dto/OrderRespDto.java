package net.hongkuang.ditui.project.api.dto;

import net.hongkuang.ditui.project.busi.shop.domain.Shop;

public class OrderRespDto extends Shop {

    private Long unitPrice;

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }
}

package net.hongkuang.ditui.project.busi.orderTemplate.domain;

import net.hongkuang.ditui.framework.web.domain.BaseEntity;


/**
 * 订单模版关键词表 busi_order_template_keywords
 *
 * @author:zy
 * @date: 2019/2/26
 */
public class OrderTemplateKeyWords extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 订单数量
     */
    private Integer num;
    /**
     * 订单模版id
     */
    private Long orderTemplateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getOrderTemplateId() {
        return orderTemplateId;
    }

    public void setOrderTemplateId(Long orderTemplateId) {
        this.orderTemplateId = orderTemplateId;
    }

    @Override
    public String toString() {
        return "OrderTemplateKeyWords{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", orderTemplateId='" + orderTemplateId + '\'' +
                '}';
    }
}

package com.test.client.model;

public class Order {

    private Long id;

    private String goodsName;

    private Category category;

    public Order() {}

    public Order(Long id, String goodsName, Category category) {
        this.id = id;
        this.goodsName = goodsName;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

package com.yjl.vo;

import com.yjl.entity.Category;
import com.yjl.entity.Order;
import com.yjl.entity.Product;

public class OrderVo {
    private Order order;
    private String productName;
    private String category;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

package com.example.grocerymark.Model;

public class Order {
    public String OrderDate,OrderPrice,OrderName,OrderQuantity,OrderImage;

    public Order() {
    }

    public Order(String orderDate, String orderPrice, String orderName, String orderQuantity, String orderImage) {
        OrderDate = orderDate;
        OrderPrice = orderPrice;
        OrderName = orderName;
        OrderQuantity = orderQuantity;
        OrderImage = orderImage;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        OrderQuantity = orderQuantity;
    }

    public String getOrderImage() {
        return OrderImage;
    }

    public void setOrderImage(String orderImage) {
        OrderImage = orderImage;
    }
}

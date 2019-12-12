package com.example.grocerymark.Model;

public class Product {


    private String ProductName;
    private String ProductImage;
    private String Quantity;
    private String Price;
    private String Date;
    private String Time;

    public Product() {
    }

    public Product(String productName, String productImage, String quantity, String price, String date, String time) {

        ProductName = productName;
        ProductImage = productImage;
        Quantity = quantity;
        Price = price;
        Date = date;
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }



    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}

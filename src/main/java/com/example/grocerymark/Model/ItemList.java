package com.example.grocerymark.Model;

public class ItemList {
    private String Name;
    private String Image;
    private String Price;

    public ItemList() {
    }

    public ItemList(String name, String image, String price) {
        Name = name;
        Image = image;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}

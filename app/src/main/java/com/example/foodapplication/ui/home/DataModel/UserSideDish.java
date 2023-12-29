package com.example.foodapplication.ui.home.DataModel;

public class UserSideDish {
    private String name,about,imageUrl;
    private double price;

    public UserSideDish(){

    }
    public UserSideDish(String name, String about, String imageUrl, double price) {
        this.name = name;
        this.about = about;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

package com.example.foodapplication.RestaurantApp.DataModel;

public class Dish {
    private String dishId;
    private String name;
    private String about;
    private String speciality;
    private double price;
    private String imageUrl;

    public Dish() {
        // Default constructor required for calls to DataSnapshot.getValue(Dish.class)
    }

    public Dish(String dishId, String name, String about, String speciality, double price, String imageUrl) {
        this.dishId = dishId;
        this.name = name;
        this.about = about;
        this.speciality = speciality;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

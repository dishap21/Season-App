package com.dishap.seasonapp.Models;

import androidx.annotation.NonNull;

public class Recipe {
    private String id;
    private String Title;
    private String Image;
    private int amountOfDishes;
    private int readyInMins;

    public Recipe(String id, String title, String image, int amountOfDishes, int readyInMins) {
        this.id = id;
        Title = title;
        Image = image;
        this.amountOfDishes = amountOfDishes;
        this.readyInMins = readyInMins;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage(){
        return Image;
    }

    public int getAmountOfDishes() {
        return amountOfDishes;
    }

    public int getReadyInMins() {
        return readyInMins;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}

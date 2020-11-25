package com.example.automatedfoodorderingsystem.Model;

public class FoodDetails {
    String ChefId, Description, Dishes, ImageURL, Price, Quantity, RandomUID;

    public FoodDetails() {
    }

    public FoodDetails(String chefId, String description, String dishes, String imageURL, String price, String quantity, String randomUID) {
        ChefId = chefId;
        Description = description;
        Dishes = dishes;
        ImageURL = imageURL;
        Price = price;
        Quantity = quantity;
        RandomUID = randomUID;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDishes() {
        return Dishes;
    }

    public void setDishes(String dishes) {
        Dishes = dishes;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }
}

package com.example.automatedfoodorderingsystem.Model;

public class OrderDetails {
    String userName;
    String userId;
    String userPhone;
    String userImg;


    String dishName, dishPrice, dishQty;
    String restaurantId;
    int billAmount;


    public OrderDetails() {
    }

    ;

    public OrderDetails(String userName, String userId, String userPhone, String userImg, String dishName, String dishPrice, String dishQty, String restaurantId, int billAmount) {
        this.userName = userName;
        this.userId = userId;
        this.userPhone = userPhone;
        this.userImg = userImg;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishQty = dishQty;
        this.restaurantId = restaurantId;
        this.billAmount = billAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishQty() {
        return dishQty;
    }

    public void setDishQty(String dishQty) {
        this.dishQty = dishQty;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(int billAmount) {
        this.billAmount = billAmount;
    }
}

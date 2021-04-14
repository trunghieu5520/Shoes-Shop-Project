/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author coder
 */
public class Cart {

    private int id;
    private int userID;
    private int productID;
    private double price;
    private int amount;
    private double totalPrice;
    private String image;
    private String name;

    public Cart() {
    }

    public Cart(int userID, int productID, double price, int amount, double totalPrice, String image, String name) {
        this.userID = userID;
        this.productID = productID;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.image = image;
        this.name = name;
    }

    public Cart(int iD, int userID, int productID, double price, int amount, double totalPrice, String image, String name) {
        this.id = iD;
        this.userID = userID;
        this.productID = productID;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.image = image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int iD) {
        this.id = iD;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

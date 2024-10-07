package com.revshop.model;

import java.util.Base64;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String sellerEmail;
    private String category;
    private String subcategory;
    private byte[] imageUrl;  // Now storing image as byte array
    private int stock;

    // Default Constructor
    public Product() {}

    // Constructor without image
//    public Product(String name, String description, double price, String sellerEmail, String category, String subcategory, int stock) {
//        this.name = name;
//        this.description = description;
//        this.price = price;
//        this.sellerEmail = sellerEmail;
//        this.category = category;
//        this.subcategory = subcategory;
//        this.stock = stock;
//    }

    // Constructor with image
    public Product(String name, String description, double price, String sellerEmail, String category, String subcategory, byte[] imageUrl, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerEmail = sellerEmail;
        this.category = category;
        this.subcategory = subcategory;
        this.imageUrl = imageUrl;  // Accept image as byte array
        this.stock = stock;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getImageBase64() {
        return imageUrl != null ? Base64.getEncoder().encodeToString(imageUrl) : null;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

	
}

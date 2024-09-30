package com.example.rentomatic;
public class itemDetails {
    String location;
    String Coordinates;
    String name;
    String price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    String description;
    String amenities;
    String email;
    String phone;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAmenities() {
        return amenities;
    }
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    String imageURL;
    public itemDetails(){}
    public itemDetails(String location, String name, String price, String imageURL, String description, String amenities, String email, String phone) {
        this.location = location;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.description = description;
        this.amenities =amenities;
        this.email = email;
        this.phone = phone;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCoordinates(){return Coordinates;}
    public void setCoordinates(String Coordinates){this.Coordinates = Coordinates;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}
package com.example.review.model;

public class Map {
    private String key;
    private String keyShop;
    private String coordinateX;
    private String coordinateY;
    private String location;

    public Map() {
    }

    public Map(String key, String keyShop, String coordinateX, String coordinateY, String location) {
        this.key = key;
        this.keyShop = keyShop;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyShop() {
        return keyShop;
    }

    public void setKeyShop(String keyShop) {
        this.keyShop = keyShop;
    }

    public String getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(String coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(String coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

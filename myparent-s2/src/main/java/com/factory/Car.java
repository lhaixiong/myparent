package com.factory;

public class Car {
    private String brand;
    private String corp;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", corp='" + corp + '\'' +
                '}';
    }

    public Car(String brand, String corp) {
        this.brand = brand;
        this.corp = corp;
    }

    public Car() {
    }
}

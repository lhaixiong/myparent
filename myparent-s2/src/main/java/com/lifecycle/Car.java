package com.lifecycle;

public class Car {
    private String brand;
    private String corp;
    private float price;
    private int maxSpeed;
    private float perimeter;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(float perimeter) {
        this.perimeter = perimeter;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", corp='" + corp + '\'' +
                ", price=" + price +
                ", maxSpeed=" + maxSpeed +
                ", perimeter=" + perimeter +
                '}';
    }
    public void initCar(){
        System.out.println("3333initCar method is invoked....");
    }
    public Car(){
        System.out.println("1111car is constructed");
    }
    public void destroyCar(){
        System.out.println("55555destroyCar method is invoked...");
    }
}

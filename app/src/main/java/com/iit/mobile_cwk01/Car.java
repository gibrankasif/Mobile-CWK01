package com.iit.mobile_cwk01;

import java.io.Serializable;

public class Car implements Serializable {
    private int carImg;
    private String carMake;

    @Override
    public String toString() {
        return "Car{" +
                "carImg=" + carImg +
                ", carMake='" + carMake + '\'' +
                '}';
    }

    public Car (int carImg, String carMake) {
        this.carImg = carImg;
        this.carMake = carMake;
    }

    public int getCarImg() {
        return carImg;
    }

    public String getCarMake() {
        return carMake;
    }


}

package com.iit.mobile_cwk01;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
    private int carImg;
    private String carMake;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carImg == car.carImg &&
                carMake.equals(car.carMake);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(carImg, carMake);
    }

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

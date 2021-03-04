package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    String[] carMakes = {"Audi", "Mercedes", "BMW", "Toyota", "Tesla", "Honda", "Nissan"};
    Car[] cars = new Car[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < cars.length; i++){
            String carImgName = "car" + (i + 1);
            int carDrawable = getResources().getIdentifier(carImgName, "drawable", getPackageName());
            if(i <= 9) {
                cars[i] = new Car(carDrawable, carMakes[0]);
            }
            else if(i <= 19){
                cars[i] = new Car(carDrawable, carMakes[1]);
            }
            else{
                cars[i] = new Car(carDrawable, carMakes[2]);
            }
        }
    }
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_identify_make:
                Intent intent = new Intent(MainActivity.this, CarMakeActivity.class);
                intent.putExtra("carMakesArray", carMakes);
                intent.putExtra("carObjectArray", cars);
                startActivity(intent);
                break;
            case R.id.button_hints:
                Intent intent2 = new Intent(MainActivity.this, HintsActivity.class);
                intent2.putExtra("carMakesArray", carMakes);
                startActivity(intent2);
                break;
            case R.id.button_identify_image:
                Intent intent3 = new Intent(MainActivity.this, CarImageActivity.class);
                intent3.putExtra("carMakesArray", carMakes);
                intent3.putExtra("carObjectArray", cars);
                startActivity(intent3);
                break;
            default:
                break;
        }

    }

}
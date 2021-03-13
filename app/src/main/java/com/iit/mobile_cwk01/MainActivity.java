package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    String[] carMakes = {"Audi", "Mercedes", "BMW", "Toyota", "Tesla", "Honda", "Nissan"};
    Car[] cars = new Car[30];
    Switch switchCountTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.getSupportActionBar().hide();              // remove title bar of app
        } catch (NullPointerException e) {
        }

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

        switchCountTimer = findViewById(R.id.timer_switch);

    }
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_identify_make:
                Intent intent = new Intent(MainActivity.this, CarMakeActivity.class);
                intent.putExtra("carMakesArray", carMakes);
                intent.putExtra("carObjectArray", cars);
                intent.putExtra("timerActivated", switchCountTimer.isChecked());
                startActivity(intent);
                break;
            case R.id.button_hints:
                Intent intent2 = new Intent(MainActivity.this, HintsActivity.class);
                intent2.putExtra("carMakesArray", carMakes);
                intent2.putExtra("carObjectArray", cars);
                intent2.putExtra("timerActivated", switchCountTimer.isChecked());

                startActivity(intent2);
                break;
            case R.id.button_identify_image:
                Intent intent3 = new Intent(MainActivity.this, CarImageActivity.class);
                intent3.putExtra("carMakesArray", carMakes);
                intent3.putExtra("carObjectArray", cars);
                intent3.putExtra("timerActivated", switchCountTimer.isChecked());

                startActivity(intent3);
                break;
            case R.id.button_adv_level:
                Intent intent4 = new Intent(MainActivity.this, AdvancedLevel.class);
                intent4.putExtra("carMakesArray", carMakes);
                intent4.putExtra("carObjectArray", cars);
                intent4.putExtra("timerActivated", switchCountTimer.isChecked());

                startActivity(intent4);
                break;
            default:
                break;
        }

    }

}
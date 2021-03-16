package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    /*6 Car Brands are included into the game, each brand contains 6 images, giving a total of 36 images been
      shared in all 4 games
    **/
    String[] carMakes = {"Audi", "Mercedes", "BMW", "Toyota", "Ferrari", "Tesla"};

    //Custom Car object array.
    Car[] cars = new Car[36];
    Switch switchCountTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide title bar of app
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        //Following loop is used to add a car image to each car object in the array following its car make
        for(int i = 0; i < cars.length; i++){
            String carImgName = "car" + (i + 1);
            int carDrawable = getResources().getIdentifier(carImgName, "drawable", getPackageName());
            if(i <= 5) {
                cars[i] = new Car(carDrawable, carMakes[0]);
            }
            else if(i <= 11){
                cars[i] = new Car(carDrawable, carMakes[1]);
            }
            else if(i <= 17){
                cars[i] = new Car(carDrawable, carMakes[2]);
            }
            else if(i <= 23){
                cars[i] = new Car(carDrawable, carMakes[3]);
            }
            else if(i <= 29){
                cars[i] = new Car(carDrawable, carMakes[4]);
            }
            else{
                cars[i] = new Car(carDrawable, carMakes[5]);
            }
        }

        //Keeping track of timer switch
        switchCountTimer = findViewById(R.id.timer_switch);
    }

    //Following button method is used to direct the user to the relevant game based on the clicked button
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_identify_make:
                Intent game01 = new Intent(MainActivity.this, CarMakeActivity.class);
                game01.putExtra("carObjectArray", cars);
                game01.putExtra("timerActivated", switchCountTimer.isChecked());
                startActivity(game01);
                break;
            case R.id.button_hints:
                Intent game02 = new Intent(MainActivity.this, HintsActivity.class);
                game02.putExtra("carMakesArray", carMakes);
                game02.putExtra("carObjectArray", cars);
                game02.putExtra("timerActivated", switchCountTimer.isChecked());
                startActivity(game02);
                break;
            case R.id.button_identify_image:
                Intent game03 = new Intent(MainActivity.this, CarImageActivity.class);
                game03.putExtra("carObjectArray", cars);
                game03.putExtra("timerActivated", switchCountTimer.isChecked());
                startActivity(game03);
                break;
            case R.id.button_adv_level:
                Intent game04 = new Intent(MainActivity.this, AdvancedLevel.class);
                game04.putExtra("carMakesArray", carMakes);
                game04.putExtra("carObjectArray", cars);
                game04.putExtra("timerActivated", switchCountTimer.isChecked());
                startActivity(game04);
                break;
            default:
                break;
        }

    }

}
//References

//          https://stackoverflow.com/questions/1397361/how-to-restart-activity-in-android
//
//        https://stackoverflow.com/questions/22655599/alertdialog-builder-with-custom-layout-and-edittext-cannot-access-view
//
//        https://stackoverflow.com/questions/46664186/pause-and-continue-countdowntimer-in-android
//
//        https://www.youtube.com/watch?v=HR-3fm1Pcxg&list=PLfrnkW5d2l6UkJS3uuP9WnRsl1w9rzrJX&index=3

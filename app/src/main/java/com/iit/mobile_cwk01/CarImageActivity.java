package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarImageActivity extends AppCompatActivity {
    private  static  final long START_TIME_IN_MILLIS = 20000;
    private boolean timeOption = false;
    private long timeRemainingInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer gameTimer;
    private TextView timerDisplay;

    private ImageButton carButton1;
    private ImageButton carButton2;
    private ImageButton carButton3;
    private TextView carImgMake;
    private Button imageSubmitButton;
    private String selectedCarMake = "";
    int randomIndexA = 0;
    int randomIndexB = 0;
    int randomIndexC = 0;
    boolean roundFinish = true;
    List<Integer> remainingCarList = new ArrayList<>();

    private Car[] cars;
    private String[] displayedCarMakes = {"Audi", "Mercedes", "BMW"};
    String correctMake;
    Random random = new Random();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);



        carButton1 = findViewById(R.id.carimageButton1);
        carButton2 = findViewById(R.id.carimageButton2);
        carButton3 = findViewById(R.id.carimageButton3);
        carImgMake = findViewById(R.id.carMake_textview);
        imageSubmitButton = findViewById(R.id.carimage_button);

        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        timeOption = getIntent().getBooleanExtra("timerActivated", false);


        showRandomCarImages();
        if (timeOption){
            startTimer();
        }

        carImgMake.setText(displayedCarMakes[random.nextInt(displayedCarMakes.length)]);
        correctMake = carImgMake.getText().toString();
       // carMakes = getIntent().getStringArrayExtra("carMakesArray");
    }
    @Override
    protected void onDestroy() {                // when going back to the main menu
        super.onDestroy();

        if (timeOption) {         // only if the countdown toggle had been turned on
            if (gameTimer != null) {
                gameTimer.cancel();           // stopping the countdown running in the background
            }
        }
    }
    public void submitCorrectCarImage(View view) {
        if (roundFinish) {
            startCarImageGame();
        }
        else{
            carButton1.setBackgroundResource(0);
            carButton2.setBackgroundResource(0);
            carButton3.setBackgroundResource(0);
            carImgMake.setText(displayedCarMakes[random.nextInt(displayedCarMakes.length)]);
            correctMake = carImgMake.getText().toString();
            selectedCarMake = "";
            showRandomCarImages();

            imageSubmitButton.setText("Submit");
            roundFinish = true;

            if (timeOption){
                startTimer();
            }
        }
    }

    public void startCarImageGame(){
        if (selectedCarResult(randomIndexA)) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_correct);
            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            if(!(selectedCarMake.equals(correctMake))){
                carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_answer);
                displayToast("WRONG!");

            }else{
                displayToast("CORRECT!");

            }
        }  else if (selectedCarResult(randomIndexB)) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_correct);
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            if(!(selectedCarMake.equals(correctMake))){
                carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_answer);
                displayToast("WRONG!");

            }else{
                displayToast("CORRECT!");

            }
        }  else if (selectedCarResult(randomIndexC)) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_correct);
            if(!(selectedCarMake.equals(correctMake))){
                carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_answer);
                displayToast("WRONG!");

            }else{
                displayToast("CORRECT!");

            }
        }
        roundFinish = false;
        imageSubmitButton.setText("Next");
        if(timeOption){
            gameTimer.cancel();
        }
    }

    public void showRandomCarImages() {
        Random rand0 = new Random();
        Random rand1 = new Random();
        Random rand2 = new Random();


        while (true) {

            if (remainingCarList.size() == cars.length) {
                displayToast("That's all " + count);
                break;
            }

            randomIndexA = rand0.nextInt(cars.length);
            randomIndexB = rand1.nextInt(cars.length);
            randomIndexC = rand2.nextInt(cars.length);

            Car carA = cars[randomIndexA];
            Car carB = cars[randomIndexB];
            Car carC = cars[randomIndexC];

            if (!(remainingCarList.contains(randomIndexA) || remainingCarList.contains(randomIndexB) || remainingCarList.contains(randomIndexC))) {
                if (!((randomIndexA == randomIndexB) || (randomIndexA == randomIndexC) || (randomIndexC == randomIndexB))) {
                    String carMakeA = carA.getCarMake();
                    String carMakeB = carB.getCarMake();
                    String carMakeC = carC.getCarMake();

                    if (!((carMakeA.equals(carMakeB)) || carMakeA.equals(carMakeC) || carMakeB.equals(carMakeC))) {
                        carButton1.setImageResource(carA.getCarImg());
                        carButton2.setImageResource(carB.getCarImg());
                        carButton3.setImageResource(carC.getCarImg());

                        displayedCarMakes[0] = carA.getCarMake();
                        displayedCarMakes[1] = carB.getCarMake();
                        displayedCarMakes[2] = carC.getCarMake();

                        remainingCarList.add(randomIndexA);
                        remainingCarList.add(randomIndexB);
                        remainingCarList.add(randomIndexC);

                        count++;
                        break;
                    }
                }
            }
        }
    }
    public void selectCarImage(View view) {
        carButton1.setBackgroundResource(0);
        carButton2.setBackgroundResource(0);
        carButton3.setBackgroundResource(0);
        if (view.getId() == R.id.carimageButton1){
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_selected);
            selectedCarMake = cars[randomIndexA].getCarMake();
        }
         else if (view.getId() == R.id.carimageButton2){

            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_selected);
            selectedCarMake = cars[randomIndexB].getCarMake();

        }
         else if (view.getId() == R.id.carimageButton3){
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_selected);
            selectedCarMake = cars[randomIndexC].getCarMake();

        }
    }
    public boolean selectedCarResult(int randomIndex){
        return cars[randomIndex].getCarMake().equals(correctMake);
    }


    public void displayToast(String message) {
        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(this).inflate(R.layout.toast_layout, null);
        TextView toastTextView = view.findViewById(R.id.textViewToast);

        toastTextView.setText(message);
        if (message.equals("CORRECT!")) {
            toastTextView.setBackgroundResource(R.drawable.toast_correct);
        }else if(message.equals("WRONG!")){
            toastTextView.setBackgroundResource(R.drawable.toast_wrong);
        }else{
            toastTextView.setBackgroundResource(R.drawable.toast_answer);
        }
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);

        toast.show();

    }
    private void startTimer() {
        timerDisplay = findViewById(R.id.timeView_3);
        timeRemainingInMillis = START_TIME_IN_MILLIS;


        gameTimer = new CountDownTimer(timeRemainingInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining =  (int) ((timeRemainingInMillis / 1000));
                timerDisplay.setVisibility(View.VISIBLE);
                timerDisplay.setText(Integer.toString(secondsRemaining));
                timeRemainingInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timerDisplay.setText(Integer.toString(0));
                startCarImageGame();

            }
        }.start();
    }

//    public void selectCarImage(View view) {
//        if (view.getId() == R.id.carimageButton1){
//            if (cars[randomIndexA].getCarMake().equals(correctMake)){
//                displayToast("CORRECT!");
//            }else{
//                displayToast("WRONG!");
//            }
//        }
//        else if (view.getId() == R.id.carimageButton2){
//            if (cars[randomIndexB].getCarMake().equals(correctMake)){
//                displayToast("CORRECT!");
//            }else{
//                displayToast("WRONG!");
//            }
//        }
//
//        else if (view.getId() == R.id.carimageButton3){
//            if (cars[randomIndexC].getCarMake().equals(correctMake)){
//                displayToast("CORRECT!");
//            }else{
//                displayToast("WRONG!");
//            }
//        }
//        carImgMake.setText(displayedCarMakes[random.nextInt(displayedCarMakes.length)]);
//        correctMake = carImgMake.getText().toString();
//    }

    /*
            if(!(previousCarMakeList.contains(randomIndexA) || previousCarMakeList.contains(randomIndexB) || previousCarMakeList.contains(randomIndexC)) ){
                if(!((randomIndexA == randomIndexB) || (randomIndexA == randomIndexC) || (randomIndexC == randomIndexB))) {
                    if(!(threeCarMakes[randomIndexA].equals(threeCarMakes[randomIndexB]) || threeCarMakes[randomIndexA].equals(threeCarMakes[randomIndexC]) || threeCarMakes[randomIndexC].equals(threeCarMakes[randomIndexB]))){
                        int carintA = cars[randomIndexA].getCarImg();
                        int carintB = cars[randomIndexB].getCarImg();
                        int carintC = cars[randomIndexC].getCarImg();

                        previousCarMakeList.add(randomIndexA);
                        previousCarMakeList.add(randomIndexB);
                        previousCarMakeList.add(randomIndexC);

                        threeCarMakes[0] = cars[randomIndexA].getCarMake();
                        threeCarMakes[1] = cars[randomIndexB].getCarMake();
                        threeCarMakes[2] = cars[randomIndexC].getCarMake();


                        carButton1.setImageResource(carintA);
                        carButton2.setImageResource(carintB);
                        carButton3.setImageResource(carintC);
                        count++;
                        break;
                    }
                }
            } */

    //    public boolean isConditionSatisfied(String a, String b, String c) {
//        return Objects.equals(a,b) && Objects.equals(b,c);
//    }




}
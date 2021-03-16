package com.iit.mobile_cwk01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarImageActivity extends AppCompatActivity {
    /**
     * Following first five variables are only made use of, if the timer option was clicked on back in
     * the homescreen
     */
    private static final long START_TIME_IN_MILLIS = 20000;
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

    int randomIndexA = 0; //Random index representing the first car image
    int randomIndexB = 0;//Random index representing the second car image
    int randomIndexC = 0;//Random index representing the third car image
    boolean roundFinish = true;
    List<Integer> placedRandomIndexesList = new ArrayList<>();

    private Car[] cars;
    private String[] displayedCarMakes = new String[3]; // Array representing the current vehicle makes been shown during the round.
    private String correctMake; // The presented car make name, which the user has to know before selecting any image.
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_image);
        this.setTitle("PICK THE CORRECT CAR");
        carButton1 = findViewById(R.id.carimageButton1);
        carButton2 = findViewById(R.id.carimageButton2);
        carButton3 = findViewById(R.id.carimageButton3);
        carImgMake = findViewById(R.id.carMake_textview);
        imageSubmitButton = findViewById(R.id.carimage_button);

        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        timeOption = getIntent().getBooleanExtra("timerActivated", false);

//        if (savedInstanceState != null) {
//// Read the state of item position
////            displayedCarMakes = savedInstanceState.getStringArray("displayedCarsArray");
////            timeOption = savedInstanceState.getBoolean("timerStatus");
////            correctMake = savedInstanceState.getString("expectedMake");
////            selectedCarMake = savedInstanceState.getString("selectedImage");
////            remainingCarList = savedInstanceState.getIntegerArrayList("displayedCarsList");
////            roundFinish = savedInstanceState.getBoolean("currentRoundProgress");
////            remainingCarList = savedInstanceState.getIntegerArrayList("displayedCarsList");
////            timeRemainingInMillis = savedInstanceState.getLong("remainingTime");
////            randomIndexA = savedInstanceState.getInt("randomCarIndexA");
////            randomIndexC = savedInstanceState.getInt("randomCarIndexB");
////            randomIndexC = savedInstanceState.getInt("randomCarIndexC");
////
////
////
////            carButton1.setImageResource(cars[randomIndexA].getCarImg());
////            carButton2.setImageResource(cars[randomIndexB].getCarImg());
////            carButton3.setImageResource(cars[randomIndexC].getCarImg());
////            carImgMake.setText(correctMake);
//
//        }       // if screen was rotated and activity was restarted
//        else


        if (timeOption) {
            startTimer();
        }
        showRandomCarImages();


        carButton1.setBackgroundResource(0);
        carButton2.setBackgroundResource(0);
        carButton3.setBackgroundResource(0);

        correctMake = displayedCarMakes[random.nextInt(displayedCarMakes.length)];
        carImgMake.setText(correctMake);

    }

    /**
     * When returning back to the home menu, it is ensure that the activated timer,
     * does not run in the background.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeOption) {
            if (gameTimer != null) {
                gameTimer.cancel();
            }
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putStringArray("displayedCarsArray", displayedCarMakes);
//        outState.putBoolean("timerStatus", timeOption);
//        outState.putString("expectedMake", correctMake);
//        outState.putString("selectedImage", selectedCarMake);
//        outState.putIntegerArrayList("displayedCarsList", (ArrayList<Integer>) placedRandomIndexesList);
//        outState.putBoolean("currentRoundProgress", roundFinish);
//        outState.putLong("remainingTime", timeRemainingInMillis);
//        outState.putInt("randomCarIndexA", randomIndexA);
//        outState.putInt("randomCarIndexB", randomIndexB);
//        outState.putInt("randomCarIndexC", randomIndexC);
//
//    }

    //The method would be used to present the images, along with moving the move user to the next round, based on the user's button click.
    public void submitCorrectCarImage(View view) {
        if (roundFinish) {
            startCarImageGame();
        } else {
            carButton1.setEnabled(true);
            carButton2.setEnabled(true);
            carButton3.setEnabled(true);
            carButton1.setBackgroundResource(0);
            carButton2.setBackgroundResource(0);
            carButton3.setBackgroundResource(0);
            selectedCarMake = "";
            showRandomCarImages();
            correctMake = displayedCarMakes[random.nextInt(displayedCarMakes.length)];
            carImgMake.setText(correctMake);

            roundFinish = true;

            if (timeOption) {
                startTimer();
            }
        }
    }

    /**
     * The following method is an additional functionality which is used to visually show the user, the selected button,
     * and the final outcomes will also be represented based on the uniform colors.
     */
    public void startCarImageGame() {
        if (selectedCarResult(randomIndexA)) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_correct);
            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            if (!(selectedCarMake.equals(correctMake))) {
                carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_answer);
                displayMessage("WRONG!");

            } else {
                displayMessage("CORRECT!");

            }
        } else if (selectedCarResult(randomIndexB)) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_correct);
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            if (!(selectedCarMake.equals(correctMake))) {
                carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_answer);
                displayMessage("WRONG!");

            } else {
                displayMessage("CORRECT!");

            }
        } else if (selectedCarResult(randomIndexC)) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_wrong);
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_correct);
            if (!(selectedCarMake.equals(correctMake))) {
                carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_answer);
                displayMessage("WRONG!");

            } else {
                displayMessage("CORRECT!");

            }
        }
        carButton1.setEnabled(false);
        carButton2.setEnabled(false);
        carButton3.setEnabled(false);

        roundFinish = false;
        if (timeOption) {
            gameTimer.cancel();
        }
    }

    public void showRandomCarImages() {
        Random rand = new Random();

        while (true) {
            //Following statement is active only at the end of the game. Where the user cannot play any further
            if (placedRandomIndexesList.size() == cars.length) {
                endMenu();
                break;
            }
            //Collecting three random indexes inorder to the extract random car images
            randomIndexA = rand.nextInt(cars.length);
            randomIndexB = rand.nextInt(cars.length);
            randomIndexC = rand.nextInt(cars.length);

            Car carA = cars[randomIndexA];
            Car carB = cars[randomIndexB];
            Car carC = cars[randomIndexC];

            //Logic responsible to prevent duplicate images from appearing and any duplicates of the same index
            if (!(placedRandomIndexesList.contains(randomIndexA) || placedRandomIndexesList.contains(randomIndexB) || placedRandomIndexesList.contains(randomIndexC))) {
                if (!((randomIndexA == randomIndexB) || (randomIndexA == randomIndexC) || (randomIndexC == randomIndexB))) {
                    String carMakeA = carA.getCarMake();
                    String carMakeB = carB.getCarMake();
                    String carMakeC = carC.getCarMake();
                    //Logic established to ensure each make is a unique brand.
                    if (!((carMakeA.equals(carMakeB)) || carMakeA.equals(carMakeC) || carMakeB.equals(carMakeC))) {
                        carButton1.setImageResource(carA.getCarImg());
                        carButton2.setImageResource(carB.getCarImg());
                        carButton3.setImageResource(carC.getCarImg());
                        //Current car makes been displayed.
                        displayedCarMakes[0] = carA.getCarMake();
                        displayedCarMakes[1] = carB.getCarMake();
                        displayedCarMakes[2] = carC.getCarMake();

                        placedRandomIndexesList.add(randomIndexA);
                        placedRandomIndexesList.add(randomIndexB);
                        placedRandomIndexesList.add(randomIndexC);
                        break;
                    }
                }
            }
        }
    }

    //Method used to extract the car make associated to the image based on the user selection.
    public void selectCarImage(View view) {
        carButton1.setBackgroundResource(0);
        carButton2.setBackgroundResource(0);
        carButton3.setBackgroundResource(0);
        if (view.getId() == R.id.carimageButton1) {
            carButton1.setBackgroundResource(R.drawable.transparent_bg_bordered_button_selected);
            selectedCarMake = cars[randomIndexA].getCarMake();
        } else if (view.getId() == R.id.carimageButton2) {

            carButton2.setBackgroundResource(R.drawable.transparent_bg_bordered_button_selected);
            selectedCarMake = cars[randomIndexB].getCarMake();

        } else if (view.getId() == R.id.carimageButton3) {
            carButton3.setBackgroundResource(R.drawable.transparent_bg_bordered_button_selected);
            selectedCarMake = cars[randomIndexC].getCarMake();

        }
    }

    //returns a boolean to identify if the selected result is the same as the randomly chosen one.
    public boolean selectedCarResult(int randomIndex) {
        return cars[randomIndex].getCarMake().equals(correctMake);
    }

    /**
     * startTimer is used to automatically simulate a user submission towards submit, once
     * the allocated time of 20secs has been reached*
     */
    private void startTimer() {
        timerDisplay = findViewById(R.id.timeView_3);
        timeRemainingInMillis = START_TIME_IN_MILLIS;


        gameTimer = new CountDownTimer(timeRemainingInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) ((timeRemainingInMillis / 1000));
                timerDisplay.setVisibility(View.VISIBLE);
                timerDisplay.setText(Integer.toString(secondsRemaining)+"sec");
                if(secondsRemaining <= 9) {
                    timerDisplay.setText("0"+Integer.toString(secondsRemaining)+"sec");
                }
                timeRemainingInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timerDisplay.setText("0"+Integer.toString(0)+"sec");
                startCarImageGame();

            }
        }.start();
    }

    /**
     * The following method is the main View used to displayed the result
     * out come after each input from the user. Which is an alert-box, based on
     * it's own custom layout. For this activity it is only designed to show
     * if the answer is right or wrong. And displays the correct make if incorrect
     **/
    public void displayMessage(String message) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // get inflater and inflate layer for dialogue
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_box_layout, null);
        dialogView.setBackgroundResource(R.drawable.dialog_background);

        // now set layout to dialog
        dialogBuilder.setView(dialogView);

        // create instance like this OR directly mentioned in layout
        TextView outcome = (TextView) dialogView.findViewById(R.id.message_text);
        TextView rightAnswer = (TextView) dialogView.findViewById(R.id.message_correct);

        ImageView image = (ImageView) dialogView.findViewById(R.id.message_result);
        outcome.setText(message);

        if (message.equals("CORRECT!")) {
            outcome.setTextColor(Color.GREEN);
            image.setImageResource(R.drawable.open);

        } else if (message.equals("WRONG!")) {
            outcome.setTextColor(Color.RED);
            image.setImageResource(R.drawable.close);

            rightAnswer.setText(correctMake);
            rightAnswer.setTextColor(Color.YELLOW);
        }


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    /**
     * The endMenu is initiated only at the end of the game rounds
     * it provides the user two options to either restart playing the
     * game or head back to the main menu.*/
    public void endMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to play again?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
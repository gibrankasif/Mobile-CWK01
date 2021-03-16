package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
/**Following first five variables are only made use of, if the timer option was clicked on back in
 * the homescreen
* */
    private static final long START_TIME_MILLIS = 20000;
    private boolean timeOption = false;
    private long timeRemainingInMillis = START_TIME_MILLIS;
    private CountDownTimer gameTimer;
    private TextView timerDisplay;

    private Spinner spinner;

    private ImageView carMakeImageView;
    private Button identifyButton;
    private String selectedCarMake; //Refers to the selected random answer
    private int gameProgress = 1; //Current game round, the user will be in.
    boolean roundFinish = true;


    private Car[] cars;
    private final List<Integer> previousCarList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);

        this.setTitle("GUESS THE CAR MAKE");
        //Borrowing the array of car objects which was initialized in the main activity and is sent through the intent itself.
        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        //Another var, borrowed from the main activity which is used to determine if the timer should be activated or not.
        timeOption = getIntent().getBooleanExtra("timerActivated", false);

        carMakeImageView = findViewById(R.id.imageView_car);

        spinner = findViewById(R.id.carMake_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        //Assigned a custom layout to single list item layout of the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.carMake_array, R.layout.spinner_single_list);
        adapter.setDropDownViewResource(R.layout.spinner_style_whole);
        spinner.setAdapter(adapter);

        //Showing the number of images the user has completed out of the total
        TextView textView = (TextView) findViewById(R.id.progressView);
        textView.setText("PROGRESS: " + gameProgress + "/36");

        identifyButton = findViewById(R.id.identify_button);
        //Starts of with the first game simulation which begins as soon as the activity was created.
        showRandomCarImage();
        //The timer will only be initiated during the time when only the timer option has been selected.
        if (timeOption){
            startTimer();
        }

        //The button event which would then continue each game round based on the users input towards the submit button
        identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**The following condition will be satisfied right after the user submits their
                 * input which would then later move on towards the next condition, which would
                 * commence the next round.
                */
                if (roundFinish) {
                   submitResponse();
                }else{
                    showRandomCarImage();
                    gameProgress++;
                    textView.setText("PROGRESS: " + gameProgress + "/36");
                    identifyButton.setText("Identify");
                    roundFinish = true;
                    if (timeOption){
                        startTimer();
                    }
                }
            }
        });
    }
/**When returning back to the home menu, it is ensure that the activated timer,
does not run in the background.*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeOption) {
            if (gameTimer != null) {
                gameTimer.cancel();
            }
        }
    }
/*Method used to collect user's selection and validate if its the correct answer not, displayed in
    the form of an alert*/
    public void submitResponse(){
        if (spinner.getSelectedItem().toString().equals(selectedCarMake)) {
            displayMessage("CORRECT!");
        } else {
            displayMessage("WRONG!");
        }
        identifyButton.setText("Next");
        roundFinish=false;
        if(timeOption){
            gameTimer.cancel();
        }
    }

    /*The method involved in randomizing car images*/
    public void showRandomCarImage() {
        Random rand = new Random();
        Integer randomIndex = 0;
        while (true) {
            //A random value is picked out to represent the car image been selected
            randomIndex = rand.nextInt(cars.length);
            //Following statement is active only at the end of the game. Where the user cannot play any further
            if(previousCarList.size() == cars.length){
                carMakeImageView.setVisibility(View.INVISIBLE);
                identifyButton.setEnabled(false);
                selectedCarMake = "empty";
                endMenu();
                break;
            }
            /*Main logic to display the selected random image, this prevents previously selected
            **images from being repeated during an entire game play*/
            if (!previousCarList.contains(randomIndex)) {
                previousCarList.add(randomIndex);
                carMakeImageView.setImageResource(cars[randomIndex].getCarImg());
                selectedCarMake = cars[randomIndex].getCarMake();
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**The following method is the main View used to displayed the result
     * out come after each input from the user. Which is an alert-box, based on
     * it's own custom layout. For this activity it is only designed to show
     * if the answer is right or wrong. And displays the correct make if incorrect**/
    public void displayMessage(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        // get inflater and inflate layour for dialogue
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

        }else if(message.equals("WRONG!")){
            outcome.setTextColor(Color.RED);
            image.setImageResource(R.drawable.close);

            rightAnswer.setText(selectedCarMake);
            rightAnswer.setTextColor(Color.YELLOW);
        }


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    /** startTimer is used to automatically simulate a user submission towards submit, once
     * the allocated time of 20secs has been reached* */
    private void startTimer() {
        timerDisplay = findViewById(R.id.timeView_01);
        timeRemainingInMillis = START_TIME_MILLIS;

        gameTimer = new CountDownTimer(timeRemainingInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Converting remaining time from milliseconds to seconds
                int secondsRemaining =  (int) ((timeRemainingInMillis / 1000));
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
                submitResponse();
            }
        }.start();
    }
    /**The endMenu is initiated only at the end of the game rounds
     * it provides the user two options to either restart playing the
     * game or head back to the main menu.
     * */
    public void endMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to play again?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ends the activity and returns back to the primary activity
                        finish();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Ends the activity but restarts the same activity from the start.
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}

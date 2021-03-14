package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintsActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 20000;
    private boolean timeOption = false;
    private long timeRemainingInMillis = START_TIME_IN_MILLIS;
    private  CountDownTimer gameTimer;
    private TextView timerDisplay;

    private TextView displayHintsResult;
    private EditText letterInputField;
    private Button submitGuess;
    private TextView lifeCount;
    private ImageView hintCarView;
    private String[] carMakes;
    private Car[] cars;

    private int count = 0;
    private String word;
    private String underscore;
    private String letterGuess;

    private final List<Integer> previousCarMakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        lifeCount = findViewById(R.id.score_life_pts);
        hintCarView = findViewById(R.id.imageView_hints);
        displayHintsResult = findViewById(R.id.hints_textview);
        letterInputField = findViewById(R.id.hints_textfield);
        submitGuess = findViewById(R.id.submit_button);

        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        carMakes = getIntent().getStringArrayExtra("carMakesArray");
        timeOption = getIntent().getBooleanExtra("timerActivated", false);

        for(int i = 0; i < carMakes.length; i++) {
            carMakes[i] = carMakes[i].toUpperCase();
        }

        randomMake();
        if (timeOption){
            startTimer();
        }

        submitGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitInputResponse();
            }
        });
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

    private void submitInputResponse() {

        if(letterInputField.getText().length() == 0) {
            displayToast("Guess a letter!");
        } else if(letterInputField.getText().length() > 1) {
            displayToast("Enter a single character!");
        } else if(count < 3 && underscore.contains("_")) {
            letterGuess = letterInputField.getText().toString().toUpperCase();
            randomHintGenerator(letterGuess);
            if (count <= 3){
                if(timeOption){
                gameTimer.cancel();
                startTimer();
            }
            }
            if (count == 3 || underscore.equals(word)){
                submitGuess.setText("Next");
                if(timeOption){
                    gameTimer.cancel();
                }
            }
            lifeCount.setText(stringMultiplier(" X ", count));
            displayHintsResult.setText(underscore);
            if (count == 3 && !(underscore.equals(word))){
                displayToast("WRONG!");
                displayHintsResult.setTextColor(getResources().getColor(R.color.Yellow));
                displayHintsResult.setText(word);
            }
        } else{
            letterInputField.setVisibility(View.VISIBLE);
            randomMake();
            count = 0;
            lifeCount.setText("");
            submitGuess.setText("Submit");
            letterInputField.setText(null);
            displayHintsResult.setTextColor(getResources().getColor(R.color.black));

            if (timeOption){
                startTimer();
            }
        }
    }

    private void startTimer() {
        timerDisplay = findViewById(R.id.timeView_2);
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
//                letterInputField.setVisibility(View.INVISIBLE);
                if (letterInputField.getText().toString().equals(null) || letterInputField.getText().toString().equals("")){
                letterInputField.setText("X");}else{
                    letterInputField.setText(letterInputField.getText().toString());
                }

//                count = 2;

                submitInputResponse();
            }
        }.start();
    }

    public void randomMake(){
        Random rand = new Random();
        int randomIndex = 0;
        while (true) {
            randomIndex = rand.nextInt(cars.length);
            if(previousCarMakeList.size() == cars.length){
                displayHintsResult.setText("GAME OVER");
                submitGuess.setEnabled(false);
                break;
            }

            if (!previousCarMakeList.contains(randomIndex)) {
                hintCarView.setImageResource(cars[randomIndex].getCarImg());
                previousCarMakeList.add(randomIndex);
                word = cars[randomIndex].getCarMake().toUpperCase();
                underscore = stringMultiplier("_", word.length());
                displayHintsResult.setText(underscore);
                break;
            }
        }
    }

    public void randomHintGenerator(String guess) {
        String newUnderscore = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newUnderscore += guess.charAt(0);
            } else if (underscore.charAt(i) != '_') {
                newUnderscore += word.charAt(i);
            } else {
                newUnderscore += "_";
            }
        }if (underscore.equals(newUnderscore)) {
            count++;
        } else {
            underscore = newUnderscore;
        }
        if (underscore.equals(word)) {
            displayToast("CORRECT!");
        }

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
    private static String stringMultiplier(String str, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}


//        while (true)
//        {
//            randomIndex = rand.nextInt(carMakes.length);
//            if(previousCarMakeList.size() == carMakes.length)
//            {
//                displayToast("Finished");
//                break;
//            }
//            if (!previousCarMakeList.contains(randomIndex))
//            {
//                previousCarMakeList.add(randomIndex);
//                String randomMake = carMakes[randomIndex];
//                displayHintsResult.setText(multiply(" _ ", randomMake.length()));
//                break;
//            }
//        }
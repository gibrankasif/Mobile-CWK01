package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintsActivity extends AppCompatActivity {
    /**Following first five variables are only made use of, if the timer option was clicked on back in
     * the homescreen
     * */
    private static final long START_TIME_MILLIS = 20000;
    private boolean timeOption = false;
    private long timeRemainingInMillis = START_TIME_MILLIS;
    private CountDownTimer gameTimer;
    private TextView timerDisplay;

    private TextView displayHintsResult;
    private EditText letterInputField;
    private Button submitGuess;
    private TextView lifeCount;
    private ImageView hintCarView;
    private String[] carMakes;
    private Car[] cars;

    private int countRound = 0; //Value used to track the number of rounds, each input has been sent, max only 3.
    private String word; // The selected random car make name which would be presented as the hint for the user to guess
    private String underscore; // The hint word which is based on the random provided word
    private String letterGuess;//Each letter guess inputted from the user

    private final List<Integer> previousCarMakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);
        this.setTitle("GUESS THE HINT");


        lifeCount = findViewById(R.id.score_life_pts);
        hintCarView = findViewById(R.id.imageView_hints);
        displayHintsResult = findViewById(R.id.hints_textview);
        letterInputField = findViewById(R.id.hints_textfield);
        submitGuess = findViewById(R.id.submit_button);

        //Borrowing the array of car objects which was initialized in the main activity and is sent through the intent itself.
        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        //Array of car makes from the main activity used to present all hinted questions
        carMakes = getIntent().getStringArrayExtra("carMakesArray");
        //Another var, borrowed from the main activity which is used to determine if the timer should be activated or not.
        timeOption = getIntent().getBooleanExtra("timerActivated", false);

        //Converting all car make contents to uppercase to validate against the user's input which is also in uppercase format
        for(int i = 0; i < carMakes.length; i++) {
            carMakes[i] = carMakes[i].toUpperCase();
        }

        //Starts of with the first game simulation which begins as soon as the activity was created.
        randomMake();
        //The timer will only be initiated during the time when only the timer option has been selected.
        if (timeOption){
            startTimer();
        }

        submitGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The entire method is used to process the user's input and compare against the hinted word characters.
                submitInputResponse();
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

    /**
     * The following method is used to validate each character guess provided by the user,
     * additional validation is provided to ensure that the user must only enter at
     * least a single char character. However during the case where the timer is activer
     * a wrong answer would be provided if no input was ever entered.
     */
    private void submitInputResponse() {
        if(letterInputField.getText().length() == 0) {
            displayMessage("Guess a letter!");
        } else if(letterInputField.getText().length() > 1) {
            displayMessage("Enter a single character!");
        } else if(countRound < 3 && underscore.contains("_")) {
            letterGuess = letterInputField.getText().toString().toUpperCase();
            randomHintGenerator(letterGuess);
            if (countRound <= 3){
                if(timeOption){
                gameTimer.cancel();
                startTimer();
            }
            }
            if (countRound == 3 || underscore.equals(word)){
                submitGuess.setText("Next");
                if(timeOption){
                    gameTimer.cancel();
                }
            }
            lifeCount.setText(stringMultiplier(" X ", countRound));
            displayHintsResult.setText(underscore);
            if (countRound == 3 && !(underscore.equals(word))){
                displayMessage("WRONG!");
                displayHintsResult.setTextColor(getResources().getColor(R.color.Yellow));
                displayHintsResult.setText(word);
            }
        } else{
            letterInputField.setVisibility(View.VISIBLE);
            randomMake();
            countRound = 0;
            lifeCount.setText("");
            submitGuess.setText("Submit");
            letterInputField.setText(null);
            displayHintsResult.setTextColor(getResources().getColor(R.color.black));

            if (timeOption){
                startTimer();
            }
        }
    }

    /**
     * The logic behind randomizing the car makes presented in the hinted game
     */
    public void randomMake(){
        Random rand = new Random();
        int randomIndex = 0;
        while (true) {
            //The following would only occur at the end of the run.
            //The game will only run based on the available number of car makes, which would go on to 6 rounds.
            randomIndex = rand.nextInt(cars.length);
            if(previousCarMakeList.size() == cars.length){
                displayHintsResult.setText("GAME OVER");
                submitGuess.setEnabled(false);
                endMenu();
                break;
            }
            //Ensures a previously presented word will not appear in future rounds, until the end of the entire game.
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
        //The necessary logic behind identify if the input character is represented into any of the letters of the whole hint.
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newUnderscore += guess.charAt(0);
            } else if (underscore.charAt(i) != '_') {
                newUnderscore += word.charAt(i);
            } else {
                newUnderscore += "_";
            }
            //If the word does not match, it would deduct the user's chance.
        }if (underscore.equals(newUnderscore)) {
            countRound++;
        } else {
            //Else it would update the hinted word with addition to the correctly guessed character. No chances would be deducted.
            underscore = newUnderscore;
        }
        if (underscore.equals(word)) {
            displayMessage("CORRECT!");
        }
    }
    //Used to multiply a specific character value in this case, it is the life scores been charged. Reducing the user's chances to keep playing.
    private static String stringMultiplier(String str, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
    /** startTimer is used to automatically simulate a user submission towards submit, once
     * the allocated time of 20secs has been reached* */
    private void startTimer() {
        timerDisplay = findViewById(R.id.timeView_2);
        timeRemainingInMillis = START_TIME_MILLIS;


        gameTimer = new CountDownTimer(timeRemainingInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
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
//                letterInputField.setVisibility(View.INVISIBLE);
                if (letterInputField.getText().toString().equals(null) || letterInputField.getText().toString().equals("")){
                letterInputField.setText("X");}else{
                    letterInputField.setText(letterInputField.getText().toString());
                }
                submitInputResponse();
            }
        }.start();
    }
    /**The following method is the main View used to displayed the result
     * out come after each input from the user. Which is an alert-box, based on
     * it's own custom layout. For this activity it is only designed to show
     * if the answer is right or wrong. And displays the correct make if incorrect**/
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

        }else if(message.equals("WRONG!")){
            outcome.setTextColor(Color.RED);
            image.setImageResource(R.drawable.close);
        }
        else{
            outcome.setText("");
            rightAnswer.setText(message);
            rightAnswer.setTextColor(Color.YELLOW);
        }


        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    /**The endMenu is initiated only at the end of the game rounds
     * it provides the user two options to either restart playing the
     * game or head back to the main menu.
     * */
    public void endMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to play again?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
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


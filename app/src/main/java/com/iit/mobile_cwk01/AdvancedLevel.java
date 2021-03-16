package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class AdvancedLevel extends AppCompatActivity {
    /**Following first five variables are only made use of, if the timer option was clicked on back in
     * the homescreen
     * */
    private static final long START_TIME_IN_MILLIS = 20000;
    private boolean timeOption = false;
    private long timeRemainingInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer gameTimer;
    private TextView timerDisplay;
    private TextView gameChances;

    private Car[] cars;
    private ImageView carImage01;
    private ImageView carImage02;
    private ImageView carImage03;

    private EditText carField01;
    private EditText carField02;
    private EditText carField03;

    private TextView correctAnswer01;
    private TextView correctAnswer02;
    private TextView correctAnswer03;
    private TextView currentScore;

    private String[] displayedCarMakes = new String[3];
    List<Integer> remainingCarList = new ArrayList<>();
    int count = 0;
    int anotherCount = 0;
    int totalAdvancedLevelScore = 0;
    int currentCompletion = 0;
    ArrayList<String> correctInputsList = new ArrayList<>(3);

    int randomIndexA = 0; //Random index representing the first car image
    int randomIndexB = 0;//Random index representing the second car image
    int randomIndexC = 0;//Random index representing the third car image

    //used to update the total score each submitted round.
    boolean scoreRound1 = false;
    boolean scoreRound2 = false;
    boolean scoreRound3 = false;

    boolean roundFinish = true;
    Button advancedSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);
        this.setTitle("ADVANCED CAR MATCH");
        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        timeOption = getIntent().getBooleanExtra("timerActivated", false);

        carImage01 = findViewById(R.id.advancedImgView_car1);
        carImage02 = findViewById(R.id.advancedImgView_car2);
        carImage03 = findViewById(R.id.advancedImgView_car3);

        carField01 = findViewById(R.id.advancedLevelTextField1);
        carField02 = findViewById(R.id.advancedLevelTextField2);
        carField03 = findViewById(R.id.advancedLevelTextField3);

        correctAnswer01 = findViewById(R.id.textView_rightAnswer1);
        correctAnswer02 = findViewById(R.id.textView_rightAnswer2);
        correctAnswer03 = findViewById(R.id.textView_rightAnswer3);

        gameChances = findViewById(R.id.advanced_life_pts);
        currentScore = findViewById(R.id.advanced_life_score_points);

        advancedSubmitButton = findViewById(R.id.advanced_button);
        //The timer will only be initiated during the time when only the timer option has been selected.

        if (timeOption) {
            startTimer();
        }

        showRandomCarImages();

        advancedSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (roundFinish) {
                        submitFieldAnswers();

                    } else {
                        anotherCount = 0;
                        currentCompletion = 0;
                        gameChances.setText("");

                        correctAnswer01.setText(null);
                        correctAnswer02.setText(null);
                        correctAnswer03.setText(null);

                        scoreRound1 = false;
                        scoreRound2 = false;
                        scoreRound3 = false;


                        correctInputsList.clear();
                        enableAllFields(true);

                        carField01.setText(null);
                        carField02.setText(null);
                        carField03.setText(null);

                        carField01.setTextColor(ContextCompat.getColor(AdvancedLevel.this, R.color.black));
                        carField02.setTextColor(ContextCompat.getColor(AdvancedLevel.this, R.color.black));
                        carField03.setTextColor(ContextCompat.getColor(AdvancedLevel.this, R.color.black));

                        showRandomCarImages();
                        advancedSubmitButton.setText("Submit");

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


    /**
     * Major logic responsible to validate each input filed answer
     * along with the score update.
     */

    public void submitFieldAnswers(){
        correctInputsList.clear();

        answerCompare(randomIndexA, carField01);
        answerCompare(randomIndexB, carField02);
        answerCompare(randomIndexC, carField03);
        addCurrentScore();


        currentScore.setText("0"+Integer.toString(totalAdvancedLevelScore));
        anotherCount++;

        if (correctInputsList.size() == 3 || anotherCount == 3) {
            if(timeOption){
                gameTimer.cancel();
            }
            enableAllFields(false);
            advancedSubmitButton.setText("Next");
            roundFinish = false;


            if (correctInputsList.size() == 3) {
                displayMessage("CORRECT!");
            } else {
                gameChances.setText(stringMultiplier(" X ", anotherCount));
                displayMessage("WRONG!");
//                displayToast(totalAdvancedLevelScore + " \n  " + currentCompletion +"\n");
                presentCorrectAnswers();
            }
    }else{
            gameChances.setText(stringMultiplier(" X ", anotherCount));
            if(timeOption){
                gameTimer.cancel();
                startTimer();
            }
        }
}

    public void showRandomCarImages() {
        Random rand = new Random();

        while (true) {

            if (remainingCarList.size() == cars.length) {
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
            if (!(remainingCarList.contains(randomIndexA) || remainingCarList.contains(randomIndexB) || remainingCarList.contains(randomIndexC))) {
                if (!((randomIndexA == randomIndexB) || (randomIndexA == randomIndexC) || (randomIndexC == randomIndexB))) {
                    String carMakeA = carA.getCarMake();
                    String carMakeB = carB.getCarMake();
                    String carMakeC = carC.getCarMake();
                    //Logic established to ensure each make is a unique brand.

                    if (!((carMakeA.equals(carMakeB)) || carMakeA.equals(carMakeC) || carMakeB.equals(carMakeC))) {
                        carImage01.setImageResource(carA.getCarImg());
                        carImage02.setImageResource(carB.getCarImg());
                        carImage03.setImageResource(carC.getCarImg());
                        //Current car makes been displayed.

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

    //Activate/Deactivate EditTexts when necessary
    public void enableAllFields(boolean enable) {
        carField01.setEnabled(enable);
        carField02.setEnabled(enable);
        carField03.setEnabled(enable);
    }
    //return the comparison between input-field text and car make string
    public boolean compareInputAnswer(int index, EditText field) {
        return cars[index].getCarMake().toUpperCase().equals(field.getText().toString().toUpperCase());
    }
    //Presents the answers of which the user had got wrong.
    public void presentCorrectAnswers() {
        if (!(compareInputAnswer(randomIndexA, carField01))) {
            correctAnswer01.setText(cars[randomIndexA].getCarMake());
        }
        if (!(compareInputAnswer(randomIndexB, carField02))) {
            correctAnswer02.setText(cars[randomIndexB].getCarMake());
        }
        if (!(compareInputAnswer(randomIndexC, carField03))) {
            correctAnswer03.setText(cars[randomIndexC].getCarMake());
        }
    }
    //Method used to update total score, through each submitted round
    public void addCurrentScore() {
        if (!scoreRound1) {
            if ((compareInputAnswer(randomIndexA, carField01))) {
                totalAdvancedLevelScore++;
                currentCompletion++;
                scoreRound1 = true;
            }
        }
        if (!scoreRound2){
            if ((compareInputAnswer(randomIndexB, carField02))) {
                totalAdvancedLevelScore++;
                currentCompletion++;
                scoreRound2 = true;

            }
        }
        if (!scoreRound3) {
            if ((compareInputAnswer(randomIndexC, carField03))) {
                totalAdvancedLevelScore++;
                currentCompletion++;
                scoreRound3 = true;

            }
        }
    }
    //Sets the text field font color based on the provided answer.
    public void answerCompare(int index, EditText field) {
        if (!compareInputAnswer(index, field)) {
            field.setTextColor(ContextCompat.getColor(this, R.color.Red));
        } else {
            field.setTextColor(ContextCompat.getColor(this, R.color.Lime));
            correctInputsList.add(field.getText().toString());
            field.setEnabled(false);
        }
    }
    /**
     * startTimer is used to automatically simulate a user submission towards submit, once
     * the allocated time of 20secs has been reached*
     */
    private void startTimer() {
        timerDisplay = findViewById(R.id.timeView_4);
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
                submitFieldAnswers();
            }
        }.start();
    }

    //Used to multiply a specific character value in this case, it is the life scores been charged. Reducing the user's chances to keep playing.
    private static String stringMultiplier(String str, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
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

        }else if(message.equals("WRONG!")){
            outcome.setTextColor(Color.RED);
            image.setImageResource(R.drawable.close);
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
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        recreate();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}


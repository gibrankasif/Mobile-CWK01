package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintsActivity extends AppCompatActivity {
    private TextView displayHintsResult;
    private EditText letterInputField;
    private Button submitGuess;
    private TextView lifeCount;
    private String[] carMakes;

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
        displayHintsResult = findViewById(R.id.hints_textview);
        letterInputField = findViewById(R.id.hints_textfield);
        submitGuess = findViewById(R.id.submit_button);

        carMakes = getIntent().getStringArrayExtra("carMakesArray");

        for(int i = 0; i < carMakes.length; i++) {
            carMakes[i] = carMakes[i].toUpperCase();
        }

        randomMake();

        submitGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayToast(word);
                if(letterInputField.getText().length() == 0) {
                    displayToast("Guess a letter!");
                }
                else if(letterInputField.getText().length() > 1) {
                    displayToast("Enter a single character!");
                }
                else if(count < 3 && underscore.contains("_")) {
                    letterGuess = letterInputField.getText().toString().toUpperCase();
                    randomHintGenerator(letterGuess);
                    if (count == 3 || underscore.equals(word)){
                        submitGuess.setText("Next");
                    }
                    if (count == 3 && !(underscore.equals(word))){
                        displayToast("Wrong");
                    }
                    lifeCount.setText(stringMultiplier(" X ", count));
                    displayHintsResult.setText(underscore);
                }
                else{
                    randomMake();
                    count = 0;
                    lifeCount.setText("");
                    submitGuess.setText("Submit");
                }
            }
        });
    }

    public void randomMake(){
        Random rand = new Random();
        int randomIndex = 0;
        while (true) {
            randomIndex = rand.nextInt(carMakes.length);
            if(previousCarMakeList.size() == carMakes.length){
                displayHintsResult.setText("GAME OVER");
                submitGuess.setEnabled(false);
                break;
            }

            if (!previousCarMakeList.contains(randomIndex)) {
                previousCarMakeList.add(randomIndex);
                word = carMakes[randomIndex];
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
            displayToast("Correct!");
        }

    }

    public void displayToast(String message) {
        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(this).inflate(R.layout.toast_layout, null);
        TextView toastTextView = view.findViewById(R.id.textViewToast);

        toastTextView.setText(message);
        if (message.equals("Correct")) {
            toastTextView.setBackgroundResource(R.drawable.toast_correct);
        }else if(message.equals("Wrong")){
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
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
    private String guessLetter;
    private String[] carMakes;

    private final List<Integer> previousCarMakeList = new ArrayList<>();


    private int count;
    private String word;
    private String asterisk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

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
                }else if(letterInputField.getText().length() > 1) {
                    displayToast("Enter a single character!");
                }else if(count < 7 && asterisk.contains("_")) {
                        String guess = letterInputField.getText().toString().toUpperCase();
                        randomHintGenerator(guess);
                        displayHintsResult.setText(asterisk);

                    }
                }

        });
    }
    public void randomMake(){
        Random rand = new Random();
        int randomIndex = 0;
        randomIndex = rand.nextInt(carMakes.length);
        word = carMakes[(int) (Math.random() * carMakes.length)];
        asterisk = multiply("_",word.length());
        displayHintsResult.setText(asterisk);
    }

    public void randomHintGenerator(String guess) {
        String newasterisk = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                newasterisk += guess.charAt(0);
            } else if (asterisk.charAt(i) != '_') {
                newasterisk += word.charAt(i);
            } else {
                newasterisk += "_";
            }
        }
        if (asterisk.equals(newasterisk)) {
            count++;
        } else {
            asterisk = newasterisk;
        }
        if (asterisk.equals(word)) {
            displayToast("Correct! You win! The word was " + word);
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
    private static String multiply(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
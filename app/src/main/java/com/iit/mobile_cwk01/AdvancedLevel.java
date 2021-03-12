package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
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

public class AdvancedLevel extends AppCompatActivity {
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

    private String[] displayedCarMakes = {"Audi", "Mercedes", "BMW"};
    List<Integer> remainingCarList = new ArrayList<>();
    int count = 0;
    int anotherCount = 0;
    ArrayList<String> correctInputsList = new ArrayList<>(3);

    int randomIndexA = 0;
    int randomIndexB = 0;
    int randomIndexC = 0;

    boolean roundFinish = true;
    Button advancedSubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);
        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");

        carImage01 = findViewById(R.id.advancedImgView_car1);
        carImage02 = findViewById(R.id.advancedImgView_car2);
        carImage03 = findViewById(R.id.advancedImgView_car3);

        carField01 = findViewById(R.id.advancedLevelTextField1);
        carField02 = findViewById(R.id.advancedLevelTextField2);
        carField03 = findViewById(R.id.advancedLevelTextField3);

        correctAnswer01 = findViewById(R.id.textView_rightAnswer1);
        correctAnswer02 = findViewById(R.id.textView_rightAnswer2);
        correctAnswer03 = findViewById(R.id.textView_rightAnswer3);

         advancedSubmitButton = findViewById(R.id.advanced_button);

        showRandomCarImages();
    }

    public void compareThreeCarAnswers(View view) {

        if (view.getId() == R.id.advanced_button) {
            if (roundFinish) {
                correctInputsList.clear();

                answerCompare(randomIndexA, carField01, 0);
                answerCompare(randomIndexB, carField02, 1);
                answerCompare(randomIndexC, carField03, 2);

                anotherCount++;
                if(correctInputsList.size() == 3 || anotherCount == 3){
                    enableAllFields(false);
                    advancedSubmitButton.setText("Next");
                    roundFinish = false;
                    if (correctInputsList.size() == 3){
                        displayToast("CORRECT!");
                    } else{
                        displayToast("WRONG!");
                        presentCorrectAnswers();
                    }
                }

            }else {
                anotherCount = 0;
                correctAnswer01.setText(null);
                correctAnswer02.setText(null);
                correctAnswer03.setText(null);


                correctInputsList.clear();
                enableAllFields(true);

                carField01.setText(null);
                carField02.setText(null);
                carField03.setText(null);

                carField01.setTextColor(ContextCompat.getColor(this, R.color.black));
                carField02.setTextColor(ContextCompat.getColor(this, R.color.black));
                carField03.setTextColor(ContextCompat.getColor(this, R.color.black));

                showRandomCarImages();
                advancedSubmitButton.setText("Submit");

                roundFinish = true;
            }
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

            Car A = cars[randomIndexA];
            Car B = cars[randomIndexB];
            Car C = cars[randomIndexC];

            if (!(remainingCarList.contains(randomIndexA) || remainingCarList.contains(randomIndexB) || remainingCarList.contains(randomIndexC))) {
                if (!((randomIndexA == randomIndexB) || (randomIndexA == randomIndexC) || (randomIndexC == randomIndexB))) {
                    String a = A.getCarMake();
                    String b = B.getCarMake();
                    String c = C.getCarMake();

                    if (!((a.equals(b)) || a.equals(c) || b.equals(c))) {
                        carImage01.setImageResource(A.getCarImg());
                        carImage02.setImageResource(B.getCarImg());
                        carImage03.setImageResource(C.getCarImg());

                        displayedCarMakes[0] = A.getCarMake();
                        displayedCarMakes[1] = B.getCarMake();
                        displayedCarMakes[2] = C.getCarMake();

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

    public void enableAllFields(boolean enable){
        carField01.setEnabled(enable);
        carField02.setEnabled(enable);
        carField03.setEnabled(enable);
    }

    public boolean compareInputAnswer(int index, EditText field){
        return cars[index].getCarMake().toUpperCase().equals(field.getText().toString().toUpperCase());
    }

    public void presentCorrectAnswers(){
        if (!(compareInputAnswer(randomIndexA, carField01))){
            correctAnswer01.setText(cars[randomIndexA].getCarMake());
        }
        if (!(compareInputAnswer(randomIndexB, carField02))){
            correctAnswer02.setText(cars[randomIndexB].getCarMake());
        }
        if (!(compareInputAnswer(randomIndexC, carField03))){
            correctAnswer03.setText(cars[randomIndexC].getCarMake());
        }
    }

    public void answerCompare(int index, EditText field, int slot){
        if (!compareInputAnswer(index, field)) {
            field.setTextColor(ContextCompat.getColor(this, R.color.Red));
        } else {
            field.setTextColor(ContextCompat.getColor(this, R.color.Lime));
            correctInputsList.add(field.getText().toString());
            field.setEnabled(false);
        }
    }
}
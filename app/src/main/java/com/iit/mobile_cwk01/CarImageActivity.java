package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarImageActivity extends AppCompatActivity {
    private ImageButton carButton1;
    private ImageButton carButton2;
    private ImageButton carButton3;
    private TextView carImgMake;
    int randomIndexA = 0;
    int randomIndexB = 0;
    int randomIndexC = 0;

    List<Integer> remainingCarList = new ArrayList<>();

    private Car[] cars;
    private String[] displayedCarMakes = {"Audi", "Mercedes", "BMW"};
    String selectedCarMake;
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

        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");


        showRandomCarImages();

        carImgMake.setText(displayedCarMakes[random.nextInt(displayedCarMakes.length)]);
        correctMake = carImgMake.getText().toString();
       // carMakes = getIntent().getStringArrayExtra("carMakesArray");
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
                        carButton1.setImageResource(A.getCarImg());
                        carButton2.setImageResource(B.getCarImg());
                        carButton3.setImageResource(C.getCarImg());

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
    public void selectCarImage(View view) {
        if (view.getId() == R.id.carimageButton1){
            if (cars[randomIndexA].getCarMake().equals(correctMake)){
                displayToast("Correct");
            }else{
                displayToast("Wrong");
            }showRandomCarImages();
        }
        else if (view.getId() == R.id.carimageButton2){
            if (cars[randomIndexB].getCarMake().equals(correctMake)){
                displayToast("Correct");
            }else{
                displayToast("Wrong");
            }showRandomCarImages();
        }

        else if (view.getId() == R.id.carimageButton3){
            if (cars[randomIndexC].getCarMake().equals(correctMake)){
                displayToast("Correct");
            }else{
                displayToast("Wrong");
            }showRandomCarImages();
        }
        carImgMake.setText(displayedCarMakes[random.nextInt(displayedCarMakes.length)]);
        correctMake = carImgMake.getText().toString();

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
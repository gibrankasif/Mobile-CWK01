package com.iit.mobile_cwk01;

import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class CarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private ImageView imageView;
    private Button identifyButton;
    private String selectedCarImage;

    Car car1 = new Car(R.drawable.audi_1, "Audi");
    Car car2 = new Car(R.drawable.audi_2, "Audi");
    Car car3 = new Car(R.drawable.audi_3, "Audi");
    Car car4 = new Car(R.drawable.audi_4, "Audi");
    Car car5 = new Car(R.drawable.audi_5, "Audi");
    Car car6 = new Car(R.drawable.audi_6, "Audi");
    Car car7 = new Car(R.drawable.audi_7, "Audi");
    Car car8 = new Car(R.drawable.audi_8, "Audi");
    Car car9 = new Car(R.drawable.audi_9, "Audi");
    Car car10 = new Car(R.drawable.audi_10, "Audi");
    Car[] cars = new Car[]{car1, car2, car3, car4, car5, car6, car7, car8, car9, car10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);
        this.setTitle("Guess the Car Make");
        imageView = findViewById(R.id.imageView_car);

        Spinner spinner = (Spinner) findViewById(R.id.carMake_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.carMake_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView textView = (TextView) findViewById(R.id.ddd);

        identifyButton = findViewById(R.id.identify_button);
        showRandomCarImage();
        identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner.getSelectedItem().toString().equals(selectedCarImage)) {
                    displayToast("Correct");
                    showRandomCarImage();

                } else {
                    displayToast("Wrong");
                    showRandomCarImage();

                }
            }
        });
    }

    public void showRandomCarImage() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(cars.length);
        imageView.setImageResource(cars[randomIndex].getCarImg());
        selectedCarImage = cars[randomIndex].getCarMake();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String spinnerLabel = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void displayToast(String message) {

        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(this).inflate(R.layout.toast_layout, null);
        TextView toastTextView = view.findViewById(R.id.textViewToast);
        toastTextView.setText(message);
        if (message.equals("Correct")) {
            toastTextView.setBackgroundResource(R.drawable.toast_correct);
        }else{
            toastTextView.setBackgroundResource(R.drawable.toast_wrong);
        }
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);

        toast.show();

    }

}


//    ImageView imageView;
//    Button identifyButton;
//    Random randomImg;
//
//    Integer[] carImgs = {
//            R.drawable.audi_1,
//            R.drawable.audi_2,
//            R.drawable.audi_3,
//            R.drawable.audi_4,
//            R.drawable.audi_5,
//            R.drawable.audi_6,
//            R.drawable.audi_7,
//            R.drawable.audi_8,
//            R.drawable.audi_9,
//            R.drawable.audi_10};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_car_make);
//        this.setTitle("Guess the Car Make");
//
//        imageView = findViewById(R.id.imageView_car);
//        identifyButton = findViewById(R.id.submit_button);
//        randomImg = new Random();
//
//        imageView.setImageResource(carImgs[randomImg.nextInt(carImgs.length)]);
//
//
//        identifyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageView.setImageResource(carImgs[randomImg.nextInt(carImgs.length)]);
//            }
//        });
//
//
//    }
//
//}


//
//
//    private static final String audiBrand = "Audi";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ImageView carImgDisplay = (ImageView) findViewById(R.id.imageView_car);
//        Button nextButton = (Button) findViewById(R.id.submit_button);
//        //showRandomImage();
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                carImgDisplay.setImageResource(cars[0].getCarImg());
//            }
//        });
//
//    }
//
//    Car car1 = new Car(R.drawable.audi_1,audiBrand);
//    Car car2 = new Car(R.drawable.audi_2, audiBrand);
//    Car car3 = new Car(R.drawable.audi_3, audiBrand);
//    Car car4 = new Car(R.drawable.audi_4, audiBrand);
//    Car car5 = new Car(R.drawable.audi_5, audiBrand);
//
//    Car[] cars = new Car[]{car1,car2,car3,car4,car5};
//
//    public void showRandomImage() {
//        Random num = new Random();
//        ImageView carImgDisplay = (ImageView) findViewById(R.id.imageView_car);
//
//        //carImgDisplay.setImageResource(cars[0].getCarImg());
//        carImgDisplay.setImageResource(cars[num.nextInt(cars.length)].getCarImg());
//
//    }
//
//    public void shuffleImages() {
//        Collections.shuffle(Arrays.asList(cars));
//    }
//
//
//}

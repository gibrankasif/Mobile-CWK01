package com.iit.mobile_cwk01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView imageView;
    private Button identifyButton;
    private String selectedCarMake;
    private int gameProgress = 1;
    boolean roundFinish = true;

    private Car[] cars;
    private final List<Integer> previousCarList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_make);
        this.setTitle("Guess the Car Make");

        cars = (Car[]) getIntent().getSerializableExtra("carObjectArray");
        imageView = findViewById(R.id.imageView_car);

        Spinner spinner = (Spinner) findViewById(R.id.carMake_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.carMake_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView textView = (TextView) findViewById(R.id.prgressView);
        textView.setText("PROGRESS: " + gameProgress + "/30");

        identifyButton = findViewById(R.id.identify_button);
        showRandomCarImage();
        identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roundFinish) {
                    if (spinner.getSelectedItem().toString().equals(selectedCarMake)) {
                        displayToast("Correct");
                    } else {
                        displayToast("Wrong");
                        displayToast(selectedCarMake);
                    }
                    identifyButton.setText("Next");
                    roundFinish=false;
                }else{
                    showRandomCarImage();
                    gameProgress++;
                    textView.setText("PROGRESS: " + gameProgress + "/30");
                    identifyButton.setText("Identify");
                    roundFinish = true;
                }
            }
        });
    }

    public void showRandomCarImage() {
        Random rand = new Random();
        Integer randomIndex = 0;
        while (true) {
            randomIndex = rand.nextInt(cars.length);
            if(previousCarList.size() == cars.length){
                imageView.setImageResource(R.drawable.ic_launcher_background);
                selectedCarMake = "Over";
                displayToast("Finished");
                break;
            }

            if (!previousCarList.contains(randomIndex)) {
                previousCarList.add(randomIndex);
                imageView.setImageResource(cars[randomIndex].getCarImg());
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
    public void onNothingSelected(AdapterView<?> parent) {

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

}
 /* Random rand = new Random();
        int randomIndex = rand.nextInt(cars.length);
        imageView.setImageResource(cars[randomIndex].getCarImg());
        selectedCarImage = cars[randomIndex].getCarMake();*/
        /*do{
            nextImg = rand.nextInt(cars.length);}
        while (nextImg == previousImg);
                previousImg = nextImg;
                imageView.setImageResource(cars[nextImg].getCarImg());
                selectedCarImage = cars[nextImg].getCarMake();*/

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

//    Car car1 = new Car(R.drawable.audi_1, carBrand[0]);
//    Car car2 = new Car(R.drawable.audi_2, carBrand[0]);
//    Car car3 = new Car(R.drawable.audi_3, carBrand[0]);
//    Car car4 = new Car(R.drawable.audi_4, carBrand[0]);
//    Car car5 = new Car(R.drawable.audi_5, carBrand[0]);
//    Car car6 = new Car(R.drawable.audi_6, carBrand[0]);
//    Car car7 = new Car(R.drawable.audi_7, carBrand[0]);
//    Car car8 = new Car(R.drawable.audi_8, carBrand[0]);
//    Car car9 = new Car(R.drawable.audi_9, carBrand[0]);
//    Car car10 = new Car(R.drawable.audi_10, carBrand[0]);
//    Car car11 = new Car(R.drawable.mercedes_1, carBrand[1]);
//    Car car12 = new Car(R.drawable.mercedes_2, carBrand[1]);
//    Car car13 = new Car(R.drawable.mercedes_3, carBrand[1]);
//    Car car14 = new Car(R.drawable.mercedes_4, carBrand[1]);
//    Car car15 = new Car(R.drawable.mercedes_5, carBrand[1]);
//    Car car16 = new Car(R.drawable.mercedes_6, carBrand[1]);
//    Car car17 = new Car(R.drawable.mercedes_7, carBrand[1]);
//    Car car18 = new Car(R.drawable.mercedes_8, carBrand[1]);
//    Car car19 = new Car(R.drawable.mercedes_9, carBrand[1]);
//    Car car20 = new Car(R.drawable.mercedes_10, carBrand[1]);
// public Car[] cars = new Car[]{car1, car2, car3, car4, car5, car6, car7, car8, car9, car10, car11,car12,car13,car14,car15,car16,car17,car18,car19,car20};
package com.moutamid.carrentalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookCarActivity extends AppCompatActivity {
    private static final String TAG = "BookCarActivity";

    private ImageView carImage;
    private TextView name, transmission, model, seats, status,
            engine, price, description, perMileageRate, totalCostTv, currentMileageTv;
    private LinearLayout ac, gps;

    private ImageView plusBtn, minusBtn;

    private CarModel carModel;

    private int currentMileageInt = 5;
    private int totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_car);

        carImage = findViewById(R.id.car_image_view_layout_activity_bok_car);
        name = findViewById(R.id.name_layout_car_home);
        transmission = findViewById(R.id.transmission_layout_car_activity_bok_car);
        model = findViewById(R.id.model_layout_car_activity_bok_car);
        seats = findViewById(R.id.seats_layout_car_activity_bok_car);
        status = findViewById(R.id.status_layout_car_activity_bok_car);
        engine = findViewById(R.id.engine_layout_car_activity_bok_car);
        price = findViewById(R.id.price_layout_car_activity_bok_car);
        ac = findViewById(R.id.ac_layout_car_activity_bok_car);
        gps = findViewById(R.id.gps_layout_car_activity_bok_car);
        description = findViewById(R.id.car_description_activity_bok_car);
        perMileageRate = findViewById(R.id.per_mileage_rate_activity_book_car);
        totalCostTv = findViewById(R.id.total_cost_activity_bok_car);
        plusBtn = findViewById(R.id.plus_btn_activity_bok_car);
        minusBtn = findViewById(R.id.minus_btn_activity_bok_car);
        currentMileageTv = findViewById(R.id.current_mileages_activity_bok_car);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String key = getIntent().getStringExtra("key");

        databaseReference.child("cars").child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            return;
                        }

                        carModel = snapshot.getValue(CarModel.class);

                        setDetailsOnCarModel();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: " + error.getMessage());
                    }
                }
        );

    }

    private void setDetailsOnCarModel() {

        if (carModel.isGPS())
            gps.setVisibility(View.VISIBLE);

        if (carModel.isAC())
            ac.setVisibility(View.VISIBLE);

        price.setText("$" + carModel.getPerDayRate() + " /Mileage");

        engine.setText(carModel.getEngine());

        status.setText(carModel.getStatus());

        seats.setText(carModel.getSeats() + " Seats");

        model.setText(carModel.getModelYear());

        transmission.setText(carModel.getTransmission());

        name.setText(carModel.getName());

        Glide.with(BookCarActivity.this)
                .load(carModel.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.color.grey)
                        .error(R.color.grey)
                )
                .into(carImage);

        description.setText(carModel.getDescription());

        perMileageRate.setText("~ $" + carModel.getPerDayRate());

        totalCost = Integer.parseInt(carModel.getPerDayRate()) * currentMileageInt;

        totalCostTv.setText("$" + totalCost);

        plusBtn.setOnClickListener(plusBtnClickListener());
        minusBtn.setOnClickListener(minusBtnClickListener());
    }

    private View.OnClickListener minusBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentMileageInt == 5) {
                    Toast.makeText(BookCarActivity.this, "You can't book for lower than 5 mileages!", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentMileageInt = currentMileageInt - 5;

                totalCost = Integer.parseInt(carModel.getPerDayRate()) * currentMileageInt;

                currentMileageTv.setText(currentMileageInt + " mil");

                totalCostTv.setText("$" + totalCost);
            }
        };
    }

    private View.OnClickListener plusBtnClickListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentMileageInt = currentMileageInt + 5;

                totalCost = Integer.parseInt(carModel.getPerDayRate()) * currentMileageInt;

                currentMileageTv.setText(currentMileageInt + " mil");

                totalCostTv.setText("$" + totalCost);
            }
        };
    }

    private static class CarModel {

        private String name, modelYear, seats, transmission,
                status, perDayRate, engine, description, imageUrl, carKey;
        private boolean AC, GPS;

        public CarModel(String name, String modelYear, String seats, String transmission, String status, String perDayRate, String engine, String description, String imageUrl, String carKey, boolean AC, boolean GPS) {
            this.name = name;
            this.modelYear = modelYear;
            this.seats = seats;
            this.transmission = transmission;
            this.status = status;
            this.perDayRate = perDayRate;
            this.engine = engine;
            this.description = description;
            this.imageUrl = imageUrl;
            this.carKey = carKey;
            this.AC = AC;
            this.GPS = GPS;
        }

        public String getCarKey() {
            return carKey;
        }

        public void setCarKey(String carKey) {
            this.carKey = carKey;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModelYear() {
            return modelYear;
        }

        public void setModelYear(String modelYear) {
            this.modelYear = modelYear;
        }

        public String getSeats() {
            return seats;
        }

        public void setSeats(String seats) {
            this.seats = seats;
        }

        public String getTransmission() {
            return transmission;
        }

        public void setTransmission(String transmission) {
            this.transmission = transmission;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPerDayRate() {
            return perDayRate;
        }

        public void setPerDayRate(String perDayRate) {
            this.perDayRate = perDayRate;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isAC() {
            return AC;
        }

        public void setAC(boolean AC) {
            this.AC = AC;
        }

        public boolean isGPS() {
            return GPS;
        }

        public void setGPS(boolean GPS) {
            this.GPS = GPS;
        }

        CarModel() {
        }
    }

}
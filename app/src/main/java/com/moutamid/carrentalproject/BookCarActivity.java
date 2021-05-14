package com.moutamid.carrentalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

    private Utils utils = new Utils();

    private ImageView plusBtn, minusBtn;
    private Button requestButton;

    private CarModel carModel;

    private int currentMileageInt = 5;
    private int totalCost;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_car);

        carImage = findViewById(R.id.car_image_view_layout_activity_bok_car);
        name = findViewById(R.id.name_layout_car_activity_bok_car);
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
        requestButton = findViewById(R.id.request_booking_car_btn);

        progressDialog = new ProgressDialog(BookCarActivity.this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

//        String key = "-M_VDfYhPGwzV3ebjyWY";
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
                        progressDialog.dismiss();
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
        requestButton.setOnClickListener(requestButtonClickListener());

        progressDialog.dismiss();
    }

    private View.OnClickListener requestButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!utils.getStoredBoolean(BookCarActivity.this, "alreadyBooked")){
                    Toast.makeText(BookCarActivity.this, "You can only apply for one booking at a time!", Toast.LENGTH_SHORT).show();
                    return;
                }

                utils.showDialog(BookCarActivity.this,
                        "Are you sure?",
                        "Do you really want to book this car for "
                        +currentMileageTv.getText().toString()
                        +" in " + totalCostTv.getText().toString(),
                        "Yes",
                        "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                requestABookingOfCar(dialogInterface);

//                                dialogInterface.dismiss();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }, true);

            }
        };
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

    private void requestABookingOfCar(final DialogInterface dialogInterface) {
        progressDialog.show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String name = utils.getStoredString(BookCarActivity.this, "nameStr");
//        String email = utils.getStoredString(BookCarActivity.this, "emailStr");
        String license = utils.getStoredString(BookCarActivity.this, "licenseStr");

        RequestBookingModel model = new RequestBookingModel();
        model.setTotalCost(totalCost);
        model.setTotalMileages(currentMileageInt);
        model.setStatus("pending");
        model.setLicenseNumber(license);
        model.setMyUid(auth.getCurrentUser().getUid());
        model.setMyName(name);
        model.setCarImageUrl(carModel.getImageUrl());
        model.setCarName(carModel.getName());
        model.setCarKey(carModel.getCarKey());

        databaseReference.child("requests").child(auth.getCurrentUser().getUid())
                .setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    dialogInterface.dismiss();

                    Toast.makeText(BookCarActivity.this,
                            "Your request has been submitted successfully!"
                            , Toast.LENGTH_SHORT).show();

                    utils.storeBoolean(BookCarActivity.this, "alreadyBooked", true);

                    Intent intent = new Intent(BookCarActivity.this, BottomNavigationActivity.class);
                    intent.putExtra("fromBookingActivity", true);
                    finish();
                    startActivity(intent);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(BookCarActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private static class RequestBookingModel {

        private String carKey, carName, carImageUrl, myName, myUid,
                licenseNumber, status;
        private int totalMileages, totalCost;

        public RequestBookingModel(String carKey, String carName, String carImageUrl, String myName, String myUid, String licenseNumber, String status, int totalMileages, int totalCost) {
            this.carKey = carKey;
            this.carName = carName;
            this.carImageUrl = carImageUrl;
            this.myName = myName;
            this.myUid = myUid;
            this.licenseNumber = licenseNumber;
            this.status = status;
            this.totalMileages = totalMileages;
            this.totalCost = totalCost;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getCarImageUrl() {
            return carImageUrl;
        }

        public void setCarImageUrl(String carImageUrl) {
            this.carImageUrl = carImageUrl;
        }

        public int getTotalMileages() {
            return totalMileages;
        }

        public void setTotalMileages(int totalMileages) {
            this.totalMileages = totalMileages;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }

        public String getCarKey() {
            return carKey;
        }

        public void setCarKey(String carKey) {
            this.carKey = carKey;
        }

        public String getMyName() {
            return myName;
        }

        public void setMyName(String myName) {
            this.myName = myName;
        }

        public String getMyUid() {
            return myUid;
        }

        public void setMyUid(String myUid) {
            this.myUid = myUid;
        }

        RequestBookingModel() {
        }
    }

}
package com.moutamid.carrentalproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestStatusFragment extends Fragment {
    private static final String TAG = "RequestStatusFragment";

    private ImageView carImage;
    private TextView name, transmission, model, seats, status,
            engine, price, description, totalMileagesTv,
            totalCostTv, requestStatusTv, cancelButton;
    private LinearLayout ac, gps;
    private RelativeLayout parentLayout;

    private Utils utils = new Utils();
    private CarModel carModel;
    private ProgressBar progressBar;
    private LottieAnimationView errorView;

    private View view;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.request_status_fragment, container, false);

        carImage = view.findViewById(R.id.car_image_view_layout_request_status);
        name = view.findViewById(R.id.name_layout_car_request_status);
        transmission = view.findViewById(R.id.transmission_layout_car_request_status);
        model = view.findViewById(R.id.model_layout_car_request_status);
        seats = view.findViewById(R.id.seats_layout_car_request_status);
        status = view.findViewById(R.id.status_layout_car_request_status);
        engine = view.findViewById(R.id.engine_layout_car_request_status);
        price = view.findViewById(R.id.price_layout_car_request_status);
        ac = view.findViewById(R.id.ac_layout_car_request_status);
        gps = view.findViewById(R.id.gps_layout_car_request_status);
        description = view.findViewById(R.id.car_description_request_status);

        totalCostTv = view.findViewById(R.id.total_cost_request_status);
        totalMileagesTv = view.findViewById(R.id.total_mileages_request_status);
        requestStatusTv = view.findViewById(R.id.status_request_status);
        cancelButton = view.findViewById(R.id.cancel_request_btn_request_status);

        progressBar = view.findViewById(R.id.progress_bar_status_fragment);
        parentLayout = view.findViewById(R.id.request_status_parent_layout);
        errorView = view.findViewById(R.id.error_layout_fragment_status);
        errorView.pauseAnimation();
//        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

//        String key = "-M_VDfYhPGwzV3ebjyWY";
//        String key = getIntent().getStringExtra("key");

        databaseReference.child("requests")
                .child(auth.getCurrentUser().getUid())
                .addValueEventListener(
                        bookingRequestObjectListener()
                );

        return view;
    }

    private String carKey = "null";

    private ValueEventListener bookingRequestObjectListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                    errorView.playAnimation();
                    errorView.setVisibility(View.VISIBLE);
//                    if (getActivity() != null)
//                        Toast.makeText(getActivity(), "No request exists!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (errorView.getVisibility() == View.VISIBLE) {
                    errorView.setVisibility(View.GONE);
                    errorView.pauseAnimation();
                }

                RequestBookingModel model = snapshot.getValue(RequestBookingModel.class);

                carKey = model.getCarKey();

                totalMileagesTv.setText(model.getTotalMileages() + " Mileages");
                totalCostTv.setText("RM" + model.getTotalCost());

                if (getActivity() != null) {
                    if (model.getStatus().equals("pending"))
                        requestStatusTv.setTextColor(getActivity().getResources()
                                .getColor(R.color.greyTextColor));

                    if (model.getStatus().equals("accepted"))
                        requestStatusTv.setTextColor(getActivity().getResources()
                                .getColor(R.color.green));

                    if (model.getStatus().equals("rejected"))
                        requestStatusTv.setTextColor(getActivity().getResources()
                                .getColor(R.color.red));
                }

                requestStatusTv.setText(model.getStatus());

                databaseReference.child("cars").child(carKey)
                        .addListenerForSingleValueEvent(
                                carModelListener()
                        );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorView.playAnimation();
                errorView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private ValueEventListener carModelListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "No car exist!", Toast.LENGTH_SHORT).show();
                    return;
                }

                carModel = snapshot.getValue(CarModel.class);

                setDetailsOnCarModel();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        };
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

    private void setDetailsOnCarModel() {

        if (carModel.isGPS())
            gps.setVisibility(View.VISIBLE);

        if (carModel.isAC())
            ac.setVisibility(View.VISIBLE);

        price.setText("RM" + carModel.getPerDayRate() + " /Mileage");

        engine.setText(carModel.getEngine());

        status.setText(carModel.getStatus());

        seats.setText(carModel.getSeats() + " Seats");

        model.setText(carModel.getModelYear());

        transmission.setText(carModel.getTransmission());

        name.setText(carModel.getName());

        if (getActivity() != null) {

            Glide.with(getActivity())
                    .load(carModel.getImageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.color.grey)
                            .error(R.color.grey)
                    )
                    .into(carImage);
        }

        description.setText(carModel.getDescription());

        if (progressBar.getVisibility() == View.GONE)
            return;

        progressBar.setVisibility(View.GONE);
        parentLayout.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.Wobble).duration(1500).delay(1000).repeat(5000)
                .playOn(requestStatusTv);

        cancelButton.setOnClickListener(cancelButtonClickListener());
    }

    private View.OnClickListener cancelButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                utils.showDialog(getActivity(),
                        "Are you sure?",
                        "Do you really want to cancel this booking?",
                        "Yes",
                        "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ProgressDialog progressDialog;
                                progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Loading...");
                                progressDialog.show();

                                databaseReference.child("requests")
                                        .child(auth.getCurrentUser().getUid())
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    progressDialog.dismiss();
                                                    parentLayout.setVisibility(
                                                            View.GONE
                                                    );

                                                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();

                                                    utils.storeBoolean(getActivity(), "alreadyBooked", false);

                                                    getActivity().recreate();

                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                                dialogInterface.dismiss();
                                            }
                                        });
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

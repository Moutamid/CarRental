package com.moutamid.carrentalproject;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TrackerFragment extends Fragment {
    private static final String TAG = "TrackerFragment";

    private boolean golden = true;
    private YoYo.YoYoString gpsAnimation;

    private RelativeLayout parentLayout;
    private ProgressBar progressBar;
    private LottieAnimationView errorView;

    private double currentMileagesDouble = 0;
    private double totalMileagesDouble = 0;
    private double finalDistancee = 0;

    private double finalDistancec = 0;

    private View view;

    private Context context = getActivity();

    private LocationManager locationManager;
    private LocationListener locationListener;

    private int LOCATION_REQUEST_CODE = 10001;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationRequest locationRequest;

    private boolean locationRequested = true;
    private Location startLocation;

    private ImageView carImageView;
    private TextView carNameTextView, myCarNameTextView, currentMileagesTextView,
            totalMileagesTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tracker_fragment, container, false);

//        if (getActivity() != null) {
//
//
//            locationListener = new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    Toast.makeText(getActivity(), location.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String s) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String s) {
//
//                }
//            };
//
//            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            } else {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//            }
//
//        }
        //--------------------------------------------------------------

//        Location locationA = new Location("point A");
//
//        locationA.setLatitude(31.48695);
//        locationA.setLongitude(74.367028);
//
//        Location locationB = new Location("point B");
//
//        locationB.setLatitude(31.485486);
//        locationB.setLongitude(74.365666);
//
//        double distance = (double) locationA.distanceTo(locationB);
//
//        double finalDistance = distance / 1609;

//        distance = finalDistance;

//        DecimalFormat df = new DecimalFormat("#.##");
//        df.setRoundingMode(RoundingMode.CEILING);
//        distance = df.format(finalDistance);

//        int d = (int) distance;

        // IN MILES BUT 0 IN METERS

//        if (distance)
//        double inches = (39.370078 * distance);
//        int miles = (int) (inches / 63360);
//        distance = miles;

        // IN KILOMETERS
//        distance = distance / 1000;

/**
 */

//        TextView textView = view.findViewById(R.id.trackerTextview);
////        textView.setText(df.format(finalDistance));
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (getActivity() != null) {
//
//
//                    //getLastLocation();
//                }
////                if (getActivity() != null) {
////                    Toast.makeText(getActivity(), "started", Toast.LENGTH_SHORT).show();
////                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////
////                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
////                    } else {
////                        Toast.makeText(getActivity(), "started 2", Toast.LENGTH_SHORT).show();
////                        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
////
////                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
////                            @Override
////                            public void onLocationChanged(@NonNull Location location) {
////                                Toast.makeText(getActivity(), location.toString(), Toast.LENGTH_SHORT).show();
////
////                            }
////                        });
////                    }
////
////                }
//
//            }
//        });
//        textView.setText(distance + "");
//        if (context != null)
//            Toast.makeText(context, distance + "", Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        carImageView = view.findViewById(R.id.car_image_view_tracker);
        carNameTextView = view.findViewById(R.id.car_name_tracker);
        myCarNameTextView = view.findViewById(R.id.my_car_name_tracker);
        currentMileagesTextView = view.findViewById(R.id.current_mileages_text_view_tracker);
        totalMileagesTextView = view.findViewById(R.id.total_mileages_tracker);

        parentLayout = view.findViewById(R.id.parent_layout_car_tracker);
        progressBar = view.findViewById(R.id.progress_bar_car_tracker);
        errorView = view.findViewById(R.id.error_layout_fragment_tracker);

//        double value = 0;
//
//        databaseReference.child("requests")
//                .child(mAuth.getCurrentUser().getUid())
//                .child("currentMileages")
//                .setValue(value);

        databaseReference.child("requests")
                .child(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(
                        bookingRequestObjectListener()
                );

        return view;
    }

    private ValueEventListener bookingRequestObjectListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists() && getActivity() != null) {
                    progressBar.setVisibility(View.GONE);
                    errorView.playAnimation();
                    errorView.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "No snapshot exists!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestBookingModel model = snapshot.getValue(RequestBookingModel.class);

//                carKey = model.getCarKey();

//                totalMileagesTv.setText(model.getTotalMileages() + " Mileages");
//                totalCostTv.setText("$" + model.getTotalCost());
//
//                if (getActivity() != null) {
//                    if (model.getStatus().equals("pending"))
//                        requestStatusTv.setTextColor(getActivity().getResources()
//                                .getColor(R.color.greyTextColor));
//
//                    if (model.getStatus().equals("accepted"))
//                        requestStatusTv.setTextColor(getActivity().getResources()
//                                .getColor(R.color.green));
//
//                    if (model.getStatus().equals("rejected"))
//                        requestStatusTv.setTextColor(getActivity().getResources()
//                                .getColor(R.color.red));
//                }

                if (model.getStatus().equals("pending")) {
                    progressBar.setVisibility(View.GONE);
                    errorView.playAnimation();
                    errorView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Status is still pending!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (model.getStatus().equals("rejected")) {
                    progressBar.setVisibility(View.GONE);
                    errorView.playAnimation();
                    errorView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Status is rejected!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Glide.with(getActivity())
                        .load(model.carImageUrl)
                        .apply(new RequestOptions()
                                .placeholder(R.color.grey)
                                .error(R.color.grey)
                        )
                        .into(carImageView);

                carNameTextView.setText(model.getCarName());
                myCarNameTextView.setText(model.getMyName());

                totalMileagesDouble = (double) model.getTotalMileages();

                totalMileagesTextView.setText(
                        model.getTotalMileages() + ""
                );

                if (snapshot.child("currentMileages").exists()) {

                    currentMileagesDouble = snapshot.child("currentMileages")
                            .getValue(Double.class);

                    String value = String.valueOf(currentMileagesDouble);

                    currentMileagesTextView.setText(value);

                }

                if (currentMileagesDouble > totalMileagesDouble) {
                    showLimitReachedDialog();
                }

                setStartDriveBtnCLickListener();
//                    requestStatusTv.setText(model.getStatus());

//                databaseReference.child("cars").child(carKey)
//                        .addListenerForSingleValueEvent(
//                                carModelListener()
//                        );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                errorView.playAnimation();
                errorView.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private boolean firstTime = true;

    private void setStartDriveBtnCLickListener() {
        final RelativeLayout startDriveLayout = (RelativeLayout) view.findViewById(R.id.bottom_layout_tracker);
        final TextView startDriveTextView = (TextView) view.findViewById(R.id.start_driving_textview);
        final ImageView gpsImageView = view.findViewById(R.id.tracker_image_gps);

        databaseReference.child("requests")
                .child(mAuth.getCurrentUser().getUid())
                .child("tracker_started")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            return;
                        }

                        boolean value = snapshot.getValue(Boolean.class);

                        if (value && firstTime) {
                            // TRACKER_STARTED

                            startDriveLayout.setBackgroundResource(R.drawable.bg_stop_tracker_btn);
                            startDriveTextView.setText("Tracking is started");

                            gpsAnimation = YoYo.with(Techniques.Flash).duration(4000).delay(1000).repeat(10000)
                                    .playOn(gpsImageView);

                            getLastLocation();
                            firstTime = false;
//                    startLocationChecker();

//                            golden = false;
                        } else {
                            // TRACKER_STOPPED

                            if (firstTime)
                                return;

                            startDriveLayout.setBackgroundResource(R.drawable.bg_get_started_btn);
                            startDriveTextView.setText("Tracking is stopped");

                            gpsAnimation.stop();
//                    gpsImageView.clearAnimation();

                            stopLocationUpdates();

//                            golden = true;

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        parentLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        startDriveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (golden) {
                //
                //                    startDriveLayout.setBackgroundResource(R.drawable.bg_stop_tracker_btn);
                //                    startDriveTextView.setText("Stop");
                //
                //                    gpsAnimation = YoYo.with(Techniques.Flash).duration(4000).delay(1000).repeat(10000)
                //                            .playOn(gpsImageView);
                //
                //                    getLastLocation();
                ////                    startLocationChecker();
                //
                //                    golden = false;
                //                } else {
                //
                //                    startDriveLayout.setBackgroundResource(R.drawable.bg_get_started_btn);
                //                    startDriveTextView.setText("Start driving");
                //
                //                    gpsAnimation.stop();
                ////                    gpsImageView.clearAnimation();
                //
                //                    stopLocationUpdates();
                //
                //                    golden = true;
                //
                //                }

            }
        });

    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null && getActivity() == null
                    && locationResult.getLastLocation() == null) {
                return;
            }

//            startLocation = new Location("start location");
////            startLocation.setLatitude(31.479536);
////            startLocation.setLongitude(74.3697057);
//            if (locationRequested) {
//                Log.d(TAG, "onLocationResult: startLocation  ");
//                Log.d(TAG, "onLocationResult: " + startLocation.getLatitude());
//                Log.d(TAG, "onLocationResult: " + startLocation.getLongitude());
//
//                startLocation.setLatitude(locationResult.getLastLocation().getLatitude());
//                startLocation.setLongitude(locationResult.getLastLocation().getLongitude());
////                startLocation = locationResult.getLastLocation();
//
//                if (startLocation.getLatitude() != 0.0
//                        || startLocation.getLongitude() != 0.0) {
//                    Log.d(TAG, "onLocationResult: || startLocation.getLongitude() != 0.0) {");
//                    locationRequested = false;
//                }
//
//            }

            Location currentLocation = locationResult.getLastLocation();

            Log.d(TAG, "onLocationResult: currentLocation " + currentLocation.getLatitude());
            Log.d(TAG, "onLocationResult: currentLocation " + currentLocation.getLongitude());
            //locationB.setLatitude(31.485486);
            //            locationB.setLongitude(74.365666);

            double distance = (double) startLocation.distanceTo(currentLocation);
            Log.d(TAG, "onLocationResult: distance: " + distance);

            double distanceInMiles = distance / 1609;
            Log.d(TAG, "onLocationResult: finalDistance " + distanceInMiles);

            double currentLocationDistance = currentMileagesDouble + distanceInMiles;

            if (currentLocationDistance < finalDistancee) {
                startLocation = currentLocation;
                if (finalDistancec == 0)
                    Toast.makeText(getActivity(), "finalDistancec == 0", Toast.LENGTH_SHORT).show();

                currentMileagesDouble = finalDistancec;
                finalDistancee = 0;
                return;
            }

            finalDistancee = 0;
            finalDistancee = currentMileagesDouble + distanceInMiles;

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            currentMileagesTextView.setText(df.format(finalDistancee));

            finalDistancec = Double.parseDouble(df.format(finalDistancee));

            databaseReference.child("requests")
                    .child(mAuth.getCurrentUser().getUid())
                    .child("currentMileages")
                    .setValue(finalDistancec);

            if (finalDistancec > totalMileagesDouble) {
                showLimitReachedDialog();

            }

//            currentMileagesTextView.setText(df.format(distanceInMiles));
            Log.d(TAG, "onLocationResult: textview " + currentMileagesTextView.getText().toString());
            Log.d(TAG, "--------------------------------------------------------------\n\n\n");
//            for (Location location : locationResult.getLocations()) {
//                Log.d(TAG, "onLocationResult: " + location.toString());
//            }
        }
    };

    private void showLimitReachedDialog() {

        showOfflineDialog(getActivity(), "You've reached your mileage!",
                "You can't drive more than what you've paid for! If you still drive then you'll be charged a fine of RM10 on every mileage."
        );

    }

    public void showOfflineDialog(Context context, String title, String desc) {

        Button okayBtn;

        final Dialog dialogOffline = new Dialog(context);
        dialogOffline.setContentView(R.layout.dialog_offline);

        okayBtn = dialogOffline.findViewById(R.id.okay_btn_offline_dialog);
        TextView titleTv = dialogOffline.findViewById(R.id.title_offline_dialog);
        TextView descTv = dialogOffline.findViewById(R.id.desc_offline_dialog);

        if (!TextUtils.isEmpty(title))
            titleTv.setText(title);

        if (!TextUtils.isEmpty(desc))
            descTv.setText(desc);

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOffline.dismiss();
            }
        });

        dialogOffline.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOffline.show();

    }

    private void startLocationChecker() {

        checkSettingsAndStartLocationUpdates();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() != null)
            stopLocationUpdates();
    }

    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(getActivity());

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //Settings of device are satisfied and we can start location updates
                startLocationUpdates();
            }
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(getActivity(), 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        if (fusedLocationProviderClient != null
                && locationCallback != null)
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void getLastLocation() {
        String TAG = "TrackerFragment";

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //We have a location
//                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: startLocation: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: startLocation: " + location.getLongitude());

                    startLocation = location;

                    startLocationChecker();
                } else {
                    Toast.makeText(getActivity(), "Location is null", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: Location was null...");
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (context != null)
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
        }
    }

}















package com.moutamid.carrentalproject;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TrackerFragment extends Fragment {
    private static final String TAG = "TrackerFragment";

    private boolean golden = true;
    private YoYo.YoYoString gpsAnimation;


    private View view;

    private Context context = getActivity();

    private LocationManager locationManager;
    private LocationListener locationListener;

    private int LOCATION_REQUEST_CODE = 10001;

    private FusedLocationProviderClient fusedLocationProviderClient;

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

    LocationRequest locationRequest;

    private boolean locationRequested = true;
    private Location startLocation;

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

            double finalDistance = distance / 1609;
            Log.d(TAG, "onLocationResult: finalDistance " + finalDistance);

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            TextView textView = view.findViewById(R.id.current_mileages_text_view_tracker);
            textView.setText(df.format(finalDistance));
            Log.d(TAG, "onLocationResult: textview " + textView.getText().toString());
            Log.d(TAG, "--------------------------------------------------------------\n\n\n");
//            for (Location location : locationResult.getLocations()) {
//                Log.d(TAG, "onLocationResult: " + location.toString());
//            }
        }
    };

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

        Location locationA = new Location("point A");

        locationA.setLatitude(31.48695);
        locationA.setLongitude(74.367028);

        Location locationB = new Location("point B");

        locationB.setLatitude(31.485486);
        locationB.setLongitude(74.365666);

        double distance = (double) locationA.distanceTo(locationB);

        double finalDistance = distance / 1609;

//        distance = finalDistance;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
//        distance = df.format(finalDistance);

//        int d = (int) distance;

        // IN MILES BUT 0 IN METERS

//        if (distance)
//        double inches = (39.370078 * distance);
//        int miles = (int) (inches / 63360);
//        distance = miles;

        // IN KILOMETERS
//        distance = distance / 1000;

        double d = Double.parseDouble(df.format(finalDistance));

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

        if (getActivity() != null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

            locationRequest = LocationRequest.create();
            locationRequest.setInterval(4000);
            locationRequest.setFastestInterval(2000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        final RelativeLayout startDriveLayout = (RelativeLayout) view.findViewById(R.id.bottom_layout_tracker);
        final TextView startDriveTextView = (TextView) view.findViewById(R.id.start_driving_textview);
        final ImageView gpsImageView = view.findViewById(R.id.tracker_image_gps);

        startDriveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (golden) {

                    startDriveLayout.setBackgroundResource(R.drawable.bg_stop_tracker_btn);
                    startDriveTextView.setText("Stop");

                    gpsAnimation = YoYo.with(Techniques.Flash).duration(4000).delay(1000).repeat(10000)
                            .playOn(gpsImageView);

                    getLastLocation();
//                    startLocationChecker();

                    golden = false;
                } else {

                    startDriveLayout.setBackgroundResource(R.drawable.bg_get_started_btn);
                    startDriveTextView.setText("Start driving");

                    gpsAnimation.stop();
//                    gpsImageView.clearAnimation();

                    stopLocationUpdates();

                    golden = true;

                }

            }
        });

        return view;
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

}















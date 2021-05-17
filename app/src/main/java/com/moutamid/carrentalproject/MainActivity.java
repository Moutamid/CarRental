package com.moutamid.carrentalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("email", "email");
//        map.put("name", "name");
//        map.put("licenseNumber", "licenseNumber");
//
//        databaseReference.child("users")
//                .child("mAuthgetCurrentUsergetUid")
//                .setValue(map);

//        databaseReference.child("requests").child("1_auth_user_uid")
//                .setValue(new RequestBookingModel("ferrari key", "Moutamid", "my uid code", "8757858769", "pending"));
//
//

//        databaseReference.child("users").orderByChild("status").equalTo(true)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        if (!snapshot.exists()) {
//                            Toast.makeText(MainActivity.this, "Not exist", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            Toast.makeText(MainActivity.this,
//                                    dataSnapshot.child("myName").getValue(String.class)
//                                    , Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mAuth.getCurrentUser() == null) {
                    // USER IS NOT SIGNED IN

                    Intent intent = new Intent(MainActivity.this, OnBoardingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                } else {
                    // USER IS SIGNED IN

                    Intent intent = new Intent(MainActivity.this, BottomNavigationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }


//                finish();
//                startActivity(new Intent(MainActivity.this, BookCarActivity.class));
//                startActivity(new Intent(MainActivity.this, OnBoardingActivity.class));
//                startActivity(new Intent(MainActivity.this, BottomNavigationActivity.class));
            }
        }, 2000);
    }
}
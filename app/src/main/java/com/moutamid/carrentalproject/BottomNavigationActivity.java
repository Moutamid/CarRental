package com.moutamid.carrentalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class BottomNavigationActivity extends AppCompatActivity {
    private static final String TAG = "BottomNavigationActivit";

    private ViewPagerFragmentAdapter adapter;
    private ViewPager viewPager;
    private SmoothBottomBar smoothBottomBar;

//    public void RefreshActivity(){
//        recreate();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_bottom_navigation);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(BottomNavigationActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        viewPager = findViewById(R.id.bottom_navigation_view_pager);
        smoothBottomBar = findViewById(R.id.bottom_navigation_Bar);

        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        // Setting up the view Pager
        setupViewPager(viewPager);

        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                viewPager.setCurrentItem(i, true);

                return false;
            }
        });

        if (getIntent().hasExtra("fromBookingActivity")) {

            viewPager.setCurrentItem(1, true);

        }

//        // CODE HERE
//        Dexter.withActivity(BottomNavigationActivity.this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
////                        Toast.makeText(BottomNavigationActivity.this, "Permission granted successfully!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        if (response.isPermanentlyDenied()) {
//                            // open device settings when the permission is
//                            // denied permanently
//                            Toast.makeText(BottomNavigationActivity.this, "You need to provide permission!", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent();
//                            intent.setAction(
//                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            Uri uri = Uri.fromParts("package",
//                                    BuildConfig.APPLICATION_ID, null);
//                            intent.setData(uri);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//
//        Dexter.withActivity(BottomNavigationActivity.this)
//                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
////                        Toast.makeText(BottomNavigationActivity.this, "Permission granted successfully!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        if (response.isPermanentlyDenied()) {
//                            // open device settings when the permission is
//                            // denied permanently
//                            Toast.makeText(BottomNavigationActivity.this, "You need to provide permission!", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent();
//                            intent.setAction(
//                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                            Uri uri = Uri.fromParts("package",
//                                    BuildConfig.APPLICATION_ID, null);
//                            intent.setData(uri);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
    }

    private void setupViewPager(ViewPager viewPager) {

        // Adding Fragments to Adapter
        adapter.addFragment(new HomeFragment());
//        adapter.addFragment(new BookingHistoryFragment());
        adapter.addFragment(new RequestStatusFragment());
        adapter.addFragment(new BookingHistoryFragment());
//        adapter.addFragment(new TrackerFragment());

        // Setting Adapter To ViewPager
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        Log.d(TAG, "setupViewPager: adapter attached");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        smoothBottomBar.setItemActiveIndex(position);
                        break;
                    default:
                        Log.d(TAG, "onPageSelected: default occurred");
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0) super.onBackPressed();

        else viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

    }

    public static class NonSwipableViewPager extends ViewPager {


        public NonSwipableViewPager(@NonNull Context context) {
            super(context);
        }

        public NonSwipableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return false;
        }

        public void setMyScroller() {

            try {
                Class<?> viewpager = ViewPager.class;
                Field scroller = viewpager.getDeclaredField("mScroller");
                scroller.setAccessible(true);
                scroller.set(this, new MyScroller(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public class MyScroller extends Scroller {

            public MyScroller(Context context) {
                super(context, new DecelerateInterpolator());
            }

            @Override
            public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                super.startScroll(startX, startY, dx, dy, 350);
            }
        }
    }

    public static class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        public ViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
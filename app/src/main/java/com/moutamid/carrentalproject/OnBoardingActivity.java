package com.moutamid.carrentalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    private static final String TAG = "OnBoardingActivity";

    private ViewPagerFragmentAdapter adapter;
    private ViewPager viewPager;

//    private boolean space, vision, dream, logo = true;

//    private boolean onpagescrollend = true;
//    private boolean onpageselectedend = false;

//    private Button getStartedBtn;
//    private TextView swipeTextView;

    private SpringDotsIndicator dotsIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.onBoarding_walkThrough_view_pager);

//        findViewById(R.id.loginBtnRegistration).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp = getSharedPreferences("dev.moutamid.buildersapp", Context.MODE_PRIVATE);
//                sp.edit().putString("isLogin", "yes").apply();
//                finish();
//                Intent intent = new Intent(OnBoardingActivity.this, HomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
//
//        getStartedBtn = findViewById(R.id.previous_btn_onBoarding_walkThrough);
//        getStartedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final RelativeLayout parentLayout = findViewById(R.id.parentLayoutOnBoarding);
//                final RelativeLayout registrationLayout = findViewById(R.id.registrationLayout_onboarding);
//                final LinearLayout dotsindicatorlayout = findViewById(R.id.dots_indicator_linearLayout_onBoarding);
//
//                viewPager.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        viewPager.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                }).start();
//
//                dotsindicatorlayout.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        dotsindicatorlayout.setVisibility(View.GONE);
//                        parentLayout.setBackgroundResource(R.drawable.building_blurred);
//
//                        YoYo.with(Techniques.FadeInUp).duration(900).onStart(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
//
////                                    new Handler().postDelayed(new Runnable() {
////                                        @Override
////                                        public void run() {
//                                registrationLayout.setVisibility(View.VISIBLE);
////                                        }
////                                    }, 500);
//
//
//                            }
//                        }).playOn(registrationLayout);
//
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                }).start();
//
//
////                startActivity(new Intent(OnBoardingActivity.this, HomeActivity.class));
//            }
//        });
        dotsIndicator = findViewById(R.id.dots_indicator_onBoarding);
//        nextTxt = findViewById(R.id.next_text_onBoarding_walkThrough);

//        nextBtn.setOnClickListener(nextBtnListener());
//        getStartedBtn.setOnClickListener(getstartedListener());
//        skipBtn.setOnClickListener(skipBtnListener());

        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        // Setting up the view Pager
        setupViewPager(viewPager);

        dotsIndicator.setViewPager(viewPager);

        findViewById(R.id.getStartedBtn_onboarding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(OnBoardingActivity.this, ActivityLogin.class));
            }
        });

        findViewById(R.id.skipBtn_onboarding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(OnBoardingActivity.this, ActivityLogin.class));
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {

        // Adding Fragments to Adapter
        adapter.addFragment(new FragmentOnBoardingOne());
        adapter.addFragment(new FragmentOnBoardingTwo());
        adapter.addFragment(new FragmentOnBoardingThree());

        // Setting Adapter To ViewPager
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        Log.d(TAG, "setupViewPager: adapter attached");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

//                if (onpagescrollend && position == 0) {
//
////                        FragmentOnBoardingOne boardingSpace = new FragmentOnBoardingOne();
////                        if (space)
//                    YoYo.with(Techniques.FadeInUp).delay(100).duration(900).onStart(new YoYo.AnimatorCallback() {
//                        @Override
//                        public void call(Animator animator) {
//
//                            findViewById(R.id.textonboardingspace).setVisibility(View.VISIBLE);
//
//
//                        }
//                    }).playOn(findViewById(R.id.textonboardingspace));
//                    onpagescrollend = false;
//
//
//                }
            }

            @Override
            public void onPageSelected(int position) {

//                if (onpageselectedend) {

//                switch (position) {
//                    case 0:
////                            onpagescrollend = true;
////                            onpageselectedend = false;
//
//                        getStartedBtn.setVisibility(View.GONE);
//
////                        FragmentOnBoardingOne boardingSpace = new FragmentOnBoardingOne();
////                        if (space)
//                        YoYo.with(Techniques.FadeInUp).duration(900).onStart(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
//
////                                    new Handler().postDelayed(new Runnable() {
////                                        @Override
////                                        public void run() {
//                                findViewById(R.id.textonboardingspace).setVisibility(View.VISIBLE);
////                                        }
////                                    }, 500);
//
//
//                            }
//                        }).playOn(findViewById(R.id.textonboardingspace));
//
//                        findViewById(R.id.logoonboarding).setVisibility(View.GONE);
//                        findViewById(R.id.dreamslayout).setVisibility(View.GONE);
//                        findViewById(R.id.visionLayout).setVisibility(View.GONE);
////                        space = false;
//                        break;
//                    case 1:
//                        getStartedBtn.setVisibility(View.GONE);
////                        FragmentOnBoardingVision boardingvision = new FragmentOnBoardingVision();
////                        if (vision)
//                        YoYo.with(Techniques.FadeInUp).duration(900).onStart(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
//
////                                    new Handler().postDelayed(new Runnable() {
////                                        @Override
////                                        public void run() {
//                                findViewById(R.id.visionLayout).setVisibility(View.VISIBLE);
////                                        }
////                                    }, 500);
//
//
//                            }
//                        }).playOn(findViewById(R.id.visionLayout));
////                        vision = false;
//                        findViewById(R.id.logoonboarding).setVisibility(View.GONE);
//                        findViewById(R.id.dreamslayout).setVisibility(View.GONE);
//                        findViewById(R.id.textonboardingspace).setVisibility(View.GONE);
//
//                        break;
//                    case 2:
//                        // Page 3 (Past Papers)
////
////                        utils.changeStatusBarColor(ActivityMain.this, (R.color.yellow));
////
//                        // Page 2 (Video Lectures)
////
////                        utils.changeStatusBarColor(ActivityMain.this, (R.color.darkSkyBlue));
//
//                        // Showing previous Button
//                        // Page 1 (Welcome)
//
////                        utils.changeStatusBarColor(ActivityMain.this, R.color.pinkish);
//
//                        // Hiding previous Button
////                        FragmentOnBoardingTwo boardingDreams = new FragmentOnBoardingTwo();
//
////                        if (dream)
//                        YoYo.with(Techniques.FadeInUp).duration(900).onStart(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
//
////                                    new Handler().postDelayed(new Runnable() {
////                                        @Override
////                                        public void run() {
//                                findViewById(R.id.dreamslayout).setVisibility(View.VISIBLE);
////                                        }
////                                    }, 500);
//
//
//                            }
//                        }).playOn(findViewById(R.id.dreamslayout));
////                        dream = false;
//                        findViewById(R.id.logoonboarding).setVisibility(View.GONE);
//                        findViewById(R.id.textonboardingspace).setVisibility(View.GONE);
//                        findViewById(R.id.visionLayout).setVisibility(View.GONE);
//                        getStartedBtn.setVisibility(View.GONE);
//                        break;
//                    // Animately change text of Next Button
////                        nextTxt.setText("Next");
//                    case 3:
//                        // Page 3 (Guides and More)
//
////                        utils.changeStatusBarColor(ActivityMain.this, (R.color.indigo));
//
//                        // Showing previous Button
////                        FragmentOnBoardingThree boardingLogo = new FragmentOnBoardingThree();
////                        if (logo)
//                        YoYo.with(Techniques.FadeInUp).duration(900).onStart(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
////                                    new Handler().postDelayed(new Runnable() {
////                                        @Override
////                                        public void run() {
//                                findViewById(R.id.logoonboarding).setVisibility(View.VISIBLE);
////                                        }
////                                    }, 500);
//
//                            }
//                        }).playOn(findViewById(R.id.logoonboarding));
//
//                        findViewById(R.id.dreamslayout).setVisibility(View.GONE);
//                        findViewById(R.id.textonboardingspace).setVisibility(View.GONE);
//                        findViewById(R.id.visionLayout).setVisibility(View.GONE);
////                        logo = false;
//                        YoYo.with(Techniques.FadeIn).duration(900).onStart(new YoYo.AnimatorCallback() {
//                            @Override
//                            public void call(Animator animator) {
//
////                                    new Handler().postDelayed(new Runnable() {
////                                        @Override
////                                        public void run() {
//                                getStartedBtn.setVisibility(View.VISIBLE);
////                                        }
////                                    }, 500);
//
//
//                            }
//                        }).playOn(getStartedBtn);
////                        swipeTextView.setVisibility(View.GONE);
//
//                        // Animately change text of Next Button
////                        animateNextBtn();
//                        break;
//                }

//                }
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
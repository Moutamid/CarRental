package com.moutamid.carrentalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookingHistoryFragment extends Fragment {

    private View view;

//    public LinearLayout spaceLayout() {
//
//        return view.findViewById(R.id.textonboardingspace);
//
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_history_fragment, container, false);

//        ImageView progress = view.findViewById(R.id.welcome_imageView);
//
//        AnimationDrawable frameAnimation = (AnimationDrawable) progress.getDrawable();
//        frameAnimation.setCallback(progress);
//        frameAnimation.setVisible(true, true);
//        frameAnimation.start();

        return view;
    }
}

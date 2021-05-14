package com.moutamid.carrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private View view;

    private ArrayList<CarModel> carsArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

//    public LinearLayout spaceLayout() {
//
//        return view.findViewById(R.id.textonboardingspace);
//
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("cars").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    ProgressBar progressBar = view.findViewById(R.id.progress_bar_home);
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                carsArrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CarModel model = dataSnapshot.getValue(CarModel.class);
                    carsArrayList.add(model);
                }

                initRecyclerView();

                setDetailsOnFirstDeal();
                setDetailsOnSecondDeal();
                setDetailsOnThirdDeal();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });

//Name,
//Image Url
//Model year,
//Seats,
//AC,
//GPS,
//Transmission(Manual or Automatic),
//Status(Economy, Premium, Sports)
//Per day rate(190$)
//Engine(Petrol, Gas, , Electric, Hybrid)
//Description

        return view;
    }

    private void setDetailsOnFirstDeal() {
        if (carsArrayList.size() <= 0) {
            return;
        }

        FrameLayout linearLayout = view.findViewById(R.id.first_layout_home_fragment);
        TextView name = (TextView) view.findViewById(R.id.name_first_layout_home_fragment);
        TextView price = (TextView) view.findViewById(R.id.price_first_layout_home_fragment);
        ImageView image = (ImageView) view.findViewById(R.id.image_first_layout_home_fragment);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), BookCarActivity.class);
                intent.putExtra("key", carsArrayList.get(0).getCarKey());
                startActivity(intent);
            }
        });

        price.setText(carsArrayList.get(0).getPerDayRate() + "$ per mileage");

        name.setText(carsArrayList.get(0).getName());

        Glide.with(getActivity())
                .load(carsArrayList.get(0).getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.color.grey)
                        .error(R.color.grey)
                )
                .into(image);

    }

    private void setDetailsOnSecondDeal() {
        if (carsArrayList.size() <= 1) {
            return;
        }

        FrameLayout linearLayout = view.findViewById(R.id.second_layout_home_fragment);
        TextView name = (TextView) view.findViewById(R.id.name_second_layout_home_fragment);
        TextView price = (TextView) view.findViewById(R.id.price_second_layout_home_fragment);
        ImageView image = (ImageView) view.findViewById(R.id.image_second_layout_home_fragment);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BookCarActivity.class);
                intent.putExtra("key", carsArrayList.get(1).getCarKey());
                startActivity(intent);
            }
        });

        price.setText(carsArrayList.get(1).getPerDayRate() + "$ per mileage");

        name.setText(carsArrayList.get(1).getName());

        Glide.with(getActivity())
                .load(carsArrayList.get(1).getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.color.grey)
                        .error(R.color.grey)
                )
                .into(image);

    }

    private void setDetailsOnThirdDeal() {
        if (carsArrayList.size() <= 2) {
            return;
        }

        FrameLayout linearLayout = view.findViewById(R.id.third_layout_home_fragment);
        TextView name = (TextView) view.findViewById(R.id.name_third_layout_home_fragment);
        TextView price = (TextView) view.findViewById(R.id.price_third_layout_home_fragment);
        ImageView image = (ImageView) view.findViewById(R.id.image_third_layout_home_fragment);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BookCarActivity.class);
                intent.putExtra("key", carsArrayList.get(2).getCarKey());
                startActivity(intent);
            }
        });

        price.setText(carsArrayList.get(2).getPerDayRate() + "$ per mileage");

        name.setText(carsArrayList.get(2).getName());

        Glide.with(getActivity())
                .load(carsArrayList.get(2).getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.color.grey)
                        .error(R.color.grey)
                )
                .into(image);

    }

    private void uploadNewCar() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String pushKey1 = databaseReference.child("cars").push().getKey();

        CarModel model = new CarModel();
        model.setName("Audi A7 Sportback");
        model.setImageUrl("https://www.ultimatespecs.com/cargallery/2/9867/Audi-25_02_2018__A7-1.jpg");
        model.setModelYear("2020");
        model.setSeats("4");
        model.setAC(true);
        model.setGPS(true);
        model.setTransmission("Automatic");
        model.setStatus("Economy");
        model.setPerDayRate("190");
        model.setEngine("Petrol");
        model.setCarKey(pushKey1);
        model.setDescription("With a fuel consumption of 6.2 litres/100km - 46 mpg UK - 38 mpg US (Average), 0 to 100 km/h (62mph) in 6.9 seconds, a maximum top speed of 155 mph (250 km/h), a curb weight of 3693 lbs (1675 kgs), the A7 Sportback (C8) 45 TFSI has a turbocharged Inline 4 cylinder engine, Petrol motor.");

        databaseReference.child("cars").child(pushKey1).setValue(model);
    }

    private void initRecyclerView() {

        conversationRecyclerView = view.findViewById(R.id.home_recycler_view);
        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        //        LinearLayoutManager layoutManagerUserFriends = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        //    int numberOfColumns = 3;
        //int mNoOfColumns = calculateNoOfColumns(getApplicationContext(), 50);
        //  recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {

            ProgressBar progressBar = view.findViewById(R.id.progress_bar_home);
            progressBar.setVisibility(View.GONE);

            //        noChatsLayout.setVisibility(View.GONE);
            //        chatsRecyclerView.setVisibility(View.VISIBLE);

        }

    }

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cars_home, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            CarModel carModel = carsArrayList.get(position);

//            ImageView carImage;
//            TextView name, transmission, model, seats, status, engine, price;
//            LinearLayout ac, gps;
            //ParentLayout

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), BookCarActivity.class);
                    intent.putExtra("key", carModel.getCarKey());
                    startActivity(intent);
                }
            });

            if (carModel.isGPS())
                holder.gps.setVisibility(View.VISIBLE);

            if (carModel.isAC())
                holder.ac.setVisibility(View.VISIBLE);

            holder.price.setText("$" + carModel.getPerDayRate() + " /Mileage");

            holder.engine.setText(carModel.getEngine());

            holder.status.setText(carModel.getStatus());

            holder.seats.setText(carModel.getSeats() + " Seats");

            holder.model.setText(carModel.getModelYear());

            holder.transmission.setText(carModel.getTransmission());

            holder.name.setText(carModel.getName());

            Glide.with(getActivity())
                    .load(carModel.getImageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.color.grey)
                            .error(R.color.grey)
                    )
                    .into(holder.carImage);

        }

        @Override
        public int getItemCount() {
            if (carsArrayList == null)
                return 0;
            return carsArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            ImageView carImage;
            TextView name, transmission, model, seats, status, engine, price;
            LinearLayout ac, gps;
            RelativeLayout parentLayout;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                carImage = v.findViewById(R.id.car_image_view_layout_home);
                name = v.findViewById(R.id.name_layout_car_home);
                transmission = v.findViewById(R.id.transmission_layout_car_home);
                model = v.findViewById(R.id.model_layout_car_home);
                seats = v.findViewById(R.id.seats_layout_car_home);
                status = v.findViewById(R.id.status_layout_car_home);
                engine = v.findViewById(R.id.engine_layout_car_home);
                price = v.findViewById(R.id.price_layout_car_home);
                ac = v.findViewById(R.id.ac_layout_car_home);
                gps = v.findViewById(R.id.gps_layout_car_home);
                parentLayout = v.findViewById(R.id.parent_layout_car_home);

            }
        }

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

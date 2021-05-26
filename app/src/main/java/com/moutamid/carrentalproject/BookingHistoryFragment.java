package com.moutamid.carrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingHistoryFragment extends Fragment {
    private static final String TAG = "BookingHistoryFragment";
    private ArrayList<RequestBookingModel> tasksArrayList = new ArrayList<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_history_fragment, container, false);

        databaseReference.child("booking_history").orderByChild("myUid")
                .equalTo(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            return;
                        }

                        tasksArrayList.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            tasksArrayList.add(dataSnapshot.getValue(RequestBookingModel.class));

                        }

                        initRecyclerView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }

    private void initRecyclerView() {

        conversationRecyclerView = view.findViewById(R.id.history_recyclerview);
        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        //        LinearLayoutManager layoutManagerUserFriends = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        //    int numberOfColumns = 3;
        //int mNoOfColumns = calculateNoOfColumns(getApplicationContext(), 50);
        //  recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {

            //        noChatsLayout.setVisibility(View.GONE);
            //        chatsRecyclerView.setVisibility(View.VISIBLE);

        }

    }

    /*public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }*/

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_requests_history, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            RequestBookingModel model = tasksArrayList.get(position);
//            Toast.makeText(getActivity(), model.getStatus(), Toast.LENGTH_SHORT).show();

            Glide.with(getActivity())
                    .asBitmap()
                    .load(model.getCarImageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.color.lighterGrey)
                            .error(R.color.lighterGrey)
                    )
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.imageViewH);

            holder.dateH.setText("on " + model.getBooking_date());

            holder.statusH.setText(model.getStatus());

            holder.carNameH.setText(model.getCarName());

//            holder.userNameH.setText(model.getMyName());

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ViewBookingActivity.class);
                    intent.putExtra("key", model.getPushKey());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            TextView userNameH, carNameH, statusH, dateH;
            ImageView imageViewH;
            RelativeLayout parentLayout;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                userNameH = v.findViewById(R.id.user_name_layout_request);
                carNameH = v.findViewById(R.id.car_name_layout_request);
                statusH = v.findViewById(R.id.status_layout_car_request);
                imageViewH = v.findViewById(R.id.car_image_view_layout_request);
                parentLayout = v.findViewById(R.id.parent_layout_request);
                dateH = v.findViewById(R.id.booking_date_layout_request);

            }
        }

    }

    private static class RequestBookingModel {

        private String start_date, end_date, booking_date, pushKey;
        private String carKey, carName, carImageUrl, myName, myUid,
                licenseNumber, status;
        private int totalMileages, totalCost;

        public RequestBookingModel(String start_date, String end_date, String booking_date, String pushKey, String carKey, String carName, String carImageUrl, String myName, String myUid, String licenseNumber, String status, int totalMileages, int totalCost) {
            this.start_date = start_date;
            this.end_date = end_date;
            this.booking_date = booking_date;
            this.pushKey = pushKey;
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

        public String getPushKey() {
            return pushKey;
        }

        public void setPushKey(String pushKey) {
            this.pushKey = pushKey;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
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

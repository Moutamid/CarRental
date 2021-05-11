package com.moutamid.carrentalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class ActivitySignUp extends AppCompatActivity {
    private static final String TAG = "ActivitySignUp";

//    private EditText userNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
//    private Button signUpBtn;
//
//    private ProgressDialog mDialog;
//
//    private FirebaseAuth mAuth;
//
//    private DatabaseReference mDatabaseUsers;
//
//    private String userNameStr;
//
//    private String emailStr;
//
//    private String passwordStr;
//
//    private String confirmedPasswordStr;

    //-----------------------------------------------

    private EditText etName, etEmail, etPassword;
    private TextView tvNameError, tvEmailError, tvPasswordError, tvColor;
    private CardView frameOne, frameTwo, frameThree, frameFour;
    private Button btnRegister;
    private boolean isTermsAccepted = false, isAtLeast8 = false, hasUppercase = false, hasNumber = false, hasSymbol = false, isRegistrationClickable = false;

    private void checkEmpty(String name, String email, String password) {
        if (name.length() > 0 && tvNameError.getVisibility() == View.VISIBLE) {
            tvNameError.setVisibility(View.GONE);
        }
        if (password.length() > 0 && tvPasswordError.getVisibility() == View.VISIBLE) {
            tvPasswordError.setVisibility(View.GONE);
        }
        if (email.length() > 0 && tvEmailError.getVisibility() == View.VISIBLE) {
            tvEmailError.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ResourceType")
    private void checkAllData(String email) {
        if (isTermsAccepted && isAtLeast8 && hasUppercase && hasNumber && hasSymbol && email.length() > 0) {
            isRegistrationClickable = true;
//            tvColor.setTextColor(Color.WHITE);
            btnRegister.setBackgroundColor(Color.parseColor(getString(R.color.goldenrodd)));
        } else {
            isRegistrationClickable = false;
            btnRegister.setBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
    }

    @SuppressLint("ResourceType")
    private void registrationDataCheck() {
        String password = etPassword.getText().toString(), email = etEmail.getText().toString(), name = etName.getText().toString();

        checkEmpty(name, email, password);

        if (password.length() >= 8) {
            isAtLeast8 = true;
            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.goldenrodd)));
        } else {
            isAtLeast8 = false;
            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[A-Z].*)")) {
            hasUppercase = true;
            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.goldenrodd)));
        } else {
            hasUppercase = false;
            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[0-9].*)")) {
            hasNumber = true;
            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.goldenrodd)));
        } else {
            hasNumber = false;
            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("^(?=.*[_.@#$%&*!:;+<>()]).*$")) {
            hasSymbol = true;
            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.goldenrodd)));
        } else {
            hasSymbol = false;
            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }

        checkAllData(email);
    }

    private void inputChange() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registrationDataCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registrationDataCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean shown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        tvColor = findViewById(R.id.tvColor);
        tvNameError = findViewById(R.id.tvNameError);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        frameOne = findViewById(R.id.frameOne);
        frameTwo = findViewById(R.id.frameTwo);
        frameThree = findViewById(R.id.frameThree);
        frameFour = findViewById(R.id.frameFour);
        btnRegister = findViewById(R.id.btnRegister);

        RelativeLayout relativeLayout = findViewById(R.id.terms_conditions_circle);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shown) {
                    relativeLayout.setBackgroundResource(R.drawable.bg_terms_conditions_tick);

                    isTermsAccepted = true;

                    shown = false;

                    registrationDataCheck();

                } else {

                    relativeLayout.setBackgroundResource(R.drawable.bg_terms_conditions_tick_selected);

                    isTermsAccepted = false;

                    shown = true;

                    registrationDataCheck();

                }

            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString(), email = etEmail.getText().toString(), password = etPassword.getText().toString();

                if (name.length() > 0 && email.length() > 0 && password.length() > 0) {
                    if (isRegistrationClickable) {
                        Toast.makeText(ActivitySignUp.this, "REGISTRATION", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (name.length() == 0) {
                        Toast.makeText(ActivitySignUp.this, "Name is none", Toast.LENGTH_SHORT).show();
//                        tvNameError.setVisibility(View.VISIBLE);
                    }
                    if (email.length() == 0) {
                        Toast.makeText(ActivitySignUp.this, "Email is none", Toast.LENGTH_SHORT).show();

//                        tvEmailError.setVisibility(View.VISIBLE);
                    }
                    if (password.length() == 0) {
                        Toast.makeText(ActivitySignUp.this, "Password is none", Toast.LENGTH_SHORT).show();

//                        tvPasswordError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        inputChange();

        //------------------------------------------------------------------
//        mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            Intent intent = new Intent(ActivitySignUp.this, BottomNavigationActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            return;
//        }

        findViewById(R.id.loginBtn_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySignUp.this, ActivityLogin.class));
            }
        });

//        findViewById(R.id.backbtn_signup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        mDatabaseUsers = FirebaseDatabase.getInstance().getReference();
//        mDatabaseUsers.keepSynced(true);
//
//        mDialog = new ProgressDialog(this);
//        mDialog.setCancelable(false);
//        mDialog.setMessage("Signing you in...");

//        initViews();

    }

//    private View.OnClickListener signUpBtnListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mDialog.show();
//                checkStatusOfEditTexts();
//            }
//        };
//    }

//    private void checkStatusOfEditTexts() {
//
//        // Getting strings from edit texts
//        userNameStr = userNameEditText.getText().toString();
//        emailStr = emailEditText.getText().toString();
//        passwordStr = passwordEditText.getText().toString();
//        confirmedPasswordStr = confirmPasswordEditText.getText().toString();
//
//        if (TextUtils.isEmpty(userNameStr)) {
//            mDialog.dismiss();
//            userNameEditText.setError("Username is empty");
//            userNameEditText.requestFocus();
//            return;
//        }
//
//        // Checking if Fields are empty or not
//        if (!TextUtils.isEmpty(emailStr) && !TextUtils.isEmpty(passwordStr) && !TextUtils.isEmpty(confirmedPasswordStr)) {
//
//            // Checking if passwordStr is equal to confirmed Password
//            if (passwordStr.equals(confirmedPasswordStr)) {
//
//                // Signing up user
//                signUpUserWithNameAndPassword();
//
//            } else {
//
//                mDialog.dismiss();
//                confirmPasswordEditText.setError("Password does not match!");
//                confirmPasswordEditText.requestFocus();
//
//            }
//
//            // User Name is Empty
//        } else if (TextUtils.isEmpty(emailStr)) {
//
//            mDialog.dismiss();
//            emailEditText.setError("Please provide a emailStr");
//            emailEditText.requestFocus();
//
//
//            // Password is Empty
//        } else if (TextUtils.isEmpty(passwordStr)) {
//
//            mDialog.dismiss();
//            passwordEditText.setError("Please provide a passwordStr");
//            passwordEditText.requestFocus();
//
//
//            // Confirm Password is Empty
//        } else if (TextUtils.isEmpty(confirmedPasswordStr)) {
//
//            mDialog.dismiss();
//            confirmPasswordEditText.setError("Please confirm your passwordStr");
//            confirmPasswordEditText.requestFocus();
//
//
//        }
//
//    }
//
//    private void signUpUserWithNameAndPassword() {
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
//            //if Email Address is Invalid..
//
//            mDialog.dismiss();
//            emailEditText.setError("Please enter a valid email with no spaces and special characters included");
//            emailEditText.requestFocus();
//        } else {
//
//            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if (task.isSuccessful()) {
//
//                        addUserDetailsToDatabase();
//
//                    } else {
//
//                        mDialog.dismiss();
//                        Toast.makeText(ActivitySignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
////        } else {
////
////            mDialog.dismiss();
////            Toast.makeText(this, "You are not online", Toast.LENGTH_SHORT).show();
////        }
//    }
//
//    private static class UserDetails {
//
//        private String name, email, profileUrl;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getProfileUrl() {
//            return profileUrl;
//        }
//
//        public void setProfileUrl(String profileUrl) {
//            this.profileUrl = profileUrl;
//        }
//
//        public UserDetails(String name, String email, String profileUrl) {
//            this.name = name;
//            this.email = email;
//            this.profileUrl = profileUrl;
//        }
//
//        UserDetails() {
//        }
//    }
//
//    private void addUserDetailsToDatabase() {
//
//        UserDetails userDetails = new UserDetails();
//        userDetails.setEmail(emailStr);
//        userDetails.setName(userNameStr);
//        userDetails.setProfileUrl("Error");
//
//        mDatabaseUsers.child("users").child(mAuth.getCurrentUser().getUid())
//                .setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()) {
//
//                    new Utils().storeString(ActivitySignUp.this,
//                            "usernameStr", userNameStr);
//
//                    mDialog.dismiss();
//
//                    finish();
//                    Intent intent = new Intent(ActivitySignUp.this, BottomNavigationActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//
//                    Toast.makeText(ActivitySignUp.this, "You are signed up!", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    mDialog.dismiss();
//                    Toast.makeText(ActivitySignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onComplete: " + task.getException().getMessage());
//                }
//
//            }
//        });
//
//    }
//
//    private void initViews() {
//
//        userNameEditText = findViewById(R.id.user_name_edittext_activity_sign_up);
//
//        emailEditText = findViewById(R.id.email_edittext_activity_sign_up);
//        passwordEditText = findViewById(R.id.password_edittext_activity_sign_up);
//        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext_activity_sign_up);
//
//        signUpBtn = findViewById(R.id.create_btn_activity_sign_up);
//        signUpBtn.setOnClickListener(signUpBtnListener());
//    }

}
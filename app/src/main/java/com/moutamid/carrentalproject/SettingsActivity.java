package com.moutamid.carrentalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    private Utils utils = new Utils();
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private TextView nameTextView, emailTextView, licenseTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        nameTextView = findViewById(R.id.name_textview_settings);
        emailTextView = findViewById(R.id.email_textview_settings);
        licenseTextView = findViewById(R.id.license_textview_settings);

        nameTextView.setText(utils.getStoredString(
                SettingsActivity.this,
                "nameStr"
        ));

        emailTextView.setText(utils.getStoredString(
                SettingsActivity.this,
                "emailStr"
        ));

        licenseTextView.setText(utils.getStoredString(
                SettingsActivity.this,
                "licenseStr"
        ));

        findViewById(R.id.sign_out_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.removeSharedPref(SettingsActivity.this);
                auth.signOut();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");

    }

    public void edit_btn_clicked_method(View view) {
        ImageView imageView = (ImageView) view;

        String tag = (String) imageView.getTag().toString();

        switch (tag) {

            case "name":
                showDialog1("name", "Please enter a new name");
                break;
            case "email":
                showDialog1("email", "Please enter a new email");
                break;
            case "password":
                updateUserPassword();
                break;
            case "license":
                showDialog1("license", "Please enter a new license");
                break;
            default:
                Log.d(TAG, "edit_btn_clicked_method: default switch runned!");
                break;

        }

    }

    private void updateUserLicense(Dialog dialog, String text) {
        progressDialog.show();

        databaseReference.child("users").child(auth.getCurrentUser().getUid())
                .child("licenseNumber").setValue(text)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            utils.storeString(SettingsActivity.this,
                                    "licenseStr", text);
                            licenseTextView.setText(text);

                            progressDialog.dismiss();
                            dialog.dismiss();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateUserPassword() {
        utils.showDialog(SettingsActivity.this,
                "Are you sure?",
                "We will send you a password reset email on your account address with instructions to reset your password. ",
                "Send",
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.show();
                        auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            progressDialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(SettingsActivity.this, "Sent!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

//                        dialogInterface.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }, true);
    }

    private void updateUserEmail(Dialog dialog, String text) {

        if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            //if Email Address is Invalid..
            Toast.makeText(SettingsActivity.this, "Email is invalid!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        databaseReference.child("users").child(auth.getCurrentUser().getUid())
                .child("email").setValue(text)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            auth.getCurrentUser().updateEmail(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        utils.storeString(SettingsActivity.this,
                                                "emailStr", text);

                                        emailTextView.setText(text);

                                        progressDialog.dismiss();
                                        dialog.dismiss();

                                    } else {

                                        progressDialog.dismiss();
                                        Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateUserName(Dialog dialog, String text) {
        progressDialog.show();

        databaseReference.child("users").child(auth.getCurrentUser().getUid())
                .child("name").setValue(text)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            utils.storeString(SettingsActivity.this,
                                    "nameStr", text);
                            nameTextView.setText(text);

                            progressDialog.dismiss();
                            dialog.dismiss();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void showDialog1(String tag, String title) {

        Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_settings);
        dialog.setCancelable(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView titleTv = (TextView) dialog.findViewById(R.id.title_settings_dialog);
        EditText editText1 = dialog.findViewById(R.id.edittext_settings_dialog);

        titleTv.setText(title);
//        editText1.setHint("");

        dialog.findViewById(R.id.upload_btn_settings_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CODE HERE

                String text = editText1.getText().toString();

                if (TextUtils.isEmpty(text) || text.equals("")) {
                    Toast.makeText(SettingsActivity.this, "Please enter your " + tag, Toast.LENGTH_SHORT).show();
                    return;
                }

                switch (tag) {

                    case "name":
                        updateUserName(dialog, text);
                        break;
                    case "email":
                        updateUserEmail(dialog, text);
                        break;
                    case "license":
                        updateUserLicense(dialog, text);
                        break;
                    default:
                        Log.d(TAG, "edit_btn_clicked_method: default switch runned!");
                        break;

                }

//                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }
}
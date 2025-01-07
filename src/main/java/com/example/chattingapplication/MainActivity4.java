package com.example.chattingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chattingapplication.Utils.FirebaseUtil;
import com.example.chattingapplication.model.UserModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity4 extends AppCompatActivity {

    private static final String TAG = "MainActivity3";
    EditText otpDigit1, otpDigit2, otpDigit3, otpDigit4, otpDigit5, otpDigit6;
    Button verifyOtpButton;
    String verificationId, phone, username, password;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        otpDigit1 = findViewById(R.id.otpDigit1);
        otpDigit2 = findViewById(R.id.otpDigit2);
        otpDigit3 = findViewById(R.id.otpDigit3);
        otpDigit4 = findViewById(R.id.otpDigit4);
        otpDigit5 = findViewById(R.id.otpDigit5);
        otpDigit6 = findViewById(R.id.otpDigit6);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);

        // Retrieve the verification ID and user details from the intent
        Intent intent = getIntent();
        verificationId = intent.getStringExtra("verificationId");
        phone = intent.getStringExtra("mobile");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredOtp = otpDigit1.getText().toString() +
                        otpDigit2.getText().toString() +
                        otpDigit3.getText().toString() +
                        otpDigit4.getText().toString() +
                        otpDigit5.getText().toString() +
                        otpDigit6.getText().toString();

                if (enteredOtp.length() == 6) {
                    verifyCode(enteredOtp);
                } else {
                    Toast.makeText(MainActivity4.this, "Please enter the complete OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        addUserToFirestore();
                    } else {
                        Toast.makeText(MainActivity4.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addUserToFirestore() {
        Timestamp timestamp = Timestamp.now();
        UserModel user = new UserModel(phone, password, username, timestamp,FirebaseUtil.currentUserId());

        // Store user in Firestore using FirebaseUtil
        FirebaseUtil.currentUserDetails().set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity4.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Signup successful, navigating to Userlist");
                        // Navigate to Userlist page
                        Intent intent = new Intent(MainActivity4.this, Userlist.class);
                        startActivity(intent);
                        finish(); // Prevent returning to signup activity
                    } else {
                        Log.e(TAG, "Signup failed", task.getException());
                        Toast.makeText(MainActivity4.this, "Signup Failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

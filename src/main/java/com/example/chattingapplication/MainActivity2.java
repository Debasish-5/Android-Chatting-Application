package com.example.chattingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chattingapplication.Utils.FirebaseUtil;
import com.example.chattingapplication.model.UserModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    EditText username;
    EditText password;
    EditText number;
    Button signupbutton;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already signed in
        if (mAuth.getCurrentUser() != null) {
            navigateToUserlist();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        number = findViewById(R.id.number);
        signupbutton = findViewById(R.id.loginButton);

        // Set OnEditorActionListener on username EditText
        username.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                password.requestFocus();
                return true;
            }
            return false;
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfUserExistsAndAdd();
            }
        });
    }

    private void navigateToUserlist() {
        Intent intent = new Intent(MainActivity2.this, Userlist.class);
        startActivity(intent);
        finish();
    }


    private void checkIfUserExistsAndAdd() {
        // Retrieve user inputs
        String uname = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        String num = number.getText().toString().trim();

        // Add country code if not present
        if (!num.startsWith("+")) {
            num = "+91" + num; // Adjust the country code as needed
        }

        // Log the phone number for debugging purposes
        Log.d("MainActivity2", "Phone Number: " + num);
        String finalNum = num;

        // Query the Firestore collection to check if the user with this phone number exists
        FirebaseUtil.allUserCollectionReference().whereEqualTo("phone", finalNum)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Check if user already exists
                        if (task.getResult() != null && !task.getResult().isEmpty()) {
                            // User exists, retrieve the first matching user
                            UserModel existingUser = task.getResult().toObjects(UserModel.class).get(0);
                            Log.d("MainActivity2", "Existing User: " + existingUser.getPhone());

                            // Set current user details
                            FirebaseUtil.currentUserDetails().set(existingUser)
                                    .addOnCompleteListener(currentUserTask -> {
                                        if (currentUserTask.isSuccessful()) {
                                            // User details set successfully, navigate to Userlist
                                            Toast.makeText(getApplicationContext(), "Sign in Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent2 = new Intent(MainActivity2.this, Userlist.class);
                                            startActivity(intent2);
                                        } else {
                                            // Error setting current user details
                                            Log.e("MainActivity2", "Error setting current user details: " + currentUserTask.getException().getMessage());
                                            Toast.makeText(MainActivity2.this, "Error setting current user details: " + currentUserTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // User does not exist, initiate SMS verification
                            addUser();
                        }
                    } else {
                        // Failed to check user existence
                        Log.e("MainActivity2", "Failed to check user existence: " + task.getException().getMessage());
                        Toast.makeText(MainActivity2.this, "Failed to check user existence: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addUser() {
        String uname = username.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        String num = number.getText().toString().trim();

        if (uname.isEmpty() || pwd.isEmpty() || num.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Ensure the number is formatted correctly
            if (!num.startsWith("+")) {
                num = "+91" + num; // Adjust the country code as needed
            }

            String finalNum = num;
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(finalNum)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    // No automatic verification needed
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(MainActivity2.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId,
                                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                    // Save the verification id so we can use it later
                                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                                    intent.putExtra("mobile", finalNum);
                                    intent.putExtra("username", uname);
                                    intent.putExtra("password", pwd);
                                    intent.putExtra("verificationId", verificationId);
                                    startActivity(intent);
                                }
                            })          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }
    }
}

package com.example.chattingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Userlist extends AppCompatActivity {


    chat chatFragment;
    profile profileFragment;
    thought thoughtFragment;
    TextView fragment_name;
    BottomNavigationView bottomNavigationView;
    ImageButton searchButton,backButton;
    Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

            chatFragment = new chat();
            profileFragment = new profile();
            thoughtFragment = new thought();

            bottomNavigationView = findViewById(R.id.bottom_navigation);
            searchButton = findViewById(R.id.main_search_btn);
            backButton = findViewById(R.id.back_btn);
            fragment_name = findViewById(R.id.fragment_name);
            searchButton.setOnClickListener((v)->{
                startActivity(new Intent(Userlist.this,SearchUserActivity.class));
            });
            backButton.setOnClickListener(v -> {
                if (currentFragment instanceof profile) {
                    bottomNavigationView.setSelectedItemId(R.id.menu_chat);
                }
//                else if (currentFragment instanceof profile) {
//                    bottomNavigationView.setSelectedItemId(R.id.menu_Thoughts);
//                }
            });

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId()==R.id.menu_chat){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, chatFragment).commit();
                        fragment_name.setText("Recent Charts");
                        backButton.setVisibility(View.GONE);
                        currentFragment = chatFragment;
                    }
                    if(item.getItemId()==R.id.menu_profile){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, profileFragment).commit();
                        fragment_name.setText("User Profile");
                        backButton.setVisibility(View.VISIBLE);
                        currentFragment = profileFragment;
                    }
//                      if(item.getItemId()==R.id.menu_Thoughts){
//                       getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, thoughtFragment).commit();
//                        fragment_name.setText("Thoughts");
//                        backButton.setVisibility(View.VISIBLE);
//                        currentFragment = thoughtFragment;
//                    }
                    return true;
                }
            });
            bottomNavigationView.setSelectedItemId(R.id.menu_chat);
        }

}
package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        ImageView ivProfileImage = findViewById(R.id.ivProfileImage);
        TextView tvFollowerCount  = findViewById(R.id.tvFollowerCount);
        TextView tvFollowingCount  = findViewById(R.id.tvFollowingCount);
        TextView tvDescription  = findViewById(R.id.tvDescription);
    }
    //Still working on this optional feature where the user can click on th image of user and go to user profile
}
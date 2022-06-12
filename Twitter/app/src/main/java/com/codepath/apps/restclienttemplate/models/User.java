package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class User {
    public String name;
    public String screenName; //handle
    public String profileImageUrl;
    public String timeAgo;
    public String getProfileImageUrl;
    public String profileBannerImageUrl;
    public int followerCount;
    public int followingCount;
    public String tvDescription;
    public User() {}

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.followerCount = jsonObject.getInt("followers_count");
        user.followingCount = jsonObject.getInt("friends_count");
        user.tvDescription = jsonObject.getString("description");
        return user;
    }




}

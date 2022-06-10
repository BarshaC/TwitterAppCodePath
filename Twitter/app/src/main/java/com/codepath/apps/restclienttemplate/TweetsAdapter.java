package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.jetbrains.annotations.NonNls;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class TweetsAdapter extends  RecyclerView.Adapter <TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }


    //inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        //Binding the tweet with the view holder
        holder.bind(tweet);


    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //define viewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView timeAgo;
        ImageView imageContent;
        TextView tvFavorite;
        ImageButton ibFavorite;
        ImageButton ibReply;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            timeAgo = itemView.findViewById(R.id.timeAgo);
            imageContent = itemView.findViewById(R.id.imageContent);
            tvFavorite = itemView.findViewById(R.id.tvFavoriteCount);
            ibFavorite = itemView.findViewById(R.id.ibFavorite);
            ibReply = itemView.findViewById(R.id.ibReply);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvFavorite.setText(String.valueOf(tweet.favoriteCount));
            if (tweet.isFavorited){
                Drawable newimage = context.getDrawable(R.drawable.ic_vector_heart);
                ibFavorite.setImageDrawable(newimage);
            }else{
                Drawable newimage = context.getDrawable(R.drawable.ic_vector_heart_stroke);
                ibFavorite.setImageDrawable(newimage);

            }
            timeAgo.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            if (tweet.bodyImage.equals("null")) {
                imageContent.setVisibility(View.GONE);
            } else {
                imageContent.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.bodyImage).into(imageContent);
            }
            ibFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!tweet.isFavorited){
                        TwitterApp.getRestClient(context).favorite(tweet.id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.i("adapter","This should've been favorited. Go Check");
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.i("adapter","This should've been unfavorited. Go Check");

                            }
                        });
                        tweet.isFavorited = true;
                        Drawable newimage = context.getDrawable(R.drawable.ic_vector_heart);
                        ibFavorite.setImageDrawable(newimage);
                        tweet.favoriteCount++;
                        tvFavorite.setText(String.valueOf(tweet.favoriteCount));
                }
                    else{
                        tweet.isFavorited = false;
                        TwitterApp.getRestClient(context).unfavorite(tweet.id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.i("adapter","This should've been favorited. Go Check");
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.i("adapter", "This should've been unfavorited. Go Check");
                            }
                            });

                        Drawable newimage = context.getDrawable(R.drawable.ic_vector_heart_stroke);
                        ibFavorite.setImageDrawable(newimage);
                        tweet.favoriteCount--;
                        tvFavorite.setText(String.valueOf(tweet.favoriteCount));


                    }

                    }
            });
            ibReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent (context, ComposeActivity.class);
                    i.putExtra("tweet_to_reply_to", Parcels.wrap(tweet));
                    ((Activity)context).startActivityForResult(i,TimelineActivity.REQUEST_CODE);
                }
            });

        }

        public void clear() {
            tweets.clear();
            notifyDataSetChanged();

        }

        public void addAll(List<Tweet> list) {
            tweets.addAll(list);
            notifyDataSetChanged();
        }
    }
}
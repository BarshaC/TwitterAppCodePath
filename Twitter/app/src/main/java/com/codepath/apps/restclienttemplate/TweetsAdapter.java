package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.media.Image;
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

import org.jetbrains.annotations.NonNls;

import java.util.List;

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            timeAgo = itemView.findViewById(R.id.timeAgo);
            imageContent = itemView.findViewById(R.id.imageContent);
            tvFavorite = itemView.findViewById(R.id.tvFavoriteCount);
            ibFavorite = itemView.findViewById(R.id.ibFavorite);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
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
                    //if not already favorited
                    //tell Twitter i want to favorite this
                    //change the drawable to btn_star_big_on
                    //change the text inside tvFavoriteCount

                    // Else if already favorited
                        //tell twitter to favorite it
                        // change the drawable back to btn star_big_off
                        //decrement the text inside tvFavoriteCount
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
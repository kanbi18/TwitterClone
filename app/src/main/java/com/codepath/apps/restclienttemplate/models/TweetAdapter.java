package com.codepath.apps.restclienttemplate.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.DetailedTweetActivity;
import com.codepath.apps.restclienttemplate.R;

import org.parceler.Parcels;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    List<Tweet> tweets;
    Context context;

    public TweetAdapter(List<Tweet> tweets, Context context) {
        this.tweets = tweets;
        this.context = context;
    }

    @NonNull
    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View tweetView = layoutInflater.inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetAdapter.ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout tweetContainer;
        TextView tweetOwner;
        TextView tweetOwnerAt;
        TextView tweetText;
        ImageView profilePicture;
        ImageView likedIV;
        ImageView retweetsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tweetContainer = itemView.findViewById(R.id.tweet_container);
            tweetOwner = itemView.findViewById(R.id.tweet_owner);
            tweetText = itemView.findViewById(R.id.tweet_text);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            likedIV = itemView.findViewById(R.id.liked);
            retweetsIV = itemView.findViewById(R.id.retweeted);
        }


        public void bind(final Tweet tweet) {
            tweetOwner.setText(tweet.getTweetOwner());
            tweetText.setText(tweet.getTweetText());
            Boolean liked = tweet.getLiked();
            Boolean retweeted = tweet.getRetweeted();

            loadLiked(liked, likedIV);
            loadRetweeted(retweeted, retweetsIV);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.tweet_placeholder);

            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(tweet.getProfilePicture())
                    .into(profilePicture);

            tweetContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailedTweetActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, (View) tweetOwner, "profile");
                    context.startActivity(intent, options.toBundle());
                }
            });

        }


        private void loadRetweeted(Boolean retweeted, ImageView retweetedIV) {
            if(retweeted){
                retweetedIV.setImageResource(R.drawable.retweet);
            }else{retweetedIV.setImageResource(R.drawable.not_retweeted);}
        }

        private void loadLiked(Boolean liked, ImageView likedIV) {
            if(liked){
                likedIV.setImageResource(R.drawable.liked);;
            }else{likedIV.setImageResource(R.drawable.not_liked);}
        }

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

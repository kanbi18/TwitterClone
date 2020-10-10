package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailedTweetActivity extends AppCompatActivity {

    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tweet);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        populateDetailedTweet(tweet);
    }

    private void populateDetailedTweet(Tweet tweet) {
        TextView tweetOwner = findViewById(R.id.tweet_owner);
        TextView tweetOwnerAt = findViewById(R.id.tweet_owner_at);
        TextView tweetText = findViewById(R.id.tweet_text);
        ImageView profilePicture = findViewById(R.id.profile_picture);
        ImageView tweetPicture = findViewById(R.id.tweet_picture);
        TextView tweetTime = findViewById(R.id.tweet_time);
        TextView tweetDate = findViewById(R.id.tweet_date);
        TextView tweetLikes = findViewById(R.id.tweet_likes);
        TextView tweetRetweets = findViewById(R.id.tweet_retweets);
        ImageView likedIV = findViewById(R.id.liked);
        ImageView retweetsIV = findViewById(R.id.retweeted);

        tweetOwnerAt.setText(tweet.getTweetOwnerAt());
        tweetOwner.setText(tweet.getTweetOwner());
        tweetDate.setText(tweet.getDate());
        tweetText.setText(tweet.getTweetText());
        tweetTime.setText(tweet.getTime());
        tweetLikes.setText(tweet.getLikes());
        tweetRetweets.setText(tweet.getRetweets());


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.tweet_placeholder);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(tweet.getProfilePicture())
                .into(profilePicture);

        Boolean liked = tweet.getLiked();
        Boolean retweeted = tweet.getRetweeted();

        loadLiked(liked, likedIV);
        loadRetweeted(retweeted, retweetsIV);

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
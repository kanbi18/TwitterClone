package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Parcel
public class Tweet {

    String tweetText;
    String tweetOwner;
    String tweetOwnerAt;
    String profilePicture;
    String[] createdAt;
    long id;
    String time;
    String date;
    int retweets;
    int likes;
    Boolean liked;
    Boolean retweeted;

    public Tweet(){}

    public Tweet(JSONObject jsonObject) throws JSONException {
        tweetText = jsonObject.getString("text");
        profilePicture = jsonObject.getJSONObject("user").getString("profile_image_url_https");
        createdAt = jsonObject.getString("created_at").split(" ", 0);
        date = createdAt[1] + "/" + createdAt[2] + "/" + createdAt[createdAt.length- 1];
        time = createdAt[3];
        tweetOwner = jsonObject.getJSONObject("user").getString("name");
        tweetOwnerAt = "@" + jsonObject.getJSONObject("user").getString("screen_name");
        retweets = jsonObject.getInt(  "retweet_count");
        likes = jsonObject.getInt("favorite_count");
        id = jsonObject.getLong("id");
        liked = jsonObject.getBoolean("favorited");
        retweeted = jsonObject.getBoolean("retweeted");
    }

    public static List<Tweet> getTimelineFromJsonArray(JSONArray results) throws JSONException {
        List<Tweet> Tweets = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            Tweets.add(new Tweet(results.getJSONObject(i)));
        }
        return Tweets;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetText='" + tweetText + '\'' +
                ", tweetOwner='" + tweetOwner + '\'' +
                ", tweetOwnerAt='" + tweetOwnerAt + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", createdAt=" + Arrays.toString(createdAt) +
                ", id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", retweets='" + retweets + '\'' +
                ", likes='" + likes + '\'' +
                ", liked=" + liked +
                ", retweeted=" + retweeted +
                '}';
    }

    public String getTweetText() {
        return tweetText;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String  getRetweets() {
        return Integer.toString(retweets);
    }

    public String getLikes() {
        return Integer.toString(likes);
    }

    public String getTweetOwner() {
        return tweetOwner;
    }

    public String getTweetOwnerAt() {
        return tweetOwnerAt;
    }

    public long getId() {
        return id;
    }

    public Boolean getLiked() {
        return liked;
    }

    public Boolean getRetweeted() {
        return retweeted;
    }

}

package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetAdapter;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class TimelineActivity extends AppCompatActivity {

    TwitterClient twitterClient;
    List<Tweet> tweets;
    RecyclerView timelineRV;
    TweetAdapter tweetAdapter;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        timelineRV = findViewById(R.id.rvTimeline);
        tweets = new ArrayList<>();

        tweetAdapter = new TweetAdapter(tweets, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        timelineRV.setLayoutManager(layoutManager);
        timelineRV.setAdapter(tweetAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMore();
            }
        };

        timelineRV.addOnScrollListener(scrollListener);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        twitterClient = TwitterApplication.getRestClient(this);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Fetching new data...", "Timeline Activity");
                populateHomeTimeline();
            }
        });

        populateHomeTimeline();

    }

    private void loadMore() {
        twitterClient.getNextTweets(tweets.get(tweets.size()-1).getId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i("TimelineActivity", "Call to Load More Passed");
                JSONArray results = json.jsonArray;
                try {
                    Log.i("TimelineActivity", results.toString());
                    List<Tweet> tweets = Tweet.getTimelineFromJsonArray(results);
                    tweetAdapter.addAll(tweets);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("TimelineActivity", "Call to Load More Failed");
            }
        });
    }

    private void populateHomeTimeline() {
        twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i("TimelineActivity", "Call to Twitter API Passed");
                JSONArray results = json.jsonArray;
                try {
                    tweetAdapter.clear();
                    tweets.addAll(Tweet.getTimelineFromJsonArray(results));
                    swipeRefresh.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("TimelineActivity", "Call to Twitter API Failed");
            }
        });
    }
}
package com.example.user.dht11_get_v0;

/**
 * Created by user on 2016/12/1.
 */

public class ThingSpeak {
    private String channel;
    private Feeds[] feeds;

    public ThingSpeak(){};

    public void setFeeds(Feeds[] feeds){
        this.feeds = feeds;
    }

    public Feeds[] getFeeds(){
        return feeds;
    }
}

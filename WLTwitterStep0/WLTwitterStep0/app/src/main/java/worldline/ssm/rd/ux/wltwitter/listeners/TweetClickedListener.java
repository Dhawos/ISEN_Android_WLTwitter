package worldline.ssm.rd.ux.wltwitter.listeners;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 04/10/2015.
 */
public interface TweetClickedListener {
    public void onTweetClicked(Tweet tweet);
    public void onRetweet(Tweet tweet);
}

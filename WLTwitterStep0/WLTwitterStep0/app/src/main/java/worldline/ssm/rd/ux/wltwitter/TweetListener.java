package worldline.ssm.rd.ux.wltwitter;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 02/10/2015.
 */
public interface TweetListener {
    public void onTweetsRetrieved(List<Tweet> tweets);
}

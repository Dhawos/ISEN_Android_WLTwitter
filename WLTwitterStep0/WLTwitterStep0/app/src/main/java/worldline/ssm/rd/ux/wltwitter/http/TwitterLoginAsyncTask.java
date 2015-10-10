package worldline.ssm.rd.ux.wltwitter.http;

import android.os.AsyncTask;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 02/10/2015.
 */
public class TwitterLoginAsyncTask extends AsyncTask<String,Integer,List<Tweet>>{
    TweetListener listener;

    public TwitterLoginAsyncTask(TweetListener listener){
        this.listener = listener;
    }

    @Override
    protected List<Tweet> doInBackground(String... params) {
        String login = params[0];
        if(login != null){
            List<Tweet> tweets = TwitterHelper.getTweetsOfUser(login);
            return tweets;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Tweet> tweets) {
        listener.onTweetsRetrieved(tweets);

    }
}

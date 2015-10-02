package worldline.ssm.rd.ux.wltwitter.http;

import android.os.AsyncTask;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.helpers.TwitterHelper;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 02/10/2015.
 */
public class TwitterLoginAsyncTask extends AsyncTask<String,Integer,List<Tweet>>{

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
        super.onPostExecute(tweets);
        for(Tweet element : tweets){
            System.out.println( "[" + R.string.app_name + "]" + element.text );
        }
    }
}

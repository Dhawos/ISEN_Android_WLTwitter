package worldline.ssm.rd.ux.wltwitter.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.http.TwitterLoginAsyncTask;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 23/10/2015.
 */
public class TweetService extends Service implements TweetListener {

    public TweetService(){
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
        String login = intent.getExtras().getString("login");
        AsyncTask task = new TwitterLoginAsyncTask(this).execute(login);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this,"Service stopped",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        for(Tweet element : tweets){
            WLTwitterApplication.getContext().getContentResolver().insert(WLTwitterDatabaseContract.TWEETS_URI, WLTwitterDatabaseManager.tweetToContentValues(element));
        }
        stopSelf();
    }
}

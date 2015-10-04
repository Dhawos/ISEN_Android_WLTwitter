package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.http.TwitterLoginAsyncTask;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;


/**
 * Created by dhawo on 02/10/2015.
 */
public class TweetsFragment extends Fragment implements TweetListener,AdapterView.OnItemClickListener{

    ListView listView;
    TweetClickedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tweets_fragment,container,false);
        listView = (ListView)rootView.findViewById(R.id.tweetsListView);
        final ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);
        listView.setOnItemClickListener(this);
        ((ViewGroup)rootView).addView(progressBar);
        return rootView;


    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = WLTwitterApplication.getContext().getApplicationContext().getSharedPreferences("twitter_preferences", Context.MODE_PRIVATE);
        String login = prefs.getString("login",null);
        AsyncTask task = new TwitterLoginAsyncTask(this).execute(login);
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        //TextView textView = (TextView)getView().findViewById(R.id.tweetTextView);
        //textView.setText("");
        //for(Tweet element : tweets){
        //    textView.setText(textView.getText() + element.text + "\n\n");
        //}
        final ArrayAdapter<Tweet> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,tweets);
        listView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener = (TweetClickedListener)activity;
        }
        catch(ClassCastException ex){
            throw new ClassCastException(activity.toString() + "must implement TweetClickedListener");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Tweet tweet = (Tweet)parent.getItemAtPosition(position);
        mListener.onTweetClicked(tweet);
    }
}
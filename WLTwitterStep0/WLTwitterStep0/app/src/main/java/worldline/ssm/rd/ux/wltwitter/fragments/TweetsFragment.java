package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseHelper;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetClickedListener;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.http.TwitterLoginAsyncTask;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.utils.TweetsAdapter;


/**
 * Created by dhawo on 02/10/2015.
 */
public class TweetsFragment extends Fragment implements TweetListener,AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{

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

        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public void onTweetsRetrieved(List<Tweet> tweets) {
        final TweetsAdapter adapter = new TweetsAdapter(tweets,(TweetClickedListener)getActivity(),getActivity().getApplicationContext());
        WLTwitterDatabaseManager.testContentProvider(tweets);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader cursorLoader = new CursorLoader(WLTwitterApplication.getContext());
        cursorLoader.setUri(WLTwitterDatabaseContract.TWEETS_URI);
        cursorLoader.setProjection(WLTwitterDatabaseContract.PROJECTION_FULL);
        cursorLoader.setSelection(null);
        cursorLoader.setSelectionArgs(null);
        cursorLoader.setSortOrder(null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null){
            while (data.moveToNext()){
                final Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(data);
                Log.d("TweetsFragment",tweet.toString());
            }
            if(!data.isClosed()){
                data.close();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

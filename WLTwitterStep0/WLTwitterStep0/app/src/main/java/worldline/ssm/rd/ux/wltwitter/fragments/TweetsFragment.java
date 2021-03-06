package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.Calendar;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseContract;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseHelper;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetClickedListener;
import worldline.ssm.rd.ux.wltwitter.WLTwitterApplication;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;
import worldline.ssm.rd.ux.wltwitter.service.TweetService;
import worldline.ssm.rd.ux.wltwitter.utils.TweetsCursorAdapter;


/**
 * Created by dhawo on 02/10/2015.
 */
public class TweetsFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{

    ListView listView;
    TweetClickedListener mListener;
    PendingIntent mServicePendingIntent;

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

    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = WLTwitterApplication.getContext().getApplicationContext().getSharedPreferences("twitter_preferences", Context.MODE_PRIVATE);
        String login = prefs.getString("login", null);
        final Calendar cal = Calendar.getInstance();
        Intent serviceIntent = new Intent(getActivity(), TweetService.class);
        serviceIntent.putExtra("login", login);
        mServicePendingIntent = PendingIntent.getService(getActivity(),0,serviceIntent,0);
        final AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60000, mServicePendingIntent);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onPause() {
        super.onPause();
        final AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(mServicePendingIntent);

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
        Cursor c = (Cursor) parent.getItemAtPosition(position);
        final Tweet tweet = WLTwitterDatabaseManager.tweetFromCursor(c);
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
        final TweetsCursorAdapter adapter = new TweetsCursorAdapter(getActivity(),data, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER,(TweetClickedListener)getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

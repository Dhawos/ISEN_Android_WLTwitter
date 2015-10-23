package worldline.ssm.rd.ux.wltwitter.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import worldline.ssm.rd.ux.wltwitter.utils.Constants;

/**
 * Created by dhawo on 16/10/2015.
 */
public class WLDatabaseTwitterProvider extends ContentProvider {
    public static final int TWEET_CORRECT_URI_CODE = 42;
    public WLTwitterDatabaseHelper mDBHelper;
    public UriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mDBHelper = new WLTwitterDatabaseHelper(getContext());
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(WLTwitterDatabaseContract.CONTENT_PROVIDER_TWEETS_AUTHORITY,WLTwitterDatabaseContract.TABLE_TWEETS,TWEET_CORRECT_URI_CODE);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(Constants.General.LOG_TAG,"QUERY");
        Cursor c =  mDBHelper.getReadableDatabase().query(WLTwitterDatabaseContract.TABLE_TWEETS,projection,selection,selectionArgs,sortOrder,null,null);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        if(mUriMatcher.match(uri) == TWEET_CORRECT_URI_CODE){
            return WLTwitterDatabaseContract.TWEETS_CONTENT_TYPE;
        }
        throw new IllegalArgumentException("Unknown URI : " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(Constants.General.LOG_TAG, "INSERT");
        long id = mDBHelper.getWritableDatabase().insert(WLTwitterDatabaseContract.TABLE_TWEETS,null,values);
        Uri returnedUri = ContentUris.withAppendedId(uri,id);
        getContext().getContentResolver().notifyChange(returnedUri,null);
        return returnedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(Constants.General.LOG_TAG,"DELETE");
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(Constants.General.LOG_TAG,"UPDATE");
        getContext().getContentResolver().notifyChange(uri,null);
        return 0;
    }
}

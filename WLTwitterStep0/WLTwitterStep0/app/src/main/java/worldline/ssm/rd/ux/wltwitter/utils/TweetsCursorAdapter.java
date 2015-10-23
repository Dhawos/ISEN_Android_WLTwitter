package worldline.ssm.rd.ux.wltwitter.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.squareup.picasso.Picasso;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.database.WLTwitterDatabaseManager;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetClickedListener;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 23/10/2015.
 */
public class TweetsCursorAdapter extends CursorAdapter implements View.OnClickListener{
    TweetClickedListener listener;
    public TweetsCursorAdapter(Context context, Cursor c, int flags, TweetClickedListener listener) {
        super(context, c, flags);
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder;
        View newView;

        LayoutInflater inflater = LayoutInflater.from(context);
        newView = inflater.inflate(R.layout.tweet_layout, null);

        holder = new ViewHolder(newView);
        newView.setTag(holder);
        bindView(newView, context, cursor);
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder;
        holder = (ViewHolder) view.getTag();

        final Tweet tweet = (Tweet) WLTwitterDatabaseManager.tweetFromCursor(cursor);

        holder.name.setText(tweet.user.name);
        holder.alias.setText("@" + tweet.user.screenName);
        holder.content.setText(tweet.text);

        Picasso.with(context).load(tweet.user.profileImageUrl).into(holder.image);

        holder.button.setTag(tweet);


        holder.button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Tweet tweet = (Tweet) v.getTag();
        listener.onRetweet(tweet);
    }
}

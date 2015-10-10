package worldline.ssm.rd.ux.wltwitter.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 09/10/2015.
 */
public class TweetsAdapter extends BaseAdapter {
    private List<Tweet> tweets;
    private View.OnClickListener listener;


    public TweetsAdapter(List<Tweet> tweets, View.OnClickListener listener){
        this.tweets = tweets;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.tweet_layout, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }



        final Tweet tweet = (Tweet) this.getItem(position);

        holder.name.setText(tweet.user.name);
        holder.alias.setText("@" + tweet.user.screenName);
        holder.content.setText(tweet.text);

        holder.button.setOnClickListener(listener);

        return convertView;
    }
}

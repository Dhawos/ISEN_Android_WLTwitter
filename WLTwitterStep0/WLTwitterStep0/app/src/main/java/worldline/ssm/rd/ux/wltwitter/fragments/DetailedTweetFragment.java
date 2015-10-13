package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.listeners.TweetClickedListener;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 09/10/2015.
 */
public class DetailedTweetFragment extends Fragment implements View.OnClickListener{
    Tweet tweet;
    TweetClickedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.detailed_tweet_fragment,container,false);
        tweet = getArguments().getParcelable("tweet");
        listener = (TweetClickedListener)getActivity();

        //Retrieve username
        final TextView usernameView = (TextView)rootView.findViewById(R.id.username_detailed);
        usernameView.setText(tweet.user.name);

        //Retrieve screenName
        final TextView screenNameView = (TextView)rootView.findViewById(R.id.alias_detailed);
        screenNameView.setText("@" + tweet.user.screenName);

        //Retrieve screenName
        final TextView contentView = (TextView)rootView.findViewById(R.id.content_detailed);
        contentView.setText(tweet.text);

        //Retrieve userImage
        final ImageView userImageView = (ImageView)rootView.findViewById(R.id.userPicture_detailed);
        Picasso.with(getActivity().getApplicationContext()).load(tweet.user.profileImageUrl).into(userImageView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        View rootView = this.getView();
        //Add Click listeners on buttons
        Button btn_reply = (Button)rootView.findViewById(R.id.reply_button);
        Button btn_rt = (Button)rootView.findViewById(R.id.rt_button);
        Button btn_star = (Button)rootView.findViewById(R.id.star_button);

        btn_reply.setOnClickListener(this);
        btn_rt.setOnClickListener(this);
        btn_star.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.findViewById(R.id.root_layout).setBackgroundColor(Color.DKGRAY);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.root_layout).setBackgroundColor(Color.WHITE);
    }

    public static DetailedTweetFragment newInstance(Tweet tweet){
        final DetailedTweetFragment newFragment = new DetailedTweetFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable("tweet",tweet);
        newFragment.setArguments(arguments);
        return newFragment;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reply_button:
                onReply();
                break;
            case R.id.rt_button:
                onRt();
                break;
            case R.id.star_button:
                onStar();
                break;
        }
    }

    public void onReply(){
        listener.onReply(tweet);
    }

    public void onRt(){
        listener.onRetweet(tweet);
    }

    public void onStar(){
        listener.onStar(tweet);
    }
}

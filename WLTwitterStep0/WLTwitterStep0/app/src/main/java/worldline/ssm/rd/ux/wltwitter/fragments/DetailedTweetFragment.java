package worldline.ssm.rd.ux.wltwitter.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import worldline.ssm.rd.ux.wltwitter.R;
import worldline.ssm.rd.ux.wltwitter.pojo.Tweet;

/**
 * Created by dhawo on 09/10/2015.
 */
public class DetailedTweetFragment extends Fragment implements View.OnClickListener{
    Tweet tweet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.detailed_tweet_fragment,container,false);
        tweet = getArguments().getParcelable("tweet");

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
        //TO DO




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

    public static DetailedTweetFragment newInstance(Tweet tweet){
        final DetailedTweetFragment newFragment = new DetailedTweetFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable("tweet",tweet);
        newFragment.setArguments(arguments);
        return newFragment;
    }

    @Override
    public void onClick(View v) {
        Log.i("Test" , "Click is detected");
        Log.i("Test" , v.toString());
        switch(v.getId()){
            case R.id.reply_button:
                onReply();
                break;
            case R.id.rt_button:
                Log.i("Test" , "RT_Click is detected");
                onRt();
                break;
            case R.id.star_button:
                onStar();
                break;
        }
    }

    public void onReply(){
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context,"Reply - " + tweet.text,duration).show();
    }

    public void onRt(){
        Log.i("Test" , "onRTLaunched");
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context,"RT - " + tweet.text,duration).show();
    }

    public void onStar(){
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context,"Star - " + tweet.text,duration).show();
    }
}

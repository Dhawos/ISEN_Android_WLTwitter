package worldline.ssm.rd.ux.wltwitter.utils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import worldline.ssm.rd.ux.wltwitter.R;

/**
 * Created by dhawo on 09/10/2015.
 */
public class ViewHolder {
    public ImageView image;
    public TextView name;
    public TextView alias;
    public TextView content;
    public Button button;

    public ViewHolder(View view){
        image = (ImageView)view.findViewById(R.id.userPicture);
        name = (TextView)view.findViewById(R.id.username);
        alias = (TextView)view.findViewById(R.id.alias);
        content = (TextView)view.findViewById(R.id.content);
        button = (Button)view.findViewById(R.id.rtbutton);
    }
}

package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViewById(R.id.login_button).setOnClickListener(this);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("twitter_preferences",Context.MODE_PRIVATE);
        if(prefs.contains("login")){
            this.lauchMainActivity();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        //Retrieve each field
        Context context = getApplicationContext();
        String username = ((EditText)findViewById(R.id.login)).getText().toString();
        if(username == null || username.length() == 0){
            CharSequence text = "Please enter a username.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context,text,duration).show();
        }
        else{
            String password = ((EditText)findViewById(R.id.password)).getText().toString();
            if(password == null || password.length() == 0){

                CharSequence text = "Please enter a password.";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context,text,duration).show();
            }else{
                SharedPreferences prefs = context.getSharedPreferences("twitter_preferences",Context.MODE_PRIVATE);

                prefs.edit().putString("login",username).commit();

                prefs.edit().putString("password",password).commit();

                this.lauchMainActivity();
            }
        }
    }

    public void lauchMainActivity(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("twitter_preferences", Context.MODE_PRIVATE);
        String login = prefs.getString("login", null);
        String password = prefs.getString("password", null);
        Intent intent = new Intent(this,WLTwitterActivity.class);
        Bundle extras = new Bundle();
        extras.putString("login" , login);
        extras.putString("password" , password);
        intent.putExtras(extras);

        startActivity(intent);
    }
}

package com.posco.erp.wipapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String _username = "cthee";
    private String _password = "0504";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //get controls
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.button);

        //Main action
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        password.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            doLogin();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void doLogin(){
        String sUser = username.getText().toString().trim();
        String sPasword = password.getText().toString().trim();

        if (sUser.equalsIgnoreCase(_username) && sPasword.equalsIgnoreCase(_password))
//                if(true)
        {
            //Success
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            //Fail
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.activity_log_in), R.string.login_fail_notify, Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }


}

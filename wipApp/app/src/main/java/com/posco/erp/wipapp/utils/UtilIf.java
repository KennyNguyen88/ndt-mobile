package com.posco.erp.wipapp.utils;

import android.app.Activity;
import android.widget.Toast;

import com.posco.erp.wipapp.R;

public class UtilIf {
    public static void notify_message(Activity activity, String message)
    {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }

}

package com.example.noteappbysqldatabase.widget;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {

    public static void message (Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

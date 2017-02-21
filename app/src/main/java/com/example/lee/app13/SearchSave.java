package com.example.lee.app13;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lee.app13.Utils.MusicUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SearchSave extends AppCompatActivity implements Runnable{

    private static final String TAG = "test";

    private TextView txt_info;
    private Button btn_search;

    private static final int REST=0;
    private static final int SEARCHING=1;
    private static final int DONE=2;
    private int now_state=REST;

    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case SEARCHING:
                    break;
                case DONE:
                    txt_info.setText("Done!");
                    btn_search.setVisibility(View.INVISIBLE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_save);



        txt_info = (TextView) findViewById(R.id.txt_info);
        btn_search = (Button) findViewById(R.id.btn_begin_search);

        txt_info.setText("Begin Search?");

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(now_state==REST)
                {
                    Log.v(TAG,"onClick");

                    SearchSavePermissionsDispatcher.startSearchThreadWithCheck(SearchSave.this);
                    txt_info.setText("Searching...");
                    now_state=SEARCHING;
                    return;
                }
                if(now_state==SEARCHING)
                {
                    return;
                }
            }
        });

        now_state=REST;
    }


    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void startSearchThread()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        /** * logs to debug*/
        Log.v(TAG,"method run ");

        Log.v("Thread","run()");
        Message message = new Message();
        MusicUtils.StorgeMusicData(SearchSave.this);
        message.what = DONE;
        mHandler.sendMessage(message);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showWhy(final PermissionRequest request)
    {
        /** * logs to debug*/
        Log.v(TAG,"method showWhy show dialog");

        new AlertDialog.Builder(this)
                .setTitle("Need Permission")
                .setMessage("We need more permission to continue our option...")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .setCancelable(false).show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void notAsk()
    {
        /** * logs to debug*/
        Log.v(TAG,"method notAsk");

        Toast.makeText(this,"failed to get the permission",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SearchSavePermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}

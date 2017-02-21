package com.example.lee.app13;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lee.app13.Adapter.MyAdapter;
import com.example.lee.app13.Bean.Song;
import com.example.lee.app13.Utils.MusicUtils;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import permissions.dispatcher.*;
@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    protected final static int MENU_SEARCH = Menu.FIRST;
    protected final static int MENU_EXIT =Menu.FIRST+1;
    private ListView mListView;
    private List<Song> list;
    private MyAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(Menu.NONE,MENU_SEARCH,0,"搜索歌曲");
        menu.add(Menu.NONE,MENU_EXIT,0,"退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id==MENU_EXIT)
        {
            this.finish();
            return true;
        }
        MenuToActivity(id);

        return true;
    }

    private void MenuToActivity(int action)
    {
        Intent intent = new Intent(MainActivity.this,SearchSave.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        Log.v("MainActivity","onResume");
        initView();
        super.onResume();

    }

    /**
     * 初始化view
     */
    @NeedsPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void initView() {
        Log.v("MainActivity","initView");
        mListView = (ListView) findViewById(R.id.main_listview);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list

        list = MusicUtils.getMusicData(this);
        adapter = new MyAdapter(this,list);
        mListView.setAdapter(adapter);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showWhy(final PermissionRequest request)
    {
        new AlertDialog.Builder(this).setMessage("Permission test").setPositiveButton("I know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.proceed();
            }
        }).show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void notAsk()
    {
        Toast.makeText(this,"OK",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
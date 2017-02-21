package com.example.lee.app13.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lee.app13.Bean.Song;
import com.example.lee.app13.MainActivity;
import com.example.lee.app13.R;
import com.example.lee.app13.Utils.MusicUtils;

import java.util.List;

/**
 * Created by Lee on 2017/2/19.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Song> list;
    public MyAdapter(MainActivity mainActivity, List<Song> list) {
        this.context = mainActivity;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            //引入布局
            view = View.inflate(context, R.layout.item_music_listview, null);
            //实例化对象
            holder.musicPic =(ImageView) view.findViewById(R.id.item_mymusic_musicpic);
            holder.song = (TextView) view.findViewById(R.id.item_mymusic_song);
            holder.singer = (TextView) view.findViewById(R.id.item_mymusic_singer);
            holder.duration = (TextView) view.findViewById(R.id.item_mymusic_duration);
            holder.position = (TextView) view.findViewById(R.id.item_mymusic_postion);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //给控件赋值
        holder.musicPic.setImageBitmap(list.get(i).musicPic);
        holder.song.setText(list.get(i).song.toString());
        holder.singer.setText(list.get(i).singer.toString());
        //时间需要转换一下
        int duration = list.get(i).duration;
        String time = MusicUtils.formatTime(duration);
        holder.duration.setText(time);
        holder.position.setText(i+1+"");

        return view;
    }

    class ViewHolder{
        ImageView musicPic;
        TextView song;//歌曲名
        TextView singer;//歌手
        TextView duration;//时长
        TextView position;//序号

    }



}
package com.example.lee.app13.Utils;

/**
 * Created by Lee on 2017/2/19.
 */

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;


import com.example.lee.app13.Bean.Song;
import com.example.lee.app13.PlayListDB.PlayListDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * 音乐工具类,
 */

public class MusicUtils {
    /**
     * 扫描系统里面的音频文件，返回一个list集合
     */
    public static void StorgeMusicData(Context context) {

        PlayListDB db = new PlayListDB(context);

        db.cleanDB();
        //List<Song> list = new ArrayList<Song>();
        // 媒体库查询语句（写一个工具类MusicUtils）

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Song song = new Song();
                //song.musicPic = getMusicFileImage(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                if (song.size > 1000 * 800)
                {
                    // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                    if (song.song.contains("-"))
                    {
                        String[] str = song.song.split("-");
                        song.singer = str[0];
                        song.song = str[1];
                    }
                    if (song.song.contains("."))
                    {
                        String[] string = song.song.split("\\.");
                        song.song = string[0];
                    }

                    db.insert(song.song,song.singer,true,Calendar.getInstance().getTimeInMillis(),song.path,song.duration,song.size);
                }
            }
            // 释放资源
            cursor.close();
        }
        db.close();
    }

    public static List<Song> getMusicData(Context context)
    {
        List<Song> list = new ArrayList<Song>();
        PlayListDB db = new PlayListDB(context);
        Cursor cursor = db.select();
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                Song song = new Song();
                song.song = cursor.getString(cursor.getColumnIndex(PlayListDB.NAME));
                song.singer = cursor.getString(cursor.getColumnIndex(PlayListDB.SINGER));
                song.path = cursor.getString(cursor.getColumnIndex(PlayListDB.SONG_URI));
                song.duration = cursor.getInt(cursor.getColumnIndex(PlayListDB.DURATION));
                song.size = cursor.getLong(cursor.getColumnIndex(PlayListDB.SIZE));

                list.add(song);
            }

        }
        db.close();
        return list;

    }

    /**
     * 定义一个方法用来格式化获取到的时间
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }

    public static String formatSize(long size)
    {
        double kib=0,mib;
        String s=null;
        if(size/1024>0)
        {
            kib=(int) size/1024;
            s=kib +"KB";
        }
        if(kib/1024.0>0)
        {
            mib=kib/1024.0 ;
            s=null;
            s=mib + "MB";
        }
        return s;
    }

    public static Bitmap getMusicFileImage(String uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(uri);
        } catch(Exception e) {
            return null;
        }
        byte[] imageBytes = mmr.getEmbeddedPicture();
        Bitmap image = null;
        if(imageBytes!=null) {
            image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        mmr.release();
        return image;
    }
}

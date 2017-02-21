package com.example.lee.app13.Bean;

/**
 * Created by Lee on 2017/2/19.
 */

import android.graphics.Bitmap;

/**
 * Created by user on 2016/6/24.
 * 放置音乐
 */
public class Song {
    /**
     * has picture or not
     * */
    public boolean hasPic;
    /**
     * 歌曲图片
     */
    public Bitmap musicPic;
    /**
     * 歌手
     */
    public String singer;
    /**
     * 歌曲名
     */
    public String song;
    /**
     * 歌曲的地址
     */
    public String path;
    /**
     * 歌曲长度
     */
    public int duration;
    /**
     * 歌曲的大小
     */
    public long size;
}
package com.example.lee.app13.PlayListDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Lee on 2017/2/19.
 */

public class PlayListDB extends SQLiteOpenHelper{


    private static final String DB_NAME = "Playlists";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "list";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SINGER = "singer";
    public static final String HAS_PIC = "has_pic";
    public static final String LAST_PLAY_TIME = "last_play_time";
    public static final String SONG_URI = "song_uri";
    public static final String ALBUM_URI = "album_uri";
    public static final String DURATION = "duration";
    public static final String SIZE = "size";

    public PlayListDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String PLAY_LIST_CMD = "CREATE TABLE "
                +TABLE_NAME
                +"("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT,"
                + SINGER + " TEXT,"
                + HAS_PIC + " INTEGER,"
                + LAST_PLAY_TIME + " LONG,"
                + SONG_URI + " TEXT,"
                + ALBUM_URI + " TEXT,"
                + DURATION + " LONG,"
                + SIZE + " LONG"
                + ");";
        db.execSQL(PLAY_LIST_CMD);
        //db.execSQL("CREATE　TABLE Playlists (id INTEGER KEY AUTOINCREMENT, name TEXT, position INTEGER DEFAULT 0)");
        //db.execSQL("CREATE TABLE SongsInPlaylist (idSong INTEGER, idPlaylist INTEGER, uri TEXT, artist TEXT, title TEXT, position INTEGER DEFAULT 0, hasImage INTEGER DEFAULT 1, PRIMARY KEY(idSong), FOREIGN KEY(idPlaylist) REFERENCES Playlists (id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public long insert(String name, String singer,Boolean has_pic, long last_play_time, String song_uri,/**String album_uri,*/long duration,long size)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(NAME,name);
        cv.put(SINGER,singer);
        cv.put(HAS_PIC,has_pic);
        cv.put(LAST_PLAY_TIME,last_play_time);
        cv.put(SONG_URI,song_uri);
        //cv.put(ALBUM_URI,album_uri);
        cv.put(DURATION,duration);
        cv.put(SIZE,size);
        long row = db.insert(TABLE_NAME,null,cv);
        return row;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + "=?";
        String[] whereValue ={Integer.toString(id)};
        db.delete(TABLE_NAME,where,whereValue);
    }

    public <T> void update(int id, String column, T value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID +"=?";
        String[] whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        switch (column)
        {
            case NAME:
                cv.put(NAME,(String)value);
                break;
            case SINGER:
                cv.put(SINGER,(String)value);
                break;
            case HAS_PIC:
                cv.put(HAS_PIC,(Boolean)value);
                break;
            case LAST_PLAY_TIME:
                cv.put(LAST_PLAY_TIME,(Long)value);
                break;
            case SONG_URI:
                cv.put(SONG_URI,value.toString());
                break;
            /**case ALBUM_URI:
                cv.put(ALBUM_URI,value.toString());
                return;*/
            case DURATION:
                cv.put(DURATION,(Long)value);
                break;
            case SIZE:
                cv.put(SIZE,(Long)value);
                break;
        }
        db.update(TABLE_NAME,cv,where,whereValue);
    }

    public Cursor select()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,"id DESC");
        return cursor;
    }

    public void cleanDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
    }
}

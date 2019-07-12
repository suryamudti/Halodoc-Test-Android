package com.halodoc.testproject.problems.news.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.gson.Gson;
import com.halodoc.testproject.problems.news.model.Hit;
import com.halodoc.testproject.problems.news.model.NewsResponse;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int DATABASE_VERSION = 13;

    // database name
    private static final String DATABASE_NANE = "newsDb";

    // table name
    private static final String TABLE_NEWS = "user";

    // column name
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RAW = "raw";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_CATEGORY = "category";

    // table create strings
    private static final String CREATE_TABLE_NEWS = "CREATE TABLE "
            + TABLE_NEWS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NANE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
    }

    /**
     * CRUD News
     * */
    public void createNews(NewsResponse result) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_RAW, new Gson().toJson(result).getBytes());
        db.insert(TABLE_NEWS, null, values);

        db.close();
    }

    public List<Hit> getNewsList(){

        List<Hit> articles = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS , null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                NewsResponse newsResponse = gson.fromJson(new String(cursor.getBlob(1)),  NewsResponse.class);
                articles.addAll(newsResponse.getHits());
            }
            return articles;
        }finally {
            cursor.close();
        }


    }



}

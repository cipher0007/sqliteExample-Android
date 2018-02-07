package com.pushnotify.cipher.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String title, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, title, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String title, String message, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO pushsave VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, message);
        statement.bindBlob(3, image);

        statement.executeInsert();
    }

    public void updateData(String title, String message, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE pushsave SET title = ?, message = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, title);
        statement.bindString(2, message);
        statement.bindBlob(3, image);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM pushsave WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

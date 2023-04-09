package com.example.practice.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.practice.repository.PersonRepository;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;

    // Tip: i change yung database name para mabago yung tables kung meron man changes sa columns
    // Like mag add or mag delete ng columns sa table
    // Note: mawawala yung mga dating data pag pinalitan ng db name.
    private static final String DATABASE_NAME = "database1.db";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, CURSOR_FACTORY, VERSION);
    }

    // Pag create ng table sa sqlite
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PersonRepository.initTable());
    }

    // Pag mag uupgrade ng version sa SQLite
    // i cecreate ulit mga nasa onCreate na mga tables.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(PersonRepository.dropTable());
        onCreate(sqLiteDatabase);
    }

    //For DROP TABLE, CREATE TABLE, DELETE TABLE
    public void queryWritable(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    //FOR SELECT QUERY
    public Cursor queryReadable(String sql, String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sql, args);
        return c;
    }

    //To delete all data from the table
    public boolean deleteTableData(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDeleted = db.delete(tableName, null, null);
        db.close();
        return isDeleted > 0;
    }

}

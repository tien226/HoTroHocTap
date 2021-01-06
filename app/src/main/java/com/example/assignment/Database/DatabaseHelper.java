package com.example.assignment.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.assignment.Dao.CourseDao;
import com.example.assignment.Dao.SinhVienDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Databese_Name = "hotrohoctap.db";
    public static final int Databese_Version = 1;

    public DatabaseHelper(Context context ) {
        super(context, Databese_Name, null, Databese_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CourseDao.Sql_Course);
        db.execSQL(SinhVienDao.Sql_SinhVien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + CourseDao.Table_Course);
        db.execSQL("Drop table if exists " + SinhVienDao.Table_SinhVien);
        onCreate(db);
    }
}

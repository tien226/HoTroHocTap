package com.example.assignment.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Database.DatabaseHelper;
import com.example.assignment.Model.SinhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDao {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static final String Table_SinhVien = "SinhVien";
    public static final String Sql_SinhVien = "create table SinhVien"+"(Masinhvien text primary key, Makhoahoc text, Hoten text, Ngaysinh date, Quequan text)";

    public SinhVienDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public long insertSinhVien(SinhVien sinhVien){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Masinhvien", sinhVien.getMasinhvien());
        contentValues.put("Makhoahoc", sinhVien.getMakhoahoc());
        contentValues.put("Hoten", sinhVien.getHoten());
        contentValues.put("Ngaysinh",simpleDateFormat.format(sinhVien.getNgaysinh()));
        contentValues.put("Quequan", sinhVien.getQuequan());

        return sqLiteDatabase.insert(Table_SinhVien,null,contentValues);
    }

    public int updateSinhVien(SinhVien sinhVien){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Masinhvien", sinhVien.getMasinhvien());
        contentValues.put("Makhoahoc", sinhVien.getMakhoahoc());
        contentValues.put("Hoten", sinhVien.getHoten());
        contentValues.put("Ngaysinh",simpleDateFormat.format(sinhVien.getNgaysinh()));
        contentValues.put("Quequan", sinhVien.getQuequan());

        return sqLiteDatabase.update(Table_SinhVien,contentValues,"Masinhvien=?",new String[]{sinhVien.getMasinhvien()});
    }

    public int delSinhVien(String Masinhvien){
        return sqLiteDatabase.delete(Table_SinhVien,"Masinhvien=?",new String[]{Masinhvien});
    }

    public List<SinhVien> getAllSinhVien(){
        List<SinhVien> sinhVienList = new ArrayList<>();
        String truyvansv = "select * from "+ Table_SinhVien;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvansv,null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false){
                SinhVien sinhVien = new SinhVien();
                sinhVien.setMasinhvien(cursor.getString(0));
                sinhVien.setMakhoahoc(cursor.getString(1));
                sinhVien.setHoten(cursor.getString(2));
                try {
                    sinhVien.setNgaysinh(simpleDateFormat.parse(cursor.getString(3)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sinhVien.setQuequan(cursor.getString(4));

                sinhVienList.add(sinhVien);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sinhVienList;
    }


}

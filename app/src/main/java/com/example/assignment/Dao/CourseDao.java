package com.example.assignment.Dao;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.Database.DatabaseHelper;
import com.example.assignment.Model.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseDao {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static final String Table_Course = "Course";
    public static final String Sql_Course = "create table Course"+"(Makhoahoc text primary key, Tenkhoahoc text, Tengiangvienkhoahoc text, Ngaybatdaukhoahoc date, Ngayketthuckhoahoc date)";

    public CourseDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public long insertCourse(Course course){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Makhoahoc", course.getMakhoahoc());
        contentValues.put("Tenkhoahoc", course.getTenkhoahoc());
        contentValues.put("Tengiangvienkhoahoc", course.getTengiangvienkhoahoc());
        contentValues.put("Ngaybatdaukhoahoc", simpleDateFormat.format(course.getNgaybatdaukhoahoc()));
        contentValues.put("Ngayketthuckhoahoc", simpleDateFormat.format(course.getNgayketthuckhoahoc()));

        return sqLiteDatabase.insert(Table_Course,null,contentValues);
    }

    public int updateCourse(Course course){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Makhoahoc", course.getMakhoahoc());
        contentValues.put("Tenkhoahoc", course.getTenkhoahoc());
        contentValues.put("Tengiangvienkhoahoc", course.getTengiangvienkhoahoc());
        contentValues.put("Ngaybatdaukhoahoc", simpleDateFormat.format(course.getNgaybatdaukhoahoc()));
        contentValues.put("Ngayketthuckhoahoc", simpleDateFormat.format(course.getNgayketthuckhoahoc()));

        return sqLiteDatabase.update(Table_Course,contentValues,"Makhoahoc=?",new String[]{course.getMakhoahoc()});
    }

    public int delCourse(String Makhoahoc){
        return sqLiteDatabase.delete(Table_Course,"Makhoahoc=?",new String[]{Makhoahoc});
    }

    public List<Course> getAllCourse(){
        List<Course> courseList = new ArrayList<>();

        String truyvancourse = "select * from " + Table_Course;
        Cursor cursor = sqLiteDatabase.rawQuery(truyvancourse,null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false){
                Course course = new Course();
                course.setMakhoahoc(cursor.getString(0));
                course.setTenkhoahoc(cursor.getString(1));
                course.setTengiangvienkhoahoc(cursor.getString(2));
                try {
                    course.setNgaybatdaukhoahoc((Date) simpleDateFormat.parse(cursor.getString(3)));
                    course.setNgayketthuckhoahoc((Date) simpleDateFormat.parse(cursor.getString(4)));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                courseList.add(course);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return courseList;
    }
}

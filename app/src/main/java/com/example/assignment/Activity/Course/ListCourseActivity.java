package com.example.assignment.Activity.Course;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.example.assignment.Adapter.CourseAdapter;
import com.example.assignment.Dao.CourseDao;
import com.example.assignment.Model.Course;
import com.example.assignment.R;

import java.util.List;

public class ListCourseActivity extends AppCompatActivity {
    ListView list_course;
    CourseAdapter courseAdapter;
    CourseDao courseDao;
    List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course);

        list_course = findViewById(R.id.lvlist_course);

        courseDao = new CourseDao(ListCourseActivity.this);
        courseList = courseDao.getAllCourse();
        courseAdapter = new CourseAdapter(courseList,ListCourseActivity.this);
        list_course.setAdapter(courseAdapter);
        courseAdapter.changeDataset(courseDao.getAllCourse());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu_them,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_themkhoahoc){
            startActivity(new Intent(ListCourseActivity.this,CourseActivity.class));

        }else {
            startActivity(new Intent(ListCourseActivity.this,SinhVienActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
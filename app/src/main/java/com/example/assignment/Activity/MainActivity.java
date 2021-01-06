package com.example.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.assignment.Activity.Course.CourseActivity;
import com.example.assignment.Activity.Course.ListCourseActivity;
import com.example.assignment.Activity.Social.SocialActivity;
import com.example.assignment.Activity.Maps.MapsActivity;
import com.example.assignment.Activity.News.NewsActivity;
import com.example.assignment.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ViewCourse(View view) {
        Intent inten = new Intent(MainActivity.this, ListCourseActivity.class);
        startActivity(inten);
    }

    public void ViewMaps(View view) {
        Intent inten = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(inten);
    }

    public void ViewNews(View view) {
        Intent inten = new Intent(MainActivity.this, NewsActivity.class);
        startActivity(inten);
    }

    public void ViewSocial(View view) {
        Intent inten = new Intent(MainActivity.this, SocialActivity.class);
        startActivity(inten);
    }

}
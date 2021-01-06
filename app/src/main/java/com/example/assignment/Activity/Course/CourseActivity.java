package com.example.assignment.Activity.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Adapter.CourseAdapter;
import com.example.assignment.Dao.CourseDao;
import com.example.assignment.Model.Course;
import com.example.assignment.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CourseActivity extends AppCompatActivity {
    EditText edtmakhoahoc, edttenkhoahoc,edtgiaovienkhoahoc;
    TextView  tvngaybatdau, tvngayketthuc;
    CourseDao courseDao;
    CourseAdapter courseAdapter;
    Button btnthemkhoahoc, btnthoat, btnthemsinhvien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // ánh xạ
        edtmakhoahoc = findViewById(R.id.edtmakhoahoc_Course);
        edttenkhoahoc = findViewById(R.id.edttenkhoahoc_Course);
        edtgiaovienkhoahoc= findViewById(R.id.edtgiaovienkhoahoc_Course);
        tvngaybatdau  = findViewById(R.id.tvngaybatdaukhoahoc_Course);
        tvngayketthuc = findViewById(R.id.tvngayketthuckhoahoc_Course);
        btnthemkhoahoc = findViewById(R.id.btnthemkhoahoc_Course);
        btnthoat = findViewById(R.id.btnthoat_Course);
        btnthemsinhvien = findViewById(R.id.btnthemsinhvien_course);

        //button thêm khóa học
        btnthemkhoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animationaddcourse = AnimationUtils.loadAnimation(CourseActivity.this,R.anim.alpha_click);
                btnthemkhoahoc.startAnimation(animationaddcourse);

                if (edtmakhoahoc.getText().toString().isEmpty() || edttenkhoahoc.getText().toString().isEmpty() || edtgiaovienkhoahoc.getText().toString().isEmpty() || tvngaybatdau.getText().toString().isEmpty() || tvngayketthuc.getText().toString().isEmpty()){
                    Toast.makeText(CourseActivity.this, "Yêu cầu nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    Course course = new Course();
                    course.setMakhoahoc(edtmakhoahoc.getText().toString());
                    course.setTenkhoahoc(edttenkhoahoc.getText().toString());
                    course.setTengiangvienkhoahoc(edtgiaovienkhoahoc.getText().toString());
                    Date datengaybatdau = null;
                    Date datengayketthuc = null;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        datengaybatdau = simpleDateFormat.parse(tvngaybatdau.getText().toString());
                        datengayketthuc = simpleDateFormat.parse(tvngayketthuc.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    course.setNgaybatdaukhoahoc(datengaybatdau);
                    course.setNgayketthuckhoahoc(datengayketthuc);

                    courseDao = new CourseDao(CourseActivity.this);
                    if (courseDao.insertCourse(course) > 0){
                        Toast.makeText(CourseActivity.this, "Thêm khóa học thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CourseActivity.this, "Thêm khóa học không thành công!", Toast.LENGTH_SHORT).show();

                        courseAdapter.changeDataset(courseDao.getAllCourse());
                    }
                }
            }
        });

        //button thoát
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(CourseActivity.this,R.anim.alpha_click);
                btnthoat.startAnimation(animation);
                startActivity(new Intent(CourseActivity.this,ListCourseActivity.class));
            }
        });

        //button thêm sinh viên
        btnthemsinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(CourseActivity.this,R.anim.alpha_click);
                btnthemsinhvien.startAnimation(animation);
                startActivity(new Intent(CourseActivity.this,SinhVienActivity.class));
            }
        });
    }

    // láy ngày bắt đầu khóa học
    public void ngaybatdaukhoahoc_Course(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                tvngaybatdau.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    //lấy ngày kết thúc khóa học
    public void ngayketthuckhoahoc_Course(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CourseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                tvngayketthuc.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }


}
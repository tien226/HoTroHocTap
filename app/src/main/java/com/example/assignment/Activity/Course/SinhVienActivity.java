package com.example.assignment.Activity.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Dao.CourseDao;
import com.example.assignment.Dao.SinhVienDao;
import com.example.assignment.Model.Course;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SinhVienActivity extends AppCompatActivity {
    Spinner spnmakhoahoc;
    EditText edtmasv, edthoten, edtquequan;
    TextView tvngaysinh;
    String Makhoahoc = "";
    List<Course> courseList = new ArrayList<>();
    CourseDao courseDao;
    SinhVienDao sinhVienDao;
    Button btnthemsv, btnthoatsv, btndanhsachsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien);

        //ánh xạ
        spnmakhoahoc = findViewById(R.id.spmakhoahoc_SinhVien);
        edtmasv = findViewById(R.id.edtmasinhvien_SinhVien);
        edthoten = findViewById(R.id.edthoten_SinhVien);
        tvngaysinh = findViewById(R.id.tvngaysinh_SinhVien);
        edtquequan = findViewById(R.id.edtquequan_SinhVien);
        btnthemsv = findViewById(R.id.btnthem_SinhVien);
        btnthoatsv = findViewById(R.id.btnthoat_sinhvien);
        btndanhsachsv = findViewById(R.id.btndanhsach_sinhvien);

        //dùng spinner lấy danh sách khóa học
        getCourse();
        spnmakhoahoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Makhoahoc = courseList.get(spnmakhoahoc.getSelectedItemPosition()).getMakhoahoc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        spnmakhoahoc.setSelection(checkPostionCourse(Makhoahoc));

        //lấy ngày sinh
        tvngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SinhVienActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        tvngaysinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //button thêm sinh viên
        btnthemsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(SinhVienActivity.this,R.anim.alpha_click);
                btnthemsv.startAnimation(animation);

                if (edtmasv.getText().toString().isEmpty() || edthoten.getText().toString().isEmpty() || tvngaysinh.getText().toString().isEmpty() || edtquequan.getText().toString().isEmpty()){
                    Toast.makeText(SinhVienActivity.this, "Yêu cầu nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    SinhVien sinhVien = new SinhVien();
                    sinhVien.setMasinhvien(edtmasv.getText().toString());
                    sinhVien.setMakhoahoc(Makhoahoc);
                    sinhVien.setHoten(edthoten.getText().toString());
                    Date datengaysinh = null;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        datengaysinh = simpleDateFormat.parse(tvngaysinh.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    sinhVien.setNgaysinh(datengaysinh);
                    sinhVien.setQuequan(edtquequan.getText().toString());

                    sinhVienDao = new SinhVienDao(SinhVienActivity.this);
                    if (sinhVienDao.insertSinhVien(sinhVien) > 0){
                        Toast.makeText(SinhVienActivity.this, "Thêm thông tin sinh viên thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SinhVienActivity.this, "Thêm thông tin sinh viên không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //button thoát
        btnthoatsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(SinhVienActivity.this,R.anim.alpha_click);
                btnthoatsv.startAnimation(animation);
                startActivity(new Intent(SinhVienActivity.this,ListCourseActivity.class));
            }
        });

        // button danh sách sinh viên
        btndanhsachsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(SinhVienActivity.this,R.anim.alpha_click);
                btndanhsachsv.startAnimation(animation);
                startActivity( new Intent( SinhVienActivity.this,ListSinhVienActivity.class));
            }
        });
    }

    public void getCourse(){
        courseDao = new CourseDao(this);
        courseList = courseDao.getAllCourse();
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, courseList);
        courseArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnmakhoahoc.setAdapter(courseArrayAdapter);
    }

    public int checkPostionCourse(String strCourse){
        for (int i = 0; i < courseList.size(); i++) {
            if (strCourse.equalsIgnoreCase(courseList.get(i).getMakhoahoc())){
                return i;
            }
        }
        return 0;
    }

}
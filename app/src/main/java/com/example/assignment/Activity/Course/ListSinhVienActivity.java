package com.example.assignment.Activity.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.assignment.Adapter.SinhVienAdapter;
import com.example.assignment.Dao.SinhVienDao;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.R;

import java.util.List;

public class ListSinhVienActivity extends AppCompatActivity {
    ListView list_sinhvien;
    SinhVienDao sinhVienDao;
    SinhVienAdapter sinhVienAdapter;
    List<SinhVien> sinhVienList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sinh_vien);

        list_sinhvien = findViewById(R.id.lvlist_sinhvien);

        sinhVienDao = new SinhVienDao(ListSinhVienActivity.this);
        sinhVienList = sinhVienDao.getAllSinhVien();
        sinhVienAdapter = new SinhVienAdapter(sinhVienList,ListSinhVienActivity.this);
        list_sinhvien.setAdapter(sinhVienAdapter);
    }
}
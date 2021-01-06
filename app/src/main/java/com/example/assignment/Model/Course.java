package com.example.assignment.Model;

import java.util.Date;

public class Course {
    private String Makhoahoc;
    private String Tenkhoahoc;
    private String Tengiangvienkhoahoc;
    private Date Ngaybatdaukhoahoc;
    private Date Ngayketthuckhoahoc;

    public Course() {
    }

    public Course(String makhoahoc, String tenkhoahoc, String tengiangvienkhoahoc, Date ngaybatdaukhoahoc, Date ngayketthuckhoahoc) {
        Makhoahoc = makhoahoc;
        Tenkhoahoc = tenkhoahoc;
        Tengiangvienkhoahoc = tengiangvienkhoahoc;
        Ngaybatdaukhoahoc = ngaybatdaukhoahoc;
        Ngayketthuckhoahoc = ngayketthuckhoahoc;
    }

    public String getMakhoahoc() {
        return Makhoahoc;
    }

    public void setMakhoahoc(String makhoahoc) {
        Makhoahoc = makhoahoc;
    }

    public String getTenkhoahoc() {
        return Tenkhoahoc;
    }

    public void setTenkhoahoc(String tenkhoahoc) {
        Tenkhoahoc = tenkhoahoc;
    }

    public String getTengiangvienkhoahoc() {
        return Tengiangvienkhoahoc;
    }

    public void setTengiangvienkhoahoc(String tengiangvienkhoahoc) {
        Tengiangvienkhoahoc = tengiangvienkhoahoc;
    }

    public Date getNgaybatdaukhoahoc() {
        return Ngaybatdaukhoahoc;
    }

    public void setNgaybatdaukhoahoc(Date ngaybatdaukhoahoc) {
        Ngaybatdaukhoahoc = ngaybatdaukhoahoc;
    }

    public Date getNgayketthuckhoahoc() {
        return Ngayketthuckhoahoc;
    }

    public void setNgayketthuckhoahoc(Date ngayketthuckhoahoc) {
        Ngayketthuckhoahoc = ngayketthuckhoahoc;
    }

    @Override
    public String toString() {
        return  Makhoahoc + " - " + Tenkhoahoc;
    }
}

package com.example.assignment.Model;

import java.util.Date;

public class SinhVien {
    private String Masinhvien;
    private String Makhoahoc;
    private String Hoten;
    private Date Ngaysinh;
    private String Quequan;

    public SinhVien() {
    }

    public SinhVien(String masinhvien, String makhoahoc, String hoten, Date ngaysinh, String quequan) {
        Masinhvien = masinhvien;
        Makhoahoc = makhoahoc;
        Hoten = hoten;
        Ngaysinh = ngaysinh;
        Quequan = quequan;
    }

    public String getMasinhvien() {
        return Masinhvien;
    }

    public void setMasinhvien(String masinhvien) {
        Masinhvien = masinhvien;
    }

    public String getMakhoahoc() {
        return Makhoahoc;
    }

    public void setMakhoahoc(String makhoahoc) {
        Makhoahoc = makhoahoc;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public Date getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        Ngaysinh = ngaysinh;
    }

    public String getQuequan() {
        return Quequan;
    }

    public void setQuequan(String quequan) {
        Quequan = quequan;
    }
}

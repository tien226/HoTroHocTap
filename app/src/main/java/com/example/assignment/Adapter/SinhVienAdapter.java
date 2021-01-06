package com.example.assignment.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Dao.SinhVienDao;
import com.example.assignment.Model.SinhVien;
import com.example.assignment.R;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    List<SinhVien> sinhVienList;
    Context context;
    SinhVienDao sinhVienDao;

    public SinhVienAdapter(List<SinhVien> sinhVienList, Context context) {
        this.sinhVienList = sinhVienList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public SinhVien getItem(int position) {
        return sinhVienList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.sinhvienadapter,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.tvmasv = convertView.findViewById(R.id.tvmasinhvien_sinhvienadapter);
            viewHolder.tvmakhoahoc = convertView.findViewById(R.id.tvmakhoahoc_sinhvienadapter);
            viewHolder.tvhoten = convertView.findViewById(R.id.tvtensinhvien_sinhvienadapter);
            viewHolder.tvngaysinh = convertView.findViewById(R.id.tvngaysinh_sinhvienadapter);
            viewHolder.tvquequan = convertView.findViewById(R.id.tvquequan_sinhvienadapter);
            viewHolder.imgsua = convertView.findViewById(R.id.imgsuasinhvien_sinhvienAdapter);
            viewHolder.imgxoa = convertView.findViewById(R.id.imgxoasinhvien_sinhvienAdapter);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvmasv.setText("Mã sinh viên: "+sinhVienList.get(position).getMasinhvien());
        viewHolder.tvmakhoahoc.setText("Mã khóa học: "+sinhVienList.get(position).getMakhoahoc());
        viewHolder.tvhoten.setText("Họ tên: "+sinhVienList.get(position).getHoten());
        Format format = new SimpleDateFormat("dd-MM-yyyy");
        final String ngaysinh = format.format(sinhVienList.get(position).getNgaysinh());
        viewHolder.tvngaysinh.setText("Ngày sinh: "+ngaysinh);
        viewHolder.tvquequan.setText("Quê quán: "+sinhVienList.get(position).getQuequan());

        viewHolder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate_img);
                viewHolder.imgsua.startAnimation(animation);

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogsua_sinhvien);
                final TextView tvsuamasv = dialog.findViewById(R.id.tvsuamasv_dialogsuasv);
                final EditText edtsuamakhoahoc = dialog.findViewById(R.id.edtsuamakhoahoc_dialogsuasv);
                final EditText edtsuaten = dialog.findViewById(R.id.edtsuahoten_dialogsuasv);
                final TextView tvsuangaysinh = dialog.findViewById(R.id.tvsuangaysinh_dialogsuasv);
                final EditText edtsuaque = dialog.findViewById(R.id.edtsuaquequan_dialogsuasv);
                final Button btnsuasv = dialog.findViewById(R.id.btnsua_dialogsuasv);

                tvsuamasv.setText(sinhVienList.get(position).getMasinhvien());
                edtsuamakhoahoc.setText(sinhVienList.get(position).getMakhoahoc());
                edtsuaten.setText(sinhVienList.get(position).getHoten());
                tvsuangaysinh.setText(ngaysinh);
                edtsuaque.setText(sinhVienList.get(position).getQuequan());

                tvsuangaysinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        final int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year,month,dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                tvsuangaysinh.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });

                btnsuasv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvsuamasv.getText().toString().isEmpty() || edtsuamakhoahoc.getText().toString().isEmpty() || edtsuaten.getText().toString().isEmpty() || tvsuangaysinh.getText().toString().isEmpty()){
                            Toast.makeText(context, "Yêu cầu nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            SinhVien sinhVien = new SinhVien();
                            sinhVien.setMasinhvien(tvsuamasv.getText().toString());
                            sinhVien.setMakhoahoc(edtsuamakhoahoc.getText().toString());
                            sinhVien.setHoten(edtsuaten.getText().toString());
                            Date datesuangaysinh = null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                datesuangaysinh = simpleDateFormat.parse(tvsuangaysinh.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            sinhVien.setNgaysinh(datesuangaysinh);
                            sinhVien.setQuequan(edtsuaque.getText().toString());

                            sinhVienDao = new SinhVienDao(context);
                            if (sinhVienDao.updateSinhVien(sinhVien) > 0){
                                Toast.makeText(context, "Cập nhật thông tin sinh viên thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Cập nhật thông tin sinh viên không thành công!", Toast.LENGTH_SHORT).show();
                            }
                            changeData(sinhVienDao.getAllSinhVien());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        viewHolder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate_img);
                viewHolder.imgxoa.startAnimation(animation);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa thông tin");
                builder.setMessage("Bạn muốn xóa danh sách này?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sinhVienDao = new SinhVienDao(context);
                        if (sinhVienDao.delSinhVien(sinhVienList.get(position).getMasinhvien()) > 0){
                            Toast.makeText(context, "Xóa danh sách sinh viên thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa danh sách sinh viên không thành công!", Toast.LENGTH_SHORT).show();
                        }
                        changeData(sinhVienDao.getAllSinhVien());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        // hiệu ưng listview
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_list);
        convertView.startAnimation(animation);

        return convertView;
    }

    public class ViewHolder{
        TextView  tvmasv, tvmakhoahoc, tvhoten, tvngaysinh, tvquequan;
        ImageView imgsua, imgxoa;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeData(List<SinhVien> items){
        this.sinhVienList = items;
        notifyDataSetChanged();
    }
}

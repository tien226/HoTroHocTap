package com.example.assignment.Adapter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Activity.Course.CourseActivity;
import com.example.assignment.Dao.CourseDao;
import com.example.assignment.Model.Course;
import com.example.assignment.R;
import com.google.android.gms.common.internal.DialogRedirect;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseAdapter extends BaseAdapter {
    List<Course> courseList;
    Context context;
    CourseDao courseDao;

    public CourseAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Course getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.courseadapter,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.tvmakhoahoc = convertView.findViewById(R.id.tvmakhoahoc_courseadapter);
            viewHolder.tvtenkhoahoc = convertView.findViewById(R.id.tvtenkhoahoc_courseadapter);
            viewHolder.tvgiaovienkhoahoc = convertView.findViewById(R.id.tvgiaovienkhoahoc_courseadapter);
            viewHolder.tvthoigiankhoahoc = convertView.findViewById(R.id.tvthoiguankhoahoc_courseadapter);
            viewHolder.imgsuakhoahoc = convertView.findViewById(R.id.imgsuakhoahoc_CourseAdapter);
            viewHolder.imgxoakhoahoc = convertView.findViewById(R.id.imgxoakhoahoc_CourseAdapter);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvmakhoahoc.setText("Mã Khóa Học: "+courseList.get(position).getMakhoahoc());
        viewHolder.tvtenkhoahoc.setText("Tên Khóa Học: "+courseList.get(position).getTenkhoahoc());
        viewHolder.tvgiaovienkhoahoc.setText("Giáo Viên: "+courseList.get(position).getTengiangvienkhoahoc());

        final Format format = new SimpleDateFormat("dd-MM-yyyy");
        final String strngaybatdau = format.format(courseList.get(position).getNgaybatdaukhoahoc());
        final String strngayketthuc = format.format(courseList.get(position).getNgayketthuckhoahoc());
        viewHolder.tvthoigiankhoahoc.setText("Thời Gian: "+strngaybatdau+" - "+strngayketthuc);

        viewHolder.imgsuakhoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewHolder.imgsuakhoahoc,"alpha",0f,1f);
//                objectAnimator.setDuration(2000);
//                objectAnimator.start();
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate_img);
                viewHolder.imgsuakhoahoc.startAnimation(animation);

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogsua_course);
                final TextView tvsuama = dialog.findViewById(R.id.edtsuamakhoauoc_suaCourse);
                final EditText edtsuaten = dialog.findViewById(R.id.edtsuatenkhoahoc_suaCourse);
                final EditText edtsuagiaovien = dialog.findViewById(R.id.edtsuagiaovienkhoahoc_suaCourse);
                final TextView tvsuangaybatdau = dialog.findViewById(R.id.tvsuangaybatdaukhoahoc_suaCourse);
                final TextView tvsuangayketthuc = dialog.findViewById(R.id.tvsuangayketthuckhoahoc_suaCourse);
                final Button btnsua = dialog.findViewById(R.id.btnsua_dialogsuacourse);

                tvsuama.setText(courseList.get(position).getMakhoahoc());
                edtsuaten.setText(courseList.get(position).getTenkhoahoc());
                edtsuagiaovien.setText(courseList.get(position).getTengiangvienkhoahoc());
                tvsuangaybatdau.setText(strngaybatdau);
                tvsuangayketthuc.setText(strngayketthuc);

                tvsuangaybatdau.setOnClickListener(new View.OnClickListener() {
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
                                tvsuangaybatdau.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });

                tvsuangayketthuc.setOnClickListener(new View.OnClickListener() {
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
                                tvsuangayketthuc.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });

                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvsuama.getText().toString().isEmpty() || edtsuaten.getText().toString().isEmpty() || edtsuagiaovien.getText().toString().isEmpty() || tvsuangaybatdau.getText().toString().isEmpty() || tvsuangayketthuc.getText().toString().isEmpty()){
                            Toast.makeText(context, "Yêu cầu nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            Course course = new Course();
                            course.setMakhoahoc(tvsuama.getText().toString());
                            course.setTenkhoahoc(edtsuaten.getText().toString());
                            course.setTengiangvienkhoahoc(edtsuagiaovien.getText().toString());
                            Date datesuangaybatdau = null;
                            Date datesuangayketthuc = null;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                datesuangaybatdau = simpleDateFormat.parse(tvsuangaybatdau.getText().toString());
                                datesuangayketthuc = simpleDateFormat.parse(tvsuangayketthuc.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            course.setNgaybatdaukhoahoc(datesuangaybatdau);
                            course.setNgayketthuckhoahoc(datesuangayketthuc);

                            courseDao = new CourseDao(context);
                            if (courseDao.updateCourse(course) > 0){
                                Toast.makeText(context, "Cập nhật khóa học thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Cập nhật khóa học không thành công!", Toast.LENGTH_SHORT).show();
                            }
                            changeDataset(courseDao.getAllCourse());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        viewHolder.imgxoakhoahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate_img);
                viewHolder.imgxoakhoahoc.startAnimation(animation);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa thông tin");
                builder.setMessage("Bạn muốn xóa danh sách này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseDao = new CourseDao(context);
                        if(courseDao.delCourse(courseList.get(position).getMakhoahoc()) > 0){
                            Toast.makeText(context, "Xóa thông tin Khóa Học thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa thông tin Khóa Học không thành công!", Toast.LENGTH_SHORT).show();
                        }
                        changeDataset(courseDao.getAllCourse());
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

        //hiệu ứng listview
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_list);
        convertView.startAnimation(animation);

        return convertView;
    }

    public class ViewHolder{
        TextView tvmakhoahoc,tvtenkhoahoc,tvgiaovienkhoahoc,tvthoigiankhoahoc;
        ImageView imgsuakhoahoc,imgxoakhoahoc;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Course> items) {
        this.courseList = items;
        notifyDataSetChanged();
    }
}

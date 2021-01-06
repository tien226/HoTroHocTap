package com.example.assignment.Activity.Social;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.assignment.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ChucNangFB_Social_Activity extends AppCompatActivity {
    EditText edtlink;
    Button btnsharelink,btnshareimg,btnsharevideo,btnchonvideo;
    ImageView imganh;
    VideoView videoView;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;
    public static int select_image = 1;
    public static int pick_video = 2;
    Bitmap bitmap;
    Uri urivideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_f_b__social_);

//        edttitle = findViewById(R.id.edttitle_chucnangfb);
//        edtmota = findViewById(R.id.edtmota_chucnangfb);
        edtlink = findViewById(R.id.edtlink_chucnangfb);
        btnsharelink = findViewById(R.id.btnsharelink_chucnangfb);
        btnshareimg = findViewById(R.id.btnshareimg_chucnangfb);
        btnsharevideo = findViewById(R.id.btnsharevideo_chucnangfb);
        btnchonvideo = findViewById(R.id.btnchonvideo_chucnangfb);
        imganh = findViewById(R.id.chonimg_chucnangfb);
        videoView = findViewById(R.id.video_chucnangfb);

        shareDialog = new ShareDialog(ChucNangFB_Social_Activity.this);

        //share link
        btnsharelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareDialog.canShow(ShareLinkContent.class)){
                    shareLinkContent = new ShareLinkContent.Builder()
//                            .setContentTitle(edttitle.getText().toString())
//                            .setContentDescription(edtmota.getText().toString())
                            .setContentUrl(Uri.parse(edtlink.getText().toString()))
                            .build();
                }
                shareDialog.show(shareLinkContent);
            }
        });

        //share ảnh
        imganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,select_image);
            }
        });

        btnshareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });

        //share video
        btnchonvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent,pick_video);
            }
        });

        btnsharevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareVideo shareVideo = null;
                shareVideo = new ShareVideo.Builder()
                        .setLocalUrl(urivideo)
                        .build();
                ShareVideoContent content = new ShareVideoContent.Builder()
                        .setVideo(shareVideo)
                        .build();
                shareDialog.show(content);
                videoView.stopPlayback();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == select_image && resultCode == RESULT_OK){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                imganh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == pick_video && resultCode == RESULT_OK){
            urivideo = data.getData();
            videoView.setVideoURI(urivideo);
            videoView.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
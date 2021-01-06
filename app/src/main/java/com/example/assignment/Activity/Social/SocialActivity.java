package com.example.assignment.Activity.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SocialActivity extends AppCompatActivity {
    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    Button btndangxuat,btnchucnang;
    TextView tvname,tvemail;
    CallbackManager callbackManager;
    String email,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_social);

        profilePictureView = findViewById(R.id.imageprofilesocial);
        loginButton = findViewById(R.id.login_button);
        btndangxuat = findViewById(R.id.btndangxuatsocial);
        btnchucnang = findViewById(R.id.btnchucnangfbsocial);
        tvname = findViewById(R.id.tvnamesocial);
        tvemail = findViewById(R.id.tvemailsocial);

        // ẩn các nút
        btnchucnang.setVisibility(View.INVISIBLE);
        btndangxuat.setVisibility(View.INVISIBLE);
        tvname.setVisibility(View.INVISIBLE);
        tvemail.setVisibility(View.INVISIBLE);

        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        setLogin_Button();
        setLogout_Button();
        chuyenmanhinh();

    }

    private void chuyenmanhinh() {
        btnchucnang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialActivity.this, ChucNangFB_Social_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void setLogout_Button() {
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                btndangxuat.setVisibility(View.INVISIBLE);
                btnchucnang.setVisibility(View.INVISIBLE);
                tvname.setVisibility(View.INVISIBLE);
                tvemail.setVisibility(View.INVISIBLE);
                tvname.setText("");
                tvemail.setText("");
                profilePictureView.setProfileId(null);
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                btnchucnang.setVisibility(View.VISIBLE);
                btndangxuat.setVisibility(View.VISIBLE);
                tvname.setVisibility(View.VISIBLE);
                tvemail.setVisibility(View.VISIBLE);

                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON",response.getJSONObject().toString());
                try {
                    email = object.getString("email");
                    name = object.getString("name");

                    profilePictureView.setProfileId(Profile.getCurrentProfile().getId());

                    tvemail.setText(email);
                    tvname.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "name,email");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
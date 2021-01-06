package com.example.assignment.Activity.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.assignment.Model.Myitem_News;
import com.example.assignment.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    public String link = "https://vnexpress.net/rss/giao-duc.rss";
//    EditText edtlinkrss;
    ListView listviewnews;
    List<Myitem_News> myitem_newsList;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrayLink = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

//        edtlinkrss = findViewById(R.id.edtlinkrss_News);
        listviewnews = findViewById(R.id.lvlist_News);

        myitem_newsList = new ArrayList<>();
        intent = new Intent(this, WebviewNewsActivity.class);
        listviewnews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = arrayLink.get(position);
                intent.putExtra("linkURL", link);
                startActivity(intent);
            }
        });

//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo iswifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo is3g = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (iswifi.isConnected()){
//            Toast.makeText(this, "Sử dụng Wifi", Toast.LENGTH_SHORT).show();
//        }else if (is3g.isConnected()){
//            Toast.makeText(this, "Sử dụng 3G", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "Vui lòng kết nối mạng để tiếp tục!", Toast.LENGTH_SHORT).show();
//        }

        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            //getdata
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        URL url = new URL(link);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                        InputStream inputStream = httpURLConnection.getInputStream();

                        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                        xmlPullParserFactory.setNamespaceAware(false);

                        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                        xmlPullParser.setInput(inputStream,"utf-8");

                        int eventType = xmlPullParser.getEventType();
                        Myitem_News myitem_news = null;
                        String strtext = "";
                        while (eventType != xmlPullParser.END_DOCUMENT){
                            String tag = xmlPullParser.getName();
                            switch (eventType){
                                case XmlPullParser.START_TAG:
                                    if (tag.equalsIgnoreCase("item")){
                                        myitem_news = new Myitem_News();
                                    }
                                    break;
                                case XmlPullParser.TEXT:
                                    strtext = xmlPullParser.getText();
                                    break;
                                case XmlPullParser.END_TAG:
                                    if (myitem_news != null){
                                        if (tag.equalsIgnoreCase("title")){
                                            myitem_news.title = strtext;
                                        } else if (tag.equalsIgnoreCase("description")){
                                            myitem_news.description = strtext;
                                        } else if (tag.equalsIgnoreCase("pubDate")){
                                            myitem_news.pubDate = strtext;
                                        } else if (tag.equalsIgnoreCase("link")) {
                                            myitem_news.link = strtext;
                                        }else if (tag.equalsIgnoreCase("item")){
                                            myitem_newsList.add(myitem_news);
                                            arrayList.add(myitem_news.title+"\n"+myitem_news.pubDate);
                                            arrayLink.add(myitem_news.link);
                                        }
                                    }
                                    break;
                            }
                            eventType = xmlPullParser.next();
                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                //hàm gọi hiển thị dữ liệu sau khi kết thúc luồng
                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    Toast.makeText(NewsActivity.this, myitem_newsList.size() + "",Toast.LENGTH_SHORT).show();
                    arrayAdapter = new ArrayAdapter(NewsActivity.this, android.R.layout.simple_list_item_1,arrayList);
                    listviewnews.setAdapter(arrayAdapter);
                }
            };
            asyncTask.execute();
        }else {
            Toast.makeText(this, "Vui lòng kết nối mạng để tiếp tục!", Toast.LENGTH_LONG).show();
        }


    }
}
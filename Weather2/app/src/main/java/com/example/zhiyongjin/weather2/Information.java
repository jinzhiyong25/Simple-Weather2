package com.example.zhiyongjin.weather2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Information extends AppCompatActivity {
    private List<WeatherGson2.DataBean> mList = new ArrayList<WeatherGson2.DataBean>();
    private Gson gson;
    private OkHttpClient okHttpClient;
    private ListView listView;
    private TextView city_location;
    private String UUU = "https://www.tianqiapi.com/api/?version=v1&city=";
    private String cityname;
    private MyListViewAdapter myListViewAdapter;
    private WeatherGson2 weatherGson2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_temp);

        initView();

        try {
            initInternet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1 == 1) {
                city_location.setText(weatherGson2.getCity());
                mList.addAll(weatherGson2.getData());
                myListViewAdapter.notifyDataSetChanged();
            }
        }
    };

    private void initView() {
        listView = findViewById(R.id.listview_future);
        city_location = findViewById(R.id.cityname_tv);
        myListViewAdapter = new MyListViewAdapter(Information.this, mList);
        listView.setAdapter(myListViewAdapter);
    }

    private void initInternet() throws IOException {
        gson = new Gson();
        Intent getIntent = getIntent();
        cityname = getIntent.getStringExtra("cityname");
        String trueUrl = UUU + cityname;
        final Request request = new Request.Builder()
                .url(trueUrl)
                .build();
        okHttpClient = new OkHttpClient();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response;
                    response = okHttpClient.newCall(request).execute();
                    String rs = response.body().string();

//                    Type listType = new TypeToken<List<WeatherGson2.DataBean>>(){}.getType();

                    weatherGson2 = gson.fromJson(rs, WeatherGson2.class);

                    Message message = new Message();
                    message.arg1 = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}








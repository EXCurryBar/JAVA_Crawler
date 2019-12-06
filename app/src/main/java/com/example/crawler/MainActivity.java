package com.example.crawler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public TextView tv;
    public Button btn;
    public static String debugMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);

        final cr c = new cr();
        c.start();
        try{
            c.join();
        }catch (InterruptedException e){
            debugMessage+=e.toString()+"\n";
            tv.setText(debugMessage);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv.setText(debugMessage);
            }
        });
    }
}
class cr extends Thread {
    String r;
    public cr() {}
    public void run() {
        try {
            URL url = new URL("https://tw.news.yahoo.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                if ((r = reader.readLine()) != null) {
                  MainActivity.debugMessage = r;
                }
            } else {
                MainActivity.debugMessage+="伺服器響應代碼為：" + responseCode+"\n";
            }
        } catch (Exception e) {
            System.out.println("獲取不到網頁源碼：" + e);
            MainActivity.debugMessage+=e.toString()+"\n";
        }
    }
}

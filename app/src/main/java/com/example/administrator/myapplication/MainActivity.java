package com.example.administrator.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    Button bt;
    EditText et_text1,et_text2;
    TextView tv_text;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_text1= (EditText) findViewById(R.id.et_text1);
        et_text2= (EditText) findViewById(R.id.et_text2);
        tv_text=(TextView) findViewById(R.id.tv_text);
        bt= (Button) findViewById(R.id.btn);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
               if(msg.what==0x123){
                   et_text2.append("\n"+msg.obj.toString());
               }
            }
        };
        final Runnable urlConnRun=new Runnable() {
            @Override
            public void run() {
                URL url= null;
                try {
                    url = new URL(et_text1.getText().toString());
                    URLConnection con=url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line="";
                    while ((line=br.readLine())!=null){
                        Message message=new Message();
                        message.what=0x123;
                        message.obj=line;
                        handler.sendMessage(message);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        };

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_text2.setText("");
                new Thread(urlConnRun).start();


            }
        });

    }
}

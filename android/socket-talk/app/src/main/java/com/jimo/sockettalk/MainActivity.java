package com.jimo.sockettalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toServer(View v){
        Intent i = new Intent(MainActivity.this,ServerActivity.class);
        startActivity(i);
    }

    public void toClient(View v){
        Intent i = new Intent(MainActivity.this,ClientActivity.class);
        startActivity(i);
    }
}

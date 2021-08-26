package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WecomePage1 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome_page1);
        Button button_welcome = (Button) findViewById(R.id.button_welcome);
        button_welcome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(WecomePage1.this, MainActivity.class);
        startActivity(intent);
    }
}
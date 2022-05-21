package com.example.thegophergame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        startB=findViewById(R.id.button3);
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calls the gopher game
                Intent intent = new Intent(MainActivity.this, contActivity.class);
                startActivity(intent);
            }
        });



    }
}
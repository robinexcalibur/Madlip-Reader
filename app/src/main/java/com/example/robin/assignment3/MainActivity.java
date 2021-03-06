package com.example.robin.assignment3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button) findViewById(R.id.getstarted);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                Class destinationActivity = Reader.class;
                Intent startReader = new Intent(context, destinationActivity);
                startActivity(startReader);
            }
        });
    }
}

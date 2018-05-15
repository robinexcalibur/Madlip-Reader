package com.example.robin.assignment3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView story = (TextView) findViewById(R.id.story);

        Intent intentStory = getIntent();
        if (intentStory.hasExtra(Intent.EXTRA_TEXT)) {
            String allText = intentStory.getStringExtra(Intent.EXTRA_TEXT);
            story.setText(allText);
        }

        Button start = (Button) findViewById(R.id.gohome);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = Display.this;
                Class destinationActivity = MainActivity.class;
                Intent startReader = new Intent(context, destinationActivity);
                startActivity(startReader);
            }
        });
    }
}

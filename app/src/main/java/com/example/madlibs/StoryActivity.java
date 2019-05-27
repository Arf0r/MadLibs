package com.example.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    // Launch the story layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // Get the intent that was given from last activity
        Intent intent = getIntent();
        String receivedText = intent.getStringExtra("text");

        // Set the given text in the textview
        TextView textView = findViewById(R.id.textView5);
        textView.setText(receivedText);
    }

    // If the button is clicked restart GetWordActivity
    public void story(View view) {
        Intent intent = new Intent(this, GetWordActivity.class);
        startActivity(intent);
        finish();

    }
}

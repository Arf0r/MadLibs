package com.example.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        String receivedText = intent.getStringExtra("text");

        TextView textView = findViewById(R.id.textView5);
        textView.setText(receivedText);
    }

    // this event is fired when the button is clicked to go back to the story-pick page
    public void story(View view) {
        Intent intent = new Intent(this, GetWordActivity.class);
        startActivity(intent);

        // make sure to stop the current activity
        finish();

    }
}

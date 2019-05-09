package com.example.madlibs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class GetWordActivity extends AppCompatActivity {
    public Story story;
    public String textFile;
    private int storyID;
    int[] textFiles = {
            R.raw.madlib0_simple,
            R.raw.madlib1_tarzan,
            R.raw.madlib2_university,
            R.raw.madlib3_clothes,
            R.raw.madlib4_dance,
    };
    TextView textView;


    // initialize objects
    RadioButton radioButton, radioButton2, radioButton3, radioButton4, radioButton5;
    RadioGroup radioGroup;
    EditText word;
    KeyListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_word);

        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        word = findViewById(R.id.editText3);

        textView = findViewById(R.id.textView);
        listener = word.getKeyListener();
        word.setKeyListener(null);
    }


    public void checkClicked(View view) {
        if(radioButton.isChecked()) {
            storyID = 0;
        }
        if(radioButton2.isChecked()) {
            storyID = 1;
        }
        if(radioButton3.isChecked()) {
            storyID = 2;
        }
        if(radioButton4.isChecked()) {
            storyID = 3;
        }
        if(radioButton5.isChecked()) {
            storyID = 4;
        }

        InputStream inputStream = getResources().openRawResource(textFiles[storyID]);

        story = new Story(inputStream);

        word.setHint(story.getNextPlaceholder());
        word.setKeyListener(listener);
        int wordsLeft = story.getPlaceholderRemainingCount();
        textView.setText(wordsLeft + "word(s) left");
    }

    public void clickOK(View view) {
        String input = (String) word.getText().toString();
        if ( input.equals("")){
            Toast.makeText(getApplicationContext(), "Enter a word first please!", Toast.LENGTH_SHORT).show();
        }
        else {
            story.fillInPlaceholder(input);
            word.setText("");
            word.setHint(story.getNextPlaceholder());
            int wordsLeft = story.getPlaceholderRemainingCount();
            ((TextView) findViewById(R.id.textView)).setText(wordsLeft + "word(s) left");

            if(story.isFilledIn()){
                Intent StoryActivity = new Intent(this, StoryActivity.class);
                StoryActivity.putExtra("text", story.toString());
                startActivity(StoryActivity);
                finish();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("placeholderword", textView.getText().toString());
        outState.putSerializable("story", story);
        outState.putString("placeholderhint", word.getHint().toString());
        outState.putString("placeholdertext", word.getText().toString());

    }

    // set everything after screen is rotated
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        textView.setText(inState.getString("placeholderword"));
        story = (Story) inState.getSerializable("story");
        word.setText(inState.getString("placeholdertext"));
        word.setHint(inState.getString("placeholderhint"));

    }
}

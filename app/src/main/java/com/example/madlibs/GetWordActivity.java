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
    // Initialize objects
    public Story story;
    private int storyID;
    int[] textFiles = {
            R.raw.madlib0_simple,
            R.raw.madlib1_tarzan,
            R.raw.madlib2_university,
            R.raw.madlib3_clothes,
            R.raw.madlib4_dance,
    };
    TextView textView;
    RadioButton radioButton, radioButton2, radioButton3, radioButton4, radioButton5;
    EditText word;
    KeyListener listener;

    // Launch the right layout page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_word);

        // Find the radio views and edit text
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        word = findViewById(R.id.editText3);
        textView = findViewById(R.id.textView);

        // Set the edit text to unresponsive
        listener = word.getKeyListener();
        word.setKeyListener(null);
    }

    // If a story is chosen, save its ID
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

        // Make a new story object with the correct story
        InputStream inputStream = getResources().openRawResource(textFiles[storyID]);
        story = new Story(inputStream);

        // Set the hint to the next placeholder and make the edit text responsive again
        word.setHint(story.getNextPlaceholder());
        word.setKeyListener(listener);
        int wordsLeft = story.getPlaceholderRemainingCount();
        textView.setText(wordsLeft + "word(s) left");
    }

    // If the OK button is clicked save the string that was given by user
    public void clickOK(View view) {
        String input = (String) word.getText().toString();

        // If no string was given, Toast pop up
        if ( input.equals("")){
            Toast.makeText(getApplicationContext(), "Enter a word first please!", Toast.LENGTH_SHORT).show();
        }

        // Else, save the placeholder, and ask for next word
        else {
            story.fillInPlaceholder(input);
            word.setText("");
            word.setHint(story.getNextPlaceholder());
            int wordsLeft = story.getPlaceholderRemainingCount();
            ((TextView) findViewById(R.id.textView)).setText(wordsLeft + "word(s) left");


            // If the story is completed start Story activity class, and give the text in the intent
            if(story.isFilledIn()){
                Intent StoryActivity = new Intent(this, StoryActivity.class);
                StoryActivity.putExtra("text", story.toString());
                startActivity(StoryActivity);
                finish();
            }
        }
    }

    // Save for variables when device is rotated
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("placeholderword", textView.getText().toString());
        outState.putSerializable("story", story);
        outState.putString("placeholderhint", word.getHint().toString());
        outState.putString("placeholdertext", word.getText().toString());

    }

    // Reset the variables after rotation
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        textView.setText(inState.getString("placeholderword"));
        story = (Story) inState.getSerializable("story");
        word.setText(inState.getString("placeholdertext"));
        word.setHint(inState.getString("placeholderhint"));

    }
}

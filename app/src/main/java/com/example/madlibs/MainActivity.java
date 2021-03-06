package com.example.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    // Launch the first page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Check if submit button is being clicked, if so, go to GetWotdActivity class
    public void checkClicked(View view) {
        Button button = (Button) view;
        startActivity(new Intent(MainActivity.this, GetWordActivity.class));
    }
}

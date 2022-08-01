package com.example.simpleasynctask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // variable for textview
    private TextView textview;

    // when orientation change the resources are destroyed and
    // new resources created due that Async task no longer
    // update the string of textview as new object of textview is created
    // so save it's state
    private final String TEXT_STATE = "current text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.textview);            // reference the textview

        // check the bundle if not null then pass the saved string
        if(savedInstanceState != null) {
            textview.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    @Override       // saving the state of textview
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE,textview.getText().toString());
    }

    public void startTask(View view) {
        textview.setText(R.string.napping);
        new SimpleAsyncTask(textview).execute();
    }
}
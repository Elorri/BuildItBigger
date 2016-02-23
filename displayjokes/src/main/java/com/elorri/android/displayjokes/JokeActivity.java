package com.elorri.android.displayjokes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Elorri on 23/02/2016.
 */
public class JokeActivity extends ActionBarActivity {

    public static String JOKE_KEY = "Joke key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
    }

}

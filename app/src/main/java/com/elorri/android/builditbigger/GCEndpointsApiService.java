package com.elorri.android.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.elorri.android.builditbigger.backend.jokeApi.JokeApi;
import com.elorri.android.displayjokes.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


/**
 * Created by Elorri on 24/02/2016.
 */
class GCEndpointsApiService extends AsyncTask<Void, Void, String> {


    private Activity activity;
    private JokeApi myApiService = null;
    private GCEndpointsApiServiceListener mListener;
    private ProgressBar mSpinner = null;


    public GCEndpointsApiService(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        mSpinner=(ProgressBar)activity.findViewById(R.id.progressBar);
        mSpinner.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new
                    AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-1231.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

       // activity = params[0];

        try {
            return myApiService.tellaJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mSpinner.setVisibility(View.GONE);

        if (mListener != null) { //means task has been called from the android test
            mListener.onCompleted(result);
        } else {
            //means task has been called from the main activity
            Intent intent = new Intent(activity, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY, result);
            activity.startActivity(intent);// Calling this in android test causes runtime exception
        }
    }


    //This interface is needed for testing, it will allow the test to know the result of the
    // AsyncTask
    public interface GCEndpointsApiServiceListener {
        void onCompleted(String joke);
    }

    //This method will only be used for testing purpose
    public void setListener(GCEndpointsApiServiceListener mListener) {
        this.mListener = mListener;
    }
}

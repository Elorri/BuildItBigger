package com.elorri.android.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.elorri.android.builditbigger.backend.jokeApi.JokeApi;
import com.elorri.android.displayjokes.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


/**
 * Created by Elorri on 24/02/2016.
 */
class GCEndpointsApiService extends AsyncTask<Context, Void, String> {



    private JokeApi myApiService = null;



    private GCEndpointsApiServiceListener mListener;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if (myApiService == null) {  // Only do this once
//            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    // options for running against local devappserver
//                    // - 10.0.2.2 is localhost's IP address in Android emulator
//                    // - turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
//            // end options for devappserver
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new
                    AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-1231.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellaJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mListener != null) { //means task has been called from the android test
            mListener.onCompleted(result);
        } else {
            //means task has been called from the main activity
            Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY, result);
            context.startActivity(intent);// Calling this in android test causes runtime exception
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

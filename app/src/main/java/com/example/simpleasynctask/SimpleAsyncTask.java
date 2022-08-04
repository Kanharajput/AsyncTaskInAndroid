package com.example.simpleasynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

// first parameter is for doInBackground, second is for onProgressUpdate and last one is for onPostExecute
public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    // it will pass in garbage if orientation change directly
    // if we pass it in the constructor of member variable of AsyncTask it will not remove
    private WeakReference<TextView> mtextview;
    private ProgressBar mprogressBar;

    public SimpleAsyncTask(TextView tv, ProgressBar progressBar){
        mtextview = new WeakReference<>(tv);
        mprogressBar = progressBar;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random rd = new Random();
        int valuefrom0to10 = rd.nextInt(11);         // return random value between 0 to <11
        int total_time_to_sleep =  valuefrom0to10*200;            // multiply it by 200 to increase time as it in milliseconds

        // divide total time by 100 so we get 1% of it
        // now if we run the loop for 100 times definitely we will reach to the total time
        // at each iteration pass count of iteration like 2 times loop ran already means two percent work is done
        int approx_one_percent = total_time_to_sleep/100;

        for(int time_chunks = approx_one_percent; time_chunks<=total_time_to_sleep; time_chunks+=time_chunks) {
            try {
                // this is the not ui thread
                publishProgress(time_chunks);        // send the of sleep to onProgressUpdate
                Thread.sleep(approx_one_percent);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Awake at last after sleeping for" + total_time_to_sleep + "milliseconds";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int value = values[0];
        mprogressBar.setProgress(value);
    }

    @Override    // this result string is passed by doInBackground method
    protected void onPostExecute(String result) {
        mtextview.get().setText(result);
    }
}

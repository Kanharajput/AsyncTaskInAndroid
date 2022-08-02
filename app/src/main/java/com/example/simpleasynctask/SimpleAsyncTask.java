package com.example.simpleasynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

// first parameter is for doInBackground, second is for onProgressUpdate and last one is for onPostExecute
public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    // it will pass in garbage if orientation change directly
    // if we pass it in the constructor of member variable of AsyncTask it will not remove
    private WeakReference<TextView> mtextview;
    private Context context;

    public SimpleAsyncTask(Context context, TextView tv){
        mtextview = new WeakReference<>(tv);
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random rd = new Random();
        int valuefrom0to10 = rd.nextInt(11);         // return random value between 0 to <11
        int time_to_sleep =  valuefrom0to10*200;            // multiply it by 200 to increase time as it in milliseconds

        // sleep it using the try catch block
        try {
             publishProgress(time_to_sleep);        // send the of sleep to onProgressUpdate
             Thread.sleep(time_to_sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Awake at last after sleeping for" + time_to_sleep + "milliseconds";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Toast toast = new Toast(context);          //  create the toast
        int duration = values[0];
        toast.setText("showing toast for duration "+ Integer.toString(duration));
        toast.setDuration(duration);
        toast.show();
    }

    @Override    // this result string is passed by doInBackground method
    protected void onPostExecute(String result) {
        mtextview.get().setText(result);
    }
}

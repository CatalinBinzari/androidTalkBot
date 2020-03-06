package com.example.botver2;

import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;




class RetrieveFeedTask extends AsyncTask<String, String, String> {

    private Exception exception;
    private volatile String reply = null;
    protected String doInBackground(String... URL) {

        try {
            URL url = new URL(URL[0]);
            URLConnection connection;
            connection = url.openConnection();
            HttpURLConnection httppost = (HttpURLConnection) connection;
            httppost.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            reply = in.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reply;

    }
    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

}

package com.teknos.matias.carritoappad.AsyncTask;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebConection extends AsyncTask<String, Void, String> {
    private String link="https://puntjif.com/teknos/gateway.php?sql=";
    private HttpURLConnection httpURLConnection=null;
    private URL url =null;

    @Override
    protected String doInBackground(String... strings) {
        try {
            url=new URL(link+strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int code=httpURLConnection.getResponseCode();
            if(code == httpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream((httpURLConnection.getInputStream()));
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line="";
                StringBuffer buffer = new StringBuffer();
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                return buffer.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package teamb.cs262.calvin.edu.quest;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class LocationPoster extends AsyncTask<URL, Void, JSONArray> {

    private JSONObject jsonObject;

    public LocationPoster(JSONObject jsonObject) {
        super();
        this.jsonObject = jsonObject;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected JSONArray doInBackground(URL... params) {
        HttpURLConnection connection = null;
        String jsonString = null;
        JSONArray result = null;
        JSONObject jsonData = jsonObject;

        try {
            URL requestURL = params[0];
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String userpass = "postgres:Quest";
            byte[] login = Base64.getEncoder().encode(userpass.getBytes());
            String basicAuth = "Basic " + new String(login);
            connection.setRequestProperty("Authorization", basicAuth);
            connection.connect();
            //jsonString = getResponseFromConnection(connection);
            Log.d("Response",jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private static String getResponseFromConnection(URLConnection urlConnection) {
        String result = null;
        BufferedReader reader = null;
        try {
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
   /* Since it's JSON, adding a newline isn't necessary (it won't affect
      parsing) but it does make debugging a *lot* easier if you print out the
      completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            result = buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
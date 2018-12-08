package teamb.cs262.calvin.edu.quest;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationPoster extends AsyncTask<URL, Void, JSONArray> {

    private JSONObject jsonObject;

    public LocationPoster(JSONObject jsonObject) {
        super();
        this.jsonObject = jsonObject;
    }

    @Override
    protected JSONArray doInBackground(URL... params) {
        HttpURLConnection connection = null;
        StringBuilder jsonText = new StringBuilder();
        JSONArray result = null;
        try {
            // Open the connection as usual.
            JSONObject jsonData = jsonObject;
            connection = (HttpURLConnection) params[0].openConnection();
            // Configure the connection for a POST, including outputing streamed JSON data.
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setFixedLengthStreamingMode(jsonData.toString().length());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(jsonData.toString());
            out.flush();
            out.close();
            // Handle the response from the (Lab09) server as usual.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonText.append(line);
                }
                //Log.d(TAG, jsonText.toString());
                if (jsonText.charAt(0) == '[') {
                    result = new JSONArray(jsonText.toString());
                } else if (jsonText.toString().equals("null")) {
                    result = new JSONArray();
                } else {
                    result = new JSONArray().put(new JSONObject(jsonText.toString()));
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
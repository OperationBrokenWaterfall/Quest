package teamb.cs262.calvin.edu.quest;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;


public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL =  "https://cs262-teamb-fall2018.appspot.com/questapp/v1/game"; // Base URI for the Google Cloud API


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    static String getDatabaseJSON(String queryString){

            String jsonString = getJSONStringFromURI(BASE_URL, queryString);
            Log.d(LOG_TAG, jsonString);
            return jsonString;


    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getJSONStringFromURI(String uri, String queryString) {
        HttpURLConnection urlConnection = null;

        String jsonString = null;
        try {
            Uri builtURI = Uri.parse(uri).buildUpon()
                    .build();
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            String userpass = "postgres:Quest";
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.connect();
            jsonString = getResponseFromConnection(urlConnection);
            Log.d("Response",jsonString);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        JSONObject result = null;
        try {
            JSONObject json = new JSONObject();
            json.put("team", "Team A");
            json.put("score", 10);
            JSONObject json2 = new JSONObject();
            json2.put("team", "Team B");
            json2.put("score", 2);
            JSONObject json3 = new JSONObject();
            json3.put("team", "Team C");
            json3.put("score", 7);
            JSONObject json4 = new JSONObject();
            json4.put("team", "Team D");
            json4.put("score", 5);
            JSONArray items = new JSONArray();
            items.put(json);
            items.put(json2);
            items.put(json3);
            items.put(json4);
            result = new JSONObject().put("items", items);

        } catch(JSONException jsonException) {
            jsonException.printStackTrace();
            result = new JSONObject();
        }
        jsonString = result.toString();
        return jsonString;
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
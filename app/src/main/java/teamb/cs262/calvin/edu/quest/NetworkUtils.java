package teamb.cs262.calvin.edu.quest;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static final String BASE_URL =  "https://cs262-teamb-fall2018.appspot.com/questapp/v1/game"; // Base URI for the Google Cloud API


    private static class PostPlayerTask extends AsyncTask<JSONObject, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(JSONObject... params) {
            HttpURLConnection connection = null;
            StringBuilder jsonText = new StringBuilder();
            JSONArray result = null;
            try {
                // Open the connection as usual.
                JSONObject jsonData = params[0];
                connection = (HttpURLConnection) new URL(BASE_URL).openConnection();
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

    static JSONArray postDataToServer(JSONObject jsonObject) {
        return new PostPlayerTask().doInBackground(jsonObject);
    }

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
        return createJSONResultFromServerResponse(jsonString);
    }

    private static String createJSONResultFromServerResponse(String jsonString) {
        JSONObject result = null;
        try {
            result = new JSONObject(jsonString);
            JSONArray itemsFromServer = result.getJSONArray("items");
            JSONArray resultArray = new JSONArray();
            List<String> teams = getTeamsFromServerArray(itemsFromServer);
            for(int i = 0; i < teams.size(); i++) {
                String teamName = teams.get(i);
                int score = calculateScoreFromServerArray(teamName, itemsFromServer);
                JSONObject jsonObject = createTeamScoreJSONObject(teamName, score);
                resultArray.put(jsonObject);
            }

            result = new JSONObject().put("items", resultArray);

        } catch(JSONException jsonException) {
            jsonException.printStackTrace();
            result = new JSONObject();
        }
        return result.toString();
    }


    private static List<String> getTeamsFromServerArray(JSONArray array) throws JSONException {
        List<String> teams = new ArrayList<String>();
        for(int i = 0; i < array.length(); i++) {
            JSONObject entry = array.getJSONObject(i);
            String teamName = entry.getString("name");
            if(!teams.contains(teamName)) {
                teams.add(teamName);
            }
        }
        Log.d("Teams in Game", Arrays.toString(teams.toArray()));
        return teams;
    }

    private static int calculateScoreFromServerArray(String name, JSONArray array) throws JSONException {
        int score = 0;
        List<String> locations = new ArrayList<String>();
        for(int i = 0; i < array.length(); i++) {
            JSONObject entry = array.getJSONObject(i);
            if(entry.getString("name").equals(name)) {
                if(entry.has("location")) {
                    String location = entry.getString("location");
                    if(!locations.contains(location)) {
                        locations.add(location);
                        score++;
                    }
                }
            }
        }
        Log.d("Score for " + name, String.valueOf(score));
        return score;
    }

    private static JSONObject createTeamScoreJSONObject(String name, int score) throws JSONException {
        return new JSONObject().put("team", name).put("score", score);
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
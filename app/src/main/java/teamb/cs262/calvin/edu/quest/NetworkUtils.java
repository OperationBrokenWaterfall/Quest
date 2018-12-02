package teamb.cs262.calvin.edu.quest;

import android.net.Uri;
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

// Database Access Object


public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?"; // Base URI for the Books API
    private static final String QUERY_PARAM = "q"; // Parameter for the search string
    private static final String MAX_RESULTS = "maxResults"; // Parameter that limits search results
    private static final String PRINT_TYPE = "printType";   // Parameter to filter by print type


    static String getBookInfo(String queryString){

            String bookJSONString = getJSONStringFromURI(BOOK_BASE_URL, queryString);
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;


    }

    static String getJSONStringFromURI(String uri, String queryString) {
        HttpURLConnection urlConnection = null;

        String bookJSONString = null;
        try {
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            bookJSONString = getResponseFromConnection(urlConnection);
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
            json.put("team", "TeamB");
            json.put("score", 4);
            JSONObject json2 = new JSONObject();
            json2.put("team", "TeamC");
            json2.put("score", 3);
            JSONArray items = new JSONArray();
            items.put(json);
            items.put(json2);
            result = new JSONObject().put("items", items);

        } catch(JSONException jsonException) {
            jsonException.printStackTrace();
            result = new JSONObject();
        }
        bookJSONString = result.toString();
        return bookJSONString;
    }

    static String getResponseFromConnection(URLConnection urlConnection) {
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



















//package teamb.cs262.calvin.edu.quest;
//
//import android.net.Uri;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Base64;
//
//public class NetworkUtils {
//
//    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
//
//    private static final String BOOK_BASE_URL =  "http://cs262-teamb-fall2018.appspot.com/Quest/v1/Game"; // Base URI for the Books API
//    private static final String QUERY_PARAM = "q"; // Parameter for the search string
//    private static final String MAX_RESULTS = "maxResults"; // Parameter that limits search results
//    private static final String PRINT_TYPE = "printType";   // Parameter to filter by print type
//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    static String getBookInfo(String queryString){
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//        String bookJSONString = null;
//        try {
//            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
//                    .build();
//            URL requestURL = new URL(builtURI.toString());
//            urlConnection = (HttpURLConnection) requestURL.openConnection();
//            urlConnection.setRequestMethod("GET");
//            String userpass = "postgres:Quest";
//            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
//            urlConnection.setRequestProperty("Authorization", basicAuth);
//            urlConnection.connect();
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                return null;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//   /* Since it's JSON, adding a newline isn't necessary (it won't affect
//      parsing) but it does make debugging a *lot* easier if you print out the
//      completed buffer for debugging. */
//                buffer.append(line + "\n");
//            }
//            if (buffer.length() == 0) {
//                // Stream was empty.  No point in parsing.
//                return null;
//            }
//            bookJSONString = buffer.toString();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println(bookJSONString);
//            return bookJSONString;
//        }
//
//    }
//}

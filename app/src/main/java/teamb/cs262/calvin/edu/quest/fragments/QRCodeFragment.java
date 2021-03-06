package teamb.cs262.calvin.edu.quest.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;

import teamb.cs262.calvin.edu.quest.LocationPoster;
import teamb.cs262.calvin.edu.quest.NetworkUtils;
import teamb.cs262.calvin.edu.quest.R;
import teamb.cs262.calvin.edu.quest.TeamsActivity;

import static teamb.cs262.calvin.edu.quest.fragments.TaskListFragment.locationCodes;


/**
 * QRCodeFragment is a singleton
 * To get an instance of this Fragment call QRCodeFragment.getInstance()
 * rather than new QRCodeFragment. This ensures there are duplicated Fragments
 */
public class QRCodeFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

    private QRCodeReaderView qrCodeReaderView;
    private BottomNavigationView nav;
    private final int CAMERA_PERMISSION_REQUEST = 7;
    public static String qrText;
    public static int globPosition;
    ImageView imageView;
    private static QRCodeFragment instance; // singleton instance

    @SuppressLint("ValidFragment")
    private QRCodeFragment() {
        super();
    }

    /**
     * This returns the singleton QRCodeFragment instance
     * @return QRCodeFragment instance
     */
    public static QRCodeFragment getInstance() {
        if(instance == null) {
            instance = newInstance();
        }
        return instance;
    }

    /**
     * Creates a new QRCodeFragment instance
     * @return fragment
     */
    private static QRCodeFragment newInstance() {
        QRCodeFragment fragment = new QRCodeFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nav = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            Log.d("Permissions", "Not Granted Yet");
        } else {
            Log.d("Permissions", "Already Granted");
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("Permissions", "Permission Result Called");
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            //Refresh Fragment
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(getContext(), "You denied access to the Camera. Please reload the camera and try again.", Toast.LENGTH_LONG).show();

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qrcode, container, false);
        // Inflate the layout for this fragment
        qrCodeReaderView = (QRCodeReaderView) rootView.findViewById(R.id.qrdecoderview);
        try {
            launchCamera();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void launchCamera() {
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();

        qrCodeReaderView.startCamera();

    }



    /**
     * Called when a QR is decoded
     * Operations:
     *      If the scanned QR code is valid
     *          Post the location visit to the database
     *      Else
     *          Do nothing
     *      Send QR code text to the Task List so it can disable the appropriate image
     *      Switch to either the Task List or the Leaderboard
     *
     * @param text : the text encoded in QR code
     * @param points : points where QR control points are placed in View
     */
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                postToDatabase(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendDataToTaskList(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void postToDatabase(String locationText) throws JSONException, MalformedURLException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", TeamsActivity.TEAM_NAME).put("location", locationText);
        //new LocationPoster(jsonObject).execute(new URL(NetworkUtils.BASE_URL)).getStatus();

        NetworkUtils.postDataToServer(jsonObject);
    }

    private void sendDataToTaskList(String text) {
        Fragment fragment = TaskListFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putString("QR", text);
        fragment.setArguments(bundle);

        qrText = text;

        try {
            globPosition = locationCodes.get(text);
            locationCodes.remove(text);
        } catch (Exception e) {
            bundle.putString("cheater", "confirmed");
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        nav.setSelectedItemId(R.id.task_list_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }
}


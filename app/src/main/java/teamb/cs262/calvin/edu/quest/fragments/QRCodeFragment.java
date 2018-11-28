package teamb.cs262.calvin.edu.quest.fragments;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import teamb.cs262.calvin.edu.quest.R;



/**
 * QRCodeFragment is a singleton
 * To get an instance of this Fragment call QRCodeFragment.getInstance()
 * rather than new QRCodeFragment. This ensures there are duplicated Fragments
 */
public class QRCodeFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;

    private BottomNavigationView nav;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qrcode, container, false);
        // Inflate the layout for this fragment
        qrCodeReaderView = (QRCodeReaderView) rootView.findViewById(R.id.qrdecoderview);
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
        return rootView;
    }


    /**
     * Called when a QR is decoded
     *
     * @param text : the text encoded in QR code
     * @param points : points where QR control points are placed in View
     */
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Fragment fragment = LeaderBoardFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putString("QR", text);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        nav.setSelectedItemId(R.id.leaderboard_fragment);
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


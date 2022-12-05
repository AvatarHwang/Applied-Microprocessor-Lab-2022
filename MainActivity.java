package com.example.ocvtest7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.ocvtest7.databinding.ActivityMainBinding;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OcvTest2";
    private JavaCameraView mJavaCameraView;
    private Mat mRgba;

    // Used to load the 'ocvtest2' library on application startup.
    static {
        System.loadLibrary("ocvtest7");

        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCVLoader successfully loaded!");
        } else {
            Log.e(TAG, "Error: OpenCVLoader load failed!");
        }
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(PERMISSIONS)) {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
        mJavaCameraView = (JavaCameraView) findViewById(R.id.java_camera_view);
        if (mJavaCameraView != null) {
            mJavaCameraView.setVisibility(SurfaceView.VISIBLE);
            mJavaCameraView.setCvCameraViewListener(this);
        } else {
            Log.e(TAG, "onCreate: mJavaCameraView is null!");
        }
    }
    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.d(TAG, "onCameraViewStarted: width = " + width + ", height = " + height);
        mRgba = new Mat();
    }
    @Override
    public void onCameraViewStopped() {
        Log.d(TAG, "onCameraViewStopped:");
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        Core.flip(mRgba, mRgba, 1);
        nativeDetectAndDisplay(mRgba.getNativeObjAddr());
        return mRgba;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mJavaCameraView != null)
            mJavaCameraView.disableView();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mJavaCameraView != null)
            mJavaCameraView.disableView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mJavaCameraView.enableView();
    }
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.CAMERA"};
    private boolean hasPermissions(String[] permissions) {
        int result;
        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED){
                return false;
            }
        }
        return true;
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void nativeDetectAndDisplay(long addrMat);
}
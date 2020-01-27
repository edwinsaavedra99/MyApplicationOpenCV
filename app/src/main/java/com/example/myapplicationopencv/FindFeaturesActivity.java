package com.example.myapplicationopencv;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class FindFeaturesActivity extends AppCompatActivity  implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "FindFeaturesActivity";
    private JavaCamera2View javaCameraView;
    private ObjectDetection odObjectDetection;
    private BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this){
        @Override
        public void onManagerConnected(int status) throws IOException {

            switch(status){
                case BaseLoaderCallback.SUCCESS:{

                    javaCameraView.enableView();
                    odObjectDetection = new ObjectDetection();

                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };
    private Mat mRgba, mRgbf, mRgbt;

    static {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_features);
        javaCameraView = (JavaCamera2View)findViewById(R.id.javaCameraView);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(javaCameraView!=null)
            javaCameraView.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(javaCameraView!=null)
            javaCameraView.disableView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV cargado exitosamente");
            try {
                mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "OpenCV no carga");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,mLoaderCallBack);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_32FC4);
        mRgbf = new Mat(height, width, CvType.CV_8UC4);
        mRgbt = new Mat(width, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();

        odObjectDetection.DetectAndDisplay(mRgba);
        // Rotate mRgba 90 degrees
        //Core.transpose(mRgba, mRgbt);
        //Imgproc.resize(mRgbt, mRgbf, mRgbf.size(), 0,0, 0);
        //Core.flip(mRgbf, mRgba, 1 );

        return mRgba;
    }

}

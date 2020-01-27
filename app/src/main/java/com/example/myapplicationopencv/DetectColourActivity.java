package com.example.myapplicationopencv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DetectColourActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    static {
        if(!OpenCVLoader.initDebug()){
            Log.d("TAG","OpenCV not loaded");
        }else{
            Log.d("TAG","OpenCV loaded");
        }
    }

    int iLowH=45;
    int iHighH=75;
    int iLowS = 20;
    int iHighS =255;
    int iLowV = 10;
    int iHighV = 255;
    Mat imgHSV, imgThresholded;
    Scalar sc1,sc2;
    JavaCameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detect_colour);
        sc1 = new Scalar(iLowH,iLowS,iLowV);
        sc2 = new Scalar(iHighH,iHighS,iHighV);
        cameraView = (JavaCameraView) findViewById(R.id.cameraview1);
        cameraView.setCameraIndex(0);
        cameraView.setCvCameraViewListener(this);
        cameraView.enableView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.detectBordes:
                onPause();
                Intent intent0 = new Intent(this,MainActivity.class);
                startActivity(intent0);
                return true;
            case R.id.detectColour:
                onPause();
                Intent intent = new Intent(this, DetectColourActivity.class);
                startActivity(intent);
                return true;
            case R.id.detectBlod:
                onPause();
                Intent intent2 = new Intent(this, BaseColourDetectionActivity.class);
                startActivity(intent2);
                return true;
            case R.id.detectFase:
                onPause();
                Intent intent3 = new Intent(this, FdActivity.class);
                startActivity(intent3);
                return true;
            case R.id.FindFeatures:
                onPause();
                Intent intent5 = new Intent(this, FindFeaturesActivity.class);
                startActivity(intent5);
                return true;
            case R.id.Posterize:
                onPause();
                Intent intent4 = new Intent(this, PosterizeActivity.class);
                startActivity(intent4);
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        imgHSV = new Mat(width,height, CvType.CV_16UC4);
        imgThresholded = new Mat(width,height, CvType.CV_16UC4);

    }
    @Override
    public void onCameraViewStopped() {

    }
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Imgproc.cvtColor(inputFrame.rgba(),imgHSV,Imgproc.COLOR_BGR2HSV);
        Core.inRange(imgHSV,sc1,sc2,imgThresholded);
        return imgThresholded;
    }
}

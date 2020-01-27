package com.example.myapplicationopencv;

import android.content.Intent;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2  {
    private CameraBridgeViewBase cameraView = null;
    private static boolean initOpenCV = false;

    static { initOpenCV = OpenCVLoader.initDebug(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = (CameraBridgeViewBase) findViewById(R.id.cameraview);
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCvCameraViewListener(this);
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
    protected void onResume() {
        super.onResume();
        if (initOpenCV) { cameraView.enableView(); }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Release the camera.
        if (cameraView != null) {
            cameraView.disableView();
            cameraView = null;
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat src = inputFrame.gray(); // convertir a escala de grises
        Mat cannyEdges = new Mat();  // objeto para almacenar el resultado

        // aplicar el algoritmo canny para detectar los bordes
        Imgproc.Canny(src, cannyEdges, 10, 100);

        // devolver el objeto Mat procesado
        return cannyEdges;
    }
}
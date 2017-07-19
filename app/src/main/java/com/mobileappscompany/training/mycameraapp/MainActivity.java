package com.mobileappscompany.training.mycameraapp;

import java.io.IOException;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback{
    /** Called when the activity is first created. */
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        camera = Camera.open();
        if(camera!=null){

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (IOException e) {
                Toast.makeText(this, (CharSequence) e, Toast.LENGTH_LONG).show();
            }

        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }
}

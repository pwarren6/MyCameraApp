package com.mobileappscompany.training.mycameraapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    SurfaceView cameraView, transparentView;
    SurfaceHolder holder, holderTransparent;
    Camera mCamera;
    private float RectLeft, RectTop, RectRight, RectBottom;
    int deviceHeight, deviceWidth;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = (SurfaceView)findViewById(R.id.cameraView);
        holder = cameraView.getHolder();
        holder.addCallback((SurfaceHolder.Callback)this);
        cameraView.setSecure(true);

        transparentView = (SurfaceView)findViewById(R.id.transparentView);
        holderTransparent = transparentView.getHolder();
        holderTransparent.addCallback((SurfaceHolder.Callback)this);
        transparentView.setZOrderMediaOverlay(true);

        deviceWidth = getScreenWidth();
        deviceHeight = getScreenHeight();
    }

    public static int getScreenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void Draw(){
        Canvas canvas = holderTransparent.lockCanvas(null);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        RectLeft = 1;
        RectTop = 200;
        RectRight = RectLeft + deviceWidth-100;
        RectBottom = RectTop + 200;
        Rect rec = new Rect((int)RectLeft,(int)RectTop,(int)RectRight,(int)RectBottom);
        canvas.drawRect(rec, paint);
        holderTransparent.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            synchronized(holder)
            {Draw();}
            mCamera = Camera.open();
        }catch(Exception e){
            Log.i("Exception", e.toString());
            return;
        }
        Camera.Parameters param;
        param = mCamera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getRotation() == Surface.ROTATION_0){
            mCamera.setDisplayOrientation(90);
        }
        mCamera.setParameters(param);
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }catch(Exception e){
            return;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(camIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }
    public void refreshCamera(){
        if(holder.getSurface() == null){
            return;
        }
        try{
            mCamera.stopPreview();
        }catch(Exception e){
            return;
        }
        try{
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }catch(Exception e){
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }
}

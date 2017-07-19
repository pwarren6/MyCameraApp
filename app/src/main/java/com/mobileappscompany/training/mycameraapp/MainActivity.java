package com.mobileappscompany.training.mycameraapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

    final int CAMERA_CAPTURE = 1;
    private Uri picUri;
    final int PIC_CROP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button captureBtn = (Button)findViewById(R.id.capture_btn);
        captureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.capture_btn){
            try{
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            }
            catch(ActivityNotFoundException anfe){
                String errorMessage = "Sorry, but your device doesn't support capturing images.";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_CAPTURE){
                picUri = data.getData();
                performCrop();
            }
        }else if (requestCode == PIC_CROP){
            Bundle extras = data.getExtras();
            Bitmap thePic = extras.getParcelable("data");
            ImageView picView = (ImageView)findViewById(R.id.picture);
            picView.setImageBitmap(thePic);
        }
    }

    private void performCrop(){
        try{
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate desired crop aspects
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //indicate X and Y output
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        }catch(ActivityNotFoundException anfe){
            String errorMessage = "Sorry, your device doesn't support the crop action.";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

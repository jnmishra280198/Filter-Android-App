package com.example.user.filter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;

import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.user.filter.utility.Helper;
import com.example.user.filter.utility.TransformImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;


import java.util.ResourceBundle;

public class ControlActivity extends AppCompatActivity {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
    Toolbar mcontrolToolBar;
    ImageView mtickImageView;
    ImageView mcenterImageView;
    ImageView mfirstfilterpreviewImageView;
    ImageView msecondfilterpreviewimageView;
    ImageView mthirdfilterpreviewimageView;
    TransformImage mTransformImage;
    int mscrenWidth;
    int mscrenHeight;
    int mCurrentFilter;
    SeekBar mSeekBar;
    Uri mSelectedImageUri;
    ImageView mTickImageView;
    ImageView cancelImageView;
    Target mapplySingleFilter=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFiltervalue=mSeekBar.getProgress();
            if(mCurrentFilter==TransformImage.FILTER_BRIGHTNESS)
            {
              mTransformImage.applyBrightnessSubFilter(currentFiltervalue);

              Helper.writeDataIntoExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_BRIGHTNESS),mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
                Picasso.get().invalidate(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_BRIGHTNESS)));
                Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mscrenHeight/2).into(mcenterImageView);
            }
            else if (mCurrentFilter==TransformImage.DEFAULT_SATURATION)
            {
                mTransformImage.applySaturationSubFilter(currentFiltervalue);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_SATURATION),mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));
                Picasso.get().invalidate(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_SATURATION)));
                Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_SATURATION))).resize(0,mscrenHeight/2).into(mcenterImageView);
            }
            else if(mCurrentFilter==TransformImage.DEFAULT_CONTRAST)
            {
                mTransformImage.applyContrastSubFilter(currentFiltervalue);
                Helper.writeDataIntoExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_CONTRAST),mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
                Picasso.get().invalidate(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_CONTRAST)));
                Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_CONTRAST))).resize(0,mscrenHeight/2).into(mcenterImageView);
            }


        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    Target msmallTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

             mTransformImage=new TransformImage(ControlActivity.this,bitmap);
            mTransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);




            Helper.writeDataIntoExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_BRIGHTNESS),mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
            Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_BRIGHTNESS))).fit().centerInside().into(mfirstfilterpreviewImageView);

            mTransformImage.applySaturationSubFilter(TransformImage.DEFAULT_SATURATION);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_SATURATION),mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));
            Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_SATURATION))).fit().centerInside().into(msecondfilterpreviewimageView);

            mTransformImage.applyContrastSubFilter(TransformImage.DEFAULT_CONTRAST);
            Helper.writeDataIntoExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_CONTRAST),mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
            Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_CONTRAST))).fit().centerInside().into(mthirdfilterpreviewimageView);




        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    final static int PICK_IMAGE=2;
    private static final String TAG= ControlActivity.class.getSimpleName();
    final static int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        mcontrolToolBar=(Toolbar)findViewById(R.id.toolbar);
        mcenterImageView=(ImageView)findViewById(R.id.centerimageView);
        mcontrolToolBar.setTitle(getString(R.string.app_name));
        mcontrolToolBar.setNavigationIcon(R.drawable.icon);
        mSeekBar=(SeekBar)findViewById(R.id.seekBar);
      mcontrolToolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
      mtickImageView=(ImageView)findViewById(R.id.imageView3);
      mfirstfilterpreviewImageView =(ImageView)findViewById(R.id.imageView7);
      msecondfilterpreviewimageView =(ImageView)findViewById(R.id.imageView6);
      mthirdfilterpreviewimageView =(ImageView)findViewById(R.id.imageView5);
      mtickImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new     Intent(ControlActivity.this,ImagePreviewActivity.class);
              startActivity(intent);
          }
      });
      mcenterImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this , Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {

                }

              else
                  {
                      ActivityCompat.requestPermissions(ControlActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION);
                  }
                  return;
              }
              Intent intent = new Intent();
              intent.setType("image/*");
              intent.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
          }
      });
      mfirstfilterpreviewImageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mSeekBar.setMax(TransformImage.MAX_BRIGHTNESS);
              mSeekBar.setProgress(TransformImage.DEFAULT_BRIGHTNESS);
              mCurrentFilter=TransformImage.FILTER_BRIGHTNESS;
              Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mscrenHeight/2).into(mcenterImageView);

          }
      });
      msecondfilterpreviewimageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mSeekBar.setMax(TransformImage.MAX_SATURATION);
              mSeekBar.setProgress(TransformImage.DEFAULT_SATURATION);
              mCurrentFilter=TransformImage.FILTER_SATURATION;
              Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_SATURATION))).resize(0,mscrenHeight/2).into(mcenterImageView);

          }
      });
        mthirdfilterpreviewimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekBar.setMax(TransformImage.MAX_CONTRAST);
                mSeekBar.setProgress(TransformImage.DEFAULT_CONTRAST);
                mCurrentFilter=TransformImage.FILTER_CONTRAST;
                Picasso.get().load(Helper.GetFileFromExternalStorage(ControlActivity.this,mTransformImage.getfilename(TransformImage.FILTER_CONTRAST))).resize(0,mscrenHeight/2).into(mcenterImageView);


            }
        });
        mtickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(mSelectedImageUri).into(mapplySingleFilter);
            }
        });
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mscrenHeight= displayMetrics.heightPixels;
        mscrenWidth=displayMetrics.widthPixels;



    }

    public void onRequestPermissionsResult(int requestcode, String permissions[] ,int [] grantResults)
    {
        switch(requestcode){
            case MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {

                    Log.d(TAG,"permission denied");
                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PICK_IMAGE && resultCode== Activity.RESULT_OK){
         mSelectedImageUri=data.getData();
        Picasso.get().load(mSelectedImageUri).fit().into(mcenterImageView);
            Picasso.get().load(mSelectedImageUri).into(msmallTarget);




        }
    }

}

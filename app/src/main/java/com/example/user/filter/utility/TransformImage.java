package com.example.user.filter.utility;

import android.content.Context;
import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;

public class TransformImage {
    public static final int MAX_BRIGHTNESS=100;
    public static final int MAX_CONTRAST=100;
    public static final int MAX_SATURATION=5;


    public static final int DEFAULT_BRIGHTNESS=70;
    public static final int DEFAULT_SATURATION=60;
    public static final int DEFAULT_CONTRAST=5;

    private String  mfilename;
    private Bitmap  mbitmap;
    private Context mcontext;

    private Bitmap  brightnessFilteredBitmap;
    private Bitmap  saturationFilteredBitmap;
    private Bitmap  contrastFilteredBitmap;

    public static  int FILTER_BRIGHTNESS=0;
    public static  int FILTER_SATURATION=1;
    public static  int FILTER_CONTRAST=2;

    public  String getfilename(int filter)
    {
        if(filter==FILTER_BRIGHTNESS)
        {return mfilename+"_brightness";}
        else if(filter==FILTER_CONTRAST)
        {return mfilename+"_contrast";}
        else if (filter==FILTER_SATURATION)
        {return mfilename+"_saturation";}
        return mfilename;
    }

    public Bitmap getBitmap(int filter)
    {
        if(filter==FILTER_BRIGHTNESS)
        {return brightnessFilteredBitmap;}
        else if(filter==FILTER_CONTRAST)
        {return contrastFilteredBitmap;}
        else if (filter==FILTER_SATURATION)
        {return saturationFilteredBitmap;}
        return mbitmap;
    }

    public TransformImage(Context context,Bitmap bitmap)
    {
        mcontext=context;
        mbitmap=bitmap;
        mfilename=System.currentTimeMillis()+"";
    }
    public Bitmap  applyBrightnessSubFilter(int brightness)
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mbitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        brightnessFilteredBitmap=outputImage;
        return outputImage;
    }
    public Bitmap  applySaturationSubFilter(int   satration )
  {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mbitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilter.addSubFilter(new SaturationSubFilter(satration));

        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        saturationFilteredBitmap=outputImage;
        return outputImage;
    }
    public Bitmap  applyContrastSubFilter( int contrast )
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mbitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.ARGB_8888,true);

        myFilter.addSubFilter(new ContrastSubFilter(contrast));

        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        contrastFilteredBitmap=outputImage;
        return outputImage;
    }
}

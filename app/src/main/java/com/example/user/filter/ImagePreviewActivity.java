package com.example.user.filter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ImagePreviewActivity extends AppCompatActivity {
    Toolbar mcontrolToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        mcontrolToolBar=(Toolbar)findViewById(R.id.toolbar2);
        mcontrolToolBar.setTitle(getString(R.string.app_name));
        mcontrolToolBar.setNavigationIcon(R.drawable.icon);
        mcontrolToolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

    }
}

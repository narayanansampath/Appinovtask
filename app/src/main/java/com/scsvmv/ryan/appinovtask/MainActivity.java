package com.scsvmv.ryan.appinovtask;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.scsvmv.ryan.appinovtask.ui.cameramodule.CameraFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;

    private FragmentManager fragmentManager;
    private Fragment activeFragment;
    private Fragment cameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        fragmentManager = getSupportFragmentManager();
        cameraFragment = new CameraFragment();
        setActiveFragment(cameraFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.camera:
                    setActiveFragment(cameraFragment);
                    return true;
                case R.id.location:
                    return true;
                case R.id.video:
                    return true;
            }
            return false;
        }
    };

    private void setActiveFragment(Fragment fragment) {
        activeFragment = fragment;
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, cameraFragment, "Camera Module").commit();
    }

    private void findViews() {
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }
}

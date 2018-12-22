package com.scsvmv.ryan.appinovtask;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.scsvmv.ryan.appinovtask.ui.cameramodule.CameraFragment;
import com.scsvmv.ryan.appinovtask.ui.mapsmodule.MapsFragment;
import com.scsvmv.ryan.appinovtask.ui.videomodule.VideoFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
   // private Fragment activeFragment;
    private Fragment cameraFragment;
    private Fragment videoFragment;
    private Fragment mapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        cameraFragment = new CameraFragment();
        videoFragment = new VideoFragment();
        mapsFragment = new MapsFragment();

        setActiveFragment(cameraFragment,"camera module");
        //setActiveFragment(videoFragment, "video module");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.camera:
                    setActiveFragment(cameraFragment,"camera module");
                    return true;
                case R.id.location:
                    setActiveFragment(mapsFragment, "maps fragment");
                    return true;
                case R.id.video:
                    setActiveFragment(videoFragment,"video module");
                    return true;
            }
            return false;
        }
    };

    private void setActiveFragment(Fragment fragment, String fragmentName) {

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment, fragmentName).commit();
    }

    private void findViews() {
        fragmentContainer = findViewById(R.id.fragmentContainer);
        navigation = findViewById(R.id.navigation);

    }
}

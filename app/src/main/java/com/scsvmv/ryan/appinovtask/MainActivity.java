package com.scsvmv.ryan.appinovtask;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.scsvmv.ryan.appinovtask.ui.ViewPagerAdapter;
import com.scsvmv.ryan.appinovtask.ui.cameramodule.CameraFragment;
import com.scsvmv.ryan.appinovtask.ui.mapsmodule.MapsFragment;
import com.scsvmv.ryan.appinovtask.ui.videomodule.VideoFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private ViewPager viewPager;
    MapFragment mapFragment;
    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
   // private Fragment activeFragment;
    private Fragment cameraFragment;
    private Fragment videoFragment;
    private Fragment mapsFragment;
    MenuItem prevMenuItem;
    //Fragment active = cameraFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        requestStoragePermission();
        mapFragment = new MapFragment();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();

        viewPager = (ViewPager) findViewById(R.id.viewpager);


        //setActiveFragment(videoFragment,"video");
        //setActiveFragment(mapsFragment,"maps module");
        //setActiveFragment(cameraFragment,"camera module");

        //setActiveFragment(videoFragment, "video module");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe
       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        */

        setupViewPager(viewPager);
    }
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            switch (item.getItemId()) {
                case R.id.camera:
                    viewPager.setCurrentItem(0);
                    //setActiveFragment(cameraFragment,"camera module");
                    break;
                case R.id.location:
                    viewPager.setCurrentItem(1);
                    //setActiveFragment(mapsFragment, "maps fragment");
                    break;
                case R.id.video:
                    viewPager.setCurrentItem(2);
                    //setActiveFragment(videoFragment, "video module");
                    break;
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        cameraFragment = new CameraFragment();
        mapsFragment = new MapsFragment();
        videoFragment = new VideoFragment();
        adapter.addFragment(cameraFragment);
        adapter.addFragment(mapsFragment);
        adapter.addFragment(videoFragment);
        viewPager.setAdapter(adapter);
    }
}

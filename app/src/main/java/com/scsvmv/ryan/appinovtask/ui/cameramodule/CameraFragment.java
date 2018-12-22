package com.scsvmv.ryan.appinovtask.ui.cameramodule;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scsvmv.ryan.appinovtask.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraFragment extends Fragment {
    private int NUM_OF_COLS = 2;
    private int CHOOSE_FROM_GALLERY = 122;
    private int CAPTURE_FROM_CAMERA = 123;

    RecyclerView imageList;
    ImagesAdapter imagesAdapter;
    FloatingActionButton cameraButton;
    RecyclerView.LayoutManager layoutManager;

    List<Bitmap> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View screen = inflater.inflate(R.layout.fragment_camera, container, false);

        data = new ArrayList<>();

        findViews(screen);
        initRecyclerView(this.data);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabPress();
            }
        });

        return screen;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == CAPTURE_FROM_CAMERA) {
            handleResultCaptureImage(data);
        } else if (requestCode == CHOOSE_FROM_GALLERY) {
            handleResultChooseFromGallery(data);
        }
    }

    private void findViews(View container) {
        this.imageList = container.findViewById(R.id.recyclerView);
        this.cameraButton = container.findViewById(R.id.cameraFab);
    }

    private void initRecyclerView(List<Bitmap> data) {
        layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLS, GridLayoutManager.VERTICAL, false);
        imageList.setLayoutManager(layoutManager);
        imagesAdapter = new ImagesAdapter(getActivity(), data);
        imageList.setAdapter(imagesAdapter);
    }

    private void onFabPress() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    captureImageFromCamera();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    chooseImageFromGallery();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void addImageToView(Bitmap image) {
        this.data.add(image);
        imagesAdapter.notifyDataSetChanged();
    }

    private void captureImageFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_FROM_CAMERA);
    }

    public void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FROM_GALLERY);
    }

    private void handleResultChooseFromGallery(Intent data) {
        Uri selectedImage = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            addImageToView(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleResultCaptureImage(Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        addImageToView(photo);
    }
}

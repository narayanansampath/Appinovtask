package com.scsvmv.ryan.appinovtask.ui.cameramodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scsvmv.ryan.appinovtask.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter {
    List<Bitmap> images;
    Context context;

    public ImagesAdapter(Context context, List data) {
        this.context = context;
        this.images = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View imageItem = LayoutInflater.from(context).inflate(R.layout.item_image, viewGroup, false);
        ImageView image = imageItem.findViewById(R.id.imageItem);


        int imageSize = viewGroup.getMeasuredWidth() / 2;

        image.getLayoutParams().height = imageSize;
        image.getLayoutParams().width = imageSize;

        return new ViewHolder(imageItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder vHolder = (ViewHolder) viewHolder;
        vHolder.setData(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageItem);
    }

    public void setData(Bitmap image) {
        imageView.setImageBitmap(image);
    }
}
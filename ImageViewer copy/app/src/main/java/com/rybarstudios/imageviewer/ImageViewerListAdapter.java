package com.rybarstudios.imageviewer;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageViewerListAdapter extends RecyclerView.Adapter<ImageViewerListAdapter.ImageListViewHolder> {

    ArrayList<ImageData> imageList;

    public ImageViewerListAdapter(ArrayList<ImageData> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_details_list_item, parent, false);
        return new ImageListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListViewHolder imageListViewHolder, int i) {
        final ImageData data = imageList.get(i);

        imageListViewHolder.mImageView.setImageURI(data.getUri());
        imageListViewHolder.mTextView.setText(data.getName());

        imageListViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageDetails = new Intent(v.getContext(), DetailsActivity.class);
                imageDetails.putExtra(Intent.EXTRA_STREAM, data);
                ((Activity)v.getContext()).startActivity(imageDetails);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.imageList.size();
    }

    class ImageListViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        View parentLayout;

        public ImageListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextView = itemView.findViewById(R.id.image_name_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}

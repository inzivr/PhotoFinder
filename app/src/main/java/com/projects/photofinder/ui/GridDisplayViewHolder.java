package com.projects.photofinder.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.photofinder.R;

public class GridDisplayViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgItem;
    public ProgressBar progressBar;

    public GridDisplayViewHolder(@NonNull View itemView) {
        super(itemView);

        imgItem = (ImageView) itemView.findViewById(R.id.img_item);
        progressBar = (ProgressBar) itemView.findViewById(R.id.icon_progress_bar);
    }
}

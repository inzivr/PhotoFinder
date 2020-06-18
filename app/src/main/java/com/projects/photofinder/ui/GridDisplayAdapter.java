package com.projects.photofinder.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.projects.photofinder.R;
import com.projects.photofinder.datamodels.Photo;
import com.projects.photofinder.datamodels.Src;

import java.util.List;

public class GridDisplayAdapter extends RecyclerView.Adapter<GridDisplayViewHolder> {

    private Context context;
    private List<Photo> photos;
    public static final String Large = "Large";

    public GridDisplayAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public GridDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_grid_item,parent,false);
        return new GridDisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridDisplayViewHolder holder, int position) {

        // Display downloaded images here
        Src src = photos.get(position).getSrc();
        String imgUrl = src.getTiny();

        Glide.with(context).load(imgUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                Toast.makeText(context,"Image Loading Failed : Try Again",Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.imgItem);

        // Action for user click on image
        holder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start Activity to Display Enlarged Image
                Intent intent = new Intent(context, Enlarge.class);
                intent.putExtra(Large, src.getLarge2x());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addImages(List<Photo> newPhotos) {
        photos.addAll(newPhotos);
    }
}

package com.dishap.seasonapp.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dishap.seasonapp.Activities.RecipeActivity;
import com.dishap.seasonapp.Models.Recipe;
import com.dishap.seasonapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapterFavorites extends RecyclerView.Adapter<RecyclerViewAdapterFavorites.MyViewHolder>{
    private Context mContext;
    private List<Recipe> mData;

    public RecyclerViewAdapterFavorites(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterFavorites.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_favorites, parent, false);
        return new RecyclerViewAdapterFavorites.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterFavorites.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_favRecipe_title.setText(mData.get(position).getTitle());
        if(mData.get(position).getImage().isEmpty()){
            holder.img_favRecipe_thumbnail.setImageResource(R.drawable.nopicture);
        }
        else{
            Picasso.get().load(mData.get(position).getImage()).into(holder.img_favRecipe_thumbnail);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("id",mData.get(position).getId());
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("img",mData.get(position).getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_favRecipe_title;
        ImageView img_favRecipe_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_favRecipe_title = itemView.findViewById(R.id.favorites_tv);
            img_favRecipe_thumbnail = itemView.findViewById(R.id.favorites_iv);
            cardView = itemView.findViewById(R.id.favorites_cv);
        }
    }
}

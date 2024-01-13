package com.dishap.seasonapp.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dishap.seasonapp.Models.Ingredient;
import com.dishap.seasonapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterRecipeIngredient extends RecyclerView.Adapter<RecyclerViewAdapterRecipeIngredient.MyViewHolder> {
    private Context mContext;
    private List<Ingredient> mData;
    public static List<String> ingredientsList;

    public RecyclerViewAdapterRecipeIngredient(Context mContext, List<Ingredient> mData) {
        this.mContext = mContext;
        this.mData = mData;
        ingredientsList = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerViewAdapterRecipeIngredient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_ingredient, parent, false);
        return new RecyclerViewAdapterRecipeIngredient.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterRecipeIngredient.MyViewHolder holder, final int position) {
        holder.textview_ingredient_name.setText(mData.get(position).getName());
        Picasso.get().load(mData.get(position).getThumbnail()).into(holder.ingredientImage_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textview_ingredient_name;
        ImageView ingredientImage_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            textview_ingredient_name = itemView.findViewById(R.id.recipe_ingredient_name);
            ingredientImage_thumbnail = itemView.findViewById(R.id.recipe_ingredient_img);
        }
    }
}

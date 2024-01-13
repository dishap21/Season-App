package com.dishap.seasonapp.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dishap.seasonapp.Activities.RecipeActivity;
import com.dishap.seasonapp.Models.Recipe;
import com.dishap.seasonapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private Context mContext ;
    private List<Recipe> mData ;

    public RecyclerViewAdapter(Context mContext, List<Recipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_recipe,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_recipe_title.setText(mData.get(position).getTitle());
        holder.tv_ready_in_mins.setText( Integer.toString(mData.get(position).getReadyInMins()) );
        if (mData.get(position).getImage().isEmpty()) {
            holder.img_recipe_thumbnail.setImageResource(R.drawable.nopicture);
        } else{
            Picasso.get().load(mData.get(position).getImage()).into(holder.img_recipe_thumbnail);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra("id",mData.get(position).getId());
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("img",mData.get(position).getImage());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_recipe_title,tv_ready_in_mins;
        ImageView img_recipe_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_recipe_title = (TextView) itemView.findViewById(R.id.foodName_randomRecipe) ;
            tv_recipe_title.setSelected(true);
            tv_recipe_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv_recipe_title.setSingleLine(true);
            img_recipe_thumbnail = (ImageView) itemView.findViewById(R.id.imageView_randomRecipe);
            tv_ready_in_mins = (TextView) itemView.findViewById(R.id.timeTaken_RandomRecipe);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}

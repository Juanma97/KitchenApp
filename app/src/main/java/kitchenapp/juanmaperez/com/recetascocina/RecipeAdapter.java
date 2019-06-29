package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<Hit> dataset;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleRecipe, caloriesRecipe;
        public ImageView imageRecipe;

        public MyViewHolder(View view){
            super(view);
            titleRecipe = view.findViewById(R.id.titleRecipe);
            caloriesRecipe = view.findViewById(R.id.caloriesRecipe);
            imageRecipe = view.findViewById(R.id.imageRecipe);
        }

    }

    public RecipeAdapter(Context mContext, ArrayList<Hit> dataset){
        this.mContext = mContext;
        this.dataset = dataset;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Hit hit = dataset.get(position);
        holder.titleRecipe.setText(hit.getRecipe().getLabel());
        holder.caloriesRecipe.setText(hit.getRecipe().getCalories().toString() + " KCAL");
        Glide.with(mContext).load(hit.getRecipe().getImage()).into(holder.imageRecipe);

        holder.imageRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("HIT",(Serializable) hit);
                intent.putExtra("BUNDLE", args);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

}

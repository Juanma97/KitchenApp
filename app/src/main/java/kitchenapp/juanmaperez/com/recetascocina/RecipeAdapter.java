package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<Hit> dataset;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleRecipe, caloriesRecipe, dificultyRecipe;
        public ImageView imageRecipe;

        public MyViewHolder(View view){
            super(view);
            titleRecipe = view.findViewById(R.id.titleRecipe);
            caloriesRecipe = view.findViewById(R.id.caloriesRecipe);
            dificultyRecipe = view.findViewById(R.id.dificultyRecipe);
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
        Glide.with(mContext).load(hit.getRecipe().getImage()).into(holder.imageRecipe);
        holder.titleRecipe.setText(hit.getRecipe().getLabel());
        int calories = (int) Math.round(hit.getRecipe().getCalories());
        calories = calories / 10;
        holder.caloriesRecipe.setText(String.valueOf(calories) + " KCAL");
        List<String> ingredients = hit.getRecipe().getIngredientLines();
        if(ingredients.size() > 0 && ingredients.size() < 4){
            holder.dificultyRecipe.setText("Principiante +");
        }else if(ingredients.size() >= 4 && ingredients.size() < 7){
            holder.dificultyRecipe.setText("Intermedio ++");
        }else{
            holder.dificultyRecipe.setText("Experto +++");
        }

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

        holder.titleRecipe.setOnClickListener(new View.OnClickListener() {
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

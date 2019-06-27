package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private ArrayList<String> dataset;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleRecipe;
        public ImageView imageRecipe;

        public MyViewHolder(View view){
            super(view);
            titleRecipe = view.findViewById(R.id.titleRecipe);
            imageRecipe = view.findViewById(R.id.imageRecipe);
        }

    }

    public RecipeAdapter(Context mContext, ArrayList<String> dataset){
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
        String recipe = dataset.get(position);
        holder.titleRecipe.setText(recipe);
        //holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        /**holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });**/
    }



    @Override
    public int getItemCount() {
        return dataset.size();
    }

}

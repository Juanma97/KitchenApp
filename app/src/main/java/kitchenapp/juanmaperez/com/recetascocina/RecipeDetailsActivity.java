package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class RecipeDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Hit hit;
    private ImageView bgHeader;
    private TextView titleRecipe, timeRecipe, kcalRecipe, cautionsRecipe, ingredientsRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        toolbar = findViewById(R.id.MyToolbar);
        bgHeader = findViewById(R.id.bgheader);
        titleRecipe = findViewById(R.id.titleRecipeDetails);
        timeRecipe = findViewById(R.id.timeRecipe);
        kcalRecipe = findViewById(R.id.kcalRecipe);
        cautionsRecipe = findViewById(R.id.cautionsRecipe);
        ingredientsRecipe = findViewById(R.id.ingredientsRecipe);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle("");

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        hit = (Hit) args.getSerializable("HIT");

        Glide.with(getApplicationContext()).load(hit.getRecipe().getImage()).into(bgHeader);

        System.out.println("TITLE: " + hit.getRecipe().getLabel());
        titleRecipe.setText(hit.getRecipe().getLabel());
        System.out.println("TOTAL TIME: " + hit.getRecipe().getTotalTime());
        //timeRecipe.setText(hit.getRecipe().getTotalTime().toString());
        System.out.println("KCAL: ");
        kcalRecipe.setText(hit.getRecipe().getCalories().toString());
        //cautionsRecipe.setText(hit.getRecipe().getCautions());

        String ingredients = "";

        for(String ingredient : hit.getRecipe().getIngredientLines()){
            ingredients += "· " + ingredient + "\n";
        }

        ingredientsRecipe.setText(ingredients);

    }

}

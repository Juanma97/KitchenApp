package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class RecipeDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Hit hit;
    private ImageView bgHeader;
    private TextView titleRecipe, timeRecipe, kcalRecipe, cautionsRecipe, ingredientsRecipe;
    private Button buttonInstructionsRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recipe_details);

        toolbar = findViewById(R.id.MyToolbar);
        bgHeader = findViewById(R.id.bgheader);
        titleRecipe = findViewById(R.id.titleRecipeDetails);
        timeRecipe = findViewById(R.id.timeRecipe);
        kcalRecipe = findViewById(R.id.kcalRecipe);
        //cautionsRecipe = findViewById(R.id.cautionsRecipe);
        ingredientsRecipe = findViewById(R.id.ingredientsRecipe);
        buttonInstructionsRecipe = findViewById(R.id.instructionsRecipe);

        buttonInstructionsRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = hit.getRecipe().getUrl();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(" ");

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        hit = (Hit) args.getSerializable("HIT");

        Glide.with(getApplicationContext()).load(hit.getRecipe().getImage()).into(bgHeader);

        titleRecipe.setText(hit.getRecipe().getLabel());
        int calories = (int) Math.round(hit.getRecipe().getCalories());
        calories = calories / 10;
        kcalRecipe.setText(String.valueOf(calories));

        String ingredients = "";

        for(String ingredient : hit.getRecipe().getIngredientLines()){
            ingredients += "· " + ingredient + "\n\n";
        }

        ingredientsRecipe.setText(ingredients);

    }

}

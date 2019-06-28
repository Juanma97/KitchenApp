package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button buttonSearch;
    ArrayList<String> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ENTRO A ONCLICK");
                getRecipes(editTextSearch.getText().toString());
            }
        });
    }

    private void getRecipes(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://test-es.edamam.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<ResponseAPI> call = recipeService.getRecipe(query, Credentials.APP_ID, Credentials.API_KEY);

        call.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.code() != 200){
                    response.raw().body().close();
                }
                System.out.println("EXITO");
                ResponseAPI responseAPI = response.body();
                List<Hit> hits = responseAPI.getHits();
                //Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                ArrayList<String> recipesTitles = new ArrayList<>();
                ArrayList<String> recipesImages = new ArrayList<>();
                //ArrayList<List<String>> ingredients = new ArrayList<>();
                for(Hit hit : hits){
                    recipesImages.add(hit.getRecipe().getImage());
                    //System.out.println("AÑADIENDO: " + hit.getRecipe().getLabel());
                    recipesTitles.add(hit.getRecipe().getLabel());
                    /**System.out.println("INGREDIENTES: ");
                    for(String s : hit.getRecipe().getIngredientLines()){
                        System.out.println(s);
                    }**/
                }

                ArrayList<Hit> hitsToPass = new ArrayList<Hit>();
                hitsToPass.addAll(hits);
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable) hitsToPass);
                intent.putExtra("BUNDLE", args);
                intent.putExtra("TEXT_SEARCH", editTextSearch.getText().toString());
                startActivity(intent);
                //intent.putStringArrayListExtra("ARRAY", recipesTitles);
                //intent.putStringArrayListExtra("INGREDIENTS", recipes);

                //startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                System.out.println("HA FALLADO: " + t.getMessage());
            }
        });
    }

    private void initializeComponents() {
        editTextSearch = findViewById(R.id.textViewSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
    }
}

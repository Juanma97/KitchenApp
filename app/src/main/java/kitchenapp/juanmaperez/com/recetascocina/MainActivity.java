package kitchenapp.juanmaperez.com.recetascocina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                /**Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("TEXT_SEARCH", editTextSearch.getText().toString());
                startActivity(intent);**/
                System.out.println("ENTRO A ONCLICK");
                getRecipes();
            }
        });
    }

    private void getRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://test-es.edamam.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<ResponseAPI> call = recipeService.getRecipe("pollo", Credentials.APP_ID, Credentials.API_KEY);

        call.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                System.out.println("EXITO");
                System.out.println(response.body());
                ResponseAPI responseAPI = response.body();
                System.out.println(responseAPI.getHits());
                List<Hit> hits = responseAPI.getHits();
                for(Hit hit : hits){
                    System.out.println(hit.getRecipe().getLabel());
                }
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

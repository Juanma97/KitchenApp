package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsActivity extends AppCompatActivity {

    EditText editTextSearchResults;
    ImageButton buttonSearchResults, buttonBackResults;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Hit> hits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        System.out.println("ON CREATE RESULTS ACTIVITY");
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        hits = (ArrayList<Hit>) args.getSerializable("ARRAYLIST");
        initializeComponents();
        editTextSearchResults.setText(intent.getStringExtra("TEXT_SEARCH"));
    }

    private void initializeComponents() {
        editTextSearchResults = findViewById(R.id.editTextSearchResults);
        buttonSearchResults = findViewById(R.id.buttonSearchResults);
        buttonBackResults = findViewById(R.id.buttonBackResults);
        buttonBackResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultsActivity.this, MainActivity.class));
                finish();
            }
        });
        buttonSearchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecipes(editTextSearchResults.getText().toString());
            }
        });
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecipeAdapter(this, hits);
        recyclerView.setAdapter(mAdapter);
    }

    private void getRecipes(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<ResponseAPI> call = recipeService.getRecipe(query, Credentials.APP_ID, Credentials.API_KEY, 100);

        call.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.code() != 200){
                    response.raw().body().close();
                }
                System.out.println("EXITO");
                ResponseAPI responseAPI = response.body();
                List<Hit> hitsResponse = responseAPI.getHits();
                hits.clear();
                hits.addAll(hitsResponse);
                System.out.println(hits.size());
                for(Hit h : hits){
                    System.out.println(h.getRecipe().getLabel());
                }
                mAdapter = new RecipeAdapter(getApplicationContext(), hits);

                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                System.out.println("HA FALLADO: " + t.getMessage());
            }
        });
    }
}

package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    EditText editTextSearchResults;
    Button buttonSearchResults;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> recipesTitles;
    ArrayList<Hit> hits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        System.out.println("ON CREATE RESULTS ACTIVITY");
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        hits = (ArrayList<Hit>) args.getSerializable("ARRAYLIST");
        //recipesTitles = intent.getStringArrayListExtra("ARRAY");
        //ArrayList<String> ingredients = intent.getStringArrayListExtra("INGREDIENTS");
        System.out.println("HAN LLEGADO LOS HITS");
        System.out.println(hits.size());
//        System.out.println(recipesTitles.size());
        for(Hit hit : hits) {
            //recipesTitles.add(hit.getRecipe().getLabel());
            System.out.println(hit.getRecipe().getLabel());
        }
        initializeComponents();
        editTextSearchResults.setText(intent.getStringExtra("TEXT_SEARCH"));
    }

    private void initializeComponents() {
        System.out.println("INITIALIZE COMPONENTS");
        editTextSearchResults = findViewById(R.id.editTextSearchResults);
        buttonSearchResults = findViewById(R.id.buttonSearchResults);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecipeAdapter(this, hits);
        recyclerView.setAdapter(mAdapter);
    }
}

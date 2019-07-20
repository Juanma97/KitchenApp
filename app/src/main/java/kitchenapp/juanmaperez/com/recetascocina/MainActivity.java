package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

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
    TextView textLoading;
    ProgressBar progressBar;
    private AdView mAdView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        mContext = this;

        MobileAds.initialize(this, getString(R.string.ADMOB_APP_ID));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ConnectivityHelper ch = new ConnectivityHelper();

        if(!ConnectivityHelper.isConnectedToNetwork(this)){
            System.out.println("No esta conectado");
            startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
            finish();
        }else {
            System.out.println("Esta conectado");

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!ConnectivityHelper.isConnectedToNetwork(mContext)){
                        startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
                        finish();
                    }
                    if(editTextSearch.getText().length() > 0 && !ConnectivityHelper.isConnectedToNetwork(mContext)) {
                        progressBar.setVisibility(View.VISIBLE);
                        textLoading.setVisibility(View.VISIBLE);
                        buttonSearch.setVisibility(View.INVISIBLE);
                        getRecipes(editTextSearch.getText().toString());
                    }else{
                        editTextSearch.setError("Introduzca un ingrediente");
                    }
                }
            });
        }
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
                List<Hit> hits = responseAPI.getHits();

                ArrayList<Hit> hitsToPass = new ArrayList<>();
                hitsToPass.addAll(hits);
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", hitsToPass);
                intent.putExtra("BUNDLE", args);
                intent.putExtra("TEXT_SEARCH", editTextSearch.getText().toString());
                startActivity(intent);
                finish();
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
        textLoading = findViewById(R.id.textLoading);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        textLoading.setVisibility(View.INVISIBLE);
        buttonSearch.setVisibility(View.VISIBLE);
    }
}

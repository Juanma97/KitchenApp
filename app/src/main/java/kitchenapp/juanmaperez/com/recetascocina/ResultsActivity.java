package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ResultsActivity extends AppCompatActivity {

    EditText editTextSearchResults;
    Button buttonSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initializeComponents();
        Intent intent = getIntent();
        editTextSearchResults.setText(intent.getStringExtra("TEXT_SEARCH"));
    }

    private void initializeComponents() {
        editTextSearchResults = findViewById(R.id.editTextSearchResults);
        buttonSearchResults = findViewById(R.id.buttonSearchResults);
    }
}

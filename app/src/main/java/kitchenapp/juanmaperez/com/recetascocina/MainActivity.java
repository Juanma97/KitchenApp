package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("TEXT_SEARCH", editTextSearch.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initializeComponents() {
        editTextSearch = findViewById(R.id.textViewSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
    }
}

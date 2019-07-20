package kitchenapp.juanmaperez.com.recetascocina;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class NoInternetActivity extends AppCompatActivity {

    Button btnBackMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        btnBackMainActivity = findViewById(R.id.btnBackMainActivity);
        btnBackMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoInternetActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}

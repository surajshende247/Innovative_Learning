package app.iislearning.askdoubts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import app.iislearning.R;

public class ShowResponse extends AppCompatActivity {
    TextView txtAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_response);
        txtAnswer = findViewById(R.id.txtAnswer);
        txtAnswer.setText(getIntent().getStringExtra("answer"));
    }
}
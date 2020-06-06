package app.iislearning.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.iislearning.R;

public class ViewSubmission extends AppCompatActivity {
    ImageView imgAssignment;
    TextView txtFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submission);
        imgAssignment = findViewById(R.id.imgAssignment);
        txtFeedback = findViewById(R.id.txtFeedback);
        txtFeedback.setText(getIntent().getStringExtra("feedback"));

        Picasso.get().load(getIntent().getStringExtra("imgurl")).into(imgAssignment);


    }
}
package app.iislearning.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import app.iislearning.R;

public class AssignmentDetail extends AppCompatActivity {
    TextView txtAssignmentTitle;
    TextView txtAssignmentInstructions;
    ZoomageView ImageZoomageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);
        txtAssignmentTitle = findViewById(R.id.txtAssignmentTitle);
        txtAssignmentInstructions = findViewById(R.id.txtInstructions);
        txtAssignmentTitle.setText(getIntent().getStringExtra("title"));
        txtAssignmentInstructions.setText(getIntent().getStringExtra("instructions"));
        ImageZoomageView = findViewById(R.id.imageViewImageFullScreen);

                try
                {
                    Picasso.get().load(getIntent().getStringExtra("img")).into(ImageZoomageView);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


    }
}
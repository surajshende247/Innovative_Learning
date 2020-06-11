package app.iislearning.classplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.iislearning.R;
import app.iislearning.askdoubts.AddDoubt;
import app.iislearning.askdoubts.ShowDoubts;

public class LecturePlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private Button btnFullScreen;
    private YouTubePlayer ytPlayer;
    String videoid;
    Button btnPresent;
    Boolean lectureComplete=false;

    //for volly
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/addAttendance.php";

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";
    SharedPreferences.Editor editor;

    //loader while login
    ProgressDialog progressDialog;

    TextView txtTitle;
    TextView txtGradeSubject;
    TextView txtSubjectTeacher;
    TextView txtDescription;
    TextView txtInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_player);

        //for loader
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Class");
        progressDialog.setMessage("Please wait");

        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        txtTitle = findViewById(R.id.txtTitle);
        txtGradeSubject = findViewById(R.id.txtGradeSubject);
        txtSubjectTeacher = findViewById(R.id.txtTeacher);
        txtDescription = findViewById(R.id.txtDescription);
        txtInstruction = findViewById(R.id.txtInstructions);

        txtTitle.setText(getIntent().getStringExtra("title"));
        txtGradeSubject.setText(getIntent().getStringExtra("txtClass")+"("+getIntent().getStringExtra("txtSubject")+")");
        txtSubjectTeacher.setText("Instructor: "+getIntent().getStringExtra("txtTeacher"));
        txtDescription.setText(getIntent().getStringExtra("txtDescription"));
        txtInstruction.setText(getIntent().getStringExtra("txtInstructions"));


        //for blank attendance or absent
        progressDialog.show();
        addAttendance();


        btnPresent = findViewById(R.id.btnPresent);
        btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lectureComplete)
                {
                    progressDialog.setTitle("Marking Attendence");
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    addAttendance();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Watch Complete Video",Toast.LENGTH_LONG).show();
                }
            }
        });

        videoid = getIntent().getStringExtra("videoid");
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        btnFullScreen = findViewById(R.id.btnFullScreen);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ytPlayer.setFullscreen(true);
            }
        });

    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(videoid); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            ytPlayer = player;
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute("15");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }



    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }




    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0])*1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
           // finalResult.setText(result);
            lectureComplete=true;
            btnPresent.setBackgroundColor(Color.parseColor("#4CAF50"));
            addAttendance();
        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(String... text) {
           // finalResult.setText(text[0]);

        }
    }

    private void addAttendance() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                        progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i("TAG","Error :" + error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("student_id",sharedpreferences.getString("student_id", ""));
                params.put("grade",sharedpreferences.getString("student_grade", ""));
                params.put("subject",getIntent().getStringExtra("txtSubject"));
                params.put("videoid",getIntent().getStringExtra("videoid"));
                int attendanceRemark = lectureComplete ?1:0;
                params.put("remark",attendanceRemark+"");
                return  params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}

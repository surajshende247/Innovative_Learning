package app.iislearning.stdprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import app.iislearning.R;
import app.iislearning.askdoubts.AddDoubt;
import app.iislearning.askdoubts.ShowDoubts;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackProgress extends AppCompatActivity {
    //for volly
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/getProgress.php";

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";

    //loader while login
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_progress);

        //for loader
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Calculating Progress");
        progressDialog.setMessage("Please wait");


        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);


        progressDialog.show();
        getProgress();

        PieChart pieChart = findViewById(R.id.piechart);

        ArrayList att = new ArrayList();
        att.add(new Entry(80f, 0));
        att.add(new Entry(20f, 1));

        ArrayList att_status = new ArrayList();
        att_status.add("Present");
        att_status.add("Absent");


        PieDataSet dataSet = new PieDataSet(att, "Online Presence for Classes");
        PieData data = new PieData(att_status, dataSet);
        pieChart.setData(data);

        final int[] MY_COLORS = { Color.rgb(65,105,225), Color.rgb(255,0,0) };
        ArrayList<Integer> colors = new ArrayList<>();
        for(int c: MY_COLORS) colors.add(c);
        dataSet.setColors(colors);

        dataSet.setValueTextColor(Color.rgb(255,255,255));
        dataSet.setValueTextSize(15f);
        pieChart.setDescription("This is auto generate chart for attendance of your daily presence in online classes.");
        pieChart.animateXY(5000, 5000);
    }
    private void getProgress() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("TAG","Response :" + jsonObject.getString("attendance_percentage"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }



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
                return  params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

}
package app.iislearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyClasses extends AppCompatActivity {
    ArrayList<LectureData> arrayListLectures = new ArrayList<LectureData>();
    RecyclerView recyclerView;
    MyClassesAdapter adapter;
    ProgressDialog progressDialog;

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";
    SharedPreferences.Editor editor;


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/getClasses.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);

        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        recyclerView = findViewById(R.id.class_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();

        sendAndRequestResponse();


    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.i("TAG","Response :" + response.toString());
                progressDialog.dismiss();
                String jsonresponse = response;
                try {
                    JSONArray arr = new JSONArray(jsonresponse);
                    for (int i = 0; i < arr.length(); i++) {
                     //   Log.i("TAG","Response :" + arr.getJSONObject(i).getString("class_subject"));
                        arrayListLectures.add(new LectureData(arr.getJSONObject(i).getString("class_id"), arr.getJSONObject(i).getString("class_subject"), arr.getJSONObject(i).getString("class_grade"), arr.getJSONObject(i).getString("class_title"), arr.getJSONObject(i).getString("video_id"), arr.getJSONObject(i).getString("class_description"), arr.getJSONObject(i).getString("class_instructions"), arr.getJSONObject(i).getString("subject_teacher"), arr.getJSONObject(i).getString("uploaded_date"), arr.getJSONObject(i).getString("due_date")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new MyClassesAdapter(arrayListLectures);
                recyclerView.setAdapter(adapter);

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
                params.put("class",sharedpreferences.getString("student_grade", ""));
                return  params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

}
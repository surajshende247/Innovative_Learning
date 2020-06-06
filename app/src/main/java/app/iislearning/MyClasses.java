package app.iislearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MyClasses extends AppCompatActivity {
    ArrayList<LectureData> arrayListLectures = new ArrayList<LectureData>();
    RecyclerView recyclerView;
    MyClassesAdapter adapter;
    ProgressDialog progressDialog;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/getClasses.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes);
        recyclerView = findViewById(R.id.class_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sendAndRequestResponse();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.i("TAG","Response :" + response.toString());

                String jsonresponse = response;
                try {
                    JSONArray arr = new JSONArray(jsonresponse);
                    for (int i = 0; i < arr.length(); i++) {
                        Log.i("TAG","Response :" + arr.getJSONObject(i).getString("class_subject"));
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
        });

        mRequestQueue.add(mStringRequest);
    }

}
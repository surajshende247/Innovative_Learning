package app.iislearning.askdoubts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import app.iislearning.LectureData;
import app.iislearning.MyClassesAdapter;
import app.iislearning.R;

public class ShowDoubts extends AppCompatActivity {
    FloatingActionButton fbAskQuery;

    ArrayList<MyDoubt> arrayListQueries = new ArrayList<MyDoubt>();
    RecyclerView recyclerView;
    ShowDoubtsAdapter adapter;
    ProgressDialog progressDialog;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/getQueries.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doubts);
        recyclerView = findViewById(R.id.recyclerview_doubts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");


        fbAskQuery = findViewById(R.id.fbAddQuery);
        fbAskQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDoubts.this,AddDoubt.class);
                startActivity(intent);
            }
        });

        sendAndRequestResponse();
        progressDialog.show();
    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAG","Response :" + response.toString());

                String jsonresponse = response;
                try {
                    JSONArray arr = new JSONArray(jsonresponse);
                    for (int i = 0; i < arr.length(); i++) {
                        Log.i("TAG","Response :" + arr.getJSONObject(i).getString("id"));
                        arrayListQueries.add(new MyDoubt(arr.getJSONObject(i).getString("id"),arr.getJSONObject(i).getString("student_id"), arr.getJSONObject(i).getString("grade"), arr.getJSONObject(i).getString("subject"), arr.getJSONObject(i).getString("teacher"), arr.getJSONObject(i).getString("query"), arr.getJSONObject(i).getString("answer"), arr.getJSONObject(i).getString("satisfaction"), arr.getJSONObject(i).getString("dateofquery"), arr.getJSONObject(i).getString("status")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new ShowDoubtsAdapter(arrayListQueries);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

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
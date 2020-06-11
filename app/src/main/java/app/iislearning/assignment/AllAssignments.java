package app.iislearning.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

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

import app.iislearning.MainActivity;
import app.iislearning.R;
import app.iislearning.askdoubts.MyDoubt;
import app.iislearning.askdoubts.ShowDoubtsAdapter;

public class AllAssignments extends AppCompatActivity {

    ArrayList<MyAssign> arrayListAssignment = new ArrayList<MyAssign>();
    RecyclerView recyclerView;
    AllAssignmentsAdapter adapter;

    ProgressDialog progressDialog;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/getAllAssignment.php";

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assignments);

        recyclerView = findViewById(R.id.recyclerview_assignment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");

        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);


        /*asking for storage permission starts here*/
        if (ContextCompat.checkSelfPermission(AllAssignments.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(AllAssignments.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE },100);
        }
        /*asking for storage permission ends here*/

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
                      //  Log.i("TAG","Response :" + arr.getJSONObject(i).getString("id"));
                        arrayListAssignment.add(new MyAssign(arr.getJSONObject(i).getString("id"),arr.getJSONObject(i).getString("student_id"), arr.getJSONObject(i).getString("assignment_id"), arr.getJSONObject(i).getString("stdimg_url"), arr.getJSONObject(i).getString("feedback"), arr.getJSONObject(i).getString("marks_obtained"), arr.getJSONObject(i).getString("upload_status"), arr.getJSONObject(i).getString("assignment_date"), arr.getJSONObject(i).getString("assignment_title"), arr.getJSONObject(i).getString("total_marks"),arr.getJSONObject(i).getString("teacherimg_url"),arr.getJSONObject(i).getString("instructions"),arr.getJSONObject(i).getString("subject"),arr.getJSONObject(i).getString("grade")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new AllAssignmentsAdapter(arrayListAssignment);
                recyclerView.setAdapter(adapter);
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
                return  params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
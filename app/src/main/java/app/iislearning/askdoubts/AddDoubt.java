package app.iislearning.askdoubts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.iislearning.MainActivity;
import app.iislearning.R;
import app.iislearning.auth.Login;

public class AddDoubt extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener {
    String[] teachers = { "Bhagyashree Miss", "Nidhi Miss", "Rohini Miss", "Deepika Miss", "Leena Miss", "Riya Miss", "Kalyani Miss","Suchitra Miss", "Jyoti Miss"};
    EditText txtQuery;
    EditText txtSubject;
    Button btnQuery;
    Spinner spTeacher;

    //for volly
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/addQuery.php";

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";
    SharedPreferences.Editor editor;

    //loader while login
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doubt);
        txtQuery = findViewById(R.id.txtQuery);
        txtSubject = findViewById(R.id.txtSubject);

        //for loader
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("We are here to resolve your query");

        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        spTeacher = findViewById(R.id.txtTeacher);
        spTeacher.setOnItemSelectedListener(this);
        ArrayAdapter bb = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,teachers);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTeacher.setAdapter(bb);

        btnQuery = findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                addQuery();

                   // Toast.makeText(v.getContext(), spTeacher.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
        });



    }

    private void addQuery() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("TAG","Response :" + response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AddDoubt.this, "Query sent successfully...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddDoubt.this, ShowDoubts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(AddDoubt.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
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
                params.put("grade",sharedpreferences.getString("student_grade", ""));
                params.put("subject",txtSubject.getText().toString());
                params.put("teacher",spTeacher.getSelectedItem().toString());
                params.put("query",txtQuery.getText().toString());
                return  params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
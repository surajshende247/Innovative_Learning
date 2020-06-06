package app.iislearning.auth;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.iislearning.LectureData;
import app.iislearning.MainActivity;
import app.iislearning.MyClassesAdapter;
import app.iislearning.R;

public class Login extends Activity {
    EditText txtMobile;
    EditText txtPassword;
    TextView txtError1,txtError2;
    ImageView imgPasswordToggle;
    Boolean passwordToggle=true;
    Boolean validateMobile=false;
    Boolean validatePassword=false;
    Button btnLogin;

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";
    SharedPreferences.Editor editor;

    //loader while login
    ProgressDialog progressDialog;

    //for volly
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for loader while login
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("A great learning is ahead");

        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        String value = sharedpreferences.getString("Login", "");
        if(value.equals("true"))
        {
            Intent i = new Intent(Login.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }



        txtError1 = findViewById(R.id.txtError1);
        txtError2 = findViewById(R.id.txtError2);

        imgPasswordToggle = findViewById(R.id.imgPasswordToggle);
        imgPasswordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordToggle=!passwordToggle;
                if(passwordToggle)
                {
                    //Hide Password
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    txtPassword.setSelection(txtPassword.getText().toString().length());
                }
                else
                {
                    //Show Password
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    txtPassword.setSelection(txtPassword.getText().toString().length());
                }
            }
        });

        txtMobile = findViewById(R.id.txtMobile);
        txtMobile.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(txtMobile.getText().toString().length()==0)
                {
                    txtError1.setTextColor(Color.parseColor("#ff0000"));
                    txtError1.setText("Mobile number cannot be empty");
                    validateMobile=false;
                }
                else if(txtMobile.getText().toString().length()<10)
                {
                    txtError1.setTextColor(Color.parseColor("#ff0000"));
                    txtError1.setText("Please Enter 10 digits Mobile Number");
                    validateMobile=false;
                }
                else if(txtMobile.getText().toString().length()>10)
                {
                    txtError1.setTextColor(Color.parseColor("#ff0000"));
                    txtMobile.setText(txtMobile.getText().toString().substring(0,10));
                    txtMobile.setSelection(10);
                    validateMobile=false;
                }
                else if(!TextUtils.isDigitsOnly(txtMobile.getText().toString()))
                {
                    txtError1.setTextColor(Color.parseColor("#ff0000"));
                    txtError1.setText("Only digits allowed");
                    validateMobile=false;
                }
                else
                {
                    txtError1.setTextColor(Color.parseColor("#000000"));
                    txtError1.setText("Valid");
                    validateMobile=true;
                }

            }
        });

        txtPassword = findViewById(R.id.txtPassword);
        txtPassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(txtPassword.getText().toString().length()==0)
                {
                    txtError2.setTextColor(Color.parseColor("#ff0000"));
                    txtError2.setText("Password cannot be empty");
                    validatePassword=false;
                }
                else if(txtPassword.getText().toString().length()>10)
                {
                    txtError2.setTextColor(Color.parseColor("#ff0000"));
                    txtPassword.setText(txtPassword.getText().toString().substring(0,10));
                    txtPassword.setSelection(10);
                    validatePassword=false;
                }
                else
                {
                    txtError2.setTextColor(Color.parseColor("#000000"));
                    txtError2.setText("Valid");
                    validatePassword=true;
                }

            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateMobile==true && validatePassword==true)
                {
                    Toast.makeText(Login.this,"Validated",Toast.LENGTH_LONG).show();
                    progressDialog.show();
                    login();
                }
                else
                {
                    Toast.makeText(Login.this,"Username and Password Validation Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void login() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true"))
                    {
                        Log.i("VERIFY","Response :" + jsonObject.getString("student_id"));
                        //data to store shared preferences
                        editor.putString("Login", "true");

                        editor.putString("student_id", jsonObject.getString("student_id"));
                        editor.putString("admission_no", jsonObject.getString("admission_no"));
                        editor.putString("student_grade", jsonObject.getString("student_grade"));
                        editor.putString("student_name", jsonObject.getString("student_name"));
                        editor.putString("father_name", jsonObject.getString("father_name"));
                        editor.putString("mother_name", jsonObject.getString("mother_name"));
                        editor.putString("surname", jsonObject.getString("surname"));
                        editor.putString("mobile_number1", jsonObject.getString("mobile_number1"));
                        editor.putString("mobile_number2", jsonObject.getString("mobile_number2"));

                        editor.commit();

                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String,String>();
                params.put("mobile",txtMobile.getText().toString());
                params.put("password",txtPassword.getText().toString());
                return  params;
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
package app.iislearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;
import com.varunest.sparkbutton.SparkEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import app.iislearning.askdoubts.AddDoubt;
import app.iislearning.askdoubts.ShowDoubts;
import app.iislearning.assignment.AllAssignments;
import app.iislearning.auth.Login;
import app.iislearning.stdprogress.TrackProgress;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,   ViewPagerEx.OnPageChangeListener  {
    private SliderLayout mDemoSlider;

    //for volly
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://gracecompusys.com/iis/insert_fcm.php";

    //shared preference
    SharedPreferences sharedpreferences;
    public static final String my_preference = "login_details";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //shared preference details
        sharedpreferences = getSharedPreferences(my_preference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        /*Registration of fcm token starts here*/
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //To do//
                            return;
                        }

                        // Get the Instance ID token//
                        final String token = task.getResult().getToken();
                        String msg = getString(R.string.fcm_token, token);
                        Log.d("TAG", msg);

                        //RequestQueue initialized
                        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

                        //String Request initialized
                        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("TAG","Response :" + response.toString());
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
                                params.put("fcm_token",token);
                                return  params;
                            }
                        };

                        mRequestQueue.add(mStringRequest);




                    }
                });
                /*registration of fcm token ends here*/


        mDemoSlider = findViewById(R.id.slider);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add("http://gracecompusys.com/iis/img/_sliderimages/slide1.png");
        listName.add("Making Your Child’s World Better");

        listUrl.add("http://gracecompusys.com/iis/img/_sliderimages/slide2.png");
        listName.add("One school serving all");

        listUrl.add("http://gracecompusys.com/iis/img/_sliderimages/slide3.png");
        listName.add("A Great Place To Learn");

        listUrl.add("http://gracecompusys.com/iis/img/_sliderimages/slide4.png");
        listName.add("The Future Begins Here!");

        listUrl.add("http://gracecompusys.com/iis/img/_sliderimages/slide5.png");
        listName.add("Learners Today, Leaders Tomorrow");

        listUrl.add("http://gracecompusys.com/iis/img/_sliderimages/slide6.png");
        listName.add("Education – Your Door To The Future");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.stopCyclingWhenTouch(false);


        /*code for sparkle buttons starts here*/
        final SparkButton spark_button1 = findViewById(R.id.spark_button1);
        spark_button1.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                spark_button1.setChecked(false);
                Intent intent = new Intent(MainActivity.this, MyClasses.class);
                startActivity(intent);
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
               MediaPlayer mediaPlayerSparkle= MediaPlayer.create(MainActivity.this, R.raw.sparkle);
               mediaPlayerSparkle.start();
            }
        });

        final SparkButton spark_button2 = findViewById(R.id.spark_button2);
        spark_button2.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                spark_button2.setChecked(false);
                Intent intent = new Intent(MainActivity.this, AllAssignments.class);
                startActivity(intent);
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                MediaPlayer mediaPlayerSparkle= MediaPlayer.create(MainActivity.this, R.raw.sparkle);
                mediaPlayerSparkle.start();
            }
        });

        final SparkButton spark_button3 = findViewById(R.id.spark_button3);
        spark_button3.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                spark_button3.setChecked(false);
                Toast.makeText(MainActivity.this,"Coming soon...",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                MediaPlayer mediaPlayerSparkle= MediaPlayer.create(MainActivity.this, R.raw.sparkle);
                mediaPlayerSparkle.start();
            }
        });

        final SparkButton spark_button4 = findViewById(R.id.spark_button4);
        spark_button4.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                spark_button4.setChecked(false);
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                MediaPlayer mediaPlayerSparkle= MediaPlayer.create(MainActivity.this, R.raw.sparkle);
                mediaPlayerSparkle.start();
            }
        });

        final SparkButton spark_button5 = findViewById(R.id.spark_button5);
        spark_button5.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                spark_button5.setChecked(false);
                Intent intent = new Intent(MainActivity.this, ShowDoubts.class);
                startActivity(intent);
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                MediaPlayer mediaPlayerSparkle= MediaPlayer.create(MainActivity.this, R.raw.sparkle);
                mediaPlayerSparkle.start();
            }
        });

        final SparkButton spark_button6 = findViewById(R.id.spark_button6);
        spark_button6.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
            }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
                spark_button6.setChecked(false);
                Intent intent = new Intent(MainActivity.this, TrackProgress.class);
                startActivity(intent);
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
                MediaPlayer mediaPlayerSparkle= MediaPlayer.create(MainActivity.this, R.raw.sparkle);
                mediaPlayerSparkle.start();
            }
        });

        /*code for sparkle buttons ends here*/
    }



    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
      //  Toast.makeText(this, slider.getBundle().getString("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                editor.putString("Login", "false");
                editor.putString("student_id","NA");
                editor.putString("admission_no","NA");
                editor.putString("student_grade","NA");
                editor.putString("student_name","NA");
                editor.putString("father_name","NA");
                editor.putString("mother_name","NA");
                editor.putString("surname","NA");
                editor.putString("mobile_number1","NA");
                editor.putString("mobile_number2","NA");
                editor.commit();
                Intent i = new Intent(MainActivity.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY  | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

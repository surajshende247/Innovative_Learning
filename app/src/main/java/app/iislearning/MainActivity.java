package app.iislearning;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;

import app.iislearning.askdoubts.ShowDoubts;
import app.iislearning.assignment.AllAssignments;
import app.iislearning.classplayer.LecturePlayer;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,   ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



}
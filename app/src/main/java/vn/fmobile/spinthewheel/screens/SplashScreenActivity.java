package vn.fmobile.spinthewheel.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import vn.fmobile.spinthewheel.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent  intent = new Intent(SplashScreenActivity.this, HomeActivity.class);

//        new Thread(() -> {
//            try {
//                Thread.sleep(3000);
//                startActivity(intent);
//                finish();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

       new Handler(Looper.getMainLooper()).postDelayed(()->{
           startActivity(intent);
           finish();
       },3000);

    }
    private void loadData() {
        findViewById(R.id.layout_loading).setVisibility(View.VISIBLE);
    }

}
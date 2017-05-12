package com.lekai.root.iaddcontacts;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Splashscreen extends AppCompatActivity {
    private int secs = 3000;
    final int MY_PERMISSION_REQUEST_WRITE_CONTACTS = 100;
    Button begin_work;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        begin_work = (Button) findViewById(R.id.start_button);
        begin_work.setBackgroundColor(0);
        begin_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUse();
                finish();
            }
        });
    }

    public void askForContactPermission(Intent in){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(Splashscreen.this, android.Manifest.permission.WRITE_CONTACTS);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Splashscreen.this,new String[]{android.Manifest.permission.WRITE_CONTACTS},MY_PERMISSION_REQUEST_WRITE_CONTACTS);

//                if(ActivityCompat.shouldShowRequestPermissionRationale(Splashscreen.this, android.Manifest.permission.WRITE_CONTACTS));
            }else{
                startActivity(in);
            }
        }
        else{
            startActivity(in);
        }
    }

    public void startUse(){
        askForContactPermission(new Intent(Splashscreen.this,MainActivity.class));

//        else{
//            ActivityCompat.requestPermissions(Splashscreen.this,new String[]{android.Manifest.permission.WRITE_CONTACTS},MY_PERMISSION_REQUEST_WRITE_CONTACTS);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_WRITE_CONTACTS:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Splashscreen.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },secs);
                }
                else {
                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription tDesc = null;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.iadd_contacts_icon);
            tDesc = new ActivityManager.TaskDescription("IAddContacts",bm,getResources().getColor(R.color.colorApp));
            setTaskDescription(tDesc);

        }

    }
}

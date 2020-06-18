package com.projects.photofinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INTERNET = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForPermission();
        Intent startApp = new Intent(getApplicationContext(),SearchPage.class);
        startActivity(startApp);
        finish();
    }

    protected void checkForPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},REQUEST_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Switch case used for future changes
        switch (requestCode) {
            case REQUEST_INTERNET:
            {
                for (int check : grantResults)
                {
                    if (check == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Internet permission granted", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.INTERNET);
                        if (showRationale)
                        {
                            Toast.makeText(this, "Internet permission is Required", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Internet permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

    }
}
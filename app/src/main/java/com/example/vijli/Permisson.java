package com.example.vijli;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Permisson extends AppCompatActivity {
private Button respond;
private static final int REQUEST_LOCATION = 123;
private final Activity context =this;
private  Intent  intent;


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            intent=new Intent(context,Splash.class);
            startActivity(intent);
            context.finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisson);
        intent=new Intent(context,Splash.class);

        respond=findViewById(R.id.response);
        respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRunTimePermission();
            }
        });


    } // oncreate

    public void checkRunTimePermission() {
       //may need to check build version if smaller then marshmellow ..
        // may need to check if permiison is grantedd or not
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permisson granted --
                startActivity(intent);
                context.finish();

            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale( context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Permission Required");
                    dialog.setCancelable(false);
                    dialog.setMessage("You have to Allow permission to access user location");
                    dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
                                                    context.getPackageName(), null));
                            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(i, 1001);
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show(); // If User Checked 'Don't Show Again' checkbox for runtime permission, then navigate user to Settings

                }
                //code for deny
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        switch (requestCode) {
            case 1001:
                // may need to check  if version is lower then marshmellow
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                             intent =new Intent(context,Splash.class);
                             startActivity(intent);
                             context.finish();
                        // permisson granted for -never ask again --
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},10);
                    }

                break;
            default:
                break;
        }
    }
}

package com.example.vijli;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private final Activity context=this;
    private Intent intent;
    private FirebaseUser currentUser;
    private DatabaseReference connectedRef;
    private Boolean connected;
    private ConstraintLayout constraintLayout;
    private ValueEventListener vl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        constraintLayout=findViewById(R.id.coordinatorLayout);

        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        connectedRef  = FirebaseDatabase.getInstance().getReference(".info/connected");


 vl=connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        connected = snapshot.getValue(Boolean.class);


                                if (connected) {
                                    Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                                    if(currentUser == null){

                                        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED){


                                            intent=new Intent(context,Permisson.class);
                                            startActivity(intent);

                                            context.finish();

                                        }else{
                                            intent =new Intent(context, Login.class);
                                            startActivity(intent);
                                            context.finish();
                                        }
                                    }else{
                                        Toast.makeText(context, "Log In", Toast.LENGTH_SHORT).show();
                                        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED){

                                            // permisson Denied.
                                            intent=new Intent(context,Permisson.class);
                                            startActivity(intent);
                                            context.finish();


                                        }else{

                                            //permisson Granted.
                                            intent =new Intent(context,MainScreen.class);
                                            startActivity(intent);
                                            context.finish();
                                        }

                                    }

                                } else {

                                    // no internet
                                }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Something went Wrong", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectedRef.removeEventListener(vl);
    }
}

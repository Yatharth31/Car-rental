package com.carrentalrecord.group2.batch2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity {



    ImageView splashicon_car,sign_in;

    ProgressBar progressBar;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //To adjust the width of the imageview according to the size of the screen

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screen_height = displaymetrics.heightPixels;
        int screen_width = displaymetrics.widthPixels;

        //Screen size adjust code ends here

        //ALl the UI Widgets Declared Here
        splashicon_car = (ImageView) findViewById(R.id.splash_icon);
        sign_in = (ImageView) findViewById(R.id.sign_in);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //End of UI Widgets Declaration


        //Start of UI design Programatically

        splashicon_car.getLayoutParams().height = (int) (screen_width);
        splashicon_car.getLayoutParams().width = (int) (screen_width);

        sign_in.getLayoutParams().height = (int) (screen_height * 0.045);
        sign_in.getLayoutParams().width = (int) (screen_width * 0.8);
        sign_in.setVisibility(View.GONE);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(! connected){

            Toast.makeText(getApplicationContext(),"No Internet Connection! Please Restart The Application.",Toast.LENGTH_SHORT).show();

        }else{


            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            if(account==null){

                sign_in.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Please Register To Continue.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }else{

                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();

                Toast.makeText(getApplicationContext(),"Welcome Back." + personName + ".",Toast.LENGTH_SHORT).show();
                goHomeScreen(personName,personEmail);





            }


            sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }



    }

    void signIn(){

        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(),"Successfully Signed In",Toast.LENGTH_SHORT).show();
            sign_in.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();





                goHomeScreen(personName, personEmail);




            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {

            Toast.makeText(getApplicationContext(),"Failed To Login",Toast.LENGTH_SHORT).show();
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void goHomeScreen(String Name, String Email){
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } {

        }

        if(Email.equals("anuragtekam0@gmail.com") || true) {
            Intent switchActivityIntent = new Intent(this, home.class);
            switchActivityIntent.putExtra(Name, Email);
            startActivity(switchActivityIntent);
            finish();

        }else{

            Toast.makeText(getApplicationContext(),"Invalid User",Toast.LENGTH_SHORT).show();
            signOut();
        }

    }

    private void signOut() {
        gsc.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}
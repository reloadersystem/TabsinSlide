package resembrink.dev.reloader_helpdesk.AuthGoogle;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import resembrink.dev.reloader_helpdesk.AuthFacebook.MainActivityFace;
import resembrink.dev.reloader_helpdesk.MainActivity;
import resembrink.dev.reloader_helpdesk.R;

public class firebaseAuth extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    SignInButton signInButton;

    public static final int SIGN_IN_CODE=777;// authenticacion llamando a google account

    private static final String TAG= "FACELOG";



    private ProgressBar progressBar;


    private LoginButton loginButton; //face


    private CallbackManager callbackManager; //face



    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth);

        callbackManager= CallbackManager.Factory.create(); //face

        loginButton= findViewById(R.id.loginButton); //face
        loginButton.setReadPermissions ("email", "public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {  //face
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(), "cerrar cesión facebook", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getApplicationContext(), "Ocurrio un error  al ingresar " + error, Toast.LENGTH_SHORT).show();
                Log.d(TAG, error.toString());

            }
        });



        contexto=this;

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton= (SignInButton)findViewById(R.id.signButton);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user= firebaseAuth.getCurrentUser();

                if(user != null)
                {
                    //goMainScreen();
                }

            }
        };

        progressBar = findViewById(R.id.progressPB);


    }

    private void goMainActivity() { //face

        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);

        FirebaseUser currentUser= firebaseAuth.getCurrentUser(); //face
        if(currentUser !=null)
        {
            updateUI();

        }

    }

    private void updateUI() { //face
        //Toast.makeText(firebaseAuth.this, "Your Are logged", Toast.LENGTH_LONG).show();
        //String email=firebaseAuth.getCurrentUser().getEmail().toString();
       // Toast.makeText(firebaseAuth.this, email, Toast.LENGTH_LONG).show();


        Intent mainfaceAct= new Intent(getApplicationContext(), MainActivityFace.class);
        startActivity(mainfaceAct);
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if(requestCode==SIGN_IN_CODE)
        {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }*/

        //----------------------------------------------- face

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleSignInResult(GoogleSignInResult result) {

        if(result.isSuccess())
        {
            goMainScreen();

            //firebaseAuthWithGoogle(result.getSignInAccount());
        }else
        {
            Toast.makeText(this, "No se pudo iniciar Sesión", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount signInAccount) {

        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);

        AuthCredential  credential= GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        signInButton.setVisibility(View.VISIBLE);

                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), " No se pudo autenticar con Firebase", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void goMainScreen() {

        Intent intent= new Intent(this, FBeginAuth.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if( firebaseAuthListener != null)
        {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {

        //progressBar.setVisibility(View.VISIBLE);
        //loginButton.setVisibility(View.GONE);

        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(firebaseAuth.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                          // progressBar.setVisibility(View.GONE);
                            //loginButton.setVisibility(View.VISIBLE);



                        }

                        //progressBar.setVisibility(View.VISIBLE);
                        //loginButton.setVisibility(View.GONE);

                        // ...
                    }
                });
    }
}
